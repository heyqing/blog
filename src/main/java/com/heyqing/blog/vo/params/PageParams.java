package com.heyqing.blog.vo.params;

import lombok.Data;

/**
 * ClassName:PageParams
 * Package:com.heyqing.blog.vo
 * Description:
 *
 * @Date:2023/10/17 21:29
 * @Author:HeYiQing
 */
@Data
public class PageParams {

    private int page = 1;

    private int pageSize = 10;

    private Long categoryId;

    private Long tagId;

    private String year;

    private String month;

    public String getMonth(){
        if(this.month != null && this.month.length() == 1){
            return "0"+this.month;
        }
        return this.month;
    }
}
