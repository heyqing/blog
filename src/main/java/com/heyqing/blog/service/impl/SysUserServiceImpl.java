package com.heyqing.blog.service.impl;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.heyqing.blog.controller.LoginController;
import com.heyqing.blog.dao.mapper.SysUserMapper;
import com.heyqing.blog.dao.pojo.SysUser;
import com.heyqing.blog.service.LoginService;
import com.heyqing.blog.service.SysUserService;
import com.heyqing.blog.utils.JWTUtils;
import com.heyqing.blog.vo.ErrorCode;
import com.heyqing.blog.vo.LoginUserVo;
import com.heyqing.blog.vo.Result;
import com.heyqing.blog.vo.UserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * ClassName:SysUserServiceImpl
 * Package:com.heyqing.blog.service.impl
 * Description:
 *
 * @Date:2023/10/18 16:38
 * @Author:HeYiQing
 */
@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private RedisTemplate<String ,String > redisTemplate;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    @Lazy
    private LoginService loginService;


    @Override
    public SysUser findUserById(Long id) {
        SysUser sysUser = (SysUser) sysUserMapper.selectById(id);
        if (sysUser == null){
            sysUser = new SysUser();
            sysUser.setNickname("何以晴");
        }
        return sysUser;
    }

    @Override
    public SysUser findUser(String account, String password) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount,account);
        queryWrapper.eq(SysUser::getPassword,password);
        queryWrapper.select(SysUser::getAccount,SysUser::getId,SysUser::getAvatar,SysUser::getNickname);
        queryWrapper.last("limit 1");
        return sysUserMapper.selectOne(queryWrapper);
    }

    /**
     * 根据token查询用户信息
     *
     * @param token
     * @return
     */
    @Override
    public Result findUserByToken(String token) {
        /**
         * 1.token合法校验
         *      是否为空 解析是否成功 Redis是否存在
         * 2.如果校验失败 返回错误
         * 3.如果校验成功 返回结果  LoginUserVo
         */
        Map<String, Object> map = JWTUtils.checkToken(token);
        if (map == null){
            return Result.fail(ErrorCode.SESSION_TIME_OUT.getCode(),ErrorCode.SESSION_TIME_OUT.getMsg());
        }


        String userJson = redisTemplate.opsForValue().get("TOKEN_" + token);
        if (StringUtils.isBlank(userJson)){
            return Result.fail(ErrorCode.NO_LOGIN.getCode(),ErrorCode.NO_LOGIN.getMsg());
        }
        SysUser sysUser = JSON.parseObject(userJson, SysUser.class);
        LoginUserVo loginUserVo = new LoginUserVo();
        loginUserVo.setAccount(sysUser.getAccount());
        loginUserVo.setAvatar(sysUser.getAvatar());
        loginUserVo.setId(String.valueOf(sysUser.getId()));
        loginUserVo.setNickname(sysUser.getNickname());
        return Result.success(loginUserVo);
    }

    /**
     * 根据账户查找用户
     *
     * @param account
     * @return
     */
    @Override
    public SysUser findUserByAccount(String account) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(SysUser::getAccount,account);
        queryWrapper.last("limit 1");
        return this.sysUserMapper.selectOne(queryWrapper);
    }

    /**
     * 保存用户
     *
     * @param sysUser
     */
    @Override
    public void save(SysUser sysUser) {
        //保存后Id自动生成
        //默认生成的Id是分布式Id 雪花算法
        this.sysUserMapper.insert(sysUser);
    }

    @Override
    public UserVo findUserVoById(Long id) {
        SysUser sysUser = (SysUser) sysUserMapper.selectById(id);
        if (sysUser == null){
            sysUser = new SysUser();
            sysUser.setId(1L);
            sysUser.setAvatar("/static/img/logo.b3a48c0.png");
            sysUser.setNickname("何以晴");        }
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(sysUser,userVo);
        userVo.setId(String.valueOf(sysUser.getId()));
        return userVo;
    }

}
