package com.yipingfang.commons.exception;

import com.google.common.base.Throwables;
import org.springframework.http.HttpMethod;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.net.InetAddress;
import java.time.LocalDateTime;
import java.util.Enumeration;

public class ExceptionUtil {
    private final static char COLON = ':';
    private final static char NEWLINES = '\n';
    private static String HOSTNAME;

    static {
        try {
            HOSTNAME = InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
            HOSTNAME = "UnknownHost";
        }
    }

    public static String print(HttpServletRequest request, Throwable e) {
        StringBuffer sb = new StringBuffer();
        sb.append(NEWLINES).append(NEWLINES);
        sb.append("HostName").append(COLON).append(HOSTNAME).append(NEWLINES);
        sb.append("Time").append(COLON).append(LocalDateTime.now().toString()).append(NEWLINES);
        sb.append("Error").append(COLON).append(Throwables.getRootCause(e).getMessage()).append(NEWLINES);
        sb.append("Resources").append(COLON).append(request.getMethod()).append(" ").append(request.getRequestURI()).append(NEWLINES);

        sb.append("RequestParameters {").append(NEWLINES);
            request.getParameterMap().keySet().stream().forEach(k -> sb.append("\t").append(k).append(COLON).append(request.getParameter(k.toString())).append(NEWLINES));
        sb.append("}").append(NEWLINES);

        sb.append("RequestHeaders {").append(NEWLINES);
        Enumeration headers = request.getHeaderNames();
        while (headers.hasMoreElements()) {
            String key = headers.nextElement().toString();
            sb.append("\t").append(key).append(":").append(request.getHeader(key)).append(NEWLINES);
        }
        sb.append("}").append(NEWLINES);

        /*if (request.getMethod().equals(HttpMethod.POST.name())) {
            sb.append("Body {").append(NEWLINES);
            try {
                BufferedReader br = request.getReader();
                String str = null;
                while ((str = br.readLine()) != null) {
                    sb.append(str).append(NEWLINES);
                }
            } catch (Exception e1) {
                sb.append(e1.getMessage());
            }
            sb.append("}").append(NEWLINES);
        }
        sb.append(NEWLINES);*/

        sb.append(Throwables.getStackTraceAsString(e));
        return sb.toString();
    }
}
