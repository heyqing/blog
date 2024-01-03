package com.heyqing.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.heyqing.blog.dao.mapper.CommentMapper;
import com.heyqing.blog.dao.pojo.Comment;
import com.heyqing.blog.dao.pojo.SysUser;
import com.heyqing.blog.service.CommentsService;
import com.heyqing.blog.service.SysUserService;
import com.heyqing.blog.utils.UserThreadLocal;
import com.heyqing.blog.vo.CommentVo;
import com.heyqing.blog.vo.Result;
import com.heyqing.blog.vo.UserVo;
import com.heyqing.blog.vo.params.CommentParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName:CommentsServiceImpl
 * Package:com.heyqing.blog.service.impl
 * Description:
 *
 * @Date:2023/10/23
 * @Author:HeYiQing
 */
@Service
public class CommentsServiceImpl implements CommentsService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private SysUserService sysUserService;
    /**
     * 根据文章id 查询所有的评论列表
     * @param id
     * @return
     */
    @Override
    public Result commentsByArticleId(Long id) {

        /**
         * 1.从comments中查询
         * 2.根据作者id 查询作者信息
         * 3.判断 如果 level=1 要去查询有没有子评论
         *      有   根据评论id进行查询 （parent_id）
         */
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getArticleId,id);
        queryWrapper.eq(Comment::getLevel,1);
        List<Comment> comments = commentMapper.selectList(queryWrapper);
        List<CommentVo> commentVoList = copyList(comments);
        return Result.success(commentVoList);
    }

    private List<CommentVo> copyList(List<Comment> comments) {
        List<CommentVo> commentVoList = new ArrayList<>();
        for (Comment comment:comments) {
            commentVoList.add(copy(comment));
        }
        return commentVoList;
    }

    private CommentVo copy(Comment comment) {
       CommentVo commentVo = new CommentVo();
        BeanUtils.copyProperties(comment,commentVo);
       commentVo.setId(String.valueOf(comment.getId()));
        //作者信息
        Long authorId = comment.getAuthorId();
        UserVo userVo = this.sysUserService.findUserVoById(authorId);
        commentVo.setAuthor(userVo);
        //子评论
        Integer level = comment.getLevel();
        if (1 == level){
            Long id = comment.getId();
            List<CommentVo> commentVoList = findCommentsByParentId(id);
            commentVo.setChildrens(commentVoList);
        }
        //toUser给谁评论
        if (level > 1){
            Long id = comment.getToUid();
            UserVo toUserVo = this.sysUserService.findUserVoById(id);
            commentVo.setToUser(toUserVo);
        }
        return commentVo;
    }
    //子评论查询
    private List<CommentVo> findCommentsByParentId(Long id) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(Comment::getParentId,id);
        queryWrapper.eq(Comment::getLevel,2);
        return copyList(commentMapper.selectList(queryWrapper));
    }

    @Override
    public Result comment(CommentParam commentParam) {
        SysUser sysUser = UserThreadLocal.get();
        Comment comment = new Comment();
        comment.setArticleId(commentParam.getArticleId());
        comment.setAuthorId(sysUser.getId());
        comment.setContent(commentParam.getContent());
        comment.setCreateDate(System.currentTimeMillis());
        Long parent = commentParam.getParent();
        if (parent == null || parent == 0) {
            comment.setLevel(1);
        }else{
            comment.setLevel(2);
        }
        comment.setParentId(parent == null ? 0 : parent);
        Long toUserId = commentParam.getToUserId();
        comment.setToUid(toUserId == null ? 0 : toUserId);
        this.commentMapper.insert(comment);
        return Result.success(null);
    }
}
