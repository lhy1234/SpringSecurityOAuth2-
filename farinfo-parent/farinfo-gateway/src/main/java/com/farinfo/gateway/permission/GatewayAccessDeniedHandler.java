package com.farinfo.gateway.permission;

import com.alibaba.druid.support.json.JSONUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * 自定义403异常处理器，可以自定义响应信息
 * Created by: 李浩洋 on 2020-04-22
 **/
@Component
public class GatewayAccessDeniedHandler extends OAuth2AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException authException) throws IOException, ServletException {
        //super.handle(request, response, authException); //默认处理
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("status",403);
        resultMap.put("msg","sorry! 403");
        response.getWriter().write(JSONUtils.toJSONString(resultMap));
    }
}
