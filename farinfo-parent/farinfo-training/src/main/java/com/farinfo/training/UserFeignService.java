package com.farinfo.training;

import com.farinfo.api.user.UserApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("farinfo-user")
public interface UserFeignService extends UserApi {
}
