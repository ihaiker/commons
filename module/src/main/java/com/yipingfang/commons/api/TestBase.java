package com.yipingfang.commons.api;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * <p>
 *
 * @author <a href="mailto:zhouhaichao@2008.sina.com">haiker</a>
 * @version 2017/6/27 下午4:52
 */
@Configuration
public class TestBase {

    protected AnnotationConfigApplicationContext applicationContext;

    public void init() throws Exception {
        applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(TestBase.class);
        applicationContext.refresh();
    }

    @Component
    public static class TestBeanScanner extends BeanScannerConfigurer{
    }
}
