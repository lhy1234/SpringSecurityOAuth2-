//package com.farinfo.training;
//
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
///**
// * 课程
// * Created by: 李浩洋 on 2020-04-23
// **/
//@RestController
//@RequestMapping("/course")
//public class CourseController {
//
//
//
//    @GetMapping("/getById/{id}")
//    public Course getById(@PathVariable("id") int id, @AuthenticationPrincipal String username){
//        Course course = new Course(id,"课程-"+id,100);
//        System.err.println("课程服务 username -"+username);
//        return course;
//    }
//}
