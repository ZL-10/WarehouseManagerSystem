package com.zl.sys.realm;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zl.sys.common.ActiveUser;
import com.zl.sys.common.Constant;
import com.zl.sys.domain.Permission;
import com.zl.sys.domain.User;
import com.zl.sys.service.PermissionService;
import com.zl.sys.service.RoleService;
import com.zl.sys.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserRealm extends AuthorizingRealm {

    @Autowired
    @Lazy
    private UserService userService;

    @Autowired
    @Lazy
    private PermissionService permissionService;


    @Autowired
    @Lazy
    private RoleService roleService;

    public String getClassName(){
        return this.getClass().getName();
    }

    /**
     *  授权
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo authorizationInfo=new SimpleAuthorizationInfo();
        ActiveUser activeUser= (ActiveUser) principals.getPrimaryPrincipal();
        User user = activeUser.getUser();
        List<String> permissions=activeUser.getPermissions();
        if(user.getType()==Constant.USER_TYPE_SUPER){
            authorizationInfo.addStringPermission("*:*");
        }else{
            if(null!=permissions&&permissions.size()>0){
                authorizationInfo.addStringPermissions(permissions);
            }
        }
        return authorizationInfo;

    }

    /**
     * 认证
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("loginname",token.getPrincipal().toString());
        User user=userService.getOne(queryWrapper);
        if(null!=user){
            ActiveUser activeUser = new ActiveUser();
            activeUser.setUser(user);


            QueryWrapper<Permission> queryWrapper1=new QueryWrapper();
            queryWrapper1.eq("type", Constant.TYPE_PERMISSION);
            queryWrapper1.eq("available", Constant.AVAILABLE_TRUE);
            List<Permission> list = new ArrayList<>();
            //根据用户Id查询角色
            List<Integer> currentUserRoleIds = roleService.queryUserRoleIdsByUid(user.getId());
            //根据角色id查询权限
            Set<Integer> pids = new HashSet<>();

            for (Integer rid : currentUserRoleIds) {
                List<Integer> permissionIdsByRid = roleService.queryRolePermissionIdsByRid(rid);
                pids.addAll(permissionIdsByRid);
            }
            if (pids.size() > 0) {
                queryWrapper1.in("id", pids);
                list = permissionService.list(queryWrapper1);
            }
            List<String> percodes=new ArrayList<>();
            for (Permission permission : list) {
                percodes.add(permission.getPercode());
            }

            activeUser.setPermissions(percodes);

            ByteSource salt = ByteSource.Util.bytes(user.getSalt());
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(activeUser, user.getPwd(), salt, this.getClassName());
            return info;
        }
        return null;
    }
}
