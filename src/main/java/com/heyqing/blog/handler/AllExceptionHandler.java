package com.heyqing.blog.handler;

import com.heyqing.blog.vo.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * ClassName:AllExceptionHandler
 * Package:com.heyqing.blog.handler
 * Description:
 *
 * @Date:2023/10/18 18:36
 * @Author:HeYiQing
 */
//对加了controller注解的方法进行拦截处理AOP实现
@ControllerAdvice
@ResponseBody//返回json数据
public class AllExceptionHandler {
    //进行异常处理，处理Exception.class的异常
    @ExceptionHandler(Exception.class)
    public Result doException(Exception ex){
        ex.printStackTrace();
        return Result.fail(-999,"系统异常");
    }
}
