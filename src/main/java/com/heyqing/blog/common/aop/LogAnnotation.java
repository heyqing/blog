package com.heyqing.blog.common.aop;

import java.lang.annotation.*;

/**
 * ClassName:LogAnnotation
 * Package:com.heyqing.blog.common.aop
 * Description:
 *
 * @Date:2023/10/24
 * @Author:HeYiQing
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogAnnotation {

    String module() default "";
    String operator() default "";
}
