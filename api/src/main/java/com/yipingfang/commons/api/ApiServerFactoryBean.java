package com.yipingfang.commons.api;

import com.yipingfang.commons.api.annotation.Api;
import com.yipingfang.commons.api.starter.SpringApiProperties;
import lombok.Setter;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.StringUtils;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.concurrent.ExecutorService;

public class ApiServerFactoryBean<T> implements FactoryBean<T>, InitializingBean {
    private Class<?> serverClass;
    private Retrofit retrofit;
    @Setter String baseUrl;
    @Setter SpringApiProperties springApiProperties;
    @Setter ExecutorService executorService;

    public void setServerClass(String serverClass) throws ClassNotFoundException {
        this.serverClass = Class.forName(serverClass);
    }

    public T getObject() throws Exception {
        return (T) this.retrofit.create(serverClass);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //获取服务domain
        Api api = this.serverClass.getAnnotation(Api.class);
        String baseUrl = api.value();
        if (StringUtils.hasText(baseUrl)) {
            this.baseUrl = baseUrl;
        } else if (springApiProperties.getDomain() != null && !springApiProperties.getDomain().isEmpty()) {
            String domain = this.serverClass.getSimpleName();
            this.baseUrl = springApiProperties.getDomain().get(domain);
        }
        if (!StringUtils.hasText(baseUrl)) {
            throw new Exception("the bean " + this.serverClass.getName() + " @Api value is null");
        }

        this.retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .callbackExecutor(executorService)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(SynchronousCallAdapterFactory.create())
                .build();
    }

    public Class<?> getObjectType() {
        return this.serverClass;
    }

    public boolean isSingleton() {
        return true;
    }
}