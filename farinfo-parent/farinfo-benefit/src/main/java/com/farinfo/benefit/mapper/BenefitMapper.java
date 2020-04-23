package com.farinfo.benefit.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.farinfo.benefit.beans.vo.MyRecommendVO;
import com.farinfo.benefit.beans.vo.SimpleBenefitVO;
import com.farinfo.benefit.entity.Benefit;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 公益活动基础表 Mapper 接口
 * </p>
 *
 * @author 
 * @since 2020-04-17
 */
@Repository
public interface BenefitMapper extends BaseMapper<Benefit> {



    /**
     * 分页查询活动简单信息列表（不包含活动说明）
     * @param page
     * @return
     */
    IPage<SimpleBenefitVO> pageQueryBenefitSimpleVO(Page<SimpleBenefitVO> page);
}
