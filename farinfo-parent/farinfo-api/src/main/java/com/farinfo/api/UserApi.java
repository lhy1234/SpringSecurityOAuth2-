package com.farinfo.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/users")
public interface UserApi {


    @GetMapping("/{id}")
    public SysUser getById(@PathVariable("id") int id);


}
