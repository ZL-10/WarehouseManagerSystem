package com.zl.business.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.zl.business.domain.Provider;
import com.zl.business.vo.ProviderVo;
import com.zl.sys.common.DataGridView;


public interface ProviderService extends IService<Provider> {

    //查询所有供应商
    DataGridView loadAllProvider(ProviderVo providerVo);
}
