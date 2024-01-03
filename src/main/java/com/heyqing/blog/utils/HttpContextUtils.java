package com.heyqing.blog.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * ClassName:HttpContextUtils
 * Package:com.heyqing.blog.utils
 * Description:
 *
 * @Date:2023/10/24
 * @Author:HeYiQing
 */
public class HttpContextUtils {
    public static HttpServletRequest getHttpServletRequest(){
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }
}
