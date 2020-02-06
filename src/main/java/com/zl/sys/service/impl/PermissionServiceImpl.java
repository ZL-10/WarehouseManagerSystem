package com.zl.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zl.sys.domain.Permission;
import com.zl.sys.mapper.PermissionMapper;
import com.zl.sys.service.PermissionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

//@Service("permissionService")
//@Transactional
//public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {
//    @Override
//    public boolean removeById(Serializable pid) {
//        PermissionMapper permissionMapper = this.getBaseMapper();
//        //根据权限或菜单ID删除权限表和角色表里的数据
//        permissionMapper.deleteRolePermissionByPid(pid);
//        return super.removeById(pid);
//    }
//}

@Service
@Transactional
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {


    @Override
    public boolean removeById(Serializable pid) {
        PermissionMapper permissionMapper = this.getBaseMapper();
        //根据权限或菜单ID删除权限表各和角色的关系表里面的数据
        permissionMapper.deleteRolePermissionByPid(pid);
        return super.removeById(pid);//删除 权限表的数据
    }
}
