package com.zl.sys.service;

import com.zl.sys.common.DataGridView;
import com.zl.sys.domain.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zl.sys.vo.RoleVo;

import java.util.List;
import java.util.Map;
import java.util.Set;


public interface RoleService extends IService<Role> {

    //根据角色Id查询当前角色拥有的权限或菜单id
    List<Integer> queryRolePermissionIdsByRid(Integer roleId);

    //保存角色和菜单权限之间的关系
    void saveRolePermission(Integer roleId, Integer[] ids);

    //根据用户id查询用户的角色权限
    List<Integer> queryUserRoleIdsByUid(Integer id);

    //根据用户ID查询角色并选中已拥有的角色
    List<Map<String, Object>> initRoleByUserId(Integer id);

    //获取所有角色
    DataGridView loadAllRole(RoleVo roleVo);

    //通过用户id设置左边菜单栏
    Set<Integer> queryMenuPermissionByUid(Integer id);
}
