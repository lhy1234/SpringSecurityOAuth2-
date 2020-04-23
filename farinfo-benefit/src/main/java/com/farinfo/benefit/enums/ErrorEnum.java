package com.farinfo.benefit.enums;

/**
 * Created by: 李浩洋 on 2020-04-17
 **/
public enum  ErrorEnum {

    SUCCESS(200, "成功"),
    FAIL(500, "失败"),


    ACTIVITY_NOTEXIST(300,"活动不存在"),

    ACTIVITY_END(301,"活动已结束"),
    ACTIVITY_VOTE_END(302,"活动投票已结束"),
    VOTE_LIMIT(303,"每天最多投3票"),


    ;
    private final int code;
    private final String msg;

    ErrorEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
