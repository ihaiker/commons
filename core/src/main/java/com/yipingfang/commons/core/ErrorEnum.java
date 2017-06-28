package com.yipingfang.commons.core;

public enum ErrorEnum {

    //请求错误
    BadRequest(400, "BadRequest", "Bad Request Parameter."),
    //未认证用户
    Unauthorized(401, "Unauthorized", "The request requires user authentication."),
    //权限不足
    Forbidden(403, "Forbidden", "The resources is forbidden to visit."),
    //未发现
    NotFound(404, "NotFound", "The specified resource does not exist."),

    //非法参数
    InvalidArgument(400, "InvalidArgument", "One of your provided argument is malformed or otherwise invalid."),
    //方法不允许
    MethodNotAllowed(405, "MethodNotAllowed", "The specified method is not allowed against this resource."),
    //参数确实
    MissingParameter(400, "MissingParameter", "The request you input is missing some required parameters."),
    //执行失败
    ExecuteFailed(417, "ExecuteFailed", "Failed to execute"),

    //服务运行异常
    InternalSystemError(500,"InternalError","We encountered an internal error. Please try again.");

    public int httpCode;
    public String errCode;
    public String errMsg;

    ErrorEnum(int httpCode, String code, String message) {
        this.httpCode = httpCode;
        this.errCode = code;
        this.errMsg = message;
    }
}
