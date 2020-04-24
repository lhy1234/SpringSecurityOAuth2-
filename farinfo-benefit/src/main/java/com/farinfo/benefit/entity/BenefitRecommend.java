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
 * 公益推荐表
 * </p>
 *
 * @author 
 * @since 2020-04-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("benefit_recommend")
@ApiModel(value="BenefitRecommend对象", description="公益推荐表")
public class BenefitRecommend implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "外键关联公益基础表")
    private Integer benefitId;

    @ApiModelProperty(value = "姓名")
    private String realName;

    @ApiModelProperty(value = "机构名称")
    private String orgName;

    @ApiModelProperty(value = "科室名称")
    private String clinicalDepartment;

    @ApiModelProperty(value = "职务")
    private String duty;

    @ApiModelProperty(value = "个人情况说明")
    private String introduction;

    @ApiModelProperty(value = "个人照片")
    private String headPic;

    @ApiModelProperty(value = "类型1医护人员2志愿者")
    private Integer type;

    @ApiModelProperty(value = "外键关联推荐人")
    private Integer recommendId;

    @ApiModelProperty(value = "投票数(医护人员)")
    private Integer ticketCount;

    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    @ApiModelProperty(value = "金额")
    private String money;

    @ApiModelProperty(value = "审核状态0待审核1通过2不通过")
    private Integer auditStatus;

}
