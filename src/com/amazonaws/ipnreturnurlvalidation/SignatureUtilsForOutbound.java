/******************************************************************************* 
 *  Copyright 2008-2011 Amazon Technologies, Inc.
 *  Licensed under the Apache License, Version 2.0 (the "License"); 
 *  
 *  You may not use this file except in compliance with the License. 
 *  You may obtain a copy of the License at: http://aws.amazon.com/apache2.0
 *  This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR 
 *  CONDITIONS OF ANY KIND, either express or implied. See the License for the 
 *  specific language governing permissions and limitations under the License.
 * ***************************************************************************** 
 */

package com.amazonaws.ipnreturnurlvalidation;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.Map;
import java.util.TreeMap;

public class SignatureUtilsForOutbound {

    public static final String SIGNATURE_KEYNAME = "signature";
    public static final String SIGNATURE_METHOD_KEYNAME = "signatureMethod";
    public static final String SIGNATURE_VERSION_KEYNAME = "signatureVersion";
    public static final String SIGNATURE_VERSION_2 = "2";
    public static final String RSA_SHA1_ALGORITHM = "SHA1withRSA";
    public static final String CERTIFICATE_URL_KEYNAME = "certificateUrl";

    private static final String EMPTY_STRING = "";

    private static final String UTF_8_Encoding = "UTF-8";

    private static final String FPS_PROD_ENDPOINT = "https://fps.amazonaws.com/";
    private static final String FPS_SANDBOX_ENDPOINT = "https://fps.sandbox.amazonaws.com/";

    private static final String ACTION_PARAM = "?Action=VerifySignature";
    private static final String END_POINT_PARAM = "&UrlEndPoint=";
    private static final String HTTP_PARAMS_PARAM = "&HttpParameters=";
    // current FPS API version, needs updates if new API versions are released
    private static final String VERSION_PARAM_VALUE = "&Version=2010-08-28";

    private static final String SUCCESS_RESPONSE = "<VerificationStatus>Success</VerificationStatus>";

    protected static final String USER_AGENT_STRING = "Amazon FPS 2010-08-28 Java Library 2.1";
    
    /**
     * Your 40 character aws access key. 
     */
    private String awsAccessKey;

    /**
     * Your 40 character aws secret key. Required only for ipn or return url verification signed using signature version1.
     */
    private String awsSecretKey;

    /**
     *  Use this for verifying IPNs or return urls signed using signature version 2.
     */
    public SignatureUtilsForOutbound() {}


    /**
     *  Use this for verifying IPNs or return urls signed using signature version 1.
     */
    public SignatureUtilsForOutbound(String awsAccessKey, String awsSecretKey) {
        this.awsAccessKey = awsAccessKey;
        this.awsSecretKey = awsSecretKey;
    }


    /**
     * Validates the request by checking the integrity of its parameters.
     * @param parameters - all the http parameters sent in IPNs or return urls. 
     * @param urlEndPoint should be the url which recieved this request. 
     * @param httpMethod should be either POST (IPNs) or GET (returnUrl redirections)
     */
    public boolean validateRequest(Map<String, String> parameters, String urlEndPoint, String httpMethod) throws SignatureException  {

        //This is present only in case of signature version 2. If this is not present we assume this is signature version 1. 
        String signatureVersion = parameters.get(SIGNATURE_VERSION_KEYNAME);
        if (SIGNATURE_VERSION_2.equals(signatureVersion)) {
            return validateSignatureV2(parameters, urlEndPoint, httpMethod);
        } else {
            return validateSignatureV1(parameters);
        }
    }


    private boolean validateSignatureV1(Map<String, String> parameters) throws SignatureException {
        
        if (this.awsSecretKey == null) {
            throw new SignatureException("Signature can not be verified without aws secret key.");
        }

        String stringToSign = calculateStringToSignV1(parameters);
        String signature = null;
        if (parameters.containsKey(SIGNATURE_KEYNAME)) {
            signature = parameters.get(SIGNATURE_KEYNAME);
        } else {
            throw new SignatureException("Signature is not present in input parameters.");
        }

        String result;
        try {
            SecretKeySpec signingKey = new SecretKeySpec(this.awsSecretKey.getBytes(), "HmacSHA1");
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(signingKey);
            byte[] rawHmac = mac.doFinal(stringToSign.getBytes("UTF-8"));
            result = new String(Base64.encodeBase64(rawHmac));
        } catch (NoSuchAlgorithmException e) {
            throw new SignatureException("Failed to generate HMAC : " + e.getMessage());
        } catch (InvalidKeyException e) {
            throw new SignatureException("Failed to generate HMAC : " + e.getMessage());
        } catch (UnsupportedEncodingException e) {
            throw new SignatureException("Failed to generate HMAC : " + e.getMessage());
        }

        return result.equals(signature);
    }

    /**
     * Verifies the signature .
     */
    private boolean validateSignatureV2(Map<String, String> parameters, 
            String urlEndPoint, String httpMethod) throws SignatureException {

        // 1. input validation.
        if (parameters == null || parameters.size() == 0) {
            throw new SignatureException("must provide http parameters");
        }
        
        String signature = parameters.get(SIGNATURE_KEYNAME);
        if (signature == null || EMPTY_STRING.equals(signature)) {
            throw new SignatureException(SIGNATURE_KEYNAME
                    + " is missing from the parameters.");
        }

        String signatureVersion = parameters.get(SIGNATURE_VERSION_KEYNAME);
        if (signatureVersion == null || EMPTY_STRING.equals(signatureVersion)) {
            throw new SignatureException(SIGNATURE_VERSION_KEYNAME
                    + " is missing from the parameters.");
        }

        String signatureMethod = parameters.get(SIGNATURE_METHOD_KEYNAME);
        if (signatureMethod == null || EMPTY_STRING.equals(signatureMethod)) {
            throw new SignatureException(SIGNATURE_METHOD_KEYNAME
                    + " is missing from the parameters.");
        }

        String certificateUrl = parameters.get(CERTIFICATE_URL_KEYNAME);
        if (certificateUrl == null || EMPTY_STRING.equals(certificateUrl)) {
            throw new SignatureException(CERTIFICATE_URL_KEYNAME
                    + " is missing from the parameters.");
        }

        if (urlEndPoint == null || EMPTY_STRING.equals(urlEndPoint)) {
            throw new SignatureException("urlEndPoint must be specified");
        }

        StringBuilder requestUrlBuilder = new StringBuilder();

        // 2. determine production or sandbox endpoint
        if (certificateUrl.startsWith(FPS_PROD_ENDPOINT)) {
            requestUrlBuilder.append(FPS_PROD_ENDPOINT);
        } else if (certificateUrl.startsWith(FPS_SANDBOX_ENDPOINT)) {
            requestUrlBuilder.append(FPS_SANDBOX_ENDPOINT);
        } else {
            throw new SignatureException("certificate url was incorrect");
        }

        // 3. build VerifySignature request URL
        requestUrlBuilder.append(ACTION_PARAM);
        requestUrlBuilder.append(END_POINT_PARAM);
        requestUrlBuilder.append(urlEndPoint);
        requestUrlBuilder.append(HTTP_PARAMS_PARAM);
        requestUrlBuilder.append(buildHttpParams(parameters));
        requestUrlBuilder.append(VERSION_PARAM_VALUE);

        // 4. make call to VerifySignature API
        String requestUrl = requestUrlBuilder.toString();
        String verifySignatureResponse = null;
        try {
            verifySignatureResponse = URLReader.getUrlContents(requestUrl);
        } catch (IOException e) {
            throw new SignatureException("call to VerifySignature API failed", e);
        }
        
        return verifySignatureResponse.contains(SUCCESS_RESPONSE);
    }

    private String buildHttpParams(Map<String, String> httpParams) {
        StringBuilder paramsBuilder = new StringBuilder();
        boolean first = true;
        for (String key : httpParams.keySet()) {
            if (!first) {
                paramsBuilder.append("&");
            } else {
                first = false;
            }
            paramsBuilder.append(urlEncode(key));
            paramsBuilder.append("=");
            paramsBuilder.append(urlEncode(httpParams.get(key)));
        }
        return urlEncode(paramsBuilder.toString());
    }

    private String urlEncode(String value) {
        String encoded = null;
        try {
            encoded = URLEncoder.encode(value, UTF_8_Encoding);
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex);
        }
        return encoded;
    }

    /**
     * Calculate String to Sign for SignatureVersion 1
     * 
     * @param parameters
     *            request parameters
     * @return String to Sign
     */
    private String calculateStringToSignV1(Map<String, String> parameters) {
        StringBuilder data = new StringBuilder();
        Map<String, String> sorted = new TreeMap<String, String>(String.CASE_INSENSITIVE_ORDER);
        sorted.putAll(parameters);
        for (Map.Entry<String, String> entry : sorted.entrySet()) {
            if (entry.getKey().equalsIgnoreCase(SIGNATURE_KEYNAME)) continue;
            data.append(entry.getKey());
            data.append(entry.getValue());
        }
        return data.toString();
    }
}


/**
 * Helps read content from a url. Maximum string length that can be read is 1MB.
 */

/**
 * Helps read content from a url. Maximum string length that can be read is 1MB.
 */
class URLReader {

    private static final int READ_THRESHOLD = 1024 * 1024; // 1MB
    private static final String NewLine = "\n";

    public static String getUrlContents(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection.setFollowRedirects(false);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setRequestProperty("User-Agent", SignatureUtilsForOutbound.USER_AGENT_STRING);
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            StringBuilder urlContents = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                urlContents.append(inputLine);
                urlContents.append(NewLine);
                if (urlContents.length() >= READ_THRESHOLD) {
                    throw new IOException(
                            "Size of the contents at the given url [" + url
                                    + "] exceeds threshold of ["
                                    + READ_THRESHOLD + "]");
                }
            }
            return urlContents.toString();
        } finally {
            if (in != null)
                try {
                    in.close();
                } catch (Exception ignore) {
                }
        }
    }
}

