package com.farinfo.benefit.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.farinfo.benefit.beans.Result;
import com.farinfo.benefit.beans.vo.BenefitDetailVO;
import com.farinfo.benefit.beans.vo.SimpleBenefitVO;
import com.farinfo.benefit.entity.Benefit;
import com.farinfo.benefit.entity.BenefitActivity;
import com.farinfo.benefit.entity.BenefitRecommend;
import com.farinfo.benefit.enums.AuditStatusEnum;
import com.farinfo.benefit.service.BenefitActivityService;
import com.farinfo.benefit.service.BenefitRecommendService;
import com.farinfo.benefit.service.BenefitService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.cert.ocsp.Req;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * <p>
 * 公益活动基础表 前端控制器
 * </p>
 *
 * @author 
 * @since 2020-04-17
 */
@Api(tags = "公益活动基础表")
@RestController
@RequestMapping("/benefit")
public class BenefitController {


    @Autowired
    private BenefitService benefitService;

    @Autowired
    private BenefitActivityService benefitActivityService;

    @Autowired
    private BenefitRecommendService benefitRecommendService;


    /**
     *  条件查询活动列表
     * @return
     */
    @ApiOperation("查询活动列表")
    @GetMapping("/list")
    public Result list(@RequestParam(defaultValue = "1") int pageNum,
                       @RequestParam(defaultValue = "10") int pageSize){
        Page<SimpleBenefitVO> page = new Page<>(pageNum,pageSize);
        IPage<SimpleBenefitVO> pageList = benefitService.pageQueryBenefitSimpleVO(page);
        return Result.ok(pageList);
    }

    /**
     * 最近活动
     * @return
     */
    @ApiOperation("查询最近的活动列表")
    @GetMapping("/lastActivity")
    public Result lastActivity(){
        Benefit lastActivity =  benefitService.getOne(new QueryWrapper<Benefit>().orderByDesc("create_time").last(" limit 1"));
        return Result.ok(lastActivity);
    }

    /**
     * TODO:获取是否是医生
     * 查询活动详情
     */
    @ApiOperation("查询活动详情")
    @GetMapping("/detail/{id}")
    public Result detail(HttpServletRequest request,@PathVariable("id") int id){
        Map<String,Object> resultMap = Maps.newHashMap();

        String rolesStr = (String)request.getAttribute("roles");
        if(StringUtils.contains(rolesStr,"4")){
            //是否是医生
            resultMap.put("isDoctor",1);
        }else {
            resultMap.put("isDoctor",0);
        }


        //活动基本信息
        Benefit benefit =  benefitService.getById(id);
        BenefitDetailVO benefitDetailVO = new BenefitDetailVO();
        BeanUtils.copyProperties(benefit,benefitDetailVO);
        if(benefit.getVoteEndTime() !=null && new Date().before(benefit.getVoteEndTime())){
            resultMap.put("canVote",1);
        }else{
            resultMap.put("canVote",0);
        }

        resultMap.put("benefit",benefitDetailVO);
        //查询活动进展
        List<BenefitActivity> benefitActivities = benefitActivityService.list(new QueryWrapper<BenefitActivity>().eq("benefit_id",id).orderByAsc("sort_num"));
        resultMap.put("progress",benefitActivities == null ? Collections.emptyList():benefitActivities);

        //医护人员
        List<BenefitRecommend> doctors = benefitRecommendService.list(new QueryWrapper<BenefitRecommend>()
                .eq("benefit_id",id)
                .eq("type",1)
                .eq("audit_status", AuditStatusEnum.NOPASS.value())
                .orderByAsc("create_time"));
        resultMap.put("doctors",doctors == null ? Collections.emptyList():doctors);
        //志愿者
        List<BenefitRecommend> subjects = benefitRecommendService.list(new QueryWrapper<BenefitRecommend>()
                .eq("benefit_id",id)
                .eq("type",2)
                .eq("audit_status",AuditStatusEnum.NOPASS.value())
                .orderByAsc("create_time"));
        resultMap.put("subjects",subjects == null ? Collections.emptyList():subjects);

        return Result.ok(resultMap);
    }
}

