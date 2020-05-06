package com.farinfo.user.controller;

import com.farinfo.api.user.SysUser;
import com.farinfo.api.user.UserApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by: 李浩洋 on 2020-04-26
 **/
@RestController
public class UserController implements UserApi {



    @Override
    public SysUser getById(Long id) {
        SysUser sysUser = new SysUser();
        sysUser.setId(id);
        sysUser.setUsername("xxxooo");
        return sysUser;
    }
}
