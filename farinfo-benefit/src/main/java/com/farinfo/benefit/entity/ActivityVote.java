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
 * 
 * </p>
 *
 * @author 
 * @since 2020-04-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("activity_vote")
@ApiModel(value="ActivityVote对象", description="")
public class ActivityVote implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "活动id")
    private Integer benefitId;

    @ApiModelProperty(value = "推荐表id")
    private Integer recommendId;

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    private Date createTime;


}
