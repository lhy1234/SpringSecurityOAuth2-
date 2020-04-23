package com.farinfo.benefit.beans.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by: 李浩洋 on 2020-04-23
 **/
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Course {

    private Integer id;
    private String courseName;

    private Integer money;
}
