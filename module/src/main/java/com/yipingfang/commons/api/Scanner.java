package com.yipingfang.commons.api;

import com.yipingfang.commons.api.annotation.Api;
import com.yipingfang.commons.api.starter.ApiProperties;
import lombok.Setter;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.env.Environment;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.util.Set;

public class Scanner extends ClassPathBeanDefinitionScanner {

    @Setter ApiProperties apiProperties;

    public Scanner(BeanDefinitionRegistry registry) {
        super(registry);
    }

    public void registerDefaultFilters() {
        this.addIncludeFilter(new AnnotationTypeFilter(Api.class));
    }

    public Set<BeanDefinitionHolder> doScan(String... basePackages) {
        Environment env = this.getEnvironment();
        Set<BeanDefinitionHolder> beanDefinitions = super.doScan(basePackages);
        for (BeanDefinitionHolder holder : beanDefinitions) {
            GenericBeanDefinition definition = (GenericBeanDefinition) holder.getBeanDefinition();
            definition.setBeanClass(ApiServerFactoryBean.class);
            definition.getPropertyValues().add("serverClass", definition.getBeanClassName());
            definition.getPropertyValues().add("apiProperties", apiProperties);
        }
        return beanDefinitions;
    }

    public boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        return beanDefinition.getMetadata().isInterface() && beanDefinition.getMetadata().hasAnnotation(Api.class.getName());
    }
}