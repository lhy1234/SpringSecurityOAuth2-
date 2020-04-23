package com.farinfo.auth.controller;


import com.farinfo.auth.entity.SysUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 
 * @since 2020-04-22
 */
@RestController
@RequestMapping("/users")
public class SysUserController {


    @GetMapping("/me")
    public SysUser me(@AuthenticationPrincipal SysUser user){
        System.err.println("user = "+user);
        return user;
    }

}

