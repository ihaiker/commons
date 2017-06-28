package com.yipingfang.commons.api;

import com.yipingfang.commons.api.annotation.Api;
import com.yipingfang.commons.api.starter.SpringApiProperties;
import lombok.Setter;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.env.Environment;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Scanner extends ClassPathBeanDefinitionScanner {

    @Setter
    SpringApiProperties springApiProperties;

    public Scanner(BeanDefinitionRegistry registry) {
        super(registry);
    }

    public void registerDefaultFilters() {
        this.addIncludeFilter(new AnnotationTypeFilter(Api.class));
    }

    public Set<BeanDefinitionHolder> doScan(String... basePackages) {
        ExecutorService executorService = Executors.newFixedThreadPool(this.springApiProperties.getNewFixedThreadPool());

        Environment env = this.getEnvironment();
        Set<BeanDefinitionHolder> beanDefinitions = super.doScan(basePackages);
        for (BeanDefinitionHolder holder : beanDefinitions) {
            GenericBeanDefinition definition = (GenericBeanDefinition) holder.getBeanDefinition();
            definition.setBeanClass(ApiServerFactoryBean.class);
            definition.getPropertyValues().add("serverClass", definition.getBeanClassName());
            definition.getPropertyValues().add("springApiProperties", springApiProperties);
            definition.getPropertyValues().add("executorService",executorService);
        }
        return beanDefinitions;
    }

    public boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        return beanDefinition.getMetadata().isInterface() && beanDefinition.getMetadata().hasAnnotation(Api.class.getName());
    }
}