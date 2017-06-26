package com.yipingfang.commons.exception.starter;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("spring.handler")
public class SpringHandlerProperties {
    //是否起效
    private boolean enable = false;
    private String key = "";
}