package com.zl.business.controller;


import com.zl.business.domain.Provider;
import com.zl.business.service.ProviderService;
import com.zl.business.vo.ProviderVo;
import com.zl.sys.common.DataGridView;
import com.zl.sys.common.ResultObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;


@RestController
@RequestMapping(value = "/provider")
public class ProviderController {

    @Autowired
    @Lazy
    private ProviderService providerService;

    /**
     * 查询所有供应商
     * @param providerVo
     * @return
     */
    @RequestMapping(value = "/loadAllProvider")
    public DataGridView loadAllProvider(ProviderVo providerVo){
        try{
            return this.providerService.loadAllProvider(providerVo);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 添加供应商
     * @param providerVo
     * @return
     */
    @RequestMapping(value = "/addProvider")
    public ResultObj addProvider(ProviderVo providerVo){
        try{
            this.providerService.save(providerVo);
            return ResultObj.ADD_SUCCESS;
        }catch(Exception e){
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }
    }

    /**
     * 删除供应商
     * @param providerVo
     * @return
     */
    @RequestMapping(value = "/deleteProvider")
    public ResultObj deleteProvider(ProviderVo providerVo){
        try{
            this.providerService.removeById(providerVo);
            return ResultObj.DELETE_SUCCESS;
        }catch(Exception e){
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }



    /**
     * 更新供应商
     * @param providerVo
     * @return
     */
    @RequestMapping(value = "/updateProvider")
    public ResultObj updateProvider(ProviderVo providerVo){
        try{
            this.providerService.updateById(providerVo);
            return ResultObj.UPDATE_SUCCESS;
        }catch(Exception e){
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }

    /**
     * 批量删除供应商
     * @param ids
     * @return
     */
    @RequestMapping(value = "/batchDeleteProvider")
    public ResultObj batchDeleteProvider(Integer[] ids){
        try{
            this.providerService.removeByIds(Arrays.asList(ids));
            return ResultObj.DELETE_SUCCESS;
        }catch(Exception e){
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }



    /**
     * 批量删除供应商
     * @param ids
     * @return
     */
    @RequestMapping(value = "/loadAllProviderForSelect")
    public DataGridView loadAllProviderForSelect(Integer[] ids){
        try{
            return new DataGridView(this.providerService.list());
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }



}

