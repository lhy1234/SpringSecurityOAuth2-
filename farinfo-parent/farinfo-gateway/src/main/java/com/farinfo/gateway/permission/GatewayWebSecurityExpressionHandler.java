package com.farinfo.gateway.permission;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;

import java.security.Permissions;

/**
 * 权限表达式处理器
 * Created by: 李浩洋 on 2020-04-21
 **/
@Component
public class GatewayWebSecurityExpressionHandler extends OAuth2WebSecurityExpressionHandler {

    @Autowired
    private PermissionService permissionService;

    /**
     * 创建评估上下文
     * @param authentication
     * @param invocation
     * @return
     */
    @Override
    protected StandardEvaluationContext createEvaluationContextInternal(Authentication authentication, FilterInvocation invocation) {
        //调用父类，创建一个标准的评估上下文
        StandardEvaluationContext sec = super.createEvaluationContextInternal(authentication,invocation);
        //GatewaySecurityConfig.configure(HttpSecurity http).access里面写的spring就认识了
        sec.setVariable("permissionService",permissionService);
        return sec;
    }
}
