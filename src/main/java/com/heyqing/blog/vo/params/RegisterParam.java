package com.heyqing.blog.vo.params;

import lombok.Data;

/**
 * ClassName:RegisterParam
 * Package:com.heyqing.blog.vo.params
 * Description:
 *
 * @Date:2023/10/19 18:08
 * @Author:HeYiQing
 */
@Data
public class RegisterParam {

    private String account;

    private String password;

    private String nickname;
}
