//package com.farinfo.benefit.resource.server;
//
//import org.springframework.security.oauth2.provider.OAuth2Authentication;
//import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
//import org.springframework.stereotype.Component;
//
//import java.util.Map;
//
///**
// * 定制 AccessToken 转换器，获取jwt token中的额外信息
// * Created by: 李浩洋 on 2020-04-24
// **/
//@Component
//public class CustomAccessTokenConverter extends DefaultAccessTokenConverter {
//
//    @Override
//    public OAuth2Authentication extractAuthentication(Map<String, ?> map) {
//        OAuth2Authentication authentication = super.extractAuthentication(map);
//        authentication.setDetails(map);
//        return authentication;
//    }
//}
