package com.zl.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zl.sys.common.DataGridView;
import com.zl.sys.common.ResultObj;
import com.zl.sys.domain.LogInfo;
import com.zl.sys.service.LogInfoService;
import com.zl.sys.vo.LogInfoVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.sql.Wrapper;
import java.util.ArrayList;
import java.util.Collection;

@RestController
@RequestMapping(value = "/logInfo")
public class LogInfoController {
    @Autowired
    private LogInfoService logInfoService;


    /**
     * 查询全部
     */
    @RequestMapping(value = "/loadAllLogInfo")
    public DataGridView loadAllLoginInfo(LogInfoVo logInfoVo) {
        IPage<LogInfo> page=new Page<>(logInfoVo.getPage(), logInfoVo.getLimit());
        QueryWrapper<LogInfo> queryWrapper=new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(logInfoVo.getLoginname()),"loginname", logInfoVo.getLoginname());
        queryWrapper.like(StringUtils.isNotBlank(logInfoVo.getLoginip()), "loginip",logInfoVo.getLoginip());
        queryWrapper.ge(logInfoVo.getStartTime()!=null, "logintime", logInfoVo.getStartTime());
        queryWrapper.le(logInfoVo.getEndTime()!=null, "logintime", logInfoVo.getEndTime());
        queryWrapper.orderByDesc("logintime");
        this.logInfoService.page(page, queryWrapper);
        return new DataGridView(page.getTotal(), page.getRecords());
    }


    /**
     * 删除
     */
    @RequestMapping(value = "/deleteLogInfo")
    public ResultObj deleteLogInfo(Integer id) {
        try {
            this.logInfoService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }

    }

    /**
     * 批量删除
     */
    @RequestMapping(value = "/batchDeleteLogInfo")
    public ResultObj batchDeleteLogInfo(LogInfoVo logInfoVo) {
        try {
            Collection<Serializable> idList = new ArrayList<Serializable>();
            for (Integer id : logInfoVo.getIds()) {
                idList.add(id);
            }
            this.logInfoService.removeByIds(idList);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }

}
