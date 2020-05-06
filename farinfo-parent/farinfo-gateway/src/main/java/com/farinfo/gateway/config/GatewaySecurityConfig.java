package com.farinfo.gateway.config;

import com.farinfo.gateway.permission.GatewayAccessDeniedHandler;
import com.farinfo.gateway.permission.GatewayAuthenticationEntryPoint;
import com.farinfo.gateway.permission.GatewayWebSecurityExpressionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * 网关安全配置
 *
 * Created by: 李浩洋 on 2020-04-21
 **/
@Configuration
@EnableResourceServer //作为一个资源服务器存在
public class GatewaySecurityConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private GatewayWebSecurityExpressionHandler gatewayWebSecurityExpressionHandler;

    @Autowired
    private GatewayAuthenticationEntryPoint authenticationEntryPoint;
    @Autowired
    private GatewayAccessDeniedHandler accessDeniedHandler;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        //资源服务器id
        //resources.resourceId("gateway");
        resources
                .authenticationEntryPoint(authenticationEntryPoint)  //自定义处理401
                .accessDeniedHandler(accessDeniedHandler) //自定义403没有权限的处理逻辑
                //注入自己的 表达式处理器
                .expressionHandler(gatewayWebSecurityExpressionHandler);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/token/**").permitAll() //放过token开头的请求，是在申请令牌
                .anyRequest()
                //指定权限访问规则，permissionService需要自己实现，返回布尔值，true-能访问；false-无权限
                // 传进去2个参数，1-当前请求 ，2-当前用户
                .access("#permissionService.hasPermission(request,authentication)");
    }
}
