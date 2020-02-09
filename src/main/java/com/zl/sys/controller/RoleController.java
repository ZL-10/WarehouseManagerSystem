package com.zl.sys.controller;



import com.zl.sys.common.*;
import com.zl.sys.service.PermissionService;
import com.zl.sys.service.RoleService;
import com.zl.sys.vo.RoleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;


import java.util.Date;
import java.util.List;


@RestController
@RequestMapping(value = "/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    /**
     * 获取所有角色
     */
    @RequestMapping(value = "/loadAllRole")
    public DataGridView loadAllRole(RoleVo roleVo) {
        try {
            return this.roleService.loadAllRole(roleVo);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 添加角色
     */
    @RequestMapping(value = "/addRole")
    public ResultObj addRole(RoleVo roleVo) {
        try {
            roleVo.setCreatetime(new Date());
            this.roleService.saveOrUpdate(roleVo);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }
    }

    /**
     * 更新角色
     */
    @RequestMapping(value = "/updateRole")
    public ResultObj updateRole(RoleVo roleVo) {
        try {
            this.roleService.updateById(roleVo);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }


    /**
     * 删除
     */
    @RequestMapping(value = "/deleteRole")
    public ResultObj deleteRole(Integer id) {
        try {
            this.roleService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }

    /**
     * 根据roleId加载菜单和权限的树的json串
     */
    @RequestMapping(value = "/initPermissionByRoleId")
    public DataGridView initPermissionByRoleId(Integer roleId) {
        try{
            //根据角色Id查询当前角色拥有的权限或菜单id
            List<Integer> currentRolePermissions = this.roleService.queryRolePermissionIdsByRid(roleId);
            return this.permissionService.initPermissionByRoleId(currentRolePermissions);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }


    }

    @RequestMapping(value = "/saveRolePermission")
    public ResultObj saveRolePermission(Integer roleId, Integer[] ids) {
        try {
            this.roleService.saveRolePermission(roleId, ids);
            return ResultObj.DISPATCH_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DISPATCH_ERROR;
        }
    }




}

