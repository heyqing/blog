package com.heyqing.blog.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.heyqing.blog.dao.pojo.ArticleBody;
import com.heyqing.blog.dao.pojo.Category;
import com.heyqing.blog.dao.pojo.SysUser;
import com.heyqing.blog.dao.pojo.Tag;
import lombok.Data;

import java.util.List;

@Data
public class ArticleVo {

  //  @JsonSerialize(using = ToStringSerializer.class)
    private String  id;

    private String title;

    private String summary;

    private Integer commentCounts;

    private Integer viewCounts;

    private Integer weight;
    /**
     * 创建时间
     */
    private String createDate;

    private String author = "何以晴";

    private ArticleBodyVo body;

    private List<TagVo> tags;

    private CategoryVo category;

}
