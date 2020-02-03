package com.zl.sys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/sys")
public class SystemController {

    @RequestMapping(value = "/toLogin")
    public String toLogin() {
        return "system/index/login";
    }

    @RequestMapping(value = "/index")
    public String toIndex(ModelAndView model) {
        return "system/index/index";
    }

    @RequestMapping(value = "/toDeskManager")
    public String toMainPage(){
        return "system/index/deskManager";
    }

    /**
     * 跳转到日志管理
     */
    @RequestMapping(value = "/toLogInfoManager")
    public String toLogInfoManager(){
        return "system/logInfo/logInfoManager";
    }

    /**
     * 跳转到公告管理
     */
    @RequestMapping(value = "/toNoticeManager")
    public String toNoticeManager(){
        return "system/notice/noticeManager";
    }

    /**
     * 跳转到部门管理
     */
    @RequestMapping(value = "/toDeptManager")
    public String toDeptManager(){

        return "system/dept/deptManager";
    }

    /**
     * 跳转到部门管理-left
     */
    @RequestMapping(value = "/toDeptLeft")
    public String toDeptLeft(){

        return "system/dept/deptLeft";
    }

    /**
     * 跳转到部门管理-right
     */
    @RequestMapping(value = "/toDeptRight")
    public String toDeptRight(){

        return "system/dept/deptRight";
    }




    /**
     * 跳转到菜单管理
     *
     */
    @RequestMapping(value = "/toMenuManager")
    public String toMenuManager() {
        return "system/menu/menuManager";
    }

    /**
     * 跳转到菜单管理-left
     *
     */
    @RequestMapping(value = "/toMenuLeft")
    public String toMenuLeft() {
        return "system/menu/menuLeft";
    }


    /**
     * 跳转到菜单管理--right
     *
     */
    @RequestMapping(value = "/toMenuRight")
    public String toMenuRight() {
        return "system/menu/menuRight";
    }

    /**
     * 跳转到权限管理
     *
     */
    @RequestMapping(value = "/toPermissionManager")
    public String toPermissionManager() {
        return "system/permission/permissionManager";
    }

    /**
     * 跳转到权限管理-left
     *
     */
    @RequestMapping(value = "/toPermissionLeft")
    public String toPermissionLeft() {
        return "system/permission/permissionLeft";
    }


    /**
     * 跳转到权限管理--right
     *
     */
    @RequestMapping(value = "/toPermissionRight")
    public String toPermissionRight() {
        return "system/permission/permissionRight";
    }


    /**
     * 跳转到角色管理
     *
     */
    @RequestMapping(value = "/toRoleManager")
    public String toRoleManager() {
        return "system/role/roleManager";
    }


}

