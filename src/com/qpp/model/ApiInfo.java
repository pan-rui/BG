package com.qpp.model;

import javax.validation.constraints.NotNull;

/**
 * Created by SZ_it123 on 2014/9/24.
 */
public class ApiInfo {
    private int apiId;
    @NotNull
    private String apiUrl;
    private String httpMethod;
    @NotNull
    private String apiDesc;

    public int getApiId() {
        return apiId;
    }

    public void setApiId(int apiId) {
        this.apiId = apiId;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public String getApiDesc() {
        return apiDesc;
    }

    public void setApiDesc(String apidDesc) {
        this.apiDesc = apidDesc;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }
}
