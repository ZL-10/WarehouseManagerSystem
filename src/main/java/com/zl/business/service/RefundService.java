package com.zl.business.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zl.business.domain.Refund;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zl.business.vo.RefundVo;

public interface RefundService extends IService<Refund> {

    //新增退货
    void addRefund(Integer id, Integer number, String remark);

    IPage<Refund> loadAllRefund(RefundVo refundVo);
}
