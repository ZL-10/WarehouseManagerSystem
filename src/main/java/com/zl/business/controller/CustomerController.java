package com.zl.business.controller;


import com.zl.business.service.CustomerService;
import com.zl.business.vo.CustomerVo;
import com.zl.sys.common.DataGridView;
import com.zl.sys.common.ResultObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;


@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    @Lazy
    private CustomerService customerService;

    /**
     * 查询所有客户
     * @param customerVo
     * @return
     */
    @RequestMapping(value = "/loadAllCustomer")
    public DataGridView loadAllCustomer(CustomerVo customerVo){
        try{
            return this.customerService.loadAllCustomer(customerVo);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 添加客户
     * @param customerVo
     * @return
     */
    @RequestMapping(value = "/addCustomer")
    public ResultObj addCustomer(CustomerVo customerVo) {
        try {
            this.customerService.save(customerVo);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }
    }


    /**
     * 删除客户
     * @param id
     * @return
     */
    @RequestMapping(value = "/deleteCustomer")
    public ResultObj deleteCustomer(Integer id) {
        try {
            this.customerService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }

    /**
     * 更新客户信息
     * @param customerVo
     * @return
     */
    @RequestMapping(value = "/updateCustomer")
    public ResultObj updateCustomer(CustomerVo customerVo) {
        try {
            this.customerService.updateById(customerVo);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }



    /**
     * 批量删除客户
     * @param ids
     * @return
     */
    @RequestMapping(value = "/batchDeleteCustomer")
    public ResultObj batchDeleteCustomer(Integer[] ids) {
        try {
            this.customerService.removeByIds(Arrays.asList(ids));
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }


}

