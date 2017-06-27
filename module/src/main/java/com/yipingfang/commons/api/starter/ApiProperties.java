package com.yipingfang.commons.api.starter;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * Api配置 <p>
 *
 * @author <a href="mailto:zhouhaichao@2008.sina.com">haiker</a>
 * @version 2017/6/26 下午9:20
 */
@Data
@ConfigurationProperties("spring.api")
public class ApiProperties {

    private String[] scanPackage;

    private Map<String,String> domain;

    private int newFixedThreadPool = 10;
}
