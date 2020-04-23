package com.farinfo.benefit.mapper;

import com.farinfo.benefit.beans.vo.MyRecommendVO;
import com.farinfo.benefit.entity.BenefitRecommend;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 公益推荐表 Mapper 接口
 * </p>
 *
 * @author 
 * @since 2020-04-17
 */
@Repository
public interface BenefitRecommendMapper extends BaseMapper<BenefitRecommend> {

    /**
     * 查询我参与过的活动的id，name列表
     * @param recommendId
     * @return
     */
    List<MyRecommendVO> findMyRecommendVOListByRecommendId(@Param("recommendId") int recommendId);

    /**
     * 查询我的推荐列表
     * @param recommendId 推荐人id
     * @param benefitId 公益活动id
     * @return
     */
    List<BenefitRecommend> findRecommendListByRecommendIdAndBenefitId(@Param("recommendId") int recommendId, @Param("benefitId") int benefitId);

    /**
     * 查询我参与的活动次数
     * @param recommendId 推荐人id
     * @return
     */
    int countMyActivityNum(@Param("recommendId") int recommendId);

    /**
     * 查询我推荐的人数
     * @param recommendId 推荐人id
     * @return
     */
    int countMyRecommendNum(@Param("recommendId") int recommendId);
}
