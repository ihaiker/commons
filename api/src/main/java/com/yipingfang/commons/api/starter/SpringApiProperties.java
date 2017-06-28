package com.yipingfang.commons.api.starter;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Api配置 <p>
 *
 * @author <a href="mailto:zhouhaichao@2008.sina.com">haiker</a>
 * @version 2017/6/26 下午9:20
 */
@Data
@ToString
@ConfigurationProperties("spring.api")
public class SpringApiProperties {

    private String[] scanPackage = new String[]{"com.yipingfang.api"};

    private Map<String, String> domain = new HashMap<>();

    private int newFixedThreadPool = 10;
}
