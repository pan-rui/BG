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
 * Get Outstanding Debt Balance  Samples
 *
 *
 */
public class GetOutstandingDebtBalanceSample {

    /**
     * Just add few required parameters, and try the service
     * Get Outstanding Debt Balance functionality
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
         * sample for Get Outstanding Debt Balance 
         ***********************************************************************/
         GetOutstandingDebtBalanceRequest request = new GetOutstandingDebtBalanceRequest();
        
         // @TODO: set request parameters here

         // invokeGetOutstandingDebtBalance(service, request);

    }


                                                        
    /**
     * Get Outstanding Debt Balance  request sample
     * 
     * Returns the total outstanding balance for all the credit instruments for the given creditor account.
     *   
     * @param service instance of AmazonFPS service
     * @param request Action to invoke
     */
    public static void invokeGetOutstandingDebtBalance(AmazonFPS service, GetOutstandingDebtBalanceRequest request) {
        try {
            
            GetOutstandingDebtBalanceResponse response = service.getOutstandingDebtBalance(request);

            
            System.out.println ("GetOutstandingDebtBalance Action Response");
            System.out.println ("=============================================================================");
            System.out.println ();

            System.out.println("    GetOutstandingDebtBalanceResponse");
            System.out.println();
            if (response.isSetGetOutstandingDebtBalanceResult()) {
                System.out.println("        GetOutstandingDebtBalanceResult");
                System.out.println();
                GetOutstandingDebtBalanceResult  getOutstandingDebtBalanceResult = response.getGetOutstandingDebtBalanceResult();
                if (getOutstandingDebtBalanceResult.isSetOutstandingDebt()) {
                    System.out.println("            OutstandingDebt");
                    System.out.println();
                    OutstandingDebtBalance  outstandingDebt = getOutstandingDebtBalanceResult.getOutstandingDebt();
                    if (outstandingDebt.isSetOutstandingBalance()) {
                        System.out.println("                OutstandingBalance");
                        System.out.println();
                        Amount  outstandingBalance = outstandingDebt.getOutstandingBalance();
                        if (outstandingBalance.isSetCurrencyCode()) {
                            System.out.println("                    CurrencyCode");
                            System.out.println();
                            System.out.println("                        " + outstandingBalance.getCurrencyCode().value());
                            System.out.println();
                        }
                        if (outstandingBalance.isSetValue()) {
                            System.out.println("                    Value");
                            System.out.println();
                            System.out.println("                        " + outstandingBalance.getValue());
                            System.out.println();
                        }
                    } 
                    if (outstandingDebt.isSetPendingOutBalance()) {
                        System.out.println("                PendingOutBalance");
                        System.out.println();
                        Amount  pendingOutBalance = outstandingDebt.getPendingOutBalance();
                        if (pendingOutBalance.isSetCurrencyCode()) {
                            System.out.println("                    CurrencyCode");
                            System.out.println();
                            System.out.println("                        " + pendingOutBalance.getCurrencyCode().value());
                            System.out.println();
                        }
                        if (pendingOutBalance.isSetValue()) {
                            System.out.println("                    Value");
                            System.out.println();
                            System.out.println("                        " + pendingOutBalance.getValue());
                            System.out.println();
                        }
                    } 
                } 
            } 
            if (response.isSetResponseMetadata()) {
                System.out.println("        ResponseMetadata");
                System.out.println();
                ResponseMetadata  responseMetadata = response.getResponseMetadata();
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
