package com.farinfo.benefit.service;

import com.farinfo.benefit.beans.vo.MyRecommendVO;
import com.farinfo.benefit.entity.BenefitRecommend;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 公益推荐表 服务类
 * </p>
 *
 * @author 
 * @since 2020-04-17
 */
public interface BenefitRecommendService extends IService<BenefitRecommend> {

    /**
     * 查询我参与的活动次数
     * @param recommendId 推荐人id
     * @return
     */
    int countMyActivityNum(int recommendId);

    /**
     * 查询我推荐的人数
     * @param recommendId 推荐人id
     * @return
     */
    int countMyRecommendNum(int recommendId);

    /**
     * 查询我参与过的活动的id，name列表
     * @param recommendId
     * @return
     */
    List<MyRecommendVO> findMyRecommendVOListByRecommendId(int recommendId);

    /**
     * 查询我的推荐列表
     * @param recommendId 推荐人id
     * @param benefitId 公益活动id
     * @return
     */
    List<BenefitRecommend> findRecommendListByRecommendIdAndBenefitId(int recommendId, int benefitId);
}
