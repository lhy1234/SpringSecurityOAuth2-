package com.farinfo.benefit.beans.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 公益活动简单信息
 * Created by: 李浩洋 on 2020-04-20
 **/
@Data
@ApiModel(value="公益活动简单信息", description="公益活动简单信息")
public class SimpleBenefitVO {


    @ApiModelProperty(value = "主键")
    private Integer id;

    @ApiModelProperty(value = "公益名称")
    private String title;

    @ApiModelProperty(value = "轮播图片，多个逗号分隔")
    private String pics;

    @ApiModelProperty(value = "状态1进行中2已完成")
    private Integer status;

}
