package com.farinfo.benefit.feignClient;

import com.farinfo.api.user.UserFacade;
import com.farinfo.api.user.dto.UserDTO;
import org.springframework.stereotype.Component;

/**
 * 用户服务熔断
 * Created by: 李浩洋 on 2020-04-27
 **/
@Component
public class UserFacadeFallback implements UserFacade {

    @Override
    public UserDTO getInfoById(int id) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(-1);
        userDTO.setRealName("默认用户");
        userDTO.setOrgName("默认机构");
        userDTO.setUserNick("默认昵称");
        userDTO.setWxHeadImgUrl("默认头像");
        return userDTO;
    }
}
