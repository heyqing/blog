package com.heyqing.blog.controller;

import com.heyqing.blog.service.RegisterService;
import com.heyqing.blog.vo.Result;
import com.heyqing.blog.vo.params.LoginParam;
import com.heyqing.blog.vo.params.RegisterParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName:RegisterController
 * Package:com.heyqing.blog.controller
 * Description:
 *
 * @Date:2023/10/19 18:02
 * @Author:HeYiQing
 */
@RestController
@RequestMapping("register")
public class RegisterController {
    @Autowired
    private RegisterService registerService;

    @PostMapping
    public Result register(@RequestBody RegisterParam registerParam){
        return registerService.register(registerParam);
    }

}
