package com.heyqing.blog.service;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.heyqing.blog.dao.mapper.ArticleMapper;
import com.heyqing.blog.dao.pojo.Article;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * ClassName:ThreadService
 * Package:com.heyqing.blog.service
 * Description:
 *
 * @Date:2023/10/23
 * @Author:HeYiQing
 */
@Component
public class ThreadService {

    //期望次操作在线程池 执行 不会影响原有的主线程
    @Async("taskExecutor")
    public void updateArticleViewCount(ArticleMapper articleMapper, Article article){
        int viewCounts = article.getViewCounts();
        Article articleUpdate = new Article();
        //增加阅读数  +1
        articleUpdate.setViewCounts(viewCounts+1);
        LambdaUpdateWrapper<Article> updateWrapper = new LambdaUpdateWrapper<>();
        //为了线程安全，增加viewCount
        updateWrapper.eq(Article::getId,article.getId());
        updateWrapper.eq(Article::getViewCounts,viewCounts);
        //update article set view_count=100 where view_count=99 and id=11;
        articleMapper.update(articleUpdate,updateWrapper);
        try {
            Thread.sleep(5000);
            System.out.println("更新完成了...");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
