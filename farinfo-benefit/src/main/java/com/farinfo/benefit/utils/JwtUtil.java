package com.farinfo.benefit.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

/**
 * Jwt工具类
 * Create by yzj on 2019/05/18
 */
@Configuration
public class JwtUtil {

    @Value("${jwt.config.key}")
    private String key;
    @Value("${jwt.config.ttl}")
    private Long ttl;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Long getTtl() {
        return ttl;
    }

    public void setTtl(Long ttl) {
        this.ttl = ttl;
    }

    /**
     * 签发token
     * @param id
     * @param subject
     * @param
     * @return
     */
    public String createJwt(String id,String subject){
        long nowMillis=System.currentTimeMillis();
        Date now=new Date(nowMillis);
        JwtBuilder jwtBuilder= Jwts.builder().setId(id)
                .setSubject(subject)
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS256,key);
        if(ttl>0){
            jwtBuilder.setExpiration(new Date(nowMillis+ttl));
        }
        return jwtBuilder.compact();
    }

    /**
     * 解析token
     * @param token
     * @return
     */
    public Claims parseJwt(String token){
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();
    }


    public static void main(String[] args) {
        JwtUtil jwtUtil = new JwtUtil();
        jwtUtil.setKey("Farinfo520");
        jwtUtil.setTtl(100000000000L);
        String jwt = jwtUtil.createJwt("273","1，4，5");
        System.err.println(jwt);
        //eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIyNzMiLCJzdWIiOiIx77yMNO-8jDUiLCJpYXQiOjE1ODc5NjU5NjksImV4cCI6MTY4Nzk2NTk2OX0.mnwzRjFTXiiLoCjWYYYr-l8FxhexiMdcuUGtvbjxaT0
    }


}
