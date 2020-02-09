package com.zl.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zl.business.domain.Goods;
import com.zl.business.mapper.GoodsMapper;
import com.zl.business.service.GoodsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zl.business.vo.GoodsVo;
import com.zl.sys.common.DataGridView;
import com.zl.sys.common.MyFileUtils;
import com.zl.sys.common.ResultObj;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;


@Service("goodsService")
@Transactional
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {


    @Override
    public List<Goods> loadAllGoods(IPage<Goods> page, QueryWrapper<Goods> queryWrapper, GoodsVo goodsVo) {
        queryWrapper.eq(goodsVo.getProviderid()!=null&&goodsVo.getProviderid()!=0,"providerid",goodsVo.getProviderid());
        queryWrapper.like(StringUtils.isNotBlank(goodsVo.getGoodsname()), "goodsname", goodsVo.getGoodsname());
        queryWrapper.like(StringUtils.isNotBlank(goodsVo.getProductcode()), "productcode", goodsVo.getProductcode());
        queryWrapper.like(StringUtils.isNotBlank(goodsVo.getPromitcode()), "promitcode", goodsVo.getPromitcode());
        queryWrapper.like(StringUtils.isNotBlank(goodsVo.getDescription()), "description", goodsVo.getDescription());
        queryWrapper.like(StringUtils.isNotBlank(goodsVo.getSize()), "size", goodsVo.getSize());
        return this.page(page, queryWrapper).getRecords();
    }

    @Override
    public List<Goods> loadGoodsByProviderId(Integer providerId) {
        QueryWrapper<Goods> queryWrapper=new QueryWrapper();
        queryWrapper.eq(null!=providerId,"providerId",providerId);
        return this.list(queryWrapper);
    }

    @Override
    public boolean save(Goods entity) {
        return super.save(entity);
    }

    @Override
    public Goods getById(Serializable id) {
        return super.getById(id);
    }

    @Override
    public boolean removeById(Serializable id) {
        return super.removeById(id);
    }

    @Override
    public boolean removeByIds(Collection<? extends Serializable> idList) {
        return super.removeByIds(idList);
    }

    @Override
    public boolean updateById(Goods entity) {
        return super.updateById(entity);
    }
}
