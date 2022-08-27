package com.ciusyan.crm.common.shiro;


import com.ciusyan.crm.common.filter.ErrorFilter;
import com.ciusyan.crm.common.prop.CrmProperties;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroCfg {

    @Bean
    public Realm realm() {
        return new TokenRealm(new TokenMatcher());
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(Realm realm, CrmProperties properties) {

        ShiroFilterFactoryBean filterBean = new ShiroFilterFactoryBean();

        // 安全管理器【并且告诉使用上面realm】
        filterBean.setSecurityManager(new DefaultWebSecurityManager(realm));

        // 添加自定义的Filter
        Map<String, Filter> filters = new HashMap<>();
        filters.put("token", new TokenFilter());
        filterBean.setFilters(filters);

        Map<String, String> urlMap = new LinkedHashMap<>();

        // 放行登录
        urlMap.put("/users/login", "anon");

        // 放行接口文档
        urlMap.put("/swagger*/**", "anon");
        urlMap.put("/v2/api-docs/**", "anon");

        // 放行处理Filter内部异常的请求
        urlMap.put(ErrorFilter.ERROR_URI, "anon");

        // 放行获取资源的请求
        urlMap.put("/" + properties.getUpload().getUploadPath() + "**", "anon");

        // 其他请求使用自定义的filter
        urlMap.put("/**", "token");

        filterBean.setFilterChainDefinitionMap(urlMap);

        return filterBean;
    }

    /**
     * 解决：@RequiresPermissions导致控制器接口404
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator proxyCreator() {
        DefaultAdvisorAutoProxyCreator proxyCreator = new DefaultAdvisorAutoProxyCreator();
        proxyCreator.setUsePrefix(true);
        return proxyCreator;
    }

}
