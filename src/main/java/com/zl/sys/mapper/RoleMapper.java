package com.zl.sys.mapper;

import com.zl.sys.domain.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;


public interface RoleMapper extends BaseMapper<Role> {
    //根据角色ID删除sys_role_permission
    void deleteRolePermissionByRoleId(@Param("rid") Serializable rid);

    //根据角色ID删除sys_role_User
    void deleteRoleUserByRoleId(@Param("rid") Serializable rid);

    //根据角色id查询角色权限
    List<Integer> queryRolePermissionIdsByRid(Integer roleId);

    //保存角色权限
    void saveRolePermission(@Param("rid") Integer roleId,@Param("pid") Integer pid);

    //查询当前用户拥有的角色ID集合
    void deleteRoleUserByUid(Serializable id);

    //根据用户ID查询角色并选中已拥有的角色
    List<Integer> queryUserRoleIdsByUid(Integer id);

    //保存用户与角色的关系
    void insertUserRole(@Param("uid") Integer uid, @Param("id") Integer id);
}

