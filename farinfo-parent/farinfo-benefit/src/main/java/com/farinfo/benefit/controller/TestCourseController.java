package com.farinfo.benefit.controller;

import com.farinfo.benefit.beans.dto.Course;
import com.farinfo.benefit.entity.SysUser;
import com.farinfo.benefit.service.UserConsumerService;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

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
    public SysUser userMe(@AuthenticationPrincipal SysUser sysUser,@AuthenticationPrincipal(expression = "#this.id") int id){

        System.err.println("公益 ====sysUser ："+sysUser);
        System.err.println("公益 ====userId ："+id);
        return sysUser;
    }

    @GetMapping(value = "/auth")
    public String authDemo(OAuth2Authentication auth) {
        // 获取当前用户资源
        Map user = (Map) auth.getPrincipal();

        return ReflectionToStringBuilder.toString(user);
    }


    @GetMapping(value = "/getSysUser")
    public com.farinfo.api.user.SysUser getSysUser() {
        com.farinfo.api.user.SysUser sysUser = userConsumerService.getById(101L);
        return sysUser;
    }
}
