package com.heyqing.blog.service;

import com.heyqing.blog.dao.pojo.SysUser;
import com.heyqing.blog.vo.Result;
import com.heyqing.blog.vo.params.LoginParam;

/**
 * ClassName:LoginService
 * Package:com.heyqing.blog.service
 * Description:
 *
 * @Date:2023/10/18 22:11
 * @Author:HeYiQing
 */
public interface LoginService {

    /**
     * 登录功能
     * @param loginParam
     * @return
     */
    Result login(LoginParam loginParam);

    SysUser checkToken(String token);

    /**
     * 退出登录
     * @param token
     * @return
     */
    Result logout(String token);
}
