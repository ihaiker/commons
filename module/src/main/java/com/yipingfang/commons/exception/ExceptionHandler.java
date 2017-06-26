package com.yipingfang.commons.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 服务异常拦截器（全局）<p>
 * 如果服务出现问题，次拦截器将会将其拦截
 *
 * @author <a href="mailto:zhouhaichao@2008.sina.com">haiker</a>
 * @version 2017/6/26 上午10:20
 */
@Slf4j
public class ExceptionHandler implements HandlerExceptionResolver {

    private String serverName;
    public ExceptionHandler(String serverName){
        this.serverName = serverName;
    }

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        HandlerException err;
        String errorMsg = "";
        if (ex instanceof HandlerException) {
            err = (HandlerException) ex;
            errorMsg = err.toJsonString();
            log.info("{} {}  {}", request.getMethod(), request.getRequestURI(), errorMsg);
        } else {
            if (ex instanceof IllegalArgumentException) {
                err = new HandlerException(ErrorEnum.InvalidArgument, ex);
            } else if (ex instanceof HttpRequestMethodNotSupportedException) {
                err = new HandlerException(ErrorEnum.MethodNotAllowed, ex);
            } else if (ex instanceof HttpMessageNotReadableException) {
                err = new HandlerException(ErrorEnum.BadRequest, ex);
            } else if (ex instanceof MissingServletRequestParameterException || ex instanceof NullPointerException) {
                err = new HandlerException(ErrorEnum.MissingParameter, ex);
            } else {
                err = new HandlerException(ErrorEnum.InternalSystemError, ex);
            }
            errorMsg = ExceptionUtil.print(request, ex);
            if (err.getErr() == ErrorEnum.InternalSystemError) {
                //MonitorSDK.sendAlarm("push3.exception", errorMsg);
                //发送预警通知
            }
            log.error(errorMsg);
        }

        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(err.getHttpCode());
        try {
            response.getWriter().write(err.toJsonString());
            response.flushBuffer();
        } catch (Exception ignore) {
        }
        return new ModelAndView();
    }
}
