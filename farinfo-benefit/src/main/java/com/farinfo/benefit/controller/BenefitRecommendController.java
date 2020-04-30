package com.farinfo.benefit.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.farinfo.api.user.dto.UserDTO;
import com.farinfo.benefit.beans.Result;
import com.farinfo.benefit.beans.vo.MyRecommendVO;
import com.farinfo.benefit.entity.Benefit;
import com.farinfo.benefit.entity.BenefitRecommend;
import com.farinfo.benefit.enums.ErrorEnum;
import com.farinfo.benefit.feignClient.UserFacadeImpl;
import com.farinfo.benefit.service.BenefitRecommendService;
import com.farinfo.benefit.service.BenefitService;
import com.farinfo.benefit.utils.UserHelper;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 公益推荐表 前端控制器
 * </p>
 *
 * @author
 * @since 2020-04-17
 */
@SuppressWarnings("all")
@Api(tags = "公益推荐表")
@RestController
@RequestMapping("/benefitRecommend")
public class BenefitRecommendController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private UserFacadeImpl userFacade;


    @Autowired
    private BenefitRecommendService benefitRecommendService;

    @Autowired
    private BenefitService benefitService;

    /**
     * 头像，姓名，身份（是否是医生）
     * @return
     */
    @ApiOperation("用户信息")
    @GetMapping("/userInfo")

    public Result userInfo() {
        Integer userId = UserHelper.getUserId(request);
        Map<String, Object> resultMap = Maps.newHashMap();
        //参与基金活动(次)
        int activityNum = benefitRecommendService.countMyActivityNum(userId);
        //推荐人次(人)
        int recommendNum = benefitRecommendService.countMyRecommendNum(userId);
        resultMap.put("activityNum", activityNum);
        resultMap.put("recommendNum", recommendNum);
        UserDTO userDTO = userFacade.getInfoById(userId);
        if(userDTO != null){
            resultMap.put("userNick", userDTO.getWxNick());
            resultMap.put("headImg", userDTO.getWxHeadImgUrl());
        }else {
            resultMap.put("userNick", "");
            resultMap.put("headImg", "");
        }
        return Result.ok(resultMap);
    }

    /**
     *
     *
     * @return
     */
    @ApiOperation("我的公益账户")
    @GetMapping("/myLoveAccount")
    public Result myLoveAccount() {
        Integer userId = UserHelper.getUserId(request);
        Map<String, Object> resultMap = Maps.newHashMap();
        //参与基金活动(次)
        int activityNum = benefitRecommendService.countMyActivityNum(userId);
        //推荐人次(人)
        int recommendNum = benefitRecommendService.countMyRecommendNum(userId);
        resultMap.put("activityNum", activityNum);
        resultMap.put("recommendNum", recommendNum);

        //查询我参与过的公益列表
        List<MyRecommendVO> benefitList = benefitRecommendService.findMyRecommendVOListByRecommendId(userId);
        if (benefitList != null && !benefitList.isEmpty()) {
            for (MyRecommendVO vo : benefitList) {
                //查询每次活动我推荐的志愿者和医护人员列表
                List<BenefitRecommend> recommends = benefitRecommendService.findRecommendListByRecommendIdAndBenefitId(userId, vo.getBenefitId());
                vo.setRecommends(recommends);
            }
            resultMap.put("list", benefitList);
        }
        return Result.ok(resultMap);
    }

    /**
     * 添加推荐
     * todo:需要获取到推荐人id,机构name
     *
     * @param
     * @return
     */
    @ApiOperation("推荐医护人员")
    @PostMapping("/recommendDoctor")
    //public Result recommendDoctor(@RequestBody List<BenefitRecommend> entityList){
    public Result recommendDoctor(@RequestBody Map<String, List<BenefitRecommend>> paramMap) throws Exception{

        Integer userId = UserHelper.getUserId(request);
        UserDTO userDTO = userFacade.getInfoById(userId);
        String orgName = userDTO.getOrgName();
        List<BenefitRecommend> entityList = paramMap.get("entityList");
        if (entityList != null && !entityList.isEmpty()) {
            for (BenefitRecommend entity : entityList) {
                entity.setOrgName(orgName);
                entity.setType(1);
                entity.setCreateTime(new Date());
                entity.setTicketCount(0);
                entity.setRecommendId(userId);
            }
            benefitRecommendService.saveBatch(entityList);
            return Result.ok("保存成功");
        }
        return Result.error("保存失败");
    }

    @ApiOperation("推荐志愿者")
    @PostMapping("/recommendSubject")
//    public Result save(@RequestBody List<BenefitRecommend> entityList) {
    public Result recommendSubject(@RequestBody Map<String, List<BenefitRecommend>> paramMap) {
        Integer userId = UserHelper.getUserId(request);

        List<BenefitRecommend> entityList = paramMap.get("entityList");
        if (entityList != null && !entityList.isEmpty()) {
            for (BenefitRecommend entity : entityList) {
                entity.setOrgName("北京医科大学附属第三医院阜外医院第二分院");
                entity.setType(2);
                entity.setCreateTime(new Date());
                entity.setTicketCount(0);
                entity.setRecommendId(userId);
            }
            benefitRecommendService.saveBatch(entityList);
            return Result.ok("保存成功");
        }
        return Result.ok();
    }


    /**
     * 我的推荐
     *
     * @return
     */
    @ApiOperation("我的推荐")
    @GetMapping("/myRecommend")
    public Result myRecommend() {
        Integer userId = UserHelper.getUserId(request);

        List<BenefitRecommend> doctors = benefitRecommendService.list(new QueryWrapper<BenefitRecommend>()
                .eq("recommend_id", userId)
                .eq("type", 1)
                .orderByDesc("create_time"));
        List<BenefitRecommend> subjects = benefitRecommendService.list(new QueryWrapper<BenefitRecommend>()
                .eq("recommend_id", userId)
                .eq("type", 2)
                .orderByDesc("create_time"));
        Map<String, Object> resultMap = Maps.newHashMap();
        resultMap.put("doctors", doctors);
        resultMap.put("subjects", subjects);
        return Result.ok(resultMap);
    }

    @ApiOperation("投票医护列表")
    @GetMapping("/voteList/{benefitId}")
    public Result voteList(@PathVariable("benefitId") int benefitId) {
        //校验活动是否结束投票
        Benefit benefit = benefitService.getById(benefitId);
        if (benefit == null) {
            return Result.error(ErrorEnum.ACTIVITY_NOTEXIST);
        }
        if (benefit.getStatus() == 2 || benefit.getVoteEndTime().before(new Date())) {
            return Result.error(ErrorEnum.ACTIVITY_VOTE_END);
        }
        List<BenefitRecommend> list = benefitRecommendService.list(new QueryWrapper<BenefitRecommend>()
                .eq("benefit_id", benefitId).eq("type", 1).orderByDesc("ticket_count"));

        return Result.ok(list);
    }


}

