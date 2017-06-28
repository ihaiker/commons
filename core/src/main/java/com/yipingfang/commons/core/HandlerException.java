package com.yipingfang.commons.core;


import com.alibaba.fastjson.JSONObject;

public class HandlerException extends RuntimeException {

    private static final long serialVersionUID = 1L; // stupid eclipse...

    private ErrorEnum err;

    public HandlerException(ErrorEnum err) {
        super(err.name() + ":" + err.errMsg);
        this.err = err;
    }

    public HandlerException(ErrorEnum err, String message) {
        super(err.name() + ":" + message);
        this.err = err;
        this.err.errMsg = message;
    }

    public HandlerException(ErrorEnum err, Throwable throwable) {
        this(err, throwable.getMessage());
    }

    public HandlerException(int httpCode, String code, String message) {
        this.err = ErrorEnum.InternalSystemError;
        this.err.httpCode = httpCode;
        this.err.errCode = code;
        this.err.errMsg = message;
    }

    public int getHttpCode() {
        return err.httpCode;
    }

    public ErrorEnum getErr() {
        return err;
    }

    public String toJsonString() {
        JSONObject json = new JSONObject();
        json.put("error", err.errCode);
        json.put("message", err.errMsg);
        return json.toString();
    }

}
