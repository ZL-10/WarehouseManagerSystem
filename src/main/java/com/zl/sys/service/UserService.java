package com.zl.sys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zl.sys.common.DataGridView;
import com.zl.sys.domain.User;
import com.zl.sys.vo.UserVo;

import java.util.Map;

public interface UserService extends IService<User> {
    //保存用户与角色的关系
    void saveUserRole(Integer uid, Integer[] ids);

    //加载所有用户
    IPage<User> loadAllUserByPage(UserVo userVo);

    //通过领导id设置领导名字
    void setUserLeaderName(User user,Integer mgrId);

    //获取最大排序码
    Map<String, Object> loadUserMaxOrderNum();

    //根据部门id查询用户
    DataGridView loadUsersByDeptId(Integer deptId);

    //添加用户
    void addUser(UserVo userVo);

    //重置密码
    void resetPwd(Integer id);
}
