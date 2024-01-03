package com.heyqing.blog.controller;

import com.heyqing.blog.service.SysUserService;
import com.heyqing.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * ClassName:UsersController
 * Package:com.heyqing.blog.controller
 * Description:
 *
 * @Date:2023/10/19 16:18
 * @Author:HeYiQing
 */
@RestController
@RequestMapping("users")
public class UsersController {

    @Autowired
    private SysUserService sysUserService;
    @GetMapping("currentUser")
    public Result currentUser(@RequestHeader("Authorization") String token){
        return sysUserService.findUserByToken(token);
    }
}
