package com.farinfo.benefit.feignClient;

import com.farinfo.api.user.UserFacade;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * Created by: 李浩洋 on 2020-04-26
 **/
@FeignClient("microsoft-cs520")
public interface UserFacadeImpl extends UserFacade {
}
