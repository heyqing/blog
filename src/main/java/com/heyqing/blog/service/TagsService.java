package com.heyqing.blog.service;

import com.heyqing.blog.vo.Result;
import com.heyqing.blog.vo.TagVo;

import java.util.List;

/**
 * ClassName:TagService
 * Package:com.heyqing.blog.service.impl
 * Description:
 *
 * @Date:2023/10/18 15:55
 * @Author:HeYiQing
 */
public interface TagsService {
    List<TagVo> findTagsByArticleId(Long articleId);

    Result hot(int limit);

    /**
     * 查询所有的文章标签
     * @return
     */
    Result findAll();

    Result findAllDetail();

    Result findDetailById(Long id);
}
