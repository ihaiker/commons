package com.yipingfang.commons.api.starter;

import com.yipingfang.commons.api.BeanScannerConfigurer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass({BeanScannerConfigurer.class})
@EnableConfigurationProperties({SpringApiProperties.class})
@AutoConfigureBefore(WebMvcAutoConfiguration.class)
@Slf4j
public class SpringApiAutoConfiguration {

    @Autowired
    SpringApiProperties springApiProperties;

    @Bean
    @ConditionalOnMissingBean
    public BeanScannerConfigurer beanScannerConfigurer() {
        BeanScannerConfigurer configurer = new BeanScannerConfigurer();
        configurer.setSpringApiProperties(this.springApiProperties);
        return configurer;
    }
}