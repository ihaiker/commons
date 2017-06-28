package com.yipingfang.commons.api.starter;

import com.yipingfang.commons.api.BeanScannerConfigurer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import java.util.Map;

@Slf4j
@Configuration
@ConditionalOnClass({BeanScannerConfigurer.class})
@EnableConfigurationProperties({SpringApiProperties.class})
@AutoConfigureBefore(WebMvcAutoConfiguration.class)
public class SpringApiAutoConfiguration {

    @Bean
    @ConfigurationProperties("spring.api")
    public SpringApiProperties springApiProperties(Environment environment) {
        log.info("use spring api config");
        SpringApiProperties properties = new SpringApiProperties();
        //==================================================================================//
        {
            String scanPackage = environment.getProperty("spring.api.scanPackage");
            if (StringUtils.hasText(scanPackage)) {
                properties.setScanPackage(scanPackage.split(","));
            }
        }
        //==================================================================================//
        {
            String newFixedThreadPool = environment.getProperty("spring.api.newFixedThreadPool");
            if (StringUtils.hasText(newFixedThreadPool)) {
                properties.setNewFixedThreadPool(Integer.parseInt(newFixedThreadPool));
            }
        }
        //==================================================================================//
        {
            ((ConfigurableEnvironment)environment).getPropertySources().forEach(propertySource -> {
                
            });
            Map map = environment.getProperty("spring.api.domain", Map.class);
            System.out.println(map);
        }
        return properties;
    }

    @Bean
    @ConditionalOnMissingBean
    public BeanScannerConfigurer beanScannerConfigurer(SpringApiProperties springApiProperties) {
        log.info("api use config {}", springApiProperties);
        BeanScannerConfigurer configurer = new BeanScannerConfigurer();
        configurer.setSpringApiProperties(springApiProperties);
        return configurer;
    }
}