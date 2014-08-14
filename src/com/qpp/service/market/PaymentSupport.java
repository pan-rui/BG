package com.qpp.service.market;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NoHttpResponseException;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import java.io.*;
import java.net.ConnectException;
import java.net.URLDecoder;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by qpp on 8/12/2014.
 */
public class PaymentSupport {
    private static HttpClient httpClient;
static{
    httpClient=initHttpClient();
}
    public static HttpClient initHttpClient(){
        HttpParams httpParams=new BasicHttpParams();
        httpParams.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 50000);
        httpParams.setParameter(CoreConnectionPNames.STALE_CONNECTION_CHECK, true);
        httpParams.setParameter(CoreConnectionPNames.TCP_NODELAY, true);
        HttpRequestRetryHandler retryHandler=new HttpRequestRetryHandler() {
            @Override
            public boolean retryRequest(IOException e, int i, HttpContext httpContext) {
                if(i>3)
                    return false;
                if(e instanceof NoHttpResponseException)
                    return true;
                if(e instanceof UnknownHostException)
                    return false;
                if(e instanceof InterruptedIOException)
                    return false;
                if(e instanceof ConnectException)
                    return false;
                if(e instanceof SSLException)
                    return false;
                Boolean isRequestSent = new Boolean((String) httpContext.getAttribute(ExecutionContext.HTTP_REQ_SENT));
                if(!isRequestSent)
                    return true;
                return false;
            }
        };
        SSLSocketFactory sf = null;
        try {
            sf=new SSLSocketFactory(SSLContext.getDefault(),SSLSocketFactory.STRICT_HOSTNAME_VERIFIER);
        } catch (NoSuchAlgorithmException e) {
//            logger.warn("SSL Connection could not be Initialized,Default SSLContext");
            System.err.println("SSL Connection could not be Initialized,Default SSLContext");
            e.printStackTrace();
        }
        Scheme clientConnectScheme = new Scheme("https", 443,sf);
        SchemeRegistry registry = new SchemeRegistry();
        registry.register(clientConnectScheme);



        ClientConnectionManager cm = new PoolingClientConnectionManager(registry, 50000L, TimeUnit.DAYS.MILLISECONDS);
        ((PoolingClientConnectionManager) cm).setMaxTotal(30);
        ((PoolingClientConnectionManager) cm).setDefaultMaxPerRoute(30);
        HttpClient httpClient = new DefaultHttpClient(cm, httpParams);
        ((DefaultHttpClient)httpClient).setHttpRequestRetryHandler(retryHandler);
//        ResponseHandler<String> responseHandler=new ResponseHandler<String>() {
//            @Override
//            public String handleResponse(HttpResponse httpResponse) throws ClientProtocolException, IOException {
////                String respAsString="";
//                InputStream in=httpResponse.getEntity().getContent();
//                 Reader reader = new InputStreamReader(in, "UTF-8");
//                 StringBuilder sb = new StringBuilder();
//             try {
//                 char[] c = new char[1024];
//                 int len;
//                 while ((len = reader.read(c)) != -1)
//                     sb.append(c, 0, len);
//             }finally {
//                 in.close();
//                 reader.close();
//             }
//                return sb.toString();
//            }
//        };
        return httpClient;
    }

    public static Map<String, String> execute(Map<String, String> reqMap,PaymentConfig config) {
        String responseBody=null;
        HttpPost method = new HttpPost(config.getEndPoint());
        int status=-1;
        method.addHeader("Content-Type", "application/x-www-form-urlencoded; charset="+config.getCharSet().toLowerCase());
        //添加请求信息
        List<NameValuePair> paramList = new ArrayList<NameValuePair>();
        for (Map.Entry<String, String> entry : reqMap.entrySet())
            paramList.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        boolean retry=true;
        do {
            try {
                method.setEntity(new UrlEncodedFormEntity(paramList));
                HttpResponse httpResponse = httpClient.execute(method);
                responseBody = getResponseAsString(httpResponse.getEntity().getContent(),config.getCharSet());
                status = httpResponse.getStatusLine().getStatusCode();
                if (status == HttpStatus.SC_OK) {
                    retry = false;
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }while (retry);
            return decode(responseBody,config.getCharSet());
    }
//执行请求....
    public static final synchronized Map<String,String> decode(String Payload,String charCode) {
        Map<String, String> nvpMap = new HashMap<String, String>();
        try {
            StringTokenizer stTok = new StringTokenizer(Payload, "&");
            while (stTok.hasMoreTokens()) {
                StringTokenizer stInternalTokenizer = new StringTokenizer(stTok.nextToken(), "=");
                if (stInternalTokenizer.countTokens() == 2) {
                    nvpMap.put(URLDecoder.decode(stInternalTokenizer.nextToken(), charCode),
                            URLDecoder.decode(stInternalTokenizer.nextToken(), charCode));
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return nvpMap;
    }

    public static String getResponseAsString(InputStream inputStream,String charset) {
            StringBuilder sb = new StringBuilder();
        try {
            Reader reader = new InputStreamReader(inputStream, charset);
            char[] chars = new char[2048];
            int len=0;
            while ((len=reader.read(chars))!=-1)
                sb.append(chars, 0,len);
            return sb.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try{ inputStream.close();}catch(Exception e){};
        }
        return sb.toString();
    }
}
