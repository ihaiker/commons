package com.yipingfang.commons.api;

import com.yipingfang.commons.api.starter.ApiProperties;
import lombok.Setter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class BeanScannerConfigurer implements BeanFactoryPostProcessor, ApplicationContextAware {

    @Setter
    ApplicationContext applicationContext;
    @Setter
    ApiProperties apiProperties;

    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        Scanner scanner = new Scanner((BeanDefinitionRegistry) beanFactory);
        scanner.setApiProperties(this.apiProperties);
        scanner.setResourceLoader(this.applicationContext);
        scanner.scan(apiProperties.getScanPackage());
    }

}
