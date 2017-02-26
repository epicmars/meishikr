package com.sin2pi.brick.common.http.annotation;

/**
 * Created by yinhang on 16/1/28.
 */
public enum ReqMethod {
    OPTIONS("OPTIONS"),
    GET("GET"),
    HEAD("HEAD"),
    POST("POST"),
    PUT("PUT"),
    DELETE("DELETE"),
    TRACE("TRACE");

    public final String method;

    ReqMethod(String method){
        this.method = method;
    }


}
