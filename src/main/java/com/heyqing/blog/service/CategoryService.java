package com.heyqing.blog.service;

import com.heyqing.blog.vo.CategoryVo;
import com.heyqing.blog.vo.Result;

import java.util.List;

/**
 * ClassName:CategoryService
 * Package:com.heyqing.blog.service
 * Description:
 *
 * @Date:2023/10/22
 * @Author:HeYiQing
 */
public interface CategoryService {

    CategoryVo findCategoryById(Long categoryId);

    //文章分类
    Result findAll();

    //
    Result findAllDetail();

    Result categoryDetailById(Long id);
}
