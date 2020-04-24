package com.farinfo.benefit.service;

import com.farinfo.benefit.entity.ActivityVote;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 
 * @since 2020-04-17
 */
public interface ActivityVoteService extends IService<ActivityVote> {



    void vote(ActivityVote activityVote);
}
