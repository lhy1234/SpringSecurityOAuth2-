package com.farinfo.benefit.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 公益活动进展表
 * </p>
 *
 * @author 
 * @since 2020-04-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("benefit_activity")
@ApiModel(value="BenefitActivity对象", description="公益活动进展表")
public class BenefitActivity implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "外键关联公益基础表")
    private Integer benefitId;

    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "进展创建时间")
    private Date createTime;

    @ApiModelProperty(value = "活动进展说明")
    private String remark;

    @ApiModelProperty(value = "活动图片地址逗号隔开")
    private String activePics;

    @ApiModelProperty(value = "排序号")
    private Integer sortNum;


}
