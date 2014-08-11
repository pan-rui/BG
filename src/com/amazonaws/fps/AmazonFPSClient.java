/******************************************************************************* 
 *  Copyright 2008-2011 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *  Licensed under the Apache License, Version 2.0 (the "License"); 
 *  
 *  You may not use this file except in compliance with the License. 
 *  You may obtain a copy of the License at: http://aws.amazon.com/apache2.0
 *  This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR 
 *  CONDITIONS OF ANY KIND, either express or implied. See the License for the 
 *  specific language governing permissions and limitations under the License.
 * ***************************************************************************** 
 *    __  _    _  ___ 
 *   (  )( \/\/ )/ __)
 *   /__\ \    / \__ \
 *  (_)(_) \/\/  (___/
 * 
 *  Amazon FPS Java Library
 *  API Version: 2010-08-28
 * 
 */

package com.amazonaws.fps;

import com.amazonaws.fps.model.*;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.*;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.net.*;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 
 * Amazon Flexible Payments Service
 * 
 *
 *
 * AmazonFPSClient is implementation of AmazonFPS based on the
 * Apache <a href="http://jakarta.apache.org/commons/httpclient/">HttpClient</a>.
 *
 */
public  class AmazonFPSClient implements AmazonFPS {

    private final Log log = LogFactory.getLog(AmazonFPSClient.class);

    private String awsAccessKeyId = null;
    private String awsSecretAccessKey = null;
    private AmazonFPSConfig config = null;
    private static JAXBContext  jaxbContext;
    private HttpClient httpClient = null;
    private static ThreadLocal<Unmarshaller> unmarshaller;
    private static Pattern ERROR_PATTERN_ONE = Pattern.compile(".*\\<RequestId>(.*)\\</RequestId>.*\\<Error>" +
            "\\<Code>(.*)\\</Code>\\<Message>(.*)\\</Message>\\</Error>.*(\\<Error>)?.*",
            Pattern.MULTILINE | Pattern.DOTALL);
    private static Pattern ERROR_PATTERN_TWO = Pattern.compile(".*\\<Error>\\<Code>(.*)\\</Code>\\<Message>(.*)" +
            "\\</Message>\\</Error>.*(\\<Error>)?.*\\<RequestID>(.*)\\</RequestID>.*",
            Pattern.MULTILINE | Pattern.DOTALL);
    private static String DEFAULT_ENCODING = "UTF-8";
    /** Initialize JAXBContext and  Unmarshaller **/
    static {
        try {
            jaxbContext = JAXBContext.newInstance("com.amazonaws.fps.model", AmazonFPS.class.getClassLoader());
        } catch (JAXBException ex) {
            throw new ExceptionInInitializerError(ex);
        }
        unmarshaller = new ThreadLocal<Unmarshaller>() {
            @Override
            protected synchronized Unmarshaller initialValue() {
                try {
                    return jaxbContext.createUnmarshaller();
                } catch(JAXBException e) {
                    throw new ExceptionInInitializerError(e);
                }
            }
        };
    }


    /**
     * Constructs AmazonFPSClient with AWS Access Key ID and AWS Secret Key
     *
     * @param awsAccessKeyId
     *          AWS Access Key ID
     * @param awsSecretAccessKey
     *          AWS Secret Access Key
     * @throws AmazonFPSException
     */
    public  AmazonFPSClient(String awsAccessKeyId,String awsSecretAccessKey) throws AmazonFPSException {
        this (awsAccessKeyId, awsSecretAccessKey, new AmazonFPSConfig());
    }



    /**
     * Constructs AmazonFPSClient with AWS Access Key ID, AWS Secret Key
     * and AmazonFPSConfig. Use AmazonFPSConfig to pass additional
     * configuration that affects how service is being called.
     *
     * @param awsAccessKeyId
     *          AWS Access Key ID
     * @param awsSecretAccessKey
     *          AWS Secret Access Key
     * @param config
     *          Additional configuration options
     * @throws AmazonFPSException
     */
    public  AmazonFPSClient(String awsAccessKeyId, String awsSecretAccessKey,
            AmazonFPSConfig config) throws AmazonFPSException {
        this.awsAccessKeyId = awsAccessKeyId;
        this.awsSecretAccessKey = awsSecretAccessKey;
        this.config = config;
        this.httpClient = configureHttpClient();


    }

    // Public API ------------------------------------------------------------//



    /**
     * Cancel Token
     *
     *
     * Cancels any token installed by the calling application on its own account.
     *
     * @param request
     *          CancelTokenRequest request
     * @return
     *          CancelToken Response from the service
     *
     * @throws AmazonFPSException
     */
    public CancelTokenResponse cancelToken(CancelTokenRequest request) throws AmazonFPSException {
        return invoke(CancelTokenResponse.class, convertCancelToken(request));
    }


    /**
     * Cancel
     *
     *
     * Cancels an ongoing transaction and puts it in cancelled state.
     *
     * @param request
     *          CancelRequest request
     * @return
     *          Cancel Response from the service
     *
     * @throws AmazonFPSException
     */
    public CancelResponse cancel(CancelRequest request) throws AmazonFPSException {
        return invoke(CancelResponse.class, convertCancel(request));
    }


    /**
     * Fund Prepaid
     *
     *
     * Funds the prepaid balance on the given prepaid instrument.
     *
     * @param request
     *          FundPrepaidRequest request
     * @return
     *          FundPrepaid Response from the service
     *
     * @throws AmazonFPSException
     */
    public FundPrepaidResponse fundPrepaid(FundPrepaidRequest request) throws AmazonFPSException {
        return invoke(FundPrepaidResponse.class, convertFundPrepaid(request));
    }


    /**
     * Get Account Activity
     *
     *
     * Returns transactions for a given date range.
     *
     * @param request
     *          GetAccountActivityRequest request
     * @return
     *          GetAccountActivity Response from the service
     *
     * @throws AmazonFPSException
     */
    public GetAccountActivityResponse getAccountActivity(GetAccountActivityRequest request) throws AmazonFPSException {
        return invoke(GetAccountActivityResponse.class, convertGetAccountActivity(request));
    }


    /**
     * Get Account Balance
     *
     *
     * Returns the account balance for an account in real time.
     *
     * @param request
     *          GetAccountBalanceRequest request
     * @return
     *          GetAccountBalance Response from the service
     *
     * @throws AmazonFPSException
     */
    public GetAccountBalanceResponse getAccountBalance(GetAccountBalanceRequest request) throws AmazonFPSException {
        return invoke(GetAccountBalanceResponse.class, convertGetAccountBalance(request));
    }


    /**
     * Get Transactions For Subscription
     *
     * Returns the transactions for a given subscriptionID.
     *
     * @param request
     *          GetTransactionsForSubscriptionRequest request
     * @return
     *          GetTransactionsForSubscription Response from the service
     *
     * @throws AmazonFPSException
     */
    public GetTransactionsForSubscriptionResponse getTransactionsForSubscription(GetTransactionsForSubscriptionRequest request) throws AmazonFPSException {
        return invoke(GetTransactionsForSubscriptionResponse.class, convertGetTransactionsForSubscription(request));
    }


    /**
     * Get Subscription Details
     *
     * Returns the details of Subscription for a given subscriptionID.
     *
     * @param request
     *          GetSubscriptionDetailsRequest request
     * @return
     *          GetSubscriptionDetails Response from the service
     *
     * @throws AmazonFPSException
     */
    public GetSubscriptionDetailsResponse getSubscriptionDetails(GetSubscriptionDetailsRequest request) throws AmazonFPSException {
        return invoke(GetSubscriptionDetailsResponse.class, convertGetSubscriptionDetails(request));
    }


    /**
     * Get Debt Balance
     *
     *
     * Returns the balance corresponding to the given credit instrument.
     *
     * @param request
     *          GetDebtBalanceRequest request
     * @return
     *          GetDebtBalance Response from the service
     *
     * @throws AmazonFPSException
     */
    public GetDebtBalanceResponse getDebtBalance(GetDebtBalanceRequest request) throws AmazonFPSException {
        return invoke(GetDebtBalanceResponse.class, convertGetDebtBalance(request));
    }


    /**
     * Get Outstanding Debt Balance
     *
     *
     * Returns the total outstanding balance for all the credit instruments for the given creditor account.
     *
     * @param request
     *          GetOutstandingDebtBalanceRequest request
     * @return
     *          GetOutstandingDebtBalance Response from the service
     *
     * @throws AmazonFPSException
     */
    public GetOutstandingDebtBalanceResponse getOutstandingDebtBalance(GetOutstandingDebtBalanceRequest request) throws AmazonFPSException {
        return invoke(GetOutstandingDebtBalanceResponse.class, convertGetOutstandingDebtBalance(request));
    }


    /**
     * Get Prepaid Balance
     *
     *
     * Returns the balance available on the given prepaid instrument.
     *
     * @param request
     *          GetPrepaidBalanceRequest request
     * @return
     *          GetPrepaidBalance Response from the service
     *
     * @throws AmazonFPSException
     */
    public GetPrepaidBalanceResponse getPrepaidBalance(GetPrepaidBalanceRequest request) throws AmazonFPSException {
        return invoke(GetPrepaidBalanceResponse.class, convertGetPrepaidBalance(request));
    }


    /**
     * Get Token By Caller
     *
     *
     * Returns the details of a particular token installed by this calling application using the subway co-branded UI.
     *
     * @param request
     *          GetTokenByCallerRequest request
     * @return
     *          GetTokenByCaller Response from the service
     *
     * @throws AmazonFPSException
     */
    public GetTokenByCallerResponse getTokenByCaller(GetTokenByCallerRequest request) throws AmazonFPSException {
        return invoke(GetTokenByCallerResponse.class, convertGetTokenByCaller(request));
    }


    /**
     * Cancel Subscription And Refund
     *
     *
     * Cancels a subscription.
     *
     * @param request
     *          CancelSubscriptionAndRefundRequest request
     * @return
     *          CancelSubscriptionAndRefund Response from the service
     *
     * @throws AmazonFPSException
     */
    public CancelSubscriptionAndRefundResponse cancelSubscriptionAndRefund(CancelSubscriptionAndRefundRequest request) throws AmazonFPSException {
        return invoke(CancelSubscriptionAndRefundResponse.class, convertCancelSubscriptionAndRefund(request));
    }


    /**
     * Get Token Usage
     *
     *
     * Returns the usage of a token.
     *
     * @param request
     *          GetTokenUsageRequest request
     * @return
     *          GetTokenUsage Response from the service
     *
     * @throws AmazonFPSException
     */
    public GetTokenUsageResponse getTokenUsage(GetTokenUsageRequest request) throws AmazonFPSException {
        return invoke(GetTokenUsageResponse.class, convertGetTokenUsage(request));
    }


    /**
     * Get Tokens
     *
     *
     * Returns a list of tokens installed on the given account.
     *
     * @param request
     *          GetTokensRequest request
     * @return
     *          GetTokens Response from the service
     *
     * @throws AmazonFPSException
     */
    public GetTokensResponse getTokens(GetTokensRequest request) throws AmazonFPSException {
        return invoke(GetTokensResponse.class, convertGetTokens(request));
    }


    /**
     * Get Total Prepaid Liability
     *
     *
     * Returns the total liability held by the given account corresponding to all the prepaid instruments owned by the account.
     *
     * @param request
     *          GetTotalPrepaidLiabilityRequest request
     * @return
     *          GetTotalPrepaidLiability Response from the service
     *
     * @throws AmazonFPSException
     */
    public GetTotalPrepaidLiabilityResponse getTotalPrepaidLiability(GetTotalPrepaidLiabilityRequest request) throws AmazonFPSException {
        return invoke(GetTotalPrepaidLiabilityResponse.class, convertGetTotalPrepaidLiability(request));
    }


    /**
     * Get Transaction
     *
     *
     * Returns all details of a transaction.
     *
     * @param request
     *          GetTransactionRequest request
     * @return
     *          GetTransaction Response from the service
     *
     * @throws AmazonFPSException
     */
    public GetTransactionResponse getTransaction(GetTransactionRequest request) throws AmazonFPSException {
        return invoke(GetTransactionResponse.class, convertGetTransaction(request));
    }


    /**
     * Get Transaction Status
     *
     *
     * Gets the latest status of a transaction.
     *
     * @param request
     *          GetTransactionStatusRequest request
     * @return
     *          GetTransactionStatus Response from the service
     *
     * @throws AmazonFPSException
     */
    public GetTransactionStatusResponse getTransactionStatus(GetTransactionStatusRequest request) throws AmazonFPSException {
        return invoke(GetTransactionStatusResponse.class, convertGetTransactionStatus(request));
    }


    /**
     * Get Payment Instruction
     *
     *
     * Gets the payment instruction of a token.
     *
     * @param request
     *          GetPaymentInstructionRequest request
     * @return
     *          GetPaymentInstruction Response from the service
     *
     * @throws AmazonFPSException
     */
    public GetPaymentInstructionResponse getPaymentInstruction(GetPaymentInstructionRequest request) throws AmazonFPSException {
        return invoke(GetPaymentInstructionResponse.class, convertGetPaymentInstruction(request));
    }


    /**
     * Install Payment Instruction
     *
     * Installs a payment instruction for caller.
     *
     * @param request
     *          InstallPaymentInstructionRequest request
     * @return
     *          InstallPaymentInstruction Response from the service
     *
     * @throws AmazonFPSException
     */
    public InstallPaymentInstructionResponse installPaymentInstruction(InstallPaymentInstructionRequest request) throws AmazonFPSException {
        return invoke(InstallPaymentInstructionResponse.class, convertInstallPaymentInstruction(request));
    }


    /**
     * Pay
     *
     *
     * Allows calling applications to move money from a sender to a recipient.
     *
     * @param request
     *          PayRequest request
     * @return
     *          Pay Response from the service
     *
     * @throws AmazonFPSException
     */
    public PayResponse pay(PayRequest request) throws AmazonFPSException {
        return invoke(PayResponse.class, convertPay(request));
    }


    /**
     * Refund
     *
     *
     * Refunds a previously completed transaction.
     *
     * @param request
     *          RefundRequest request
     * @return
     *          Refund Response from the service
     *
     * @throws AmazonFPSException
     */
    public RefundResponse refund(RefundRequest request) throws AmazonFPSException {
        return invoke(RefundResponse.class, convertRefund(request));
    }


    /**
     * Reserve
     *
     *
     * Reserve API is part of the Reserve and Settle API conjunction that serve the purpose of a pay where the authorization and settlement have a timing 				difference.
     *
     * @param request
     *          ReserveRequest request
     * @return
     *          Reserve Response from the service
     *
     * @throws AmazonFPSException
     */
    public ReserveResponse reserve(ReserveRequest request) throws AmazonFPSException {
        return invoke(ReserveResponse.class, convertReserve(request));
    }


    /**
     * Settle
     *
     *
     * The Settle API is used in conjunction with the Reserve API and is used to settle previously reserved transaction.
     *
     * @param request
     *          SettleRequest request
     * @return
     *          Settle Response from the service
     *
     * @throws AmazonFPSException
     */
    public SettleResponse settle(SettleRequest request) throws AmazonFPSException {
        return invoke(SettleResponse.class, convertSettle(request));
    }


    /**
     * Settle Debt
     *
     *
     * Allows a caller to initiate a transaction that atomically transfers money from a senders payment instrument to the recipient, while decreasing corresponding 				debt balance.
     *
     * @param request
     *          SettleDebtRequest request
     * @return
     *          SettleDebt Response from the service
     *
     * @throws AmazonFPSException
     */
    public SettleDebtResponse settleDebt(SettleDebtRequest request) throws AmazonFPSException {
        return invoke(SettleDebtResponse.class, convertSettleDebt(request));
    }


    /**
     * Write Off Debt
     *
     *
     * Allows a creditor to write off the debt balance accumulated partially or fully at any time.
     *
     * @param request
     *          WriteOffDebtRequest request
     * @return
     *          WriteOffDebt Response from the service
     *
     * @throws AmazonFPSException
     */
    public WriteOffDebtResponse writeOffDebt(WriteOffDebtRequest request) throws AmazonFPSException {
        return invoke(WriteOffDebtResponse.class, convertWriteOffDebt(request));
    }


    /**
     * Get Recipient Verification Status
     *
     *
     * Returns the recipient status.
     *
     * @param request
     *          GetRecipientVerificationStatusRequest request
     * @return
     *          GetRecipientVerificationStatus Response from the service
     *
     * @throws AmazonFPSException
     */
    public GetRecipientVerificationStatusResponse getRecipientVerificationStatus(GetRecipientVerificationStatusRequest request) throws AmazonFPSException {
        return invoke(GetRecipientVerificationStatusResponse.class, convertGetRecipientVerificationStatus(request));
    }


    /**
     * Verify Signature
     *
     *
     * Verify the signature that FPS sent in IPN or callback urls.
     *
     * @param request
     *          VerifySignatureRequest request
     * @return
     *          VerifySignature Response from the service
     *
     * @throws AmazonFPSException
     */
    public VerifySignatureResponse verifySignature(VerifySignatureRequest request) throws AmazonFPSException {
        return invoke(VerifySignatureResponse.class, convertVerifySignature(request));
    }



    // Private API ------------------------------------------------------------//

    /**
     * Configure HttpClient with set of defaults as well as configuration
     * from AmazonFPSConfig instance
     * @throws AmazonFPSException
     *
     */
    private HttpClient configureHttpClient() throws AmazonFPSException {

        /* Set http client parameters */
        HttpParams httpParams = new BasicHttpParams();
        httpParams.setParameter(CoreProtocolPNames.USER_AGENT, config.getUserAgent());
        httpParams.setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 50000);
        httpParams.setIntParameter(CoreConnectionPNames.SO_TIMEOUT, 50000);
        httpParams.setBooleanParameter(CoreConnectionPNames.STALE_CONNECTION_CHECK, true);
        httpParams.setBooleanParameter(CoreConnectionPNames.TCP_NODELAY, true);
        /* setting Retry manager */
        HttpRequestRetryHandler retryHandler = new HttpRequestRetryHandler() {

            @Override
            public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
            	System.out.println(exception.getMessage());
            	if (executionCount > 3) {
                  log.debug("Maximum Number of Retry attempts reached, will not retry");
                  return false;
              }
              log.debug("Retrying request. Attempt " + executionCount);
              if (exception instanceof NoHttpResponseException) {
                  log.debug("Retrying on NoHttpResponseException");
                  return true;
              }
              if (exception instanceof InterruptedIOException) {
                  log.debug("Will not retry on InterruptedIOException", exception);
                  return false;
              }
              if (exception instanceof UnknownHostException) {
                  log.debug("Will not retry on UnknownHostException", exception);
                  return false;
              }
              if (exception instanceof ConnectException) {
                  // Connection refused
                  log.debug("Will not retry on ConnectException", exception);
                  return false;
              }
              if (exception instanceof SSLException) {
                  log.debug("Will not retry on SSLException", exception);
                  return false;
              }
              Boolean isRequestSent = new Boolean((String)context.getAttribute(ExecutionContext.HTTP_REQ_SENT));
              if (!isRequestSent) {
                  log.debug("Retrying on failed sent request");
                  return true;
              }
              return false;
            }
          };

          /* Set connection manager */
        SSLSocketFactory sf;
		try {
			sf = new SSLSocketFactory(SSLContext.getDefault(), SSLSocketFactory.STRICT_HOSTNAME_VERIFIER);
		} catch (NoSuchAlgorithmException e) {
			log.debug ("Caught Exception", e);
            log.debug("SSL Connection could not be Initialized, Default SSLContext");
			throw new AmazonFPSException("Client could not be Initialized");
		}

        Scheme clientConnectionScheme = new Scheme("https", 443, sf);

        SchemeRegistry registry = new SchemeRegistry();
        registry.register(clientConnectionScheme);

        ClientConnectionManager cm = new PoolingClientConnectionManager(registry, 50000L, TimeUnit.MILLISECONDS);
        ((PoolingClientConnectionManager)cm).setMaxTotal(config.getMaxConnections());
        ((PoolingClientConnectionManager)cm).setDefaultMaxPerRoute(config.getMaxConnections());

        /* Set http client */
        httpClient = new DefaultHttpClient(cm,httpParams);
        ((DefaultHttpClient)httpClient).setHttpRequestRetryHandler(retryHandler);

        /* Set proxy if configured */
        if (config.isSetProxyHost() && config.isSetProxyPort()) {
            log.info("Configuring Proxy. Proxy Host: " + config.getProxyHost() +
                    "Proxy Port: " + config.getProxyPort() );

             /**
             * Only HTTP proxies are allowed
             */
            cm.getSchemeRegistry().register(new Scheme("http",config.getProxyPort(),new PlainSocketFactory()));

            if (config.isSetProxyUsername() &&   config.isSetProxyPassword()) {
            ((DefaultHttpClient)httpClient).getCredentialsProvider().setCredentials(
            		new AuthScope(config.getProxyHost(),config.getProxyPort()),
            		new UsernamePasswordCredentials(config.getProxyUsername(),config.getProxyPassword()));

            }
            HttpHost proxy = new HttpHost(config.getProxyHost(), config.getProxyPort());
            httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);

        }

        return httpClient;
    }



    /**
     * Invokes request using parameters from parameters map.
     * Returns response of the T type passed to this method
     */
    private <T> T invoke(Class<T> clazz, Map<String, String> parameters)
            throws AmazonFPSException {

        String actionName = parameters.get("Action");
        T response = null;
        String responseBodyString = null;
        HttpPost method = new HttpPost(config.getServiceURL());
        int status = -1;

        System.out.println("URL " + config.getServiceURL());
        log.debug("URL" + config.getServiceURL());
        System.out.println("Invoking" + actionName + " request. Current parameters: " + parameters);

        try {

            /* Set content type and encoding */
            log.debug("Setting content-type to application/x-www-form-urlencoded; charset=" + DEFAULT_ENCODING.toLowerCase());
            method.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=" + DEFAULT_ENCODING.toLowerCase());

            /* Add required request parameters and set request body */
            log.debug("Adding required parameters...");
            addRequiredParametersToRequest(method, parameters);
            log.debug("Done adding additional required parameteres. Parameters now: " + parameters);

            boolean shouldRetry = true;
            int retries = 0;
            do {
                log.debug("Sending Request to host:  " + config.getServiceURL());

                try {
                    /* Submit request */
                    HttpResponse httpResponse = httpClient.execute(method);

                    /* Consume response stream */
                    responseBodyString = getResponsBodyAsString(httpResponse.getEntity().getContent());
                    status = httpResponse.getStatusLine().getStatusCode();
                    /* Successful response. Attempting to unmarshal into the <Action>Response type */

                    if (status == HttpStatus.SC_OK) {
                        shouldRetry = false;
                        log.debug("Received Response. Status: " + status + ". " +
                                "Response Body: " + responseBodyString);
                        log.debug("Attempting to unmarshal into the " + actionName + "Response type...");
                        response = clazz.cast(getUnmarshaller().unmarshal(new StreamSource(new StringReader(responseBodyString))));

                        log.debug("Unmarshalled response into " + actionName + "Response type.");

                    } else { /* Unsucessful response. Attempting to unmarshall into ErrorResponse  type */

                        log.debug("Received Response. Status: " + status + ". " +
                                "Response Body: " + responseBodyString);

                        if ((status == HttpStatus.SC_INTERNAL_SERVER_ERROR
                            || status == HttpStatus.SC_SERVICE_UNAVAILABLE)
                            && pauseIfRetryNeeded(++retries)){
                            shouldRetry = true;
                        } else {
                            log.debug("Attempting to unmarshal into the ErrorResponse type...");
                            ErrorResponse errorResponse = (ErrorResponse) getUnmarshaller().unmarshal(new StreamSource(new StringReader(responseBodyString)));

                            log.debug("Unmarshalled response into the ErrorResponse type.");

                            com.amazonaws.fps.model.Error error = errorResponse.getError().get(0);

                                    throw new AmazonFPSException(error.getMessage(),
                                    status,
                                    error.getCode(),
                                    error.getType(),
                                    errorResponse.getRequestId(),
                                    errorResponse.toXML());
                        }
                    }
                } catch (JAXBException je) {
                    /* Response cannot be unmarshalled neither as <Action>Response or ErrorResponse types.
                    Checking for other possible errors. */

                    log.debug ("Caught JAXBException", je);
                    log.debug("Response cannot be unmarshalled neither as " + actionName + "Response or ErrorResponse types." +
                            "Checking for other possible errors.");

                    AmazonFPSException awse = processErrors(responseBodyString, status);

                    throw awse;

                } catch (IOException ioe) {
                    log.debug("Caught IOException exception", ioe);
                    throw new AmazonFPSException("Internal Error", ioe);
                } catch (Exception e) {
                    log.debug("Caught Exception", e);
                    throw new AmazonFPSException(e);
                } finally {
                    method.releaseConnection();
                }
            } while (shouldRetry);

        } catch (AmazonFPSException se) {
            log.debug("Caught AmazonFPSException", se);
            throw se;

        } catch (Throwable t) {
            log.debug("Caught Exception", t);
            throw new AmazonFPSException(t);
        }
        return response;
    }

    /**
     * Read stream into string
     * @param input stream to read
     */
    private String getResponsBodyAsString(InputStream input) throws IOException {
        String responsBodyString = null;
        try {
            Reader reader = new InputStreamReader(input, DEFAULT_ENCODING);
            StringBuilder b = new StringBuilder();
            char[] c = new char[1024];
            int len;
            while (0 < (len = reader.read(c))) {
                b.append(c, 0, len);
            }
            responsBodyString = b.toString();
        } finally {
            input.close();
        }
        return responsBodyString;
    }

    /**
     * Exponential sleep on failed request. Sleeps and returns true if retry needed
     * @param retries current retry
     * @throws InterruptedException
     */
    private boolean pauseIfRetryNeeded(int retries)
          throws InterruptedException {
        if (retries <= config.getMaxErrorRetry()) {
            long delay = (long) (Math.pow(4, retries) * 100L);
            log.debug("Retriable error detected, will retry in " + delay + "ms, attempt numer: " + retries);
            Thread.sleep(delay);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Add authentication related and version parameter and set request body
     * with all of the parameters
     * @throws java.io.UnsupportedEncodingException
     */
        private void addRequiredParametersToRequest(HttpPost method, Map<String, String> parameters)
                throws SignatureException, UnsupportedEncodingException {
        parameters.put("Version", config.getServiceVersion());
        parameters.put("SignatureVersion", config.getSignatureVersion());
        parameters.put("Timestamp", getFormattedTimestamp());
        parameters.put("AWSAccessKeyId",  this.awsAccessKeyId);
        parameters.put("Signature", signParameters(parameters, this.awsSecretAccessKey));
        List<NameValuePair> parameterList = new ArrayList<NameValuePair>();
            for (Entry<String, String> entry : parameters.entrySet()) {
                parameterList.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            method.setEntity(new UrlEncodedFormEntity(parameterList));
        }




    private AmazonFPSException processErrors(String responseString, int status)  {
        AmazonFPSException ex = null;
        Matcher matcher = null;
        if (responseString != null && responseString.startsWith("<")) {
            matcher = ERROR_PATTERN_ONE.matcher(responseString);
            if (matcher.matches()) {
                ex = new AmazonFPSException(matcher.group(3), status,
                        matcher.group(2), "Unknown", matcher.group(1), responseString);
            } else {
                matcher = ERROR_PATTERN_TWO.matcher(responseString);
                if (matcher.matches()) {
                    ex = new AmazonFPSException(matcher.group(2), status,
                            matcher.group(1), "Unknown", matcher.group(4), responseString);
            } else {
                ex =  new AmazonFPSException("Internal Error", status);
                log.debug("Service Error. Response Status: " + status);
            }
            }
        } else {
            ex =  new AmazonFPSException("Internal Error", status);
            log.debug("Service Error. Response Status: " + status);
        }
        return ex;
    }

    /**
     * Formats date as ISO 8601 timestamp
     */
    private String getFormattedTimestamp() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        return df.format(new Date());
    }

    /**
     * Computes RFC 2104-compliant HMAC signature for request parameters
     * Implements AWS Signature, as per following spec:
     *
     * If Signature Version is 2, string to sign is based on following:
     *
     *    1. The HTTP Request Method followed by an ASCII newline (%0A)
     *    2. The HTTP Host header in the form of lowercase host, followed by an ASCII newline.
     *    3. The URL encoded HTTP absolute path component of the URI
     *       (up to but not including the query string parameters);
     *       if this is empty use a forward '/'. This parameter is followed by an ASCII newline.
     *    4. The concatenation of all query string components (names and values)
     *       as UTF-8 characters which are URL encoded as per RFC 3986
     *       (hex characters MUST be uppercase), sorted using lexicographic byte ordering.
     *       Parameter names are separated from their values by the '=' character
     *       (ASCII character 61), even if the value is empty.
     *       Pairs of parameter and values are separated by the '&' character (ASCII code 38).
     *
     */
    private String signParameters(Map<String, String> parameters, String key)
            throws  SignatureException {

        String signatureVersion = parameters.get("SignatureVersion");
        String algorithm = "HmacSHA1";
        String stringToSign = null;
        if ("2".equals(signatureVersion)) {
            algorithm = config.getSignatureMethod();
            parameters.put("SignatureMethod", algorithm);
            stringToSign = calculateStringToSignV2(parameters);
        } else {
            throw new SignatureException("Invalid Signature Version specified");
        }
        log.debug("Calculated string to sign: " + stringToSign);
        return sign(stringToSign, key, algorithm);
    }

    /**
     * Calculate String to Sign for SignatureVersion 2
     * @param parameters request parameters
     * @return String to Sign
     * @throws java.security.SignatureException
     */
    private String calculateStringToSignV2(Map<String, String> parameters)
            throws SignatureException {
        StringBuilder data = new StringBuilder();
        data.append("POST");
        data.append("\n");
        URI endpoint = null;
        try {
            endpoint = new URI(config.getServiceURL().toLowerCase());
        } catch (URISyntaxException ex) {
            log.debug("URI Syntax Exception", ex);
            throw new SignatureException("URI Syntax Exception thrown " +
                    "while constructing string to sign", ex);
        }
        data.append(endpoint.getHost());
        data.append("\n");
        String uri = endpoint.getPath();
        if (uri == null || uri.length() == 0) {
            uri = "/";
        }
        data.append(urlEncode(uri, true));
        data.append("\n");
        Map<String, String> sorted = new TreeMap<String, String>();
        sorted.putAll(parameters);
        Iterator<Entry<String, String>> pairs = sorted.entrySet().iterator();
        while (pairs.hasNext()) {
            Entry<String, String> pair = pairs.next();
            String key = pair.getKey();
            data.append(urlEncode(key, false));
            data.append("=");
            String value = pair.getValue();
            data.append(urlEncode(value, false));
            if (pairs.hasNext()) {
                data.append("&");
            }
        }
        return data.toString();
    }

    private String urlEncode(String value, boolean path) {
        String encoded = null;
        try {
            encoded = URLEncoder.encode(value, DEFAULT_ENCODING)
                                        .replace("+", "%20")
                                        .replace("*", "%2A")
                                        .replace("%7E","~");
            if (path) {
                encoded = encoded.replace("%2F", "/");
            }
        } catch (UnsupportedEncodingException ex) {
            log.debug("Unsupported Encoding Exception", ex);
            throw new RuntimeException(ex);
        }
        return encoded;
    }

    /**
     * Computes RFC 2104-compliant HMAC signature.
     *
     */
    private String sign(String data, String key, String algorithm) throws SignatureException {
        byte [] signature;
        try {
            Mac mac = Mac.getInstance(algorithm);
            mac.init(new SecretKeySpec(key.getBytes(), algorithm));
            signature = Base64.encodeBase64(mac.doFinal(data.getBytes(DEFAULT_ENCODING)));
        } catch (Exception e) {
            throw new SignatureException("Failed to generate signature: " + e.getMessage(), e);
        }

        return new String(signature);
    }

    /**
     * Get unmarshaller for current thread
     */
    private Unmarshaller getUnmarshaller() {
        return unmarshaller.get();
    }

   /**
     * Convert CancelRequest to name value pairs
     */
    private Map<String, String> convertCancel(CancelRequest request) {

        Map<String, String> params = new HashMap<String, String>();
        params.put("Action", "Cancel");
        if (request.isSetTransactionId()) {
            params.put("TransactionId", request.getTransactionId());
        }
        if (request.isSetDescription()) {
            params.put("Description", request.getDescription());
        }
        if (request.isSetOverrideIPNURL()) {
            params.put("OverrideIPNURL", request.getOverrideIPNURL());
        }

        return params;
    }




   /**
     * Convert CancelTokenRequest to name value pairs
     */
    private Map<String, String> convertCancelToken(CancelTokenRequest request) {

        Map<String, String> params = new HashMap<String, String>();
        params.put("Action", "CancelToken");
        if (request.isSetTokenId()) {
            params.put("TokenId", request.getTokenId());
        }
        if (request.isSetReasonText()) {
            params.put("ReasonText", request.getReasonText());
        }
        if (request.isSetOverrideIPNURL()) {
            params.put("OverrideIPNURL", request.getOverrideIPNURL());
        }

        return params;
    }




   /**
     * Convert CancelSubscriptionAndRefundRequest to name value pairs
     */
    private Map<String, String> convertCancelSubscriptionAndRefund(CancelSubscriptionAndRefundRequest request) {

        Map<String, String> params = new HashMap<String, String>();
        params.put("Action", "CancelSubscriptionAndRefund");
        if (request.isSetSubscriptionId()) {
            params.put("SubscriptionId", request.getSubscriptionId());
        }
        if (request.isSetRefundAmount()) {
            Amount refundAmount = request.getRefundAmount();
            if (refundAmount.isSetCurrencyCode()) {
                params.put("RefundAmount" + "." + "CurrencyCode", refundAmount.getCurrencyCode().value());
            }
            if (refundAmount.isSetValue()) {
                params.put("RefundAmount" + "." + "Value", refundAmount.getValue());
            }
        }
        if (request.isSetCallerReference()) {
            params.put("CallerReference", request.getCallerReference());
        }
        if (request.isSetCancelReason()) {
            params.put("CancelReason", request.getCancelReason());
        }

        return params;
    }




   /**
     * Convert FundPrepaidRequest to name value pairs
     */
    private Map<String, String> convertFundPrepaid(FundPrepaidRequest request) {

        Map<String, String> params = new HashMap<String, String>();
        params.put("Action", "FundPrepaid");
        if (request.isSetSenderTokenId()) {
            params.put("SenderTokenId", request.getSenderTokenId());
        }
        if (request.isSetPrepaidInstrumentId()) {
            params.put("PrepaidInstrumentId", request.getPrepaidInstrumentId());
        }
        if (request.isSetFundingAmount()) {
            Amount fundingAmount = request.getFundingAmount();
            if (fundingAmount.isSetCurrencyCode()) {
                params.put("FundingAmount" + "." + "CurrencyCode", fundingAmount.getCurrencyCode().value());
            }
            if (fundingAmount.isSetValue()) {
                params.put("FundingAmount" + "." + "Value", fundingAmount.getValue());
            }
        }
        if (request.isSetCallerReference()) {
            params.put("CallerReference", request.getCallerReference());
        }
        if (request.isSetSenderDescription()) {
            params.put("SenderDescription", request.getSenderDescription());
        }
        if (request.isSetCallerDescription()) {
            params.put("CallerDescription", request.getCallerDescription());
        }
        if (request.isSetDescriptorPolicy()) {
            DescriptorPolicy descriptorPolicy = request.getDescriptorPolicy();
            if (descriptorPolicy.isSetSoftDescriptorType()) {
                params.put("DescriptorPolicy" + "." + "SoftDescriptorType", descriptorPolicy.getSoftDescriptorType().value());
            }
            if (descriptorPolicy.isSetCSOwner()) {
                params.put("DescriptorPolicy" + "." + "CSOwner", descriptorPolicy.getCSOwner().value());
            }
        }
        if (request.isSetTransactionTimeoutInMins()) {
            params.put("TransactionTimeoutInMins", request.getTransactionTimeoutInMins() + "");
        }
        if (request.isSetOverrideIPNURL()) {
            params.put("OverrideIPNURL", request.getOverrideIPNURL());
        }

        return params;
    }




   /**
     * Convert GetAccountActivityRequest to name value pairs
     */
    private Map<String, String> convertGetAccountActivity(GetAccountActivityRequest request) {

        Map<String, String> params = new HashMap<String, String>();
        params.put("Action", "GetAccountActivity");
        if (request.isSetMaxBatchSize()) {
            params.put("MaxBatchSize", request.getMaxBatchSize() + "");
        }
        if (request.isSetStartDate()) {
            params.put("StartDate", request.getStartDate() + "");
        }
        if (request.isSetEndDate()) {
            params.put("EndDate", request.getEndDate() + "");
        }
        if (request.isSetSortOrderByDate()) {
            params.put("SortOrderByDate", request.getSortOrderByDate().value());
        }
        if (request.isSetFPSOperation()) {
            params.put("FPSOperation", request.getFPSOperation().value());
        }
        if (request.isSetPaymentMethod()) {
            params.put("PaymentMethod", request.getPaymentMethod().value());
        }
        List<TransactionalRole> roleList  =  request.getRole();
        int roleListIndex = 1;
        for  (TransactionalRole role : roleList) {
            params.put("Role" + "."  + roleListIndex, role.value());
            roleListIndex++;
        }	
        if (request.isSetTransactionStatus()) {
            params.put("TransactionStatus", request.getTransactionStatus().value());
        }

        return params;
    }
        
        
    
                    
   /**
     * Convert GetAccountBalanceRequest to name value pairs
     */
    private Map<String, String> convertGetAccountBalance(GetAccountBalanceRequest request) {
        
        Map<String, String> params = new HashMap<String, String>();
        params.put("Action", "GetAccountBalance");

        return params;
    }
        
        
    
                    
   /**
     * Convert GetTransactionsForSubscriptionRequest to name value pairs
     */
    private Map<String, String> convertGetTransactionsForSubscription(GetTransactionsForSubscriptionRequest request) {
        
        Map<String, String> params = new HashMap<String, String>();
        params.put("Action", "GetTransactionsForSubscription");
        if (request.isSetSubscriptionId()) {
            params.put("SubscriptionId", request.getSubscriptionId());
        }

        return params;
    }
        
        
    
                    
   /**
     * Convert GetSubscriptionDetailsRequest to name value pairs
     */
    private Map<String, String> convertGetSubscriptionDetails(GetSubscriptionDetailsRequest request) {
        
        Map<String, String> params = new HashMap<String, String>();
        params.put("Action", "GetSubscriptionDetails");
        if (request.isSetSubscriptionId()) {
            params.put("SubscriptionId", request.getSubscriptionId());
        }

        return params;
    }
        
        
    
                    
   /**
     * Convert GetDebtBalanceRequest to name value pairs
     */
    private Map<String, String> convertGetDebtBalance(GetDebtBalanceRequest request) {
        
        Map<String, String> params = new HashMap<String, String>();
        params.put("Action", "GetDebtBalance");
        if (request.isSetCreditInstrumentId()) {
            params.put("CreditInstrumentId", request.getCreditInstrumentId());
        }

        return params;
    }
        
        
    
                    
   /**
     * Convert GetOutstandingDebtBalanceRequest to name value pairs
     */
    private Map<String, String> convertGetOutstandingDebtBalance(GetOutstandingDebtBalanceRequest request) {
        
        Map<String, String> params = new HashMap<String, String>();
        params.put("Action", "GetOutstandingDebtBalance");

        return params;
    }
        
        
    
                    
   /**
     * Convert GetPrepaidBalanceRequest to name value pairs
     */
    private Map<String, String> convertGetPrepaidBalance(GetPrepaidBalanceRequest request) {
        
        Map<String, String> params = new HashMap<String, String>();
        params.put("Action", "GetPrepaidBalance");
        if (request.isSetPrepaidInstrumentId()) {
            params.put("PrepaidInstrumentId", request.getPrepaidInstrumentId());
        }

        return params;
    }
        
        
    
                    
   /**
     * Convert GetTokenByCallerRequest to name value pairs
     */
    private Map<String, String> convertGetTokenByCaller(GetTokenByCallerRequest request) {
        
        Map<String, String> params = new HashMap<String, String>();
        params.put("Action", "GetTokenByCaller");
        if (request.isSetTokenId()) {
            params.put("TokenId", request.getTokenId());
        }
        if (request.isSetCallerReference()) {
            params.put("CallerReference", request.getCallerReference());
        }

        return params;
    }
        
        
    
                    
   /**
     * Convert GetTokenUsageRequest to name value pairs
     */
    private Map<String, String> convertGetTokenUsage(GetTokenUsageRequest request) {
        
        Map<String, String> params = new HashMap<String, String>();
        params.put("Action", "GetTokenUsage");
        if (request.isSetTokenId()) {
            params.put("TokenId", request.getTokenId());
        }

        return params;
    }
        
        
    
                    
   /**
     * Convert GetTokensRequest to name value pairs
     */
    private Map<String, String> convertGetTokens(GetTokensRequest request) {
        
        Map<String, String> params = new HashMap<String, String>();
        params.put("Action", "GetTokens");
        if (request.isSetTokenStatus()) {
            params.put("TokenStatus", request.getTokenStatus().value());
        }
        if (request.isSetTokenType()) {
            params.put("TokenType", request.getTokenType().value());
        }
        if (request.isSetCallerReference()) {
            params.put("CallerReference", request.getCallerReference());
        }
        if (request.isSetTokenFriendlyName()) {
            params.put("TokenFriendlyName", request.getTokenFriendlyName());
        }

        return params;
    }
        
        
    
                    
   /**
     * Convert GetTotalPrepaidLiabilityRequest to name value pairs
     */
    private Map<String, String> convertGetTotalPrepaidLiability(GetTotalPrepaidLiabilityRequest request) {
        
        Map<String, String> params = new HashMap<String, String>();
        params.put("Action", "GetTotalPrepaidLiability");

        return params;
    }
        
        
    
                    
   /**
     * Convert GetTransactionRequest to name value pairs
     */
    private Map<String, String> convertGetTransaction(GetTransactionRequest request) {
        
        Map<String, String> params = new HashMap<String, String>();
        params.put("Action", "GetTransaction");
        if (request.isSetTransactionId()) {
            params.put("TransactionId", request.getTransactionId());
        }

        return params;
    }
        
        
    
                    
   /**
     * Convert GetTransactionStatusRequest to name value pairs
     */
    private Map<String, String> convertGetTransactionStatus(GetTransactionStatusRequest request) {
        
        Map<String, String> params = new HashMap<String, String>();
        params.put("Action", "GetTransactionStatus");
        if (request.isSetTransactionId()) {
            params.put("TransactionId", request.getTransactionId());
        }

        return params;
    }
        
        
    
                    
   /**
     * Convert GetPaymentInstructionRequest to name value pairs
     */
    private Map<String, String> convertGetPaymentInstruction(GetPaymentInstructionRequest request) {
        
        Map<String, String> params = new HashMap<String, String>();
        params.put("Action", "GetPaymentInstruction");
        if (request.isSetTokenId()) {
            params.put("TokenId", request.getTokenId());
        }

        return params;
    }
        
        
    
                    
   /**
     * Convert InstallPaymentInstructionRequest to name value pairs
     */
    private Map<String, String> convertInstallPaymentInstruction(InstallPaymentInstructionRequest request) {
        
        Map<String, String> params = new HashMap<String, String>();
        params.put("Action", "InstallPaymentInstruction");
        if (request.isSetPaymentInstruction()) {
            params.put("PaymentInstruction", request.getPaymentInstruction());
        }
        if (request.isSetTokenFriendlyName()) {
            params.put("TokenFriendlyName", request.getTokenFriendlyName());
        }
        if (request.isSetCallerReference()) {
            params.put("CallerReference", request.getCallerReference());
        }
        if (request.isSetTokenType()) {
            params.put("TokenType", request.getTokenType().value());
        }
        if (request.isSetPaymentReason()) {
            params.put("PaymentReason", request.getPaymentReason());
        }

        return params;
    }
        
        
    
                    
   /**
     * Convert PayRequest to name value pairs
     */
    private Map<String, String> convertPay(PayRequest request) {
        
        Map<String, String> params = new HashMap<String, String>();
        params.put("Action", "Pay");
        if (request.isSetSenderTokenId()) {
            params.put("SenderTokenId", request.getSenderTokenId());
        }
        if (request.isSetRecipientTokenId()) {
            params.put("RecipientTokenId", request.getRecipientTokenId());
        }
        if (request.isSetTransactionAmount()) {
            Amount transactionAmount = request.getTransactionAmount();
            if (transactionAmount.isSetCurrencyCode()) {
                params.put("TransactionAmount" + "." + "CurrencyCode", transactionAmount.getCurrencyCode().value());
            }
            if (transactionAmount.isSetValue()) {
                params.put("TransactionAmount" + "." + "Value", transactionAmount.getValue());
            }
        } 
        if (request.isSetChargeFeeTo()) {
            params.put("ChargeFeeTo", request.getChargeFeeTo().value());
        }
        if (request.isSetCallerReference()) {
            params.put("CallerReference", request.getCallerReference());
        }
        if (request.isSetCallerDescription()) {
            params.put("CallerDescription", request.getCallerDescription());
        }
        if (request.isSetSenderDescription()) {
            params.put("SenderDescription", request.getSenderDescription());
        }
        if (request.isSetDescriptorPolicy()) {
            DescriptorPolicy descriptorPolicy = request.getDescriptorPolicy();
            if (descriptorPolicy.isSetSoftDescriptorType()) {
                params.put("DescriptorPolicy" + "." + "SoftDescriptorType", descriptorPolicy.getSoftDescriptorType().value());
            }
            if (descriptorPolicy.isSetCSOwner()) {
                params.put("DescriptorPolicy" + "." + "CSOwner", descriptorPolicy.getCSOwner().value());
            }
        } 
        if (request.isSetTransactionTimeoutInMins()) {
            params.put("TransactionTimeoutInMins", request.getTransactionTimeoutInMins() + "");
        }
        if (request.isSetMarketplaceFixedFee()) {
            Amount marketplaceFixedFee = request.getMarketplaceFixedFee();
            if (marketplaceFixedFee.isSetCurrencyCode()) {
                params.put("MarketplaceFixedFee" + "." + "CurrencyCode", marketplaceFixedFee.getCurrencyCode().value());
            }
            if (marketplaceFixedFee.isSetValue()) {
                params.put("MarketplaceFixedFee" + "." + "Value", marketplaceFixedFee.getValue());
            }
        } 
        if (request.isSetMarketplaceVariableFee()) {
            params.put("MarketplaceVariableFee", request.getMarketplaceVariableFee() + "");
        }
        if (request.isSetOverrideIPNURL()) {
            params.put("OverrideIPNURL", request.getOverrideIPNURL());
        }

        return params;
    }
        
        
    
                    
   /**
     * Convert RefundRequest to name value pairs
     */
    private Map<String, String> convertRefund(RefundRequest request) {
        
        Map<String, String> params = new HashMap<String, String>();
        params.put("Action", "Refund");
        if (request.isSetTransactionId()) {
            params.put("TransactionId", request.getTransactionId());
        }
        if (request.isSetRefundAmount()) {
            Amount refundAmount = request.getRefundAmount();
            if (refundAmount.isSetCurrencyCode()) {
                params.put("RefundAmount" + "." + "CurrencyCode", refundAmount.getCurrencyCode().value());
            }
            if (refundAmount.isSetValue()) {
                params.put("RefundAmount" + "." + "Value", refundAmount.getValue());
            }
        } 
        if (request.isSetCallerReference()) {
            params.put("CallerReference", request.getCallerReference());
        }
        if (request.isSetCallerDescription()) {
            params.put("CallerDescription", request.getCallerDescription());
        }
        if (request.isSetMarketplaceRefundPolicy()) {
            params.put("MarketplaceRefundPolicy", request.getMarketplaceRefundPolicy().value());
        }
        if (request.isSetOverrideIPNURL()) {
            params.put("OverrideIPNURL", request.getOverrideIPNURL());
        }

        return params;
    }
        
        
    
                    
   /**
     * Convert ReserveRequest to name value pairs
     */
    private Map<String, String> convertReserve(ReserveRequest request) {
        
        Map<String, String> params = new HashMap<String, String>();
        params.put("Action", "Reserve");
        if (request.isSetSenderTokenId()) {
            params.put("SenderTokenId", request.getSenderTokenId());
        }
        if (request.isSetRecipientTokenId()) {
            params.put("RecipientTokenId", request.getRecipientTokenId());
        }
        if (request.isSetTransactionAmount()) {
            Amount transactionAmount = request.getTransactionAmount();
            if (transactionAmount.isSetCurrencyCode()) {
                params.put("TransactionAmount" + "." + "CurrencyCode", transactionAmount.getCurrencyCode().value());
            }
            if (transactionAmount.isSetValue()) {
                params.put("TransactionAmount" + "." + "Value", transactionAmount.getValue());
            }
        } 
        if (request.isSetChargeFeeTo()) {
            params.put("ChargeFeeTo", request.getChargeFeeTo().value());
        }
        if (request.isSetCallerReference()) {
            params.put("CallerReference", request.getCallerReference());
        }
        if (request.isSetCallerDescription()) {
            params.put("CallerDescription", request.getCallerDescription());
        }
        if (request.isSetSenderDescription()) {
            params.put("SenderDescription", request.getSenderDescription());
        }
        if (request.isSetDescriptorPolicy()) {
            DescriptorPolicy descriptorPolicy = request.getDescriptorPolicy();
            if (descriptorPolicy.isSetSoftDescriptorType()) {
                params.put("DescriptorPolicy" + "." + "SoftDescriptorType", descriptorPolicy.getSoftDescriptorType().value());
            }
            if (descriptorPolicy.isSetCSOwner()) {
                params.put("DescriptorPolicy" + "." + "CSOwner", descriptorPolicy.getCSOwner().value());
            }
        } 
        if (request.isSetTransactionTimeoutInMins()) {
            params.put("TransactionTimeoutInMins", request.getTransactionTimeoutInMins() + "");
        }
        if (request.isSetMarketplaceFixedFee()) {
            Amount marketplaceFixedFee = request.getMarketplaceFixedFee();
            if (marketplaceFixedFee.isSetCurrencyCode()) {
                params.put("MarketplaceFixedFee" + "." + "CurrencyCode", marketplaceFixedFee.getCurrencyCode().value());
            }
            if (marketplaceFixedFee.isSetValue()) {
                params.put("MarketplaceFixedFee" + "." + "Value", marketplaceFixedFee.getValue());
            }
        } 
        if (request.isSetMarketplaceVariableFee()) {
            params.put("MarketplaceVariableFee", request.getMarketplaceVariableFee() + "");
        }
        if (request.isSetOverrideIPNURL()) {
            params.put("OverrideIPNURL", request.getOverrideIPNURL());
        }

        return params;
    }
        
        
    
                    
   /**
     * Convert SettleRequest to name value pairs
     */
    private Map<String, String> convertSettle(SettleRequest request) {
        
        Map<String, String> params = new HashMap<String, String>();
        params.put("Action", "Settle");
        if (request.isSetReserveTransactionId()) {
            params.put("ReserveTransactionId", request.getReserveTransactionId());
        }
        if (request.isSetTransactionAmount()) {
            Amount transactionAmount = request.getTransactionAmount();
            if (transactionAmount.isSetCurrencyCode()) {
                params.put("TransactionAmount" + "." + "CurrencyCode", transactionAmount.getCurrencyCode().value());
            }
            if (transactionAmount.isSetValue()) {
                params.put("TransactionAmount" + "." + "Value", transactionAmount.getValue());
            }
        } 
        if (request.isSetOverrideIPNURL()) {
            params.put("OverrideIPNURL", request.getOverrideIPNURL());
        }

        return params;
    }
        
        
    
                    
   /**
     * Convert SettleDebtRequest to name value pairs
     */
    private Map<String, String> convertSettleDebt(SettleDebtRequest request) {
        
        Map<String, String> params = new HashMap<String, String>();
        params.put("Action", "SettleDebt");
        if (request.isSetSenderTokenId()) {
            params.put("SenderTokenId", request.getSenderTokenId());
        }
        if (request.isSetCreditInstrumentId()) {
            params.put("CreditInstrumentId", request.getCreditInstrumentId());
        }
        if (request.isSetSettlementAmount()) {
            Amount settlementAmount = request.getSettlementAmount();
            if (settlementAmount.isSetCurrencyCode()) {
                params.put("SettlementAmount" + "." + "CurrencyCode", settlementAmount.getCurrencyCode().value());
            }
            if (settlementAmount.isSetValue()) {
                params.put("SettlementAmount" + "." + "Value", settlementAmount.getValue());
            }
        } 
        if (request.isSetCallerReference()) {
            params.put("CallerReference", request.getCallerReference());
        }
        if (request.isSetSenderDescription()) {
            params.put("SenderDescription", request.getSenderDescription());
        }
        if (request.isSetCallerDescription()) {
            params.put("CallerDescription", request.getCallerDescription());
        }
        if (request.isSetDescriptorPolicy()) {
            DescriptorPolicy descriptorPolicy = request.getDescriptorPolicy();
            if (descriptorPolicy.isSetSoftDescriptorType()) {
                params.put("DescriptorPolicy" + "." + "SoftDescriptorType", descriptorPolicy.getSoftDescriptorType().value());
            }
            if (descriptorPolicy.isSetCSOwner()) {
                params.put("DescriptorPolicy" + "." + "CSOwner", descriptorPolicy.getCSOwner().value());
            }
        } 
        if (request.isSetTransactionTimeoutInMins()) {
            params.put("TransactionTimeoutInMins", request.getTransactionTimeoutInMins() + "");
        }
        if (request.isSetOverrideIPNURL()) {
            params.put("OverrideIPNURL", request.getOverrideIPNURL());
        }

        return params;
    }
        
        
    
                    
   /**
     * Convert WriteOffDebtRequest to name value pairs
     */
    private Map<String, String> convertWriteOffDebt(WriteOffDebtRequest request) {
        
        Map<String, String> params = new HashMap<String, String>();
        params.put("Action", "WriteOffDebt");
        if (request.isSetCreditInstrumentId()) {
            params.put("CreditInstrumentId", request.getCreditInstrumentId());
        }
        if (request.isSetAdjustmentAmount()) {
            Amount adjustmentAmount = request.getAdjustmentAmount();
            if (adjustmentAmount.isSetCurrencyCode()) {
                params.put("AdjustmentAmount" + "." + "CurrencyCode", adjustmentAmount.getCurrencyCode().value());
            }
            if (adjustmentAmount.isSetValue()) {
                params.put("AdjustmentAmount" + "." + "Value", adjustmentAmount.getValue());
            }
        } 
        if (request.isSetCallerReference()) {
            params.put("CallerReference", request.getCallerReference());
        }
        if (request.isSetCallerDescription()) {
            params.put("CallerDescription", request.getCallerDescription());
        }

        return params;
    }
        
        
    
                    
   /**
     * Convert GetRecipientVerificationStatusRequest to name value pairs
     */
    private Map<String, String> convertGetRecipientVerificationStatus(GetRecipientVerificationStatusRequest request) {
        
        Map<String, String> params = new HashMap<String, String>();
        params.put("Action", "GetRecipientVerificationStatus");
        if (request.isSetRecipientTokenId()) {
            params.put("RecipientTokenId", request.getRecipientTokenId());
        }

        return params;
    }
        
        
    
                    
   /**
     * Convert VerifySignatureRequest to name value pairs
     */
    private Map<String, String> convertVerifySignature(VerifySignatureRequest request) {
        
        Map<String, String> params = new HashMap<String, String>();
        params.put("Action", "VerifySignature");
        if (request.isSetUrlEndPoint()) {
            params.put("UrlEndPoint", request.getUrlEndPoint());
        }
        if (request.isSetHttpParameters()) {
            params.put("HttpParameters", request.getHttpParameters());
        }

        return params;
    }
        
        
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    



}
