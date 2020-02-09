package com.zl.sys.controller;

import com.zl.sys.common.DataGridView;
import com.zl.sys.common.ResultObj;
import com.zl.sys.service.LogInfoService;
import com.zl.sys.vo.LogInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@RestController
@RequestMapping(value = "/logInfo")
public class LogInfoController {
    @Autowired
    private LogInfoService logInfoService;


    /**
     * 查询全部登录日志
     */
    @RequestMapping(value = "/loadAllLogInfo")
    public DataGridView loadAllLoginInfo(LogInfoVo logInfoVo) {
        try{
            return this.logInfoService.loadAllLoginInfo(logInfoVo);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }

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
