package com.yipingfang.commons.api.starter;

import com.yipingfang.commons.api.BeanScannerConfigurer;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@ConditionalOnClass({BeanScannerConfigurer.class})
@EnableConfigurationProperties({ApiProperties.class})
public class ApiAutoConfiguration implements ApplicationContextAware {

    @Setter ApiProperties apiProperties;
    @Setter ApplicationContext applicationContext;


    public ApiAutoConfiguration(){}
    public ApiAutoConfiguration(ApiProperties apiProperties){
        this.apiProperties = apiProperties;
    }

    @Bean
    @ConditionalOnMissingBean
    public BeanScannerConfigurer beanScannerConfigurer() {
        BeanScannerConfigurer configurer = new BeanScannerConfigurer();
        configurer.setApiProperties(apiProperties);
        configurer.setApplicationContext(applicationContext);
        return configurer;
    }

}