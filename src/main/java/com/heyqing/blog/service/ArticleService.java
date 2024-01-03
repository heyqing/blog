package com.heyqing.blog.service;

import com.heyqing.blog.vo.Result;
import com.heyqing.blog.vo.params.ArticleParam;
import com.heyqing.blog.vo.params.PageParams;

/**
 * ClassName:ArticleService
 * Package:com.heyqing.blog.service
 * Description:
 *
 * @Date:2023/10/17 21:49
 * @Author:HeYiQing
 */
public interface ArticleService {

    /**
     * 分页查询 文章列表
     * @param pageParams
     * @return
     */
    Result listArticle(PageParams pageParams);

    /**
     * 最热文章
     * @param limit
     * @return
     */
    Result hotArticle(int limit);

    /**
     * 最新文章
     * @param limit
     * @return
     */
    Result newArticles(int limit);

    /**
     * 文章归档
     * @return
     */
    Result listArchives();

    /**
     * 查询文章详情
     * @param articleId
     * @return
     */
    Result findArticleById(Long articleId);

    /**
     * 文章发布服务
     * @param articleParam
     * @return
     */
    Result publish(ArticleParam articleParam);
}
