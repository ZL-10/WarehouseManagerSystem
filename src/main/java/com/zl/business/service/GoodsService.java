package com.zl.business.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zl.business.domain.Goods;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zl.business.vo.GoodsVo;
import com.zl.sys.common.DataGridView;

import java.util.List;


public interface GoodsService extends IService<Goods> {

    //查询商品
    List<Goods> loadAllGoods(IPage<Goods> page, QueryWrapper<Goods> queryWrapper, GoodsVo goodsVo);

    //查询该供货商id下的货物
    List<Goods> loadGoodsByProviderId(Integer providerId);
}
