package com.farinfo.benefit.service;

import com.farinfo.api.user.UserApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * Created by: 李浩洋 on 2020-04-26
 **/
@FeignClient(name = "farinfo-user")
public interface UserConsumerService extends UserApi {




}
