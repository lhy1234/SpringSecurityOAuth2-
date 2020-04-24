package com.farinfo.benefit.interceptor;

import com.farinfo.benefit.beans.Result;
import com.farinfo.benefit.enums.ErrorEnum;
import com.farinfo.benefit.utils.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * jwt验签
 * Created by: 李浩洋 on 2020-04-24
 **/
@Slf4j
@Component
public class JwtVerifyInterceptor extends HandlerInterceptorAdapter {




    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private ObjectMapper objectMapper;




    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/javascript; charset=utf-8");

        final String token = request.getHeader("Authentication");
        if(StringUtils.isBlank(token)){
            log.info("[登录拦截器] 请求头 未获取到token:{} ",token);
            response.getWriter().write(objectMapper.writeValueAsString(buildNotLogin()));
            return false;
        }
        try{
            Claims claims = jwtUtil.parseJwt(token);
            if(claims == null){
                response.getWriter().write(objectMapper.writeValueAsString(buildTokenParseError()));
                return false;
            }
            request.setAttribute("userId",claims.get("jti"));
            request.setAttribute("loginName",claims.get("sub"));
            return true;
        }catch (Exception e){
            log.error("[登录拦截器] token 解析失败 ，token:{}",e);
            response.getWriter().write(objectMapper.writeValueAsString(buildTokenParseError()));
            return false;
        }
    }

    //没有token
    public Result buildNotLogin(){
        return Result.error("未找到token");
    }
    //token解析失败
    public Result buildTokenParseError(){
        return  Result.error("token解析失败");
    }


}
