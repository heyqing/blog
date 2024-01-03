package com.heyqing.blog.controller;

import com.heyqing.blog.common.aop.LogAnnotation;
import com.heyqing.blog.common.cache.Cache;
import com.heyqing.blog.service.ArticleService;
import com.heyqing.blog.vo.Result;
import com.heyqing.blog.vo.params.ArticleParam;
import com.heyqing.blog.vo.params.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;

/**
 * ClassName:ArticleController
 * Package:com.heyqing.blog.controller
 * Description:
 *
 * @Date:2023/10/17 21:25
 * @Author:HeYiQing
 */
//json格式交互
@RestController
@RequestMapping("articles")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    /**
     * 首页 文章列表
     * @param pageParams
     * @return
     */
    @PostMapping
    @LogAnnotation(module="文章",operator = "获取文章列表")
    @Cache(expire = 5*60*1000,name = "listArticle")
    public Result listArticle(@RequestBody PageParams pageParams){
    //int i = 10/0;//系统异常测试
        return articleService.listArticle(pageParams);
    }

    /**
     * 首页 最热文章
     * @return
     */
    @PostMapping("hot")
    @Cache(expire = 5*60*1000,name = "hot_article")
    public Result hotArticle(){
        int limit = 5;
        return articleService.hotArticle(limit);
    }

    /**
     * 首页 最新文章
     * @return
     */
    @PostMapping("new")
    @Cache(expire = 5*60*1000,name = "new_article")
    public Result newArticle(){
        int limit = 5;
        return articleService.newArticles(limit);
    }

    @PostMapping("listArchives")
    public Result listArchives(){
        return articleService.listArchives();
    }

    @PostMapping("view/{id}")
    public Result findArticleById(@PathVariable("id") Long articleId) {
        return articleService.findArticleById(articleId);
    }

    @PostMapping("publish")
    public Result publish(@RequestBody ArticleParam articleParam){
        return articleService.publish(articleParam);
    }
}
