package com.zl.sys.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zl.sys.common.DataGridView;
import com.zl.sys.common.PinyinUtils;
import com.zl.sys.common.ResultObj;
import com.zl.sys.domain.User;
import com.zl.sys.service.DeptService;
import com.zl.sys.service.RoleService;
import com.zl.sys.service.UserService;
import com.zl.sys.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        try {
            IPage<User> page = userService.loadAllUserByPage(userVo);
            List<User> users = page.getRecords();
            for (User user : users) {
                Integer deptId = user.getDeptid();
                if (null != deptId) {
                    this.deptService.setUserDeptName(user,deptId);
                }

                Integer mgrId = user.getMgr();
                if (null != mgrId) {
                    this.userService.setUserLeaderName(user, mgrId);
                }

            }
            return new DataGridView(page.getTotal(), users);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }


    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/loadUserById")
    public DataGridView loadUserById(Integer id) {
        try{
            return new DataGridView(this.userService.getById(id));
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 获取最大排序码
     *
     * @return
     */
    @RequestMapping("loadUserMaxOrderNum")
    public Map<String, Object> loadUserMaxOrderNum() {
        try{
            return userService.loadUserMaxOrderNum();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 根据部门id查询用户
     *
     * @param deptId
     **/
    @RequestMapping("loadUsersByDeptId")
    public DataGridView loadUsersByDeptId(Integer deptId) {
        try {
            return this.userService.loadUsersByDeptId(deptId);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

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
            this.userService.addUser(userVo);
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
            this.userService.resetPwd(id);
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
            List<Map<String,Object>> listMaps=this.roleService.initRoleByUserId(id);
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
