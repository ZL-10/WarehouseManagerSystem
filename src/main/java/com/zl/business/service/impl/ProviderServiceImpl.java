package com.zl.business.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zl.business.domain.Provider;
import com.zl.business.mapper.ProviderMapper;
import com.zl.business.service.ProviderService;
import com.zl.business.vo.ProviderVo;
import com.zl.sys.common.DataGridView;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Collection;


@Service("providerService")
public class ProviderServiceImpl extends ServiceImpl<ProviderMapper, Provider> implements ProviderService {

    @Override
    public DataGridView loadAllProvider(ProviderVo providerVo) {
        IPage<Provider> page=new Page<>(providerVo.getPage(),providerVo.getLimit());
        QueryWrapper<Provider> queryWrapper=new QueryWrapper<>();
        queryWrapper.like(providerVo.getProvidername()!=null,"providername",providerVo.getProvidername());
        queryWrapper.like(providerVo.getTelephone()!=null,"telephone",providerVo.getTelephone());
        queryWrapper.like(providerVo.getConnectionperson()!=null,"connectionperson",providerVo.getConnectionperson());
        this.page(page,queryWrapper);
        return new DataGridView(page.getTotal(),page.getRecords());
    }

    @Override
    public boolean save(Provider entity) {
        return super.save(entity);
    }

    @Override
    public boolean updateById(Provider entity) {
        return super.updateById(entity);
    }

    @Override
    public boolean removeById(Serializable id) {
        return super.removeById(id);
    }

    @Override
    public boolean removeByIds(Collection<? extends Serializable> idList) {
        return super.removeByIds(idList);
    }

    @Override
    public Provider getById(Serializable id) {
        return super.getById(id);
    }
}
