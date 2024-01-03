package com.heyqing.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.heyqing.blog.dao.pojo.SysUser;
import com.heyqing.blog.service.LoginService;
import com.heyqing.blog.service.SysUserService;
import com.heyqing.blog.utils.JWTUtils;
import com.heyqing.blog.vo.ErrorCode;
import com.heyqing.blog.vo.Result;
import com.heyqing.blog.vo.params.LoginParam;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * ClassName:LoginServiceImpl
 * Package:com.heyqing.blog.service.impl
 * Description:
 *
 * @Date:2023/10/18 22:15
 * @Author:HeYiQing
 */
@Service
public class LoginServiceImpl implements LoginService {
    //加密盐
    private static final String salt = "heyqing!@#$&";


    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private RedisTemplate<String ,String > redisTemplate;


    /**
     * 登录功能
     *
     * @param loginParam
     * @return
     */
    @Override
    public Result login(LoginParam loginParam) {
        /**
         * 1。检查参数是否合法
         * 2.根据用户名和密码去user表中查询 是否存在
         * 3.如果不存在 登录失败
         * 4.如果存在 使用JWT生成token 返回给前端
         * 5.token放入redis token：user信息 设置过期时间
         *      （登录认证的时候 先认证token字符串是否合法，去redis认证是否存在）
         */
        String account = loginParam.getAccount();
        String password = loginParam.getPassword();
        if (StringUtils.isBlank(account)||StringUtils.isBlank(password))
        {
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(),ErrorCode.PARAMS_ERROR.getMsg());
        }
        password = DigestUtils.md5Hex(password + salt);
        SysUser sysUser = sysUserService.findUser(account,password);
        if (sysUser == null){
          return Result.fail(ErrorCode.ACCOUNT_PWD_NOT_EXIST.getCode(),ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMsg());
        }
        //登录成功，使用JWT生成token，返回token和redis中
        String token = JWTUtils.createToken(sysUser.getId());
        redisTemplate.opsForValue().set("TOKEN_"+token, JSON.toJSONString(sysUser),1, TimeUnit.DAYS);
        return Result.success(token);
    }


    @Override
    public SysUser checkToken(String token) {
        if (StringUtils.isBlank(token)){
            return null;
        }
        Map<String, Object> stringObjectMap = JWTUtils.checkToken(token);
        if (stringObjectMap == null){
            return null;
        }
        String userJson = redisTemplate.opsForValue().get("TOKEN_" + token);
        if(StringUtils.isBlank(userJson)){
            return null;
        }
        SysUser sysUser = JSON.parseObject(userJson, SysUser.class);
        return sysUser;
    }

    /**
     * 退出登录
     * @param token
     * @return
     */
    @Override
    public Result logout(String token) {
        redisTemplate.delete("TOKEN_"+token);
        return Result.success(null);
    }
}
