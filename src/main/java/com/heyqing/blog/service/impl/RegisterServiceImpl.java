package com.heyqing.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.heyqing.blog.dao.pojo.SysUser;
import com.heyqing.blog.service.RegisterService;
import com.heyqing.blog.service.SysUserService;
import com.heyqing.blog.utils.JWTUtils;
import com.heyqing.blog.vo.ErrorCode;
import com.heyqing.blog.vo.Result;
import com.heyqing.blog.vo.params.RegisterParam;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

/**
 * ClassName:RegisterServiceImpl
 * Package:com.heyqing.blog.service.impl
 * Description:
 *
 * @Date:2023/10/19 18:11
 * @Author:HeYiQing
 */
@Service
@Transactional
public class RegisterServiceImpl implements RegisterService {

    private static final String salt = "heyqing!@#$&";


    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private RedisTemplate<String ,String > redisTemplate;
    /**
     * 注册
     * @param registerParam
     * @return
     */
    @Override
    public Result register(RegisterParam registerParam) {
        /**
         * 1.判断参数是否合法
         * 2.判断账户是否存在
         *      存在 返回账号已经被注册
         *      不存在 注册账户
         * 3.生成token
         * 4.存入Redis并返回
         * 5.注意
         *      加上事务 一旦中间的任何过程出现问题 注册的用户需要回滚
         */
        String account = registerParam.getAccount();
        String password = registerParam.getPassword();
        String nickname = registerParam.getNickname();
        if (StringUtils.isBlank(account)||StringUtils.isBlank(password)||StringUtils.isBlank(nickname)){
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(),ErrorCode.PARAMS_ERROR.getMsg());
        }
        SysUser sysUser = sysUserService.findUserByAccount(account);
        if (sysUser != null){
            return Result.fail(ErrorCode.ACCOUNT_EXIST.getCode(),ErrorCode.ACCOUNT_EXIST.getMsg());
        }
        sysUser = new SysUser();
        sysUser.setNickname(nickname);
        sysUser.setAccount(account);
        sysUser.setPassword(DigestUtils.md5Hex(password+salt));
        sysUser.setCreateDate(System.currentTimeMillis());
        sysUser.setLastLogin(System.currentTimeMillis());
        sysUser.setAvatar("/static/img/logo.b3a48c0.png");
        sysUser.setAdmin(1); //1 为true
        sysUser.setDeleted(0); // 0 为false
        sysUser.setSalt("");
        sysUser.setStatus("");
        sysUser.setEmail("");
        this.sysUserService.save(sysUser);

        String token = JWTUtils.createToken(sysUser.getId());
        redisTemplate.opsForValue().set("TOKEN"+token, JSON.toJSONString(sysUser),1, TimeUnit.DAYS);
        return Result.success(token);
    }
}
