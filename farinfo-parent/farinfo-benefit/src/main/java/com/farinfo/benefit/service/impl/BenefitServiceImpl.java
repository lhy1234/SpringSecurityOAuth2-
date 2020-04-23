package com.farinfo.benefit.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.farinfo.benefit.beans.vo.SimpleBenefitVO;
import com.farinfo.benefit.entity.Benefit;
import com.farinfo.benefit.mapper.BenefitMapper;
import com.farinfo.benefit.service.BenefitService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 公益活动基础表 服务实现类
 * </p>
 *
 * @author 
 * @since 2020-04-17
 */
@Service
@Transactional
public class BenefitServiceImpl extends ServiceImpl<BenefitMapper, Benefit> implements BenefitService {

    @Autowired
    private BenefitMapper benefitMapper;

    @Override
    public IPage<SimpleBenefitVO> pageQueryBenefitSimpleVO(Page<SimpleBenefitVO> page) {
        return benefitMapper.pageQueryBenefitSimpleVO(page);
    }
}
