package com.zl.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zl.sys.domain.User;

public interface UserService extends IService<User> {
    //保存用户与角色的关系
    void saveUserRole(Integer uid, Integer[] ids);
}
