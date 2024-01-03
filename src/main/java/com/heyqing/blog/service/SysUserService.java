package com.heyqing.blog.service;

import com.heyqing.blog.dao.pojo.SysUser;
import com.heyqing.blog.vo.Result;
import com.heyqing.blog.vo.UserVo;
import org.springframework.context.annotation.Lazy;


/**
 * ClassName:SysUserService
 * Package:com.heyqing.blog.service
 * Description:
 *
 * @Date:2023/10/18 16:35
 * @Author:HeYiQing
 */
public interface SysUserService {

    UserVo findUserVoById(Long id);

    SysUser findUserById (Long id);

    SysUser findUser(String account, String password);

    /**
     * 根据token查询用户信息
     * @param token
     * @return
     */
    Result findUserByToken(String token);

    /**
     * 根据账户查找用户
     * @param account
     * @return
     */
    SysUser findUserByAccount(String account);

    /**
     * 保存用户
     * @param sysUser
     */
    void save(SysUser sysUser);
}
