package com.heyqing.blog.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * ClassName:Result
 * Package:com.heyqing.blog.vo.params
 * Description:
 *
 * @Date:2023/10/17 21:33
 * @Author:HeYiQing
 */
@Data
@AllArgsConstructor
public class Result {

    private boolean success;

    private int code;

    private String msg;

    private Object data;

    public static  Result success(Object data){
        return new Result(true,200,"success",data);
    }
    public static  Result fail(int code,String msg){
        return new Result(false,code,msg,null);
    }
}
