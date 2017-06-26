package com.yipingfang.commons.exception.starter;

import com.yipingfang.commons.exception.ExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass({ExceptionHandler.class})
@EnableConfigurationProperties({SpringHandlerProperties.class})
@AutoConfigureBefore(WebMvcAutoConfiguration.class)
@Slf4j
public class SpringHandlerAutoConfiguration {

    private final SpringHandlerProperties properties;

    public SpringHandlerAutoConfiguration(SpringHandlerProperties properties) {
        this.properties = properties;
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "spring.handler", name = "enable", havingValue = "true", matchIfMissing = false)
    public ExceptionHandler exceptionHandler() {
        log.info("init default exception handler");
        return new ExceptionHandler(properties.getKey());
    }
}