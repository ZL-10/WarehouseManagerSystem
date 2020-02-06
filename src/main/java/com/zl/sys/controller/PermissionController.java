package com.zl.sys.controller;

import com.zl.sys.common.Constant;
import com.zl.sys.common.DataGridView;
import com.zl.sys.common.ResultObj;
import com.zl.sys.service.PermissionService;
import com.zl.sys.vo.PermissionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.Map;

@RestController
@RequestMapping(value = "/permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    /*****************************************权限管理开始*****************************************/
    /**
     * 加载权限管理左边的权限树的json
     */
    @RequestMapping(value ="/loadPermissionManagerLeftTreeJson")
    public DataGridView loadPermissionManagerLeftTreeJson(PermissionVo permissionVo) {
        try{
            return this.permissionService.loadPermissionManagerLeftTreeJson();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 查询所有权限
     */
    @RequestMapping(value ="/loadAllPermission")
    public DataGridView loadAllPermission(PermissionVo permissionVo) {
        try{
            return this.permissionService.loadAllPermission(permissionVo);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 加载最大的排序码
     * @return
     */
    @RequestMapping(value = "/getMaxOrderNum")
    public Map<String,Object> getMaxOrderNum(){
        try{
            return permissionService.getMaxOrderNum();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }


    /**
     * 添加权限
     * @param permissionVo
     * @return
     */
    @RequestMapping(value ="/addPermission")
    public ResultObj addPermission(PermissionVo permissionVo) {
        try {
            permissionVo.setType(Constant.TYPE_PERMISSION);//设置添加类型
            this.permissionService.save(permissionVo);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }
    }


    /**
     * 修改
     * @param permissionVo
     * @return
     */
    @RequestMapping(value ="/updatePermission")
    public ResultObj updatePermission(PermissionVo permissionVo) {
        try {
            this.permissionService.updateById(permissionVo);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }


    /**
     * 删除
     * @param permissionVo
     * @return
     */
    @RequestMapping(value ="/deletePermission")
    public ResultObj deletePermission(PermissionVo permissionVo) {
        try {
            this.permissionService.removeById(permissionVo.getId());
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }
    /*****************************************权限管理结束*****************************************/
}
