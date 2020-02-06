package com.zl.sys.controller;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zl.sys.common.Constant;
import com.zl.sys.common.DataGridView;
import com.zl.sys.common.PinyinUtils;
import com.zl.sys.common.ResultObj;
import com.zl.sys.domain.Dept;
import com.zl.sys.domain.Role;
import com.zl.sys.domain.User;
import com.zl.sys.service.DeptService;
import com.zl.sys.service.RoleService;
import com.zl.sys.service.UserService;
import com.zl.sys.vo.UserVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.transform.Result;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    @Lazy
    private UserService userService;

    @Autowired
    @Lazy
    private DeptService deptService;

    @Autowired
    @Lazy
    private RoleService roleService;

    /**
     * 加载所有用户
     *
     * @param userVo
     * @return
     */
    @RequestMapping(value = "/loadAllUser")
    public DataGridView loadAllUser(UserVo userVo) {
        IPage<User> page = new Page<>(userVo.getPage(), userVo.getLimit());
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(userVo.getName()), "loginname", userVo.getName()).or().eq(StringUtils.isNotBlank(userVo.getName()), "name", userVo.getName());
        queryWrapper.eq(StringUtils.isNotBlank(userVo.getAddress()), "address", userVo.getAddress());
        queryWrapper.eq("type", Constant.USER_TYPE_NORMAL);//查询系统用户
        queryWrapper.eq(userVo.getDeptid() != null, "deptid", userVo.getDeptid());
        this.userService.page(page, queryWrapper);

        List<User> users = page.getRecords();
        for (User user : users) {
            Integer deptId = user.getDeptid();
            if (null != deptId) {
                Dept dept = deptService.getById(deptId);
                user.setDeptName(dept.getTitle());
            }

            Integer mgrId = user.getMgr();
            if (null != mgrId) {
                User leader = this.userService.getById(mgrId);
                user.setLeaderName(leader.getName());
            }

        }
        return new DataGridView(page.getTotal(), users);
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/loadUserById")
    public DataGridView loadUserById(Integer id) {
        return new DataGridView(this.userService.getById(id));
    }

    /**
     * 获取最大排序码
     *
     * @return
     */
    @RequestMapping("loadUserMaxOrderNum")
    public Map<String, Object> loadUserMaxOrderNum() {
        Map<String, Object> map = new HashMap<String, Object>();

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("ordernum");
        IPage<User> page = new Page<>(1, 1);
        List<User> list = this.userService.page(page, queryWrapper).getRecords();
        if (list.size() > 0) {
            map.put("value", list.get(0).getOrdernum() + 1);
        } else {
            map.put("value", 1);
        }
        return map;
    }

    /**
     * 根据部门id查询用户
     *
     * @param deptId
     **/
    @RequestMapping("loadUsersByDeptId")
    public DataGridView loadUsersByDeptId(Integer deptId) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(deptId != null, "deptid", deptId);
        queryWrapper.eq("available", Constant.AVAILABLE_TRUE);
        queryWrapper.eq("type", Constant.USER_TYPE_NORMAL);
        List<User> list = this.userService.list(queryWrapper);
        return new DataGridView(list);
    }

    /**
     * 把用户名转成拼音
     *
     * @param username
     **/
    @RequestMapping("changeChineseToPinyin")
    public Map<String, Object> changeChineseToPinyin(String username) {
        Map<String, Object> map = new HashMap<>();
        if (null != username) {
            map.put("value", PinyinUtils.getPingYin(username));
        } else {
            map.put("value", "");
        }
        return map;
    }

    /**
     * 添加用户
     *
     * @param userVo
     * @return
     */
    @RequestMapping(value = "/addUser")
    public ResultObj addUser(UserVo userVo) {
        try {
            userVo.setType(Constant.USER_TYPE_NORMAL);//设置类型
            userVo.setHiredate(new Date());
            String salt = IdUtil.simpleUUID().toUpperCase();
            userVo.setSalt(salt);//设置盐
            userVo.setPwd(new Md5Hash(Constant.USER_DEFAULT_PWD, salt, 2).toString());//设置密码
            this.userService.save(userVo);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }
    }


    /**
     * 更新用户
     *
     * @param userVo
     * @return
     */
    @RequestMapping(value = "/updateUser")
    public ResultObj updateUser(UserVo userVo) {
        try {
            this.userService.updateById(userVo);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }


    /**
     * 删除用户
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/deleteUser")
    public ResultObj deleteUser(Integer id) {
        try {
            this.userService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }

    /**
     * 重置密码
     * @param id
     * @return
     */
    @RequestMapping(value = "/resetPwd")
    public ResultObj resetPwd(Integer id) {
        try {
            User user = new User();
            user.setId(id);
            String salt = IdUtil.simpleUUID().toUpperCase();
            user.setSalt(salt);//设置盐
            user.setPwd(new Md5Hash(Constant.USER_DEFAULT_PWD, salt, 2).toString());//设置密码
            this.userService.updateById(user);
            return ResultObj.RESET_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.RESET_ERROR;
        }
    }

    /**
     * 根据用户ID查询角色并选中已拥有的角色
     * @param id
     * @return
     */
    @RequestMapping(value = "/initRoleByUserId")
    public DataGridView initRoleByUserId(Integer id){
        try{
            QueryWrapper<Role> queryWrapper=new QueryWrapper<>();
            queryWrapper.eq("available",Constant.AVAILABLE_TRUE);
            List<Map<String,Object>> listMaps=this.roleService.listMaps();
            System.out.println(listMaps.size());


            List<Integer> currentUserRoleIds=this.roleService.queryUserRoleIdsByUid(id);
            for (Map<String, Object> map : listMaps) {
                Boolean LAY_CHECKED=false;
                Integer roleId=(Integer) map.get("id");
                for (Integer rid : currentUserRoleIds) {
                    if(rid==roleId) {
                        LAY_CHECKED=true;
                        break;
                    }
                }
                map.put("LAY_CHECKED", LAY_CHECKED);
            }
            return new DataGridView(Long.valueOf(listMaps.size()), listMaps);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 保存用户与角色的关系
     * @param uid
     * @param ids
     * @return
     */
    @RequestMapping(value = "/saveUserRole")
    public ResultObj saveUserRole(Integer uid,Integer[] ids){
        try{
            this.userService.saveUserRole(uid,ids);
            return ResultObj.DISPATCH_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.DISPATCH_ERROR;
        }
    }

}
