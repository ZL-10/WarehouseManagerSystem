package com.zl.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zl.sys.common.Constant;
import com.zl.sys.common.DataGridView;
import com.zl.sys.domain.Role;
import com.zl.sys.mapper.RoleMapper;
import com.zl.sys.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zl.sys.vo.RoleVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


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

    @Override
    public List<Integer> queryUserRoleIdsByUid(Integer id) {
        return this.getBaseMapper().queryUserRoleIdsByUid(id);
    }

    @Override
    public List<Map<String, Object>> initRoleByUserId(Integer id) {
        QueryWrapper<Role> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("available", Constant.AVAILABLE_TRUE);
        List<Map<String,Object>> listMaps=this.listMaps();


        List<Integer> currentUserRoleIds=this.queryUserRoleIdsByUid(id);
        for (Map<String, Object> map : listMaps) {
            Boolean LAY_CHECKED=false;
            Integer roleId=(Integer) map.get("id");
            for (Integer rid : currentUserRoleIds) {
                if(rid==roleId) {
                    LAY_CHECKED=true;
                    break;
                }
            }
            map.put("LAY_CHECKED", LAY_CHECKED);
        }
        return listMaps;
    }

    @Override
    public DataGridView loadAllRole(RoleVo roleVo) {
        IPage<Role> page = new Page<>(roleVo.getPage(), roleVo.getLimit());
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();

        queryWrapper.like(StringUtils.isNotBlank(roleVo.getName()), "name", roleVo.getName());
        queryWrapper.like(StringUtils.isNotBlank(roleVo.getRemark()), "remark", roleVo.getRemark());
        queryWrapper.eq(roleVo.getAvailable() != null, "available", roleVo.getAvailable());
        queryWrapper.orderByAsc("id");
        this.page(page, queryWrapper);

        return new DataGridView(page.getTotal(), page.getRecords());
    }

    @Override
    public Set<Integer> queryMenuPermissionByUid(Integer id) {
        List<Integer> currentUserRoleIds = this.queryUserRoleIdsByUid(id);
        //根据角色id查询权限
        Set<Integer> pids = new HashSet<>();

        for (Integer rid : currentUserRoleIds) {
            List<Integer> permissionIdsByRid = this.queryRolePermissionIdsByRid(rid);
            pids.addAll(permissionIdsByRid);
        }
        return pids;
    }
}
