package com.heyqing.blog.handler;

import com.alibaba.fastjson.JSON;
import com.heyqing.blog.dao.pojo.SysUser;
import com.heyqing.blog.service.LoginService;
import com.heyqing.blog.utils.UserThreadLocal;
import com.heyqing.blog.vo.ErrorCode;
import com.heyqing.blog.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ClassName:LoginInterceptor
 * Package:com.heyqing.blog.handler
 * Description:
 *
 * @Date:2023/10/20
 * @Author:HeYiQing
 */
@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private LoginService loginService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //在执行controller方法之前执行
        /**
         * 1.需要判断 请求的接口路径 是否为 HandlerMethod (controller方法)
         * 2.判断token是否为空
         *      为空  未登录
         *      不为空 登录验证 loginService checkToken
         * 3.认证成功 放行
         */
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
        String token = request.getHeader("Authorization");
        log.info("=================request start===========================");
        String requestURI = request.getRequestURI();
        log.info("request uri:{}",requestURI);
        log.info("request method:{}",request.getMethod());
        log.info("token:{}", token);
        log.info("=================request end===========================");

        if (StringUtils.isBlank(token)){
            Result result = Result.fail(ErrorCode.NO_LOGIN.getCode(), ErrorCode.NO_LOGIN.getMsg());
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSONString(result));
            return false;
        }
        SysUser sysUser = loginService.checkToken(token);
        if (sysUser ==null){
            Result result = Result.fail(ErrorCode.NO_LOGIN.getCode(), ErrorCode.NO_LOGIN.getMsg());
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSONString(result));
            return false;
        }
        //登录验证成功，放行
        //希望在controller中 直接获取用户信息
        UserThreadLocal.put(sysUser);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //如果不删除ThreadLocal中用完的信息会有内存泄露的风险
        UserThreadLocal.remove();
    }
}
