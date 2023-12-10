package com.dething.cloud.common.language.exception;



public abstract class ApiException extends RuntimeException {

    protected Integer code;
    protected String message;

    ApiException(StatusCode statusCode, Object... params){
        super();
        this.code = statusCode.getCode();
        this.message = statusCode.getMessage(params);
    }

    ApiException(Integer statusCode, String statusMessage){
        this.code = statusCode;
        this.message = statusMessage;
    }

    public Integer getCode(){
        return this.code;
    }

    public String getMessage(){
        return this.message;
    }
}
