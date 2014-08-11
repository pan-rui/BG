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
 
package com.amazonaws.ipnreturnurlvalidation.samples;

import com.amazonaws.ipnreturnurlvalidation.SignatureUtilsForOutbound;

import java.util.HashMap;
import java.util.Map;

public class IPNVerificationSampleCode {

    public static void main(String... args) throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        
        //Parameters present in ipn.

        String urlEndPoint = "http://www.mysite.com/ipn.jsp"; //Your url end point receiving the ipn. 
        System.out.println("Verifying IPN signed using signature v2 ....");
        //IPN is sent as a http POST request and hence we specify POST as the http method.
        //Signature verification does not require your secret key
        System.out.println("Is signature correct: " + (new SignatureUtilsForOutbound("AKIAJOOABGPDDMQCZ5VQ","2CVUJXxLD2j3CG/IhiMl2z/qXZP+weJEldWiWD+T")).validateRequest(params, urlEndPoint, "POST"));
    }
}
