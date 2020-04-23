package com.farinfo.benefit.beans;


import com.farinfo.benefit.enums.ErrorEnum;
import lombok.Data;

import java.io.Serializable;

/**
 *   接口返回数据格式
 */
@Data
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Integer RS_200 = 200;

    private static final Integer RS_500 = 500;

    private static final Integer RS_403 = 403;


    /**
     * 返回处理消息
     */
    private String message = "成功";

    /**
     * 返回代码
     */
    private Integer code = RS_200;

    /**
     * 返回数据对象 data
     */
    private T result;

    /**
     * 时间戳
     */
    private long timestamp = System.currentTimeMillis();








    public Result() {

    }

    public Result<T> error500(String message) {
        this.message = message;
        this.code = 500;
        return this;
    }

    public Result<T> success(String message) {
        this.message = message;
        this.code = 200;
        return this;
    }


    public static Result<Object> ok() {
        Result<Object> r = new Result<Object>();
        r.setCode(RS_200);
        r.setMessage("成功");
        return r;
    }

    public static Result<Object> ok(String msg) {
        Result<Object> r = new Result<Object>();
        r.setCode(RS_200);
        r.setMessage(msg);
        return r;
    }

    public static Result<Object> ok(Object data) {
        Result<Object> r = new Result<Object>();
        r.setCode(RS_200);
        r.setResult(data);
        return r;
    }

    public static Result<Object> error(String msg) {
        return error(RS_500, msg);
    }


    public static Result<Object> error(ErrorEnum errorEnum) {
        Result<Object> r = new Result<Object>();
        r.setCode(errorEnum.getCode());
        r.setMessage(errorEnum.getMsg());
        return r;
    }

    public static Result<Object> error(int code, String msg) {
        Result<Object> r = new Result<Object>();
        r.setCode(code);
        r.setMessage(msg);
        return r;
    }

    /**
     * 无权限访问返回结果
     */
    public static Result<Object> sorry403(String msg) {
        return error(RS_403, msg);
    }
}