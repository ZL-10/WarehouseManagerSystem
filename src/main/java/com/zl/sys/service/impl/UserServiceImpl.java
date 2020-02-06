package com.zl.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zl.sys.domain.User;
import com.zl.sys.mapper.RoleMapper;
import com.zl.sys.mapper.UserMapper;
import com.zl.sys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

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
}
