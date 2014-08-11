package com.qpp.service.market;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by qpp on 8/11/2014.
 */
public interface PaypmentRequest extends Serializable {

    Map<String,String> getNVPRequest();

    void setNVPResponse(Map<String, String> nvpResponse);

    Map<String, String> getResponse();

}
