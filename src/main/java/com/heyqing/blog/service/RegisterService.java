package com.heyqing.blog.service;

import com.heyqing.blog.vo.Result;
import com.heyqing.blog.vo.params.RegisterParam;
import org.springframework.transaction.annotation.Transactional;

/**
 * ClassName:RegisterService
 * Package:com.heyqing.blog.service
 * Description:
 *
 * @Date:2023/10/19 18:07
 * @Author:HeYiQing
 */
@Transactional
public interface RegisterService {
    /**
     * 注册
     * @param registerParam
     * @return
     */
    Result register(RegisterParam registerParam);
}
