package com.farinfo.training;

import com.farinfo.api.user.SysUser;
import com.farinfo.api.user.UserApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by: 李浩洋 on 2020-04-27
 **/
@RestController
@RequestMapping("/userConsumer")
public class UserConsumerController  {

    @Autowired
    UserFeignService userFeignService;


    @GetMapping("/getById")
    public SysUser getById(Long id) {
        return userFeignService.getById(id);
    }
}
