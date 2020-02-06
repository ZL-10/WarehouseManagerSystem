package com.zl.sys.service;

import com.zl.sys.common.DataGridView;
import com.zl.sys.domain.Dept;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zl.sys.domain.User;
import com.zl.sys.vo.DeptVo;


public interface DeptService extends IService<Dept> {

    //通过部门id设置用户部门名称
    void setUserDeptName(User user, Integer deptId);

    //获取所有部门
    DataGridView loadAllDept(DeptVo deptVo);
}
