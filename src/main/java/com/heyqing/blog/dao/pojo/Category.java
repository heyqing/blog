package com.heyqing.blog.dao.pojo;

import lombok.Data;

/**
 * ClassName:Category
 * Package:com.heyqing.blog.dao.pojo
 * Description:
 *
 * @Date:2023/10/22
 * @Author:HeYiQing
 */
@Data
public class Category {
    private Long id;

    private String avatar;

    private String categoryName;

    private String description;
}
