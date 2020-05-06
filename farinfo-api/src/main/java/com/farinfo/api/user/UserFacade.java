package com.farinfo.api.user;

import com.farinfo.api.user.dto.UserDTO;
import org.springframework.web.bind.annotation.*;

/**
 * 用户服务
 * Created by: 李浩洋 on 2020-04-26
 **/

public interface UserFacade {

    @GetMapping("/users/getInfoById")
    UserDTO getInfoById(@RequestParam("id") int id);
}
