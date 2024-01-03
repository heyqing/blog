package com.heyqing.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.heyqing.blog.dao.dos.Archives;
import com.heyqing.blog.dao.mapper.ArticleBodyMapper;
import com.heyqing.blog.dao.mapper.ArticleMapper;
import com.heyqing.blog.dao.mapper.ArticleTagMapper;
import com.heyqing.blog.dao.pojo.Article;
import com.heyqing.blog.dao.pojo.ArticleBody;
import com.heyqing.blog.dao.pojo.ArticleTag;
import com.heyqing.blog.dao.pojo.SysUser;
import com.heyqing.blog.service.*;
import com.heyqing.blog.utils.UserThreadLocal;
import com.heyqing.blog.vo.ArticleBodyVo;
import com.heyqing.blog.vo.ArticleVo;
import com.heyqing.blog.vo.Result;
import com.heyqing.blog.vo.TagVo;
import com.heyqing.blog.vo.params.ArticleParam;
import com.heyqing.blog.vo.params.PageParams;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName:ArticleServiceImpl
 * Package:com.heyqing.blog.service.impl
 * Description:
 *
 * @Date:2023/10/17 21:52
 * @Author:HeYiQing
 */
@Service
public class ArticleServiceImpl implements ArticleService {
    /**
     * 分页查询 文章列表
     *
     * @param pageParams
     * @return
     */
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private TagsService tagService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private ArticleTagMapper articleTagMapper;

    @Override
    public Result listArticle(PageParams pageParams) {
        Page<Article> page = new Page<>(pageParams.getPage(),pageParams.getPageSize());
        IPage<Article> articleIPage = articleMapper.listArticle(page, pageParams.getCategoryId(),
                pageParams.getTagId(), pageParams.getYear(), pageParams.getMonth());
        List<Article> records = articleIPage.getRecords();
        return Result.success(copyList(records,true,true));
    }

   /* @Override
    public Result listArticle(PageParams pageParams) {
        *//**
         * 分页查询article数据库表
         *//*
        Page<Article> page = new Page<>(pageParams.getPage(),pageParams.getPageSize());
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        if (pageParams.getCategoryId() != null){
            queryWrapper.eq(Article::getCategoryId,pageParams.getCategoryId());
        }
        List<Long> articleIdList = new ArrayList<>();
        if (pageParams.getTagId() != null){
            LambdaQueryWrapper<ArticleTag> articleTagLambdaQueryWrapper = new LambdaQueryWrapper<>();
            articleTagLambdaQueryWrapper.eq(ArticleTag::getTagId,pageParams.getTagId());
            List<ArticleTag> articleTags = articleTagMapper.selectList(articleTagLambdaQueryWrapper);
            for (ArticleTag articleTag : articleTags) {
                articleIdList.add(articleTag.getArticleId());
            }
            if (articleIdList.size()>0){
                queryWrapper.in(Article::getId,articleIdList);
            }
        }
        //置顶排序
        //时间倒序
        queryWrapper.orderByDesc(Article::getCreateDate,Article::getWeight);
        Page<Article> articlePage = articleMapper.selectPage(page, queryWrapper);
        List<Article> records = articlePage.getRecords();
        //不能直接返回，
        List<ArticleVo>  articleVoList = copyList(records,true,true);
        return Result.success(articleVoList);
    }
*/
    //重载
    private List<ArticleVo> copyList(List<Article> records,boolean isTag,boolean isAuthor) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article record : records) {
            articleVoList.add(copy(record,isTag,isAuthor,false,false));
        }
        return articleVoList;
    }

    private List<ArticleVo> copyList(List<Article> records,boolean isTag,boolean isAuthor,boolean isBody ,boolean isCategory) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article record : records) {
            articleVoList.add(copy(record,isTag,isAuthor,isBody,isCategory));
        }
        return articleVoList;
    }

    @Autowired
    private CategoryService categoryService;

    private ArticleVo copy(Article article,boolean isTag,boolean isAuthor,boolean isBody,boolean isCategory){
        ArticleVo articleVo = new ArticleVo();
        articleVo.setId(String.valueOf(article.getId()));
        BeanUtils.copyProperties(article,articleVo);

        articleVo.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
        //并不是所有的接口都需要标签、作者信息
        if (isTag){
            Long articleId = article.getId();
            articleVo.setTags(tagService.findTagsByArticleId(articleId));
        }
        if (isAuthor){
            Long authorId = article.getAuthorId();
            articleVo.setAuthor(sysUserService.findUserById(authorId).getNickname());
        }
        if(isBody){
            Long bodyId = article.getBodyId();
            articleVo.setBody(findArticleBodyById(bodyId));
        }
        if (isCategory){
            Long categoryId = article.getCategoryId();

          articleVo.setCategory(categoryService.findCategoryById(categoryId));
        }
        return articleVo;
    }

    @Autowired
    private ArticleBodyMapper articleBodyMapper;
    private ArticleBodyVo findArticleBodyById(Long bodyId) {
        ArticleBody articleBody = articleBodyMapper.selectById(bodyId);
        ArticleBodyVo articleBodyVo = new ArticleBodyVo();
        articleBodyVo.setContent(articleBody.getContent());
        return articleBodyVo;
    }

    /**
     * 最热文章
     *
     * @param limit
     * @return
     */
    @Override
    public Result hotArticle(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getViewCounts);
        queryWrapper.select(Article::getId,Article::getTitle);
        //limit后加空格
        queryWrapper.last("limit "+limit);
        //select id ,title from article order by view_counts desc limit 5
        List<Article> articles = articleMapper.selectList(queryWrapper);
        return Result.success(copyList(articles,false,false));
    }

    /**
     * 最新文章
     *
     * @return
     */
    @Override
    public Result newArticles(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getCreateDate);
        queryWrapper.select(Article::getId,Article::getTitle);
        //limit后加空格
        queryWrapper.last("limit "+limit);
        //select id ,title from article order by create_data desc limit 5
        List<Article> articles = articleMapper.selectList(queryWrapper);
        return Result.success(copyList(articles,false,false));
    }

    /**
     * 文章归档
     *
     * @return
     */
    @Override
    public Result listArchives() {
        List<Archives> archivesList = articleMapper.listArchives();
        return Result.success(archivesList);
    }

    /**
     * 查询文章详情
     *
     * @param articleId
     * @return
     */
    @Autowired
    private ThreadService threadService;
    @Override
    public Result findArticleById(Long articleId) {
        /**
         * 1.根据ID查询 文章信息
         * 2.根据bodyId和categoryId 去做关联查询
         */
        Article article = this.articleMapper.selectById(articleId);
        ArticleVo articleVo = copy(article, true, true,true,true);
        //查询完文章，新增阅读数
        //查看完文章后本应该返回数据，这时进行了更新操作   更新时数据库会加写锁，阻塞其他的读操作，性能会将低
        //更新->增加此次接口的耗时
        //如果更新出问题，不能影响查看文章的操作

        //线程池
        //可以把更新操作扔到线程池中执行
        threadService.updateArticleViewCount(articleMapper,article);
        return Result.success(articleVo);
    }

    /**
     * 文章发布服务
     *
     * @param articleParam
     * @return
     */
    @Override
    public Result publish(ArticleParam articleParam) {
        /**
         * 1.发布文章 目的是构建article对象
         * 2.作者id 当前的登录用户   UserThreadLocal获取
         * 3.标签     要将标签加入到关联表当中
         * 4.body     内容存储 article bodyId
         */
        SysUser sysUser = UserThreadLocal.get();
        Article article = new Article();
        article.setAuthorId(sysUser.getId());
        article.setWeight(Article.Article_Common);
        article.setViewCounts(0);
        article.setTitle(articleParam.getTitle());
        article.setSummary(articleParam.getSummary());
        article.setCommentCounts(0);
        article.setCreateDate(System.currentTimeMillis());
        article.setCategoryId(Long.parseLong(articleParam.getCategory().getId()));
        //插入之后会产生有一个文章id
        this.articleMapper.insert(article);
        //tag
        List<TagVo> tags = articleParam.getTags();
        if(tags!=null){
            for (TagVo tag : tags) {
                Long articleId = article.getId();
                ArticleTag articleTag = new ArticleTag();
                articleTag.setTagId(Long.parseLong(tag.getId()));
                articleTag.setArticleId(articleId);
                articleTagMapper.insert(articleTag);
            }
        }
        //body
        ArticleBody articleBody = new ArticleBody();
        articleBody.setArticleId(article.getId());
        articleBody.setContent(articleParam.getBody().getContent());
        articleBody.setContentHtml(articleParam.getBody().getContentHtml());
        articleBodyMapper.insert(articleBody);
        article.setBodyId(articleBody.getId());
        articleMapper.updateById(article);

        ArticleVo articleVo = new ArticleVo();
        articleVo.setId(String.valueOf(article.getId()));
        return Result.success(articleVo);
    }
}
