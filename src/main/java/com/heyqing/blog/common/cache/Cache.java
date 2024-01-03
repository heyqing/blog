package com.heyqing.blog.common.cache;

import java.lang.annotation.*;

/**
 * ClassName:Cache
 * Package:com.heyqing.blog.common.cache
 * Description:
 *
 * @Date:2023/10/26
 * @Author:HeYiQing
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Cache {

    //过期时间
    long expire() default 1*60*1000;
    //缓存表识 key
    String name() default "";
}
