package com.farinfo.test;

import com.farinfo.api.user.UserFacade;
import com.farinfo.api.user.dto.UserDTO;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by: 李浩洋 on 2020-04-27
 **/
@RestController
public class UserController implements UserFacade {

    @Override
    public UserDTO getInfoById(int id) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(id);
        userDTO.setRealName("name-"+id);
        userDTO.setHeadImgUrl("headimg-"+id);
        return userDTO;
    }
}
