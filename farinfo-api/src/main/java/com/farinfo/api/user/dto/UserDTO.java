package com.farinfo.api.user.dto;

import lombok.Data;

/**
 * Created by: 李浩洋 on 2020-04-26
 **/
@Data
public class UserDTO {


    private int id;

    private String userNick;

    private String wxNick;

    private int sex;

    private String realName;

    private String wxHeadImgUrl;


    private String orgName;

}
