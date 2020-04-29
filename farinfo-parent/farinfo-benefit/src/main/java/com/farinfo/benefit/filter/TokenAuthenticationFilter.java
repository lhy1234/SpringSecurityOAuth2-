package com.farinfo.benefit.filter;

import com.farinfo.vo.LoginUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 定义filter拦截token，解析token中的参数，并形成Spring Security的Authentication对象
 * 这样在安全上下文就可以直接取到 自己的User信息
 * Created by: 李浩洋 on 2020-04-29
 **/
@Slf4j
@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //1.解析token

        /////////////// 当前用户的详细信息 getDetails //////////////
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if((authentication instanceof OAuth2Authentication)){ // 无token访问网关内资源的情况，目前仅有uua服务直接暴露
            OAuth2Authentication oauth2Authentication  = (OAuth2Authentication)authentication;

            LoginUser loginUser = new LoginUser();
            SecurityContextHolder.getContext().getAuthentication();
            OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails)oauth2Authentication.getDetails();
            LinkedHashMap<String,Object> linkedHashMap = (LinkedHashMap)details.getDecodedDetails();
            long userId = Long.valueOf(String.valueOf(linkedHashMap.get("user_id")));
            String username = (String)linkedHashMap.get("user_name");
            loginUser.setId(userId);
            loginUser.setUsername(username);

            //2.新建并填充authentication
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    loginUser, null, oauth2Authentication.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(
                    request));
            //3.将authentication保存进安全上下文
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        }

        filterChain.doFilter(request, response);
    }
}
