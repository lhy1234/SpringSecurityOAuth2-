package com.farinfo.benefit.config;


import com.farinfo.benefit.interceptor.JwtVerifyInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by: 李浩洋 on 2020-04-24
 **/
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {




    @Autowired
    private JwtVerifyInterceptor jwtVerifyInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtVerifyInterceptor)
                .addPathPatterns("/**") // 静态资源
                .excludePathPatterns("/js/**", "/css/**", "/images/**", "/lib/**",
                        "/fonts/**")
                .excludePathPatterns("/swagger-resources/**", "/webjars/**",
                        "/v2/**", "/swagger-ui.html/**");
    }

//    private List<String> buildLogin(){
//        List<String> list = new ArrayList<>();
//        list.add("/benefitRecommend/**");
//        list.add("/activityVote/**");
//        return list;
//    }
//
//    private List<String> buildNotLogin(){
//        List<String> list = new ArrayList<>();
//        list.add("/benefit/**");
//        return list;
//    }
}
