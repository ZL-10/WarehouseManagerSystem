package com.zl.business.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zl.business.domain.Purchase;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zl.business.vo.PurchaseVo;
import com.zl.sys.common.DataGridView;

import java.util.List;


public interface PurchaseService extends IService<Purchase> {

    //查询所有进货信息
    IPage<Purchase> loadAllPurchase(PurchaseVo purchaseVo);
}
