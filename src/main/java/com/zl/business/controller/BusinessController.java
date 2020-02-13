package com.zl.business.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/business")
public class BusinessController {

    /**
     * 跳转到客户管理
     */
    @RequestMapping("toCustomerManager")
    public String toCustomerManager() {
        return "business/customer/customerManager";
    }
    /**
     * 跳转到供应商管理
     */
    @RequestMapping("toProviderManager")
    public String toProviderManager() {
        return "business/provider/providerManager";
    }
    /**
     * 跳转到商品管理
     */
    @RequestMapping("toGoodsManager")
    public String toGoodsManager() {
        return "business/goods/goodsManager";
    }
    /**
     * 跳转到进货管理
     */
    @RequestMapping("toPurchaseManager")
    public String toPurchaseManager() {
        return "business/purchase/purchaseManager";
    }
    /**
     * 跳转到退货查询管理
     */
    @RequestMapping("toRefundManager")
    public String toRefundManager() {
        return "business/refund/refundManager";
    }
}
