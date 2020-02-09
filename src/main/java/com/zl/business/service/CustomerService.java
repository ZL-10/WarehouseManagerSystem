package com.zl.business.service;

import com.zl.business.domain.Customer;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zl.business.vo.CustomerVo;
import com.zl.sys.common.DataGridView;


public interface CustomerService extends IService<Customer> {

    //查询所有客户
    DataGridView loadAllCustomer(CustomerVo customerVo);
}
