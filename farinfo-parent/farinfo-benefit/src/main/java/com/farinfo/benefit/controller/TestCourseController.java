package com.farinfo.benefit.controller;

import com.farinfo.benefit.beans.dto.Course;
import com.farinfo.benefit.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by: 李浩洋 on 2020-04-23
 **/
@RestController
@RequestMapping("/testCourse")
public class TestCourseController {

    //OAuth2RestTemplate：
    //会从请求的上下文里拿到令牌，放到请求头里，发出去。
    @Autowired
    private OAuth2RestTemplate oAuth2RestTemplate;


    @GetMapping("getCourse/{id}")
    public Course getCourse(HttpServletRequest request,@PathVariable("id") int id, @AuthenticationPrincipal String username){
        System.err.println("公益 ====username ："+username);
        System.err.println("request："+request.getHeader("Authorization"));
        System.err.println("oAuth2RestTemplate------"+oAuth2RestTemplate.getAccessToken());
        Course course = oAuth2RestTemplate.getForObject("http://localhost:8004/course/getById/"+id,Course.class);
        System.err.println(course.toString());
        return course;
    }


    @GetMapping("/me")
    public SysUser userMe(@AuthenticationPrincipal SysUser sysUser){
        System.err.println("公益 ====sysUser ："+sysUser);
        return sysUser;
    }
}
