package com.heyqing.blog.controller;

import com.heyqing.blog.service.CommentsService;
import com.heyqing.blog.vo.Result;
import com.heyqing.blog.vo.params.CommentParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * ClassName:CommentsController
 * Package:com.heyqing.blog.controller
 * Description:
 *
 * @Date:2023/10/23
 * @Author:HeYiQing
 */
@RestController
@RequestMapping("comments")
public class CommentsController {

    @Autowired
    private CommentsService commentsService;
    @GetMapping("article/{id}")
    public Result comments(@PathVariable("id") Long id){
        return commentsService.commentsByArticleId(id);
    }



    @PostMapping("create/change")
    public Result comment(@RequestBody CommentParam commentParam){
        return commentsService.comment(commentParam);
    }
}
