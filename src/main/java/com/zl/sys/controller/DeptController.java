package com.zl.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zl.sys.common.DataGridView;
import com.zl.sys.common.ResultObj;
import com.zl.sys.common.TreeNode;
import com.zl.sys.domain.Dept;
import com.zl.sys.service.DeptService;
import com.zl.sys.vo.DeptVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.*;


@RestController
@RequestMapping(value = "/dept")
public class DeptController {
    @Autowired
    private DeptService deptService;

    private Integer maxOrderNum;

    /**
     * 加载部门管理左边的部门树的json
     */
    @RequestMapping(value = "/loadDeptManagerLeftTreeJson")
    public DataGridView loadDeptManagerLeftTreeJson(DeptVo deptVo) {
        List<Dept> list = this.deptService.list();
        List<TreeNode> treeNodes = new ArrayList<>();
        Boolean spread;
        for (Dept dept : list) {
            spread = dept.getOpen() == 1 ? true : false;
            treeNodes.add(new TreeNode(dept.getId(), dept.getPid(), dept.getTitle(), spread));
        }
        return new DataGridView(treeNodes);
    }


    /**
     * 获取所有部门
     *
     * @param deptVo
     **/
    @RequestMapping(value = "/loadAllDept")
    public DataGridView loadAllDept(DeptVo deptVo) {
        try{
            return this.deptService.loadAllDept(deptVo);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }

    }


    /**
     * 添加部门
     *
     * @param deptVo
     **/
    @RequestMapping(value = "/addDept")
    public ResultObj addDept(DeptVo deptVo) {
        try {
            deptVo.setCreatetime(new Date());
            this.deptService.save(deptVo);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }
    }


    /**
     * 获取最大顺序号
     *
     * @return
     */
    @RequestMapping(value = "/getMaxOrderNum")
    public Map<String, Object> getMaxOrderNum() {
        Map<String, Object> map = new HashMap<String, Object>();

        QueryWrapper<Dept> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("ordernum");
        List<Dept> list = this.deptService.list(queryWrapper);
        if (list.size() > 0) {
            map.put("value", list.get(0).getOrdernum() + 1);
        } else {
            map.put("value", 1);
        }
        return map;
    }

    /**
     * 修改部门信息
     *
     * @param deptVo
     **/
    @RequestMapping(value = "/updateDept")
    public ResultObj updateDept(DeptVo deptVo) {
        try {
            deptVo.setCreatetime(new Date());
            this.deptService.updateById(deptVo);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }


    /**
     * 删除部门信息
     *
     * @param deptVo
     **/
    @RequestMapping(value = "/deleteDept")
    public ResultObj deleteDept(DeptVo deptVo){
        try{
            this.deptService.removeById(deptVo.getId());
            return ResultObj.DELETE_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }


    /**
     *  查询改节点是否有子节点
     * @param deptVo 
     **/
    @RequestMapping(value = "/checkDeptHasChildrenNode")
    public Map<String, Object> checkDeptHasChildrenNode(DeptVo deptVo) {
        Map<String, Object> map = new HashMap<>();

        QueryWrapper<Dept> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pid",deptVo.getId());
        List<Dept> list = this.deptService.list(queryWrapper);
        if(list.size()>0){
            map.put("value",true);
        }else{
            map.put("value",false);
        }
        return map;
    }
}

