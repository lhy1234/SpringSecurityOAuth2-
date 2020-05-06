package com.farinfo.api.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/users")
public interface UserApi {


    /**
     * 通过id查询
     * @param id id
     * @return
     */
    @GetMapping("/{id}")
    SysUser getById(@PathVariable("id") Long id);


}
