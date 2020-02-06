package com.zl.sys.controller;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zl.sys.common.*;
import com.zl.sys.domain.Permission;
import com.zl.sys.domain.Role;
import com.zl.sys.domain.User;
import com.zl.sys.service.PermissionService;
import com.zl.sys.service.RoleService;
import com.zl.sys.vo.RoleVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
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
        IPage<Role> page = new Page<>(roleVo.getPage(), roleVo.getLimit());
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();

        queryWrapper.like(StringUtils.isNotBlank(roleVo.getName()), "name", roleVo.getName());
        queryWrapper.like(StringUtils.isNotBlank(roleVo.getRemark()), "remark", roleVo.getRemark());
        queryWrapper.eq(roleVo.getAvailable() != null, "available", roleVo.getAvailable());
        queryWrapper.orderByAsc("id");
        this.roleService.page(page, queryWrapper);

        return new DataGridView(page.getTotal(), page.getRecords());
    }


    /**
     * 添加角色
     */
    @RequestMapping(value = "/addRole")
    public ResultObj addRole(RoleVo roleVo) {
        try {
            roleVo.setCreatetime(new Date());
            User user = (User) WebUtils.getHttpSession().getAttribute("user");
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
        //查询所有可用的菜单和权限
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("available", Constant.AVAILABLE_TRUE);
        List<Permission> allPermission = permissionService.list(queryWrapper);
        //根据角色Id查询当前角色拥有的权限或菜单id
        List<Integer> currentRolePermissions = this.roleService.queryRolePermissionIdsByRid(roleId);

        //根据查询出来的菜单id查询权限和菜单数据
        List<Permission> currentPermissions;
        if (currentRolePermissions.size() > 0) {//若sys_role_permission表数据不为空
            queryWrapper.in("id", currentRolePermissions);
            currentPermissions = permissionService.list(queryWrapper);
        } else {
            currentPermissions = new ArrayList<>();
        }

        //构造List<TreeNode>
        List<TreeNode> nodes = new ArrayList<>();
        for (Permission p1 : allPermission) {
            String checkArr = "0";
            for (Permission p2 : currentPermissions) {
                if (p1.getId() == p2.getId()) {
                    checkArr = "1";
                    break;
                }
            }
            Boolean spread = false;
            if (null != p1.getOpen()) {
                spread = p1.getOpen() == 1 ? true : false;
            }

            nodes.add(new TreeNode(p1.getId(), p1.getPid(), p1.getTitle(), spread, checkArr));
        }
        return new DataGridView(nodes);
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

