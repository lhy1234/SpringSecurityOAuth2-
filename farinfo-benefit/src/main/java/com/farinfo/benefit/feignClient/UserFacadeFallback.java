package com.farinfo.benefit.feignClient;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.farinfo.api.user.UserFacade;
import com.farinfo.api.user.dto.UserDTO;
import com.farinfo.benefit.enums.ErrorEnum;
import com.farinfo.benefit.exception.BusinessException;
import org.springframework.stereotype.Component;

/**
 * 用户服务熔断
 * Created by: 李浩洋 on 2020-04-27
 **/
@Component
public class UserFacadeFallback implements UserFacade {

    @Override
    public UserDTO getInfoById(int id) {
        throw new BusinessException(ErrorEnum.EXCEPTION);
    }
}
