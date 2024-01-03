package com.heyqing.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heyqing.blog.dao.pojo.Tag;

import java.util.List;

/**
 * ClassName:TagMapper
 * Package:com.heyqing.blog.dao.mapper
 * Description:
 *
 * @Date:2023/10/17 21:20
 * @Author:HeYiQing
 */
public interface TagMapper extends BaseMapper<Tag> {
    /**
     * 根据文章id查询标签列表
     * @param articleId
     * @return
     */
    List<Tag> findTagsByArticleId(Long articleId);

    /**
     * 查询最热标签 前limit个
     * @param limit
     * @return
     */
    List<Long> findHotsTagIds(int limit);

    List<Tag> findTagsByTagTds(List<Long> tagIds);
}
