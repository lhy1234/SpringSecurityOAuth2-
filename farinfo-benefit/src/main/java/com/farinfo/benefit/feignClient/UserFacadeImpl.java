package com.farinfo.benefit.feignClient;

import com.farinfo.api.user.UserFacade;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * Created by: 李浩洋 on 2020-04-26
 **/
@FeignClient(name = "microsoft-cs520",fallback = UserFacadeFallback.class)
public interface UserFacadeImpl extends UserFacade {
}
