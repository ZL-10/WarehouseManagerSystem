package com.zl.sys.controller;

import com.zl.sys.common.ActiveUser;
import com.zl.sys.common.ResultObj;
import com.zl.sys.common.WebUtils;
import com.zl.sys.domain.LogInfo;
import com.zl.sys.service.LogInfoService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping(value = "/login")
public class LoginController {

    @Autowired
    private LogInfoService logInfoService;

    @RequestMapping(value = "/login")
    public ResultObj login(String loginName, String pwd, Model model) {
        Subject subject = SecurityUtils.getSubject();

        AuthenticationToken token = new UsernamePasswordToken(loginName, pwd);
        try {
            subject.login(token);
            ActiveUser activeUser = (ActiveUser) subject.getPrincipal();
            model.addAttribute("user", activeUser);
            WebUtils.getHttpSession().setAttribute("user", activeUser.getUser());

            //记录登录日志
            LogInfo logInfo=new LogInfo();
            String name=activeUser.getUser().getName();
            String loginName1=activeUser.getUser().getLoginname();
            String loginIp=WebUtils.getHttpServletRequest().getLocalAddr();
            Date loginTime=new Date();
            logInfo.setLoginname(name+"-"+loginName1);
            logInfo.setLoginip(loginIp);
            logInfo.setLogintime(loginTime);
            logInfoService.save(logInfo);

            return ResultObj.LOGIN_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.LOGIN_FAIL_PASS;
        }


    }
}
