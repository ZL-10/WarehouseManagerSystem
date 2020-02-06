package com.zl.sys.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zl.sys.common.Constant;
import com.zl.sys.common.DataGridView;
import com.zl.sys.domain.User;
import com.zl.sys.mapper.RoleMapper;
import com.zl.sys.mapper.UserMapper;
import com.zl.sys.service.UserService;
import com.zl.sys.vo.UserVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("userService")
@Transactional
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public boolean save(User entity) {
        return super.save(entity);
    }

    @Override
    public boolean updateById(User entity) {
        return super.updateById(entity);
    }

    @Override
    public boolean removeById(Serializable id) {
        roleMapper.deleteRoleUserByUid(id);
        return super.removeById(id);
    }

    @Override
    public User getById(Serializable id) {
        return super.getById(id);
    }

    @Override
    public void saveUserRole(Integer uid, Integer[] ids) {
        this.roleMapper.deleteRoleUserByUid(uid);
        if(null!=ids&&ids.length>0){
            for (Integer id:ids) {
                this.roleMapper.insertUserRole(uid,id);
            }
        }
    }

    @Override
    public IPage<User> loadAllUserByPage(UserVo userVo) {
        IPage<User> page = new Page<>(userVo.getPage(), userVo.getLimit());
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(userVo.getName()), "loginname", userVo.getName()).or().eq(StringUtils.isNotBlank(userVo.getName()), "name", userVo.getName());
        queryWrapper.eq(StringUtils.isNotBlank(userVo.getAddress()), "address", userVo.getAddress());
        queryWrapper.eq("type", Constant.USER_TYPE_NORMAL);//查询系统用户
        queryWrapper.eq(userVo.getDeptid() != null, "deptid", userVo.getDeptid());
        return this.page(page, queryWrapper);
    }

    @Override
    public void setUserLeaderName(User user,Integer mgrId) {
        user.setLeaderName(this.getById(mgrId).getName());
    }

    @Override
    public Map<String, Object> loadUserMaxOrderNum() {
        Map<String, Object> map = new HashMap<String, Object>();

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("ordernum");
        IPage<User> page = new Page<>(1, 1);
        List<User> list = this.page(page, queryWrapper).getRecords();
        if (list.size() > 0) {
            map.put("value", list.get(0).getOrdernum() + 1);
        } else {
            map.put("value", 1);
        }
        return map;
    }

    @Override
    public DataGridView loadUsersByDeptId(Integer deptId) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(deptId != null, "deptid", deptId);
        queryWrapper.eq("available", Constant.AVAILABLE_TRUE);
        queryWrapper.eq("type", Constant.USER_TYPE_NORMAL);
        List<User> list = this.list(queryWrapper);
        return new DataGridView(list);
    }

    @Override
    public void addUser(UserVo userVo) {
        userVo.setType(Constant.USER_TYPE_NORMAL);//设置类型
        userVo.setHiredate(new Date());
        String salt = IdUtil.simpleUUID().toUpperCase();
        userVo.setSalt(salt);//设置盐
        userVo.setPwd(new Md5Hash(Constant.USER_DEFAULT_PWD, salt, 2).toString());//设置密码
        this.save(userVo);
    }

    @Override
    public void resetPwd(Integer id) {
        User user = new User();
        user.setId(id);
        String salt = IdUtil.simpleUUID().toUpperCase();
        user.setSalt(salt);//设置盐
        user.setPwd(new Md5Hash(Constant.USER_DEFAULT_PWD, salt, 2).toString());//设置密码
        this.updateById(user);
    }
}
