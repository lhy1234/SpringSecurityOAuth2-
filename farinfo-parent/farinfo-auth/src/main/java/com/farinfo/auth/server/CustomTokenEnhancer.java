package com.farinfo.auth.server;

import com.farinfo.auth.entity.SysUser;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * token增强，往token写额外信息
 * Created by: 李浩洋 on 2020-04-23
 **/

public class CustomTokenEnhancer implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        SysUser sysUser = (SysUser)authentication.getPrincipal();
        //往token写额外信息
        Map<String,Object> extraInfo = new HashMap<>();
        extraInfo.put("user_id",sysUser.getId());

        ((DefaultOAuth2AccessToken)accessToken).setAdditionalInformation(extraInfo);
        return accessToken;
    }
}
