package com.zl.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zl.sys.domain.User;
import com.zl.sys.mapper.UserMapper;
import com.zl.sys.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userService")
@Transactional
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
