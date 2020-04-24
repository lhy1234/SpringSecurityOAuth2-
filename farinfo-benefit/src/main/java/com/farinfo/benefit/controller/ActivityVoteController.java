package com.farinfo.benefit.controller;


import com.farinfo.benefit.beans.Result;
import com.farinfo.benefit.entity.ActivityVote;
import com.farinfo.benefit.entity.Benefit;
import com.farinfo.benefit.entity.BenefitRecommend;
import com.farinfo.benefit.enums.ErrorEnum;
import com.farinfo.benefit.service.ActivityVoteService;
import com.farinfo.benefit.service.BenefitService;
import com.farinfo.benefit.utils.RedisUtil;
import com.farinfo.benefit.utils.SysConstants;
import com.farinfo.benefit.utils.TimeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

/**
 * <p>
 *  活动投票 前端控制器
 * </p>
 *
 * @author 
 * @since 2020-04-17
 */
@Api(tags = "活动投票")
@RestController
@RequestMapping("/activityVote")
public class ActivityVoteController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private ActivityVoteService activityVoteService;

    @Autowired
    private BenefitService benefitService;


    /**
     * 投票
     * 投票结束时间到了&活动结束 不能投票
     * 一个人一天投三次
     * TODO:获取用户信息 用户id。
     *
     * @return
     */
    @ApiOperation("投票")
    @PostMapping("/vote")
    public Result vote(@RequestBody ActivityVote entity){
        Benefit benefit = benefitService.getById(entity.getBenefitId());
        if(benefit == null){
            return Result.error(ErrorEnum.ACTIVITY_NOTEXIST);
        }
        if(benefit.getStatus() == 2){
            return Result.error(ErrorEnum.ACTIVITY_END);
        }
        if(benefit.getVoteEndTime().before(new Date())){
            return Result.error(ErrorEnum.ACTIVITY_VOTE_END);
        }
        //次数限制
        String redisKey = SysConstants.VOTE_KEY+entity.getBenefitId()+":"+entity.getRecommendId();
        Integer todayNum = (Integer)redisUtil.get(redisKey);
        if(todayNum != null && todayNum == 3){
            return Result.error(ErrorEnum.VOTE_LIMIT);
        }
        long voteNum = redisUtil.incr(redisKey,1);
        redisUtil.expire(redisKey, TimeUtil.getSeconds());
        entity.setCreateTime(new Date());
        activityVoteService.save(entity);
        return Result.ok((3-voteNum)); //返回剩余次数
    }

}

