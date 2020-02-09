package com.zl.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zl.business.domain.Goods;
import com.zl.business.domain.Purchase;
import com.zl.business.domain.Refund;
import com.zl.business.mapper.GoodsMapper;
import com.zl.business.mapper.PurchaseMapper;
import com.zl.business.mapper.RefundMapper;
import com.zl.business.service.RefundService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zl.business.vo.RefundVo;
import com.zl.sys.common.WebUtils;
import com.zl.sys.domain.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


@Service("refundService")
@Transactional
public class RefundServiceImpl extends ServiceImpl<RefundMapper, Refund> implements RefundService {

    @Autowired
    private PurchaseMapper purchaseMapper;

    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public void addRefund(Integer id, Integer number, String remark) {
        //1,根据进货单ID查询进货单信息
        Purchase purchase = this.purchaseMapper.selectById(id);
        //2,根据商品ID查询商品信息
        Goods goods = this.goodsMapper.selectById(purchase.getGoodsid());
        goods.setNumber(goods.getNumber()-number);
        this.goodsMapper.updateById(goods);
        //3,添加退货单信息
        Refund entity=new Refund();
        entity.setGoodsid(purchase.getGoodsid());
        entity.setNumber(number);
        User user=(User) WebUtils.getHttpSession().getAttribute("user");
        entity.setOperateperson(user.getName());
        entity.setRefundprice((purchase.getInportprice()));
        entity.setRefundtime(new Date());
        entity.setPaytype(purchase.getPaytype());
        entity.setProviderid(purchase.getProviderid());
        entity.setRemark(remark);
        this.getBaseMapper().insert(entity);
    }

    @Override
    public IPage<Refund> loadAllRefund(RefundVo refundVo) {
        IPage<Refund> page = new Page<>(refundVo.getPage(), refundVo.getLimit());
        QueryWrapper<Refund> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(refundVo.getProviderid()!=null&&refundVo.getProviderid()!=0,"providerid",refundVo.getProviderid());
        queryWrapper.eq(refundVo.getGoodsid()!=null&&refundVo.getGoodsid()!=0,"goodsid",refundVo.getGoodsid());
        queryWrapper.ge(refundVo.getStartTime()!=null, "refundtime", refundVo.getStartTime());
        queryWrapper.le(refundVo.getEndTime()!=null, "refundtime", refundVo.getEndTime());
        queryWrapper.like(StringUtils.isNotBlank(refundVo.getOperateperson()), "operateperson", refundVo.getOperateperson());
        queryWrapper.like(StringUtils.isNotBlank(refundVo.getRemark()), "remark", refundVo.getRemark());
        queryWrapper.orderByDesc("refundtime");
        return this.page(page, queryWrapper);
    }
}
