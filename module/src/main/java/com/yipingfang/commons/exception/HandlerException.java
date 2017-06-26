package com.yipingfang.commons.exception;

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
        super(err.name() + ":" + throwable);
        this.err = err;
    }

    public int getHttpCode() {
        return err.httpCode;
    }

    public ErrorEnum getErr() {
        return err;
    }

    public String toJsonString() {
        JSONObject json = new JSONObject();
        json.put("err", err.errCode);
        json.put("msg", err.errMsg);
        return json.toString();
    }

}
