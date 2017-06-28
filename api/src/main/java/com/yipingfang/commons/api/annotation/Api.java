package com.yipingfang.commons.api.annotation;

import java.lang.annotation.*;

/**
 * 注解Api <p>
 * 如果配置了value直接使用value地址。
 * 否则从配置获取 在application.properites中获取配置<br>
 * 例如：用户服务API UserApi
 * 需在在application.properties中配置 spring.api.domain.UserApi=http://user.yipingfang.com
 *
 * @author <a href="mailto:zhouhaichao@2008.sina.com">haiker</a>
 * @version 2017/6/26 下午4:42
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Api {
    /**
     * API的接口domain
     */
    String value() default  "";
}
