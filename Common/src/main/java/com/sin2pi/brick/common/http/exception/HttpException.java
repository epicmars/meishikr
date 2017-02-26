package com.sin2pi.brick.common.http.exception;

/**
 * Created by yinhang on 16/1/20.
 */
public class HttpException extends RuntimeException {

    public HttpException(){
        super();
    }

    public HttpException(String message){
        super(message);
    }

    public HttpException(String message, Throwable throwable){
        super(message, throwable);
    }
}
