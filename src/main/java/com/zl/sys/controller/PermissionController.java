package com.zl.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zl.sys.common.Constant;
import com.zl.sys.common.DataGridView;
import com.zl.sys.common.ResultObj;
import com.zl.sys.common.TreeNode;
import com.zl.sys.domain.Permission;
import com.zl.sys.service.PermissionService;
import com.zl.sys.vo.PermissionVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    /*****************************************权限管理开始*****************************************/
    /**
     * 加载权限管理左边的权限树的json
     */
    @RequestMapping(value ="/loadPermissionManagerLeftTreeJson")
    public DataGridView loadPermissionManagerLeftTreeJson(PermissionVo permissionVo) {
        QueryWrapper<Permission> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("type", Constant.TYPE_MENU);
        List<Permission> list = this.permissionService.list(queryWrapper);
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

    /**
     * 查询
     */
    @RequestMapping(value ="/loadAllPermission")
    public DataGridView loadAllPermission(PermissionVo permissionVo) {
        IPage<Permission> page=new Page<>(permissionVo.getPage(), permissionVo.getLimit());
        QueryWrapper<Permission> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("type", Constant.TYPE_PERMISSION);//只能查询权限
        queryWrapper.eq(permissionVo.getId()!=null,"pid", permissionVo.getId());

        queryWrapper.like(StringUtils.isNotBlank(permissionVo.getTitle()), "title", permissionVo.getTitle());
        queryWrapper.like(StringUtils.isNotBlank(permissionVo.getPercode()), "percode", permissionVo.getPercode());
        queryWrapper.orderByAsc("ordernum");
        this.permissionService.page(page, queryWrapper);
        return new DataGridView(page.getTotal(), page.getRecords());
    }

    /**
     * 加载最大的排序码
     * @return
     */
    @RequestMapping(value = "/getMaxOrderNum")
    public Map<String,Object> getMaxOrderNum(){
        Map<String, Object> map=new HashMap<String, Object>();

        QueryWrapper<Permission> queryWrapper=new QueryWrapper<>();
        queryWrapper.orderByDesc("ordernum");
        IPage<Permission> page=new Page<>(1, 1);
        List<Permission> list = this.permissionService.page(page, queryWrapper).getRecords();
        if(list.size()>0) {
            map.put("value", list.get(0).getOrdernum()+1);
        }else {
            map.put("value", 1);
        }
        return map;
    }


    /**
     * 添加
     * @param permissionVo
     * @return
     */
    @RequestMapping(value ="/addPermission")
    public ResultObj addPermission(PermissionVo permissionVo) {
        try {
            permissionVo.setType(Constant.TYPE_PERMISSION);//设置添加类型
            this.permissionService.save(permissionVo);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }
    }


    /**
     * 修改
     * @param permissionVo
     * @return
     */
    @RequestMapping(value ="/updatePermission")
    public ResultObj updatePermission(PermissionVo permissionVo) {
        try {
            this.permissionService.updateById(permissionVo);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }


    /**
     * 删除
     * @param permissionVo
     * @return
     */
    @RequestMapping(value ="/deletePermission")
    public ResultObj deletePermission(PermissionVo permissionVo) {
        try {
            this.permissionService.removeById(permissionVo.getId());
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }
    /*****************************************权限管理结束*****************************************/
}
