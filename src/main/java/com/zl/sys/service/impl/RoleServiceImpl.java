package com.zl.sys.service.impl;

import com.zl.sys.domain.Role;
import com.zl.sys.mapper.RoleMapper;
import com.zl.sys.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;


@Service
@Transactional
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Override
    public boolean removeById(Serializable rid) {
        RoleMapper roleMapper = this.getBaseMapper();
        //根据角色ID删除sys_permission_role
        roleMapper.deleteRolePermissionByRoleId(rid);
        //根据角色ID删除sys_permission_User
        roleMapper.deleteRoleUserByRoleId(rid);
        return super.removeById(rid);
    }

    @Override
    public List<Integer> queryRolePermissionIdsByRid(Integer roleId) {
        return this.getBaseMapper().queryRolePermissionIdsByRid(roleId);
    }

    /**
     * 保存角色和菜单权限之间的关系
     * @param roleId
     * @param ids
     **/
    @Override
    public void saveRolePermission(Integer roleId, Integer[] ids) {
        RoleMapper roleMapper = this.getBaseMapper();

        //根据roleId删除sys_role_permission
        roleMapper.deleteRolePermissionByRoleId(roleId);

        if(null!=ids&&ids.length>0){
            for(Integer pid:ids){
                roleMapper.saveRolePermission(roleId,pid);
            }
        }

    }
}
