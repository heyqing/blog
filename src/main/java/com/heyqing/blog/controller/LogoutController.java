package com.heyqing.blog.controller;

import com.heyqing.blog.service.LoginService;
import com.heyqing.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * ClassName:LogoutController
 * Package:com.heyqing.blog.controller
 * Description:
 *
 * @Date:2023/10/19 17:52
 * @Author:HeYiQing
 */
@RestController
@RequestMapping("logout")
public class LogoutController {
    @Autowired
    private LoginService loginService;
    @GetMapping
    public Result logout(@RequestHeader("Authorization" ) String token){
        return loginService.logout(token);
    }
}
