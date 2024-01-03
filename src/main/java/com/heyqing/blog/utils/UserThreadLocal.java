package com.heyqing.blog.utils;

import com.heyqing.blog.dao.pojo.SysUser;

/**
 * ClassName:UserThreadLocal
 * Package:com.heyqing.blog.utils
 * Description:
 *      强引用、弱引用
 *      ThreadLocal、内存泄露
 * @Date:2023/10/22
 * @Author:HeYiQing
 */
public class UserThreadLocal {

    private UserThreadLocal(){}

    private static final ThreadLocal<SysUser> LOCAL = new ThreadLocal<>();

    public static void put(SysUser sysUser){
        LOCAL.set(sysUser);
    }

    public static SysUser get(){
        return LOCAL.get();
    }

    public static void remove(){
        LOCAL.remove();
    }
}
