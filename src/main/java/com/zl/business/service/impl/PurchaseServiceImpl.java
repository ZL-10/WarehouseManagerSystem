package com.zl.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zl.business.domain.Purchase;
import com.zl.business.mapper.PurchaseMapper;
import com.zl.business.service.PurchaseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zl.business.vo.PurchaseVo;
import com.zl.sys.common.DataGridView;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;


@Service("inportService")
public class PurchaseServiceImpl extends ServiceImpl<PurchaseMapper, Purchase> implements PurchaseService {

    @Override
    public IPage<Purchase> loadAllPurchase(PurchaseVo purchaseVo) {
        IPage<Purchase> page=new Page<>(purchaseVo.getPage(),purchaseVo.getLimit());
        QueryWrapper<Purchase> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq(null!=purchaseVo.getProviderid()&&purchaseVo.getProviderid()!=0,"providerid",purchaseVo.getProviderid());
        queryWrapper.eq(null!=purchaseVo.getGoodsid()&&purchaseVo.getGoodsid()!=0,"goodsid",purchaseVo.getGoodsid());
        queryWrapper.ge(null!=purchaseVo.getStartTime(),"inporttime",purchaseVo.getStartTime());
        queryWrapper.le(null!=purchaseVo.getEndTime(),"inporttime",purchaseVo.getEndTime());
        queryWrapper.like(StringUtils.isNotBlank(purchaseVo.getOperateperson()),"operateperson",purchaseVo.getOperateperson());
        queryWrapper.like(StringUtils.isNotBlank(purchaseVo.getRemark()),"remark",purchaseVo.getRemark());
        queryWrapper.orderByDesc("inporttime");
        return this.page(page,queryWrapper);
    }

    @Override
    public boolean save(Purchase entity) {
        return super.save(entity);
    }

    @Override
    public Purchase getById(Serializable id) {
        return super.getById(id);
    }

    @Override
    public boolean updateById(Purchase entity) {
        return super.updateById(entity);
    }

    @Override
    public boolean removeById(Serializable id) {
        return super.removeById(id);
    }
}
