package com.farinfo.benefit.enums;

/**
 * Created by: 李浩洋 on 2020-04-24
 **/
public enum AuditStatusEnum {


    INIT(0,"待审核"),
    PASS(1,"通过"),
    NOPASS(2,"不通过"),

    ;

    private final int value;
    private final String text;

    AuditStatusEnum(int value, String text) {
        this.value = value;
        this.text = text;
    }

    public int value() {
        return value;
    }

    public String text() {
        return text;
    }
}
