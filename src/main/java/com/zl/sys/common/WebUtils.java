package com.zl.sys.common;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class WebUtils {

    public static HttpServletRequest getHttpServletRequest(){
        ServletRequestAttributes requestAttributes=(ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request=requestAttributes.getRequest();
        return request;
    }

    public static HttpSession getHttpSession(){
        return getHttpServletRequest().getSession();
    }

}
