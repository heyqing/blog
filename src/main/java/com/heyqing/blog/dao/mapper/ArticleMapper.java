package com.heyqing.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.heyqing.blog.dao.dos.Archives;
import com.heyqing.blog.dao.pojo.Article;

import java.util.List;

/**
 * ClassName:ArticleMapper
 * Package:com.heyqing.blog.dao.mapper
 * Description:
 *
 * @Date:2023/10/17 21:18
 * @Author:HeYiQing
 */
public interface ArticleMapper extends BaseMapper<Article> {
    List<Archives> listArchives();

    IPage<Article> listArticle(Page<Article> page,Long categoryId ,Long tagId,String year ,String month);
}
