package com.zl.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zl.sys.common.Constant;
import com.zl.sys.common.DataGridView;
import com.zl.sys.common.TreeNode;
import com.zl.sys.domain.Permission;
import com.zl.sys.mapper.PermissionMapper;
import com.zl.sys.service.PermissionService;
import com.zl.sys.vo.PermissionVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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

    /**
     *
     * @param currentRolePermissions 根据角色Id查询当前角色拥有的权限或菜单id
     * @return
     */
    @Override
    public DataGridView initPermissionByRoleId(List<Integer> currentRolePermissions) {
        //查询所有可用的菜单和权限
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("available", Constant.AVAILABLE_TRUE);
        List<Permission> allPermission = this.list(queryWrapper);


        //根据查询出来的菜单id查询权限和菜单数据
        List<Permission> currentPermissions;
        if (currentRolePermissions.size() > 0) {//若sys_role_permission表数据不为空
            queryWrapper.in("id", currentRolePermissions);
            currentPermissions = this.list(queryWrapper);
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

    @Override
    public DataGridView loadPermissionManagerLeftTreeJson() {
        QueryWrapper<Permission> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("type", Constant.TYPE_MENU);
        List<Permission> list = this.list(queryWrapper);
        List<TreeNode> treeNodes=new ArrayList<>();
        for (Permission permission : list) {
            Boolean spread=false;
            if(null!=permission.getOpen()){
                spread=permission.getOpen()==1?true:false;
            }else{
                spread=false;
            }
            treeNodes.add(new TreeNode(permission.getId(), permission.getPid(), permission.getTitle(), spread));
        }
        return new DataGridView(treeNodes);
    }

    @Override
    public DataGridView loadAllPermission(PermissionVo permissionVo) {
        IPage<Permission> page=new Page<>(permissionVo.getPage(), permissionVo.getLimit());
        QueryWrapper<Permission> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("type", Constant.TYPE_PERMISSION);//只能查询权限
        queryWrapper.eq(permissionVo.getId()!=null,"pid", permissionVo.getId());

        queryWrapper.like(StringUtils.isNotBlank(permissionVo.getTitle()), "title", permissionVo.getTitle());
        queryWrapper.like(StringUtils.isNotBlank(permissionVo.getPercode()), "percode", permissionVo.getPercode());
        queryWrapper.orderByAsc("ordernum");
        this.page(page, queryWrapper);
        return new DataGridView(page.getTotal(), page.getRecords());
    }

    @Override
    public Map<String, Object> getMaxOrderNum() {
        Map<String, Object> map=new HashMap<String, Object>();

        QueryWrapper<Permission> queryWrapper=new QueryWrapper<>();
        queryWrapper.orderByDesc("ordernum");
        IPage<Permission> page=new Page<>(1, 1);
        List<Permission> list = this.page(page, queryWrapper).getRecords();
        if(list.size()>0) {
            map.put("value", list.get(0).getOrdernum()+1);
        }else {
            map.put("value", 1);
        }
        return map;
    }

    @Override
    public List<Permission> listSuperUserMenuJson() {
        //查询所有菜单
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        //设置只能查询菜单
        queryWrapper.eq("type", Constant.TYPE_MENU);
        queryWrapper.eq("available", Constant.AVAILABLE_TRUE);
        return this.list(queryWrapper);
    }

    @Override
    public DataGridView loadMenuManagerLeftTreeJson() {
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type", Constant.TYPE_MENU);
        List<Permission> list = this.list(queryWrapper);
        List<TreeNode> treeNodes = new ArrayList<>();
        for (Permission menu : list) {
            Boolean spread = menu.getOpen() == 1 ? true : false;
            treeNodes.add(new TreeNode(menu.getId(), menu.getPid(), menu.getTitle(), spread));
        }
        return new DataGridView(treeNodes);
    }

    @Override
    public DataGridView loadAllMenu(PermissionVo permissionVo) {
        IPage<Permission> page = new Page<>(permissionVo.getPage(), permissionVo.getLimit());
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(permissionVo.getId() != null, "id", permissionVo.getId()).or().eq(permissionVo.getId() != null, "pid", permissionVo.getId());
        queryWrapper.eq("type", Constant.TYPE_MENU);//只能查询菜单
        queryWrapper.like(StringUtils.isNotBlank(permissionVo.getTitle()), "title", permissionVo.getTitle());
        queryWrapper.orderByAsc("ordernum");
        this.page(page, queryWrapper);
        return new DataGridView(page.getTotal(), page.getRecords());
    }

    @Override
    public Map<String, Object> checkMenuHasChildrenNode(PermissionVo permissionVo) {
        Map<String, Object> map = new HashMap<String, Object>();

        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pid", permissionVo.getId());
        List<Permission> list = this.list(queryWrapper);
        if (list.size() > 0) {
            map.put("value", true);
        } else {
            map.put("value", false);
        }
        return map;
    }
}
