package com.farinfo.benefit.utils;

import cn.hutool.core.date.DateUtil;
import org.joda.time.DateTime;

import java.time.*;
import java.time.temporal.ChronoField;
import java.util.concurrent.TimeUnit;

/**
 * Created by: 李浩洋 on 2020-04-17
 **/
public class TimeUtil{

    /**
     * 获取每天凌晨到现在的秒数
     */
    public static long getSeconds(){
        //午夜
        LocalTime midnight = LocalTime.MIDNIGHT;
        LocalDate today = LocalDate.now();
        LocalDateTime todayMidnight = LocalDateTime.of(today, midnight);
        LocalDateTime tomorrowMidnight = todayMidnight.plusDays(1);
        return TimeUnit.SECONDS.toSeconds(Duration.between(LocalDateTime.now(), tomorrowMidnight).getSeconds());
    }

    public static void main(String[] args) {
        getSeconds();
    }
}
