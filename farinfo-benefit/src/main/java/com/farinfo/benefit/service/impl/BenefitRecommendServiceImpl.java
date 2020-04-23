package com.farinfo.benefit.service.impl;

import com.farinfo.benefit.beans.vo.MyRecommendVO;
import com.farinfo.benefit.entity.BenefitRecommend;
import com.farinfo.benefit.mapper.BenefitMapper;
import com.farinfo.benefit.mapper.BenefitRecommendMapper;
import com.farinfo.benefit.service.BenefitRecommendService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 公益推荐表 服务实现类
 * </p>
 *
 * @author 
 * @since 2020-04-17
 */
@Service
@Transactional
public class BenefitRecommendServiceImpl extends ServiceImpl<BenefitRecommendMapper, BenefitRecommend> implements BenefitRecommendService {

    @Autowired
    private BenefitRecommendMapper benefitRecommendMapper;

    @Override
    public List<MyRecommendVO> findMyRecommendVOListByRecommendId(int recommendId) {
        List<MyRecommendVO> list = benefitRecommendMapper.findMyRecommendVOListByRecommendId(recommendId);
        return list!=null?list: Collections.emptyList();
    }

    @Override
    public List<BenefitRecommend> findRecommendListByRecommendIdAndBenefitId(int recommendId, int benefitId) {
        List<BenefitRecommend> list =  benefitRecommendMapper.findRecommendListByRecommendIdAndBenefitId(recommendId,benefitId);
        return list!=null?list: Collections.emptyList();
    }

    @Override
    public int countMyActivityNum(int recommendId) {
        return benefitRecommendMapper.countMyActivityNum(recommendId);
    }

    @Override
    public int countMyRecommendNum(int recommendId) {
        return benefitRecommendMapper.countMyRecommendNum(recommendId);
    }
}
