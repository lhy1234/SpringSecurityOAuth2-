package com.farinfo.auth.server;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.farinfo.auth.entity.SysUser;
import com.farinfo.auth.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedUserException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 *
 * Created by: 李浩洋 on 2020-04-21
 **/
@Component("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SysUserService userService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {



//        return User.withUsername(username)
//                .password(passwordEncoder.encode("123456"))
//                .authorities("ROLE_ADMIN") //权限
//                .build();//构建一个User对象
//        SecurityUser securityUser = new SecurityUser();
//        securityUser.setPassword(passwordEncoder.encode("123456"));

        SysUser sysUser = userService.getOne(new QueryWrapper<SysUser>().eq("username",username));
        if(sysUser == null){
            throw new UnauthorizedUserException("未认证");
        }
        return sysUser;
    }

    public static void main(String[] args) {
        PasswordEncoder passwordEncoder =  new BCryptPasswordEncoder();
        System.out.println(passwordEncoder.encode("123456"));
    }
}
