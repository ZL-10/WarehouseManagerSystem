package com.zl.business.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zl.business.domain.Goods;
import com.zl.business.domain.Provider;
import com.zl.business.domain.Refund;
import com.zl.business.service.GoodsService;
import com.zl.business.service.ProviderService;
import com.zl.business.service.RefundService;
import com.zl.business.vo.RefundVo;
import com.zl.sys.common.DataGridView;
import com.zl.sys.common.ResultObj;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/refund")
public class RefundController {
    @Autowired
    private RefundService refundService;

    @Autowired
    private ProviderService providerService;

    @Autowired
    private GoodsService goodsService;
    
    /**
     * 查询
     */
    @RequestMapping(value = "/loadAllRefund")
    public DataGridView loadAllRefund(RefundVo refundVo) {
        IPage<Refund> page=this.refundService.loadAllRefund(refundVo);
        List<Refund> records = page.getRecords();
        for (Refund refund : records) {
            Provider provider = this.providerService.getById(refund.getProviderid());
            if(null!=provider) {
                refund.setProvidername(provider.getProvidername());
            }
            Goods goods = this.goodsService.getById(refund.getGoodsid());
            if(null!=goods) {
                refund.setGoodsname(goods.getGoodsname());
                refund.setSize(goods.getSize());
            }
        }
        return new DataGridView(page.getTotal(), records);
    }

    /**
     * 添加退货信息
     */
    @RequestMapping(value = "/addRefund")
    public ResultObj addRefund(Integer id, Integer number, String remark) {
        try {
            this.refundService.addRefund(id,number,remark);
            return ResultObj.OPERATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.OPERATE_ERROR;
        }
    }
}

