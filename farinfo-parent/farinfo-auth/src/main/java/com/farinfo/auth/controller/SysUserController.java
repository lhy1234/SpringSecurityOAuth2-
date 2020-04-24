package com.farinfo.auth.controller;


import com.farinfo.auth.entity.SysUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
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
//@RequestMapping("/users")
public class SysUserController {

//    /**
//     * 定义用户信息
//     *
//     * @param authentication
//     * @return
//     */
//    @RequestMapping(value = "/user", method = RequestMethod.GET)
//    public ResponseEntity getUser(OAuth2Authentication authentication) {
//        Authentication oauth = authentication.getUserAuthentication();
//        User user = (User) oauth.getPrincipal();
//        return new ResponseEntity<>(new HashMap<String, Object>() {{
//            put("name", user.getName());
//            put("username", user.getUsername());
//            put("id", user.getId());
//            put("createAt", user.getCreateAt());
//            put("auth", user.getAuthorities());
//        }}, HttpStatus.OK);
//    }


    @GetMapping("/user")
    public SysUser me(OAuth2Authentication authentication){
        Authentication oauth = authentication.getUserAuthentication();
        SysUser user = (SysUser) oauth.getPrincipal();
        return user;
    }

}

