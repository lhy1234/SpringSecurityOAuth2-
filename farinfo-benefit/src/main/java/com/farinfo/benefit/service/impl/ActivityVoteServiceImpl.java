package com.farinfo.benefit.service.impl;

import com.farinfo.benefit.entity.ActivityVote;
import com.farinfo.benefit.entity.BenefitRecommend;
import com.farinfo.benefit.mapper.ActivityVoteMapper;
import com.farinfo.benefit.service.ActivityVoteService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.farinfo.benefit.service.BenefitRecommendService;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 
 * @since 2020-04-17
 */
@Service
@Transactional
public class ActivityVoteServiceImpl extends ServiceImpl<ActivityVoteMapper, ActivityVote> implements ActivityVoteService {

    @Autowired
    private BenefitRecommendService benefitRecommendService;

    @Override
    public void vote(ActivityVote activityVote) {
        save(activityVote);
        //次数+1
        BenefitRecommend benefitRecommend = benefitRecommendService.getById(activityVote.getRecommendId());
        benefitRecommend.setTicketCount(benefitRecommend.getTicketCount()+1);
        benefitRecommendService.updateById(benefitRecommend);
    }
}
