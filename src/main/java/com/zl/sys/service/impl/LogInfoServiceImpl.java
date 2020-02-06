package com.zl.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zl.sys.common.DataGridView;
import com.zl.sys.domain.LogInfo;
import com.zl.sys.mapper.LogInfoMapper;
import com.zl.sys.service.LogInfoService;
import com.zl.sys.vo.LogInfoVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("logInfoService")
@Transactional
public class LogInfoServiceImpl extends ServiceImpl<LogInfoMapper, LogInfo> implements LogInfoService {
    @Override
    public DataGridView loadAllLoginInfo(LogInfoVo logInfoVo) {
        IPage<LogInfo> page=new Page<>(logInfoVo.getPage(), logInfoVo.getLimit());
        QueryWrapper<LogInfo> queryWrapper=new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(logInfoVo.getLoginname()),"loginname", logInfoVo.getLoginname());
        queryWrapper.like(StringUtils.isNotBlank(logInfoVo.getLoginip()), "loginip",logInfoVo.getLoginip());
        queryWrapper.ge(logInfoVo.getStartTime()!=null, "logintime", logInfoVo.getStartTime());
        queryWrapper.le(logInfoVo.getEndTime()!=null, "logintime", logInfoVo.getEndTime());
        queryWrapper.orderByDesc("logintime");
        this.page(page, queryWrapper);
        return new DataGridView(page.getTotal(), page.getRecords());
    }
}
