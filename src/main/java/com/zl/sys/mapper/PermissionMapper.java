package com.zl.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zl.sys.domain.Permission;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;

public interface PermissionMapper extends BaseMapper<Permission> {
    //根据权限或菜单ID删除权限表和角色表里的数据
    void deleteRolePermissionByPid(@Param("pid")Serializable pid);
}
