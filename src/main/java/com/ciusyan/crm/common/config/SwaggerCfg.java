package com.ciusyan.crm.common.config;


import com.ciusyan.crm.common.shiro.TokenFilter;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class SwaggerCfg implements InitializingBean {

    @Autowired
    private Environment environment;
    private boolean enable;

    @Bean
    public Docket userDocket() {
        return groupDocket(
                "01_用户",                          // 分组模块
                "/users.*",                         // 正则表达式，想要的模块。
                "用户模块文档",                     // 模块标题
                "登录，注册，修改信息");       // 描述信息
    }

    @Bean
    public Docket resDocket() {
        return groupDocket(
                "02_角色、资源、部门",
                "/(resources.*|roles.*|departments.*)",
                "【角色、资源、部门】模块文档",
                "资源树，父目录");
    }


    @Bean
    public Docket goodsDocket() {
        return groupDocket(
                "03_商品",
                "/(goods.*|categories.*)",
                "商品【种类、信息】文档",
                "商品列表"
        );
    }

    private Docket groupDocket(String group,
                               String regex,
                               String title,
                               String description) {

        return baseDocket()
                .groupName(group)
                .apiInfo(apiInfo(title, description))
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.regex(regex))
                .build();
    }

    private Docket baseDocket() {
        // 每个接口都要传token
        RequestParameter token = new RequestParameterBuilder()
                .name(TokenFilter.HEADER_TOKEN)
                .description("用户登录令牌")
                .in(ParameterType.HEADER)
                .build();

        return new Docket(DocumentationType.SWAGGER_2)
                // .globalRequestParameters(List.of(token))
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts())
                .ignoredParameterTypes(
                        HttpSession.class,
                        HttpServletRequest.class,
                        HttpServletResponse.class
                ).enable(enable);
    }

    private ApiInfo apiInfo(String title, String description) {
        return new ApiInfoBuilder()
                .title(title)
                .description(description)
                .version("1.0.0")
                .build();
    }

    // 配置只能在 dev 和 test环境使用文档
    @Override
    public void afterPropertiesSet() throws Exception {
        enable = environment.acceptsProfiles(Profiles.of("dev", "test"));
    }

    // 配置全局的token
    private List<SecurityScheme> securitySchemes() {
        List<SecurityScheme> apiKeyList= new ArrayList<>();
        apiKeyList.add(new ApiKey("Token", "Token", "header"));
        return apiKeyList;
    }

    private List<SecurityContext> securityContexts() {
        List<SecurityContext> securityContexts=new ArrayList<>();
        securityContexts.add(
                SecurityContext.builder()
                        .securityReferences(defaultAuth())
                        // 所有包含"auth"的接口不需要使用securitySchemes
                        .forPaths(PathSelectors.regex("^(?!auth).*$"))
                        .build());
        return securityContexts;
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        List<SecurityReference> securityReferences=new ArrayList<>();
        securityReferences.add(new SecurityReference("Token", authorizationScopes));
        return securityReferences;
    }

}
