package com.heyqing.blog.service;

import com.heyqing.blog.vo.Result;
import com.heyqing.blog.vo.params.CommentParam;

/**
 * ClassName:CommentsService
 * Package:com.heyqing.blog.service
 * Description:
 *
 * @Date:2023/10/23
 * @Author:HeYiQing
 */
public interface CommentsService {
    /**
     * 根据文章id 查询所有的评论列表
     * @param id
     * @return
     */
    Result commentsByArticleId(Long id);

    Result comment(CommentParam commentParam);
}
