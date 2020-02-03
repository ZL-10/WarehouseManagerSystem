package com.zl.sys.mapper;

import com.zl.sys.domain.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;


public interface RoleMapper extends BaseMapper<Role> {
    //根据角色ID删除sys_role_permission
    void deleteRolePermissionByRoleId(@Param("rid") Serializable rid);
    //根据角色ID删除sys_role_User
    void deleteRoleUserByRoleId(@Param("rid") Serializable rid);

    List<Integer> queryRolePermissionIdsByRid(Integer roleId);


    void saveRolePermission(@Param("rid") Integer roleId,@Param("pid") Integer pid);
}

