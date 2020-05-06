package com.farinfo.benefit.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by: 李浩洋 on 2020-04-30
 **/
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(BusinessException.class)
    public Map<Integer,String> handleBusinessException(BusinessException e){
        Map<Integer,String> map = new HashMap<>();
        map.put(e.getCode(),e.getMsg());
        return map;
    }
}
