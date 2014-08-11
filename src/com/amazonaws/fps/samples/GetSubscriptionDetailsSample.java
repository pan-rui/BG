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
 *  Generated: Mon May 30 08:09:49 GMT+00:00 2011 
 * 
 */



package com.amazonaws.fps.samples;

import com.amazonaws.fps.AmazonFPS;
import com.amazonaws.fps.AmazonFPSClient;
import com.amazonaws.fps.AmazonFPSException;
import com.amazonaws.fps.model.*;
import com.amazonaws.utils.PropertyBundle;
import com.amazonaws.utils.PropertyKeys;

/**
 *
 * Get Subscription Details  Samples
 *
 *
 */
public class GetSubscriptionDetailsSample {

    /**
     * Just add few required parameters, and try the service
     * Get Subscription Details functionality
     *
     * @param args unused
     */
    public static void main(String... args) {
        
        /************************************************************************
         * Access Key ID and Secret Acess Key ID, obtained from:
         * http://aws.amazon.com
         ***********************************************************************/
        String accessKeyId = PropertyBundle.getProperty(PropertyKeys.AWS_ACCESS_KEY);
        String secretAccessKey = PropertyBundle.getProperty(PropertyKeys.AWS_SECRET_KEY);

        /************************************************************************
         * Instantiate Http Client Implementation of Amazon FPS 
         ***********************************************************************/
        
        AmazonFPS service = null;
		try {
			service = new AmazonFPSClient(accessKeyId, secretAccessKey);
		} catch (AmazonFPSException e) {
			 System.out.println("Caught Exception: " + e.getMessage());
	   	        System.out.println("Response Status Code: " + e.getStatusCode());
	   	        System.out.println("Error Code: " + e.getErrorCode());
	   	        System.out.println("Error Type: " + e.getErrorType());
	   	        System.out.println("Request ID: " + e.getRequestId());
	   	        System.out.print("XML: " + e.getXML());
		}
        /************************************************************************
         * Uncomment to try advanced configuration options. Available options are:
         *
         *  - Signature Version
         *  - Proxy Host and Proxy Port
         *  - Service URL
         *  - User Agent String to be sent to Amazon FPS   service
         *
         ***********************************************************************/
        // AmazonFPSConfig config = new AmazonFPSConfig();
        // config.setSignatureVersion("0");
        // AmazonFPS service = new AmazonFPSClient(accessKeyId, secretAccessKey, config);
 
        /************************************************************************
         * Uncomment to try out Mock Service that simulates Amazon FPS 
         * responses without calling Amazon FPS  service.
         *
         * Responses are loaded from local XML files. You can tweak XML files to
         * experiment with various outputs during development
         *
         * XML files available under com/amazonaws/fps/mock tree
         *
         ***********************************************************************/
        // AmazonFPS service = new AmazonFPSMock();

        /************************************************************************
         * Setup request parameters and uncomment invoke to try out 
         * sample for Get Subscription Details 
         ***********************************************************************/
         GetSubscriptionDetailsRequest request = new GetSubscriptionDetailsRequest();
        
         // @TODO: set request parameters here

         // invokeGetSubscriptionDetails(service, request);

    }


                                                
    /**
     * Get Subscription Details  request sample
     * Returns the details of Subscription for a given subscriptionID.
     *   
     * @param service instance of AmazonFPS service
     * @param request Action to invoke
     */
    public static void invokeGetSubscriptionDetails(AmazonFPS service, GetSubscriptionDetailsRequest request) {
        try {
            
            GetSubscriptionDetailsResponse response = service.getSubscriptionDetails(request);

            
            System.out.println ("GetSubscriptionDetails Action Response");
            System.out.println ("=============================================================================");
            System.out.println ();

            System.out.println("    GetSubscriptionDetailsResponse");
            System.out.println();
            if (response.isSetGetSubscriptionDetailsResult()) {
                System.out.println("        GetSubscriptionDetailsResult");
                System.out.println();
                GetSubscriptionDetailsResult getSubscriptionDetailsResult = response.getGetSubscriptionDetailsResult();
                if (getSubscriptionDetailsResult.isSetSubscriptionDetails()) {
                    System.out.println("            SubscriptionDetails");
                    System.out.println();
                    SubscriptionDetails subscriptionDetails = getSubscriptionDetailsResult.getSubscriptionDetails();
                    if (subscriptionDetails.isSetSubscriptionId()) {
                        System.out.println("                SubscriptionId");
                        System.out.println();
                        System.out.println("                    " + subscriptionDetails.getSubscriptionId());
                        System.out.println();
                    }
                    if (subscriptionDetails.isSetDescription()) {
                        System.out.println("                Description");
                        System.out.println();
                        System.out.println("                    " + subscriptionDetails.getDescription());
                        System.out.println();
                    }
                    if (subscriptionDetails.isSetSubscriptionAmount()) {
                        System.out.println("                SubscriptionAmount");
                        System.out.println();
                        Amount subscriptionAmount = subscriptionDetails.getSubscriptionAmount();
                        if (subscriptionAmount.isSetCurrencyCode()) {
                            System.out.println("                    CurrencyCode");
                            System.out.println();
                            System.out.println("                        " + subscriptionAmount.getCurrencyCode().value());
                            System.out.println();
                        }
                        if (subscriptionAmount.isSetValue()) {
                            System.out.println("                    Value");
                            System.out.println();
                            System.out.println("                        " + subscriptionAmount.getValue());
                            System.out.println();
                        }
                    } 
                    if (subscriptionDetails.isSetNextTransactionAmount()) {
                        System.out.println("                NextTransactionAmount");
                        System.out.println();
                        Amount nextTransactionAmount = subscriptionDetails.getNextTransactionAmount();
                        if (nextTransactionAmount.isSetCurrencyCode()) {
                            System.out.println("                    CurrencyCode");
                            System.out.println();
                            System.out.println("                        " + nextTransactionAmount.getCurrencyCode().value());
                            System.out.println();
                        }
                        if (nextTransactionAmount.isSetValue()) {
                            System.out.println("                    Value");
                            System.out.println();
                            System.out.println("                        " + nextTransactionAmount.getValue());
                            System.out.println();
                        }
                    } 
                    if (subscriptionDetails.isSetPromotionalAmount()) {
                        System.out.println("                PromotionalAmount");
                        System.out.println();
                        Amount promotionalAmount = subscriptionDetails.getPromotionalAmount();
                        if (promotionalAmount.isSetCurrencyCode()) {
                            System.out.println("                    CurrencyCode");
                            System.out.println();
                            System.out.println("                        " + promotionalAmount.getCurrencyCode().value());
                            System.out.println();
                        }
                        if (promotionalAmount.isSetValue()) {
                            System.out.println("                    Value");
                            System.out.println();
                            System.out.println("                        " + promotionalAmount.getValue());
                            System.out.println();
                        }
                    } 
                    if (subscriptionDetails.isSetNumberOfPromotionalTransactions()) {
                        System.out.println("                NumberOfPromotionalTransactions");
                        System.out.println();
                        System.out.println("                    " + subscriptionDetails.getNumberOfPromotionalTransactions());
                        System.out.println();
                    }
                    if (subscriptionDetails.isSetStartDate()) {
                        System.out.println("                StartDate");
                        System.out.println();
                        System.out.println("                    " + subscriptionDetails.getStartDate());
                        System.out.println();
                    }
                    if (subscriptionDetails.isSetEndDate()) {
                        System.out.println("                EndDate");
                        System.out.println();
                        System.out.println("                    " + subscriptionDetails.getEndDate());
                        System.out.println();
                    }
                    if (subscriptionDetails.isSetSubscriptionPeriod()) {
                        System.out.println("                SubscriptionPeriod");
                        System.out.println();
                        Duration subscriptionPeriod = subscriptionDetails.getSubscriptionPeriod();
                        if (subscriptionPeriod.isSetValue()) {
                            System.out.println("                    Value");
                            System.out.println();
                            System.out.println("                        " + subscriptionPeriod.getValue());
                            System.out.println();
                        }
                        if (subscriptionPeriod.isSetTimeUnit()) {
                            System.out.println("                    TimeUnit");
                            System.out.println();
                            System.out.println("                        " + subscriptionPeriod.getTimeUnit().value());
                            System.out.println();
                        }
                    } 
                    if (subscriptionDetails.isSetSubscriptionFrequency()) {
                        System.out.println("                SubscriptionFrequency");
                        System.out.println();
                        Duration subscriptionFrequency = subscriptionDetails.getSubscriptionFrequency();
                        if (subscriptionFrequency.isSetValue()) {
                            System.out.println("                    Value");
                            System.out.println();
                            System.out.println("                        " + subscriptionFrequency.getValue());
                            System.out.println();
                        }
                        if (subscriptionFrequency.isSetTimeUnit()) {
                            System.out.println("                    TimeUnit");
                            System.out.println();
                            System.out.println("                        " + subscriptionFrequency.getTimeUnit().value());
                            System.out.println();
                        }
                    } 
                    if (subscriptionDetails.isSetOverrideIPNUrl()) {
                        System.out.println("                OverrideIPNUrl");
                        System.out.println();
                        System.out.println("                    " + subscriptionDetails.getOverrideIPNUrl());
                        System.out.println();
                    }
                    if (subscriptionDetails.isSetSubscriptionStatus()) {
                        System.out.println("                SubscriptionStatus");
                        System.out.println();
                        System.out.println("                    " + subscriptionDetails.getSubscriptionStatus().value());
                        System.out.println();
                    }
                    if (subscriptionDetails.isSetNumberOfTransactionsProcessed()) {
                        System.out.println("                NumberOfTransactionsProcessed");
                        System.out.println();
                        System.out.println("                    " + subscriptionDetails.getNumberOfTransactionsProcessed());
                        System.out.println();
                    }
                    if (subscriptionDetails.isSetRecipientEmail()) {
                        System.out.println("                RecipientEmail");
                        System.out.println();
                        System.out.println("                    " + subscriptionDetails.getRecipientEmail());
                        System.out.println();
                    }
                    if (subscriptionDetails.isSetRecipientName()) {
                        System.out.println("                RecipientName");
                        System.out.println();
                        System.out.println("                    " + subscriptionDetails.getRecipientName());
                        System.out.println();
                    }
                    if (subscriptionDetails.isSetSenderEmail()) {
                        System.out.println("                SenderEmail");
                        System.out.println();
                        System.out.println("                    " + subscriptionDetails.getSenderEmail());
                        System.out.println();
                    }
                    if (subscriptionDetails.isSetSenderName()) {
                        System.out.println("                SenderName");
                        System.out.println();
                        System.out.println("                    " + subscriptionDetails.getSenderName());
                        System.out.println();
                    }
                    if (subscriptionDetails.isSetNextTransactionDate()) {
                        System.out.println("                NextTransactionDate");
                        System.out.println();
                        System.out.println("                    " + subscriptionDetails.getNextTransactionDate());
                        System.out.println();
                    }
                } 
            } 
            if (response.isSetResponseMetadata()) {
                System.out.println("        ResponseMetadata");
                System.out.println();
                ResponseMetadata responseMetadata = response.getResponseMetadata();
                if (responseMetadata.isSetRequestId()) {
                    System.out.println("            RequestId");
                    System.out.println();
                    System.out.println("                " + responseMetadata.getRequestId());
                    System.out.println();
                }
            } 
            System.out.println();

           
        } catch (AmazonFPSException ex) {
            
            System.out.println("Caught Exception: " + ex.getMessage());
            System.out.println("Response Status Code: " + ex.getStatusCode());
            System.out.println("Error Code: " + ex.getErrorCode());
            System.out.println("Error Type: " + ex.getErrorType());
            System.out.println("Request ID: " + ex.getRequestId());
            System.out.print("XML: " + ex.getXML());
        }
    }
                                                                                    
}
