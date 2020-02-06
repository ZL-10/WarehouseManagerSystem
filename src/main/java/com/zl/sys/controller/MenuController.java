package com.zl.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zl.sys.common.*;
import com.zl.sys.domain.Permission;
import com.zl.sys.domain.User;
import com.zl.sys.service.PermissionService;
import com.zl.sys.service.RoleService;
import com.zl.sys.vo.PermissionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping(value = "/menu")
public class MenuController {
    @Autowired
    private PermissionService permissionService;


    @Autowired
    private RoleService roleService;

    @RequestMapping(value = "/loadIndexLeftMenuJson")
    public DataGridView loadIndexLeftMenuJson(PermissionVo permissionVo) {
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();

        User user = (User) WebUtils.getHttpSession().getAttribute("user");
        List<Permission> list = null;
        if (user.getType().equals(Constant.USER_TYPE_SUPER)) {
            list =permissionService.listSuperUserMenuJson();
        } else {//根据用户ID+角色+权限去查询
            //根据用户Id查询角色
            Set<Integer> pids=roleService.queryMenuPermissionByUid(user.getId());
            if (pids.size() > 0) {
                queryWrapper.in("id", pids);
                list = permissionService.list(queryWrapper);
            } else {
                list = new ArrayList<>();
            }
        }

        List<TreeNode> treeNodes = new ArrayList<>();
        for (Permission p : list) {
            Boolean spread = p.getOpen() == Constant.OPEN_TRUE ? true : false;
            treeNodes.add(new TreeNode(p.getId(), p.getPid(), p.getTitle(), p.getIcon(), p.getHref(), spread));
        }

        //构造层级关系
        List<TreeNode> list2 = TreeNodeBuilder.build(treeNodes, 1);

        return new DataGridView(list2);
    }

    /*****************************************菜单管理开始*****************************************/
    /**
     * 加载菜单管理左边的菜单树的json
     */
    @RequestMapping(value = "loadMenuManagerLeftTreeJson")
    public DataGridView loadMenuManagerLeftTreeJson(PermissionVo permissionVo) {
        try{
            return this.permissionService.loadMenuManagerLeftTreeJson();
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 查询所有菜单
     */
    @RequestMapping(value = "loadAllMenu")
    public DataGridView loadAllMenu(PermissionVo permissionVo) {
        try{
            return permissionService.loadAllMenu(permissionVo);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 加载最大的排序码
     *
     * @return
     */
    @RequestMapping(value = "getMaxOrderNum")
    public Map<String, Object> getMaxOrderNum() {
        try{
            return permissionService.getMaxOrderNum();
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }

    }


    /**
     * 添加
     *
     * @param permissionVo
     * @return
     */
    @RequestMapping(value = "addMenu")
    public ResultObj addMenu(PermissionVo permissionVo) {
        try {
            permissionVo.setType(Constant.TYPE_MENU);//设置添加类型
            this.permissionService.save(permissionVo);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }
    }


    /**
     * 修改
     *
     * @param permissionVo
     * @return
     */
    @RequestMapping(value = "updateMenu")
    public ResultObj updateMenu(PermissionVo permissionVo) {
        try {
            this.permissionService.updateById(permissionVo);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }


    /**
     * 查询当前的ID的菜单有没有子菜单
     */
    @RequestMapping(value = "checkMenuHasChildrenNode")
    public Map<String, Object> checkMenuHasChildrenNode(PermissionVo permissionVo) {
        try{
            return permissionService.checkMenuHasChildrenNode(permissionVo);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 删除
     *
     * @param permissionVo
     * @return
     */
    @RequestMapping(value = "deleteMenu")
    public ResultObj deleteMenu(PermissionVo permissionVo) {
        try {
            this.permissionService.removeById(permissionVo.getId());
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }
    /*****************************************菜单管理结束*****************************************/
}
