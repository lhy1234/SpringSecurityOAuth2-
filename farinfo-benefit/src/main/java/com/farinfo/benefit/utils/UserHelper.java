package com.farinfo.benefit.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by: 李浩洋 on 2020-04-24
 **/
public class UserHelper {




    public static Integer getUserId(HttpServletRequest request){
        Integer userId = Integer.parseInt((String)request.getAttribute("userId"));
        return userId;
    }
}
