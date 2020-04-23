package com.farinfo.benefit.controller;

import com.farinfo.benefit.beans.vo.MyRecommendVO;
import com.farinfo.benefit.entity.SysUser;
import com.farinfo.benefit.service.BenefitRecommendService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.farinfo.benefit.beans.Result;
import com.farinfo.benefit.entity.Benefit;
import com.farinfo.benefit.entity.BenefitRecommend;
import com.farinfo.benefit.enums.ErrorEnum;
import com.farinfo.benefit.service.BenefitService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * <p>
 * 公益推荐表 前端控制器
 * </p>
 *
 * @author 
 * @since 2020-04-17
 */

@Api(tags = "公益推荐表")
@RestController
@RequestMapping("/benefitRecommend")
public class BenefitRecommendController {

    @Autowired
    private BenefitRecommendService benefitRecommendService;

    @Autowired
    private BenefitService benefitService;



    /**
     * TODO：ID处理
     * @return
     */
    @ApiOperation("我的公益账户")
    @GetMapping("/myLoveAccount/{recommendId}")
    public Result myLoveAccount(@PathVariable("recommendId") int recommendId){

        Map<String,Object> resultMap = Maps.newHashMap();
        //参与基金活动(次)
        int activityNum = benefitRecommendService.countMyActivityNum(recommendId);
        //推荐人次(人)
        int  recommendNum = benefitRecommendService.countMyRecommendNum(recommendId);
        resultMap.put("activityNum",activityNum);
        resultMap.put("recommendNum",recommendNum);

        //查询我参与过的公益列表
        List<MyRecommendVO> benefitList = benefitRecommendService.findMyRecommendVOListByRecommendId(recommendId);
        if(benefitList != null && !benefitList.isEmpty()){
            for(MyRecommendVO vo : benefitList){
                //查询每次活动我推荐的志愿者和医护人员列表
                List<BenefitRecommend> recommends = benefitRecommendService.findRecommendListByRecommendIdAndBenefitId(recommendId,vo.getBenefitId());
                vo.setRecommends(recommends);
            }
            resultMap.put("list",benefitList);
        }
        return Result.ok(resultMap);
    }

    /**
     * 添加推荐
     * todo:需要获取到推荐人id,机构name
     * @param
     * @return
     */
    @ApiOperation("推荐医护人员")
    @PostMapping("/recommendDoctor")
    public Result recommendDoctor(@RequestBody List<BenefitRecommend> entityList){
        if(entityList != null && !entityList.isEmpty()){
            for(BenefitRecommend entity :  entityList){
                entity.setType(1);
                entity.setCreateTime(new Date());
                entity.setTicketCount(0);
            }
            benefitRecommendService.saveBatch(entityList);
            return Result.ok("保存成功");
        }
        return Result.error("保存失败");
    }

    @ApiOperation("推荐志愿者")
    @PostMapping("/recommendSubject")
    public Result save(@RequestBody List<BenefitRecommend> entityList){
        if(entityList != null && !entityList.isEmpty()){
            for(BenefitRecommend entity :  entityList){
                entity.setType(2);
                entity.setCreateTime(new Date());
                entity.setTicketCount(0);
            }
            benefitRecommendService.saveBatch(entityList);
            return Result.ok("保存成功");
        }
        return Result.ok();
    }


    /**
     * Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
     * String currentPrincipalName = authentication.getName();
     * 我的推荐
     * @return
     */
    @ApiOperation("我的推荐")
    @GetMapping("/myRecommend")
    public Result myRecommend(@AuthenticationPrincipal SysUser sysUser){

//        List<BenefitRecommend> doctors = benefitRecommendService.list(new QueryWrapper<BenefitRecommend>()
//                .eq("recommend_id",userId)
//                .eq("type",1)
//                .orderByDesc("create_time"));
//        List<BenefitRecommend> subjects = benefitRecommendService.list(new QueryWrapper<BenefitRecommend>()
//                .eq("recommend_id",userId)
//                .eq("type",2)
//                .orderByDesc("create_time"));
//        Map<String,Object> resultMap = Maps.newHashMap();
//        resultMap.put("doctors",doctors);
//        resultMap.put("subjects",subjects);
//        return Result.ok(resultMap);
        System.err.println(sysUser);
        return Result.ok(sysUser);
    }

    @ApiOperation("投票医护列表")
    @GetMapping("/voteList/{benefitId}")
    public Result voteList(@PathVariable("benefitId") int benefitId){
        //校验活动是否结束投票
        Benefit benefit = benefitService.getById(benefitId);
        if(benefit == null){
            return Result.error(ErrorEnum.ACTIVITY_NOTEXIST);
        }
        if(benefit.getStatus() == 2 || benefit.getVoteEndTime().before(new Date())){
            return Result.error(ErrorEnum.ACTIVITY_VOTE_END);
        }
        List<BenefitRecommend> list = benefitRecommendService.list(new QueryWrapper<BenefitRecommend>()
                .eq("benefit_id",benefitId).eq("type",1).orderByDesc("ticket_count"));

        return Result.ok(list);
    }




}

