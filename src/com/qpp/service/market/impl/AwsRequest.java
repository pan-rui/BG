package com.qpp.service.market.impl;

import com.qpp.model.TOrder;
import com.qpp.service.market.PaymentRequest;

import java.util.Map;

/**
 * Created by qpp on 8/13/2014.
 */
public class AwsRequest implements PaymentRequest {

    @Override
    public Map<String, String> getNVPRequest() {
        return null;
    }

    @Override
    public void setNVPResponse(Map<String, String> nvpResponse) {

    }

    @Override
    public Map<String, String> getResponse() {
        return null;
    }

    @Override
    public void setOrderParam(TOrder order) {

    }


}
