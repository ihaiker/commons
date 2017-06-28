package com.yipingfang.commons.api.annotation;

import java.lang.annotation.*;

/**
 * 返回http code的注解标识<p>
 *
 * @author <a href="mailto:zhouhaichao@2008.sina.com">haiker</a>
 * @version 2017/6/27 下午4:26
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ReturnStatus {

}
