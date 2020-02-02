package com.zl.sys.realm;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zl.sys.common.ActiveUser;
import com.zl.sys.domain.User;
import com.zl.sys.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    public String getClassName(){
        return this.getClass().getName();
    }

    /**
     * 认证
     * */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
       return null;

    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("loginname",token.getPrincipal().toString());
        User user=userService.getOne(queryWrapper);
        if(null!=user){
            ActiveUser activeUser = new ActiveUser();
            activeUser.setUser(user);
            ByteSource salt = ByteSource.Util.bytes(user.getSalt());
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(activeUser, user.getPwd(), salt, this.getClassName());
            return info;
        }
        return null;
    }
}
