package com.zl.sys.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.zl.sys.common.DataGridView;
import com.zl.sys.domain.Permission;
import com.zl.sys.vo.PermissionVo;

import java.util.List;
import java.util.Map;

public interface PermissionService extends IService<Permission> {
    //根据roleId加载菜单和权限的树的json串
    DataGridView initPermissionByRoleId(List<Integer> currentRolePermissions);

    //加载权限管理左边的权限树的json
    DataGridView loadPermissionManagerLeftTreeJson();

    //查询所有权限
    DataGridView loadAllPermission(PermissionVo permissionVo);

    //加载最大的排序码
    Map<String, Object> getMaxOrderNum();

    //获取超级用户菜单
    List<Permission> listSuperUserMenuJson();

    //加载菜单管理左边的菜单树的json
    DataGridView loadMenuManagerLeftTreeJson();

    //查询所有菜单
    DataGridView loadAllMenu(PermissionVo permissionVo);

    //查询当前的ID的菜单有没有子菜单
    Map<String, Object> checkMenuHasChildrenNode(PermissionVo permissionVo);
}
