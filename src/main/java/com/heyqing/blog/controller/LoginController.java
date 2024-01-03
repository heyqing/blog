package com.heyqing.blog.controller;

import com.heyqing.blog.service.LoginService;
import com.heyqing.blog.service.SysUserService;
import com.heyqing.blog.vo.Result;
import com.heyqing.blog.vo.params.LoginParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName:LoginController
 * Package:com.heyqing.blog.controller
 * Description:
 *
 * @Date:2023/10/18 22:05
 * @Author:HeYiQing
 */
@RestController
@RequestMapping("login")
public class LoginController {

    /*@Autowired
    private SysUserService sysUserService;*/
    //不好，登录不是SysUserService负责
    //专门写一个登录service
    @Autowired
    private LoginService loginService;

    @PostMapping
    public Result login(@RequestBody LoginParam loginParam){
        //登录 验证用户 访问用户表
        return loginService.login(loginParam);
    }
}
