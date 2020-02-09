package com.zl.business.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zl.business.domain.Goods;
import com.zl.business.domain.Provider;
import com.zl.business.domain.Purchase;
import com.zl.business.service.GoodsService;
import com.zl.business.service.ProviderService;
import com.zl.business.service.PurchaseService;
import com.zl.business.vo.PurchaseVo;
import com.zl.sys.common.DataGridView;
import com.zl.sys.common.ResultObj;
import com.zl.sys.common.WebUtils;
import com.zl.sys.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/purchase")
public class PurchaseController {
    @Autowired
    @Lazy
    private PurchaseService purchaseService;

    @Autowired
    @Lazy
    private ProviderService providerService;

    @Autowired
    @Lazy
    private GoodsService goodsService;

    /**
     * 查询所有进货信息
     *
     * @param purchaseVo
     * @return
     */
    @RequestMapping(value = "/loadAllPurchase")
    public DataGridView loadAllPurchase(PurchaseVo purchaseVo) {
        try {
            IPage<Purchase> page = this.purchaseService.loadAllPurchase(purchaseVo);
            List<Purchase> list = page.getRecords();
            for (Purchase purchase : list) {
                Provider provider = this.providerService.getById(purchase.getProviderid());
                if (null != provider) {
                    purchase.setProvidername(provider.getProvidername());
                }
                Goods goods = this.goodsService.getById(purchase.getGoodsid());
                if (null != goods) {
                    purchase.setGoodsname(goods.getGoodsname());
                    purchase.setSize(goods.getSize());
                }
            }
            return new DataGridView(page.getTotal(), list);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 添加
     */
    @RequestMapping(value = "/addPurchase")
    public ResultObj addPurchase(PurchaseVo purchaseVo) {
        try {
            purchaseVo.setInporttime(new Date());
            User user = (User) WebUtils.getHttpSession().getAttribute("user");
            purchaseVo.setOperateperson(user.getName());
            this.purchaseService.save(purchaseVo);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }
    }


    /**
     * 更新
     */
    @RequestMapping(value = "/updatePurchase")
    public ResultObj updatePurchase(PurchaseVo purchaseVo) {
        try {
            this.purchaseService.updateById(purchaseVo);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }


    /**
     * 删除
     */
    @RequestMapping(value = "/deletePurchase")
    public ResultObj deletePurchase(Integer id) {
        try {
            this.purchaseService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }


}

