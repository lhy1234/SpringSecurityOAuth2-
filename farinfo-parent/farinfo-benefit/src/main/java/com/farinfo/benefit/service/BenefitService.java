package com.farinfo.benefit.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.farinfo.benefit.beans.vo.SimpleBenefitVO;
import com.farinfo.benefit.entity.Benefit;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 公益活动基础表 服务类
 * </p>
 *
 * @author 
 * @since 2020-04-17
 */
public interface BenefitService extends IService<Benefit> {

    /**
     * 分页查询活动简单信息列表（不包含活动说明）
     * @param page
     * @return
     */
    IPage<SimpleBenefitVO> pageQueryBenefitSimpleVO(Page<SimpleBenefitVO> page);

}
