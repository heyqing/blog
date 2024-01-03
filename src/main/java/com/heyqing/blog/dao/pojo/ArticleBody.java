package com.heyqing.blog.dao.pojo;

import lombok.Data;

/**
 * ClassName:ArticleBody
 * Package:com.heyqing.blog.dao.pojo
 * Description:
 *
 * @Date:2023/10/22
 * @Author:HeYiQing
 */
@Data
public class ArticleBody {
    private Long id;
    private String content;
    private String contentHtml;
    private Long articleId;
}
