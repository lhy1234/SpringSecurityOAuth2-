package com.farinfo.benefit.controller;

import com.farinfo.benefit.beans.dto.Course;
import com.farinfo.benefit.entity.SysUser;
import com.farinfo.benefit.service.UserConsumerService;
import com.farinfo.vo.LoginUser;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.sql.Ref;
import java.util.*;

/**
 * Created by: 李浩洋 on 2020-04-23
 **/
@RestController
@RequestMapping("/testCourse")
public class TestCourseController {


    @Autowired
    private UserConsumerService userConsumerService;

    //OAuth2RestTemplate：
    //会从请求的上下文里拿到令牌，放到请求头里，发出去。
//    @Autowired
//    private OAuth2RestTemplate oAuth2RestTemplate;
//
//
//    @GetMapping("getCourse/{id}")
//    public Course getCourse(HttpServletRequest request,@PathVariable("id") int id, @AuthenticationPrincipal String username){
//        System.err.println("公益 ====username ："+username);
//        System.err.println("request："+request.getHeader("Authorization"));
//        System.err.println("oAuth2RestTemplate------"+oAuth2RestTemplate.getAccessToken());
//        Course course = oAuth2RestTemplate.getForObject("http://localhost:8004/course/getById/"+id,Course.class);
//        System.err.println(course.toString());
//        return course;
//    }


    @GetMapping("/me")
    public Object userMe(){

        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        System.err.println(ReflectionToStringBuilder.toString(loginUser));
        return loginUser;
    }

    @GetMapping(value = "/auth")
    public Object authDemo(OAuth2Authentication auth) {
        // 获取当前用户资源

        ////////////身份 ///////////
        String principal = (String)auth.getPrincipal();
        System.err.println("OAuth2Authentication.getPrincipal() : "+principal);


        ////////////身份 的name ///////////

        String principalName = auth.getName();
        System.err.println("Authentication.getName() : " +principalName);

        ////////////token 带的权限 ///////////
        List<String> authorities = new ArrayList();
        auth.getAuthorities().stream().forEach(s ->authorities.add(((GrantedAuthority) s).getAuthority()));
        System.err.println("token的权限信息 : " +ReflectionToStringBuilder.toString(authorities));

        ///////////////pwd //////////////
        String pwd = (String)auth.getCredentials();
        System.err.println("getCredentials : " +pwd);


        /////////////////////
        /////////////// 当前用户的详细信息 getDetails //////////////
        LoginUser loginUser = new LoginUser();

        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails)auth.getDetails();
        LinkedHashMap<String,Object> linkedHashMap = (LinkedHashMap)details.getDecodedDetails();
        long userId = (long)linkedHashMap.get("user_id");
        String username = (String)linkedHashMap.get("user_name");
        loginUser.setId(userId);
        loginUser.setUsername(username);
        System.err.println("getDetails  : " +ReflectionToStringBuilder.toString(details));


        return ReflectionToStringBuilder.toString(auth);
    }


    @GetMapping(value = "/getSysUser")
    public com.farinfo.api.user.SysUser getSysUser() {
        com.farinfo.api.user.SysUser sysUser = userConsumerService.getById(101L);
        return sysUser;
    }
}
