package com.farinfo.gateway.permission;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.http.AccessTokenRequiredException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * Created by: 李浩洋 on 2020-04-21
 **/
@Service
public class PermissionServiceImpl implements PermissionService{

    @Override
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        //查询数据库或者redis，处理权限
        System.err.println("2，权限过滤器："+request.getRequestURI());

        System.err.println("权限过滤器,当前用户："+ ReflectionToStringBuilder.toString(authentication));

        //如果是已认证的Authentication就是OAuth2Authentication
        //如果是没认证的Authentication就是AnonymousAuthenticationToken
        if(authentication instanceof AnonymousAuthenticationToken){
            //到这里说明没传令牌
            throw new AccessTokenRequiredException(null);
        }

        //这里模拟
        //return RandomUtils.nextInt() % 2 ==0;
        return true;
    }
}
