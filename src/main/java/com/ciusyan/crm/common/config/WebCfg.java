package com.ciusyan.crm.common.config;


import com.ciusyan.crm.common.filter.ErrorFilter;
import com.ciusyan.crm.common.prop.CrmProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;

@Configuration
public class WebCfg implements WebMvcConfigurer {

    @Autowired
    private CrmProperties properties;

    // 配置跨域请求
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(properties.getCfg().getCorsOrigins())
                .allowCredentials(true)
                .allowedHeaders("*")
                .exposedHeaders("Content-Type")
                .allowedMethods("GET", "POST");
    }


    // 在Spring中注册 Filter
    @Bean
    public FilterRegistrationBean<Filter> filterRegistrationBean() {
        FilterRegistrationBean<Filter> bean = new FilterRegistrationBean<>();

        // 设置Filter
        bean.setFilter(new ErrorFilter());
        bean.addUrlPatterns("/*");

        // 排在第一个
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }

}
