package com.farinfo.benefit.beans.vo;

import com.farinfo.benefit.entity.BenefitRecommend;
import lombok.Data;

import java.util.List;

/**
 * 我的推荐列表VO
 * Created by: 李浩洋 on 2020-04-17
 **/
@Data
public class MyRecommendVO {

    private int benefitId;//活动id
    private String title;//活动名称


    List<BenefitRecommend> recommends;
}
