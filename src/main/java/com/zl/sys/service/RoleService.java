package com.zl.sys.service;

import com.zl.sys.domain.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


public interface RoleService extends IService<Role> {

    //根据角色Id查询当前角色拥有的权限或菜单id
    List<Integer> queryRolePermissionIdsByRid(Integer roleId);

    //保存角色和菜单权限之间的关系
    void saveRolePermission(Integer roleId, Integer[] ids);

    //根据用户id查询用户的角色权限
    List<Integer> queryUserRoleIdsByUid(Integer id);
}
