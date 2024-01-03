package com.heyqing.blog.controller;

import com.heyqing.blog.service.TagsService;
import com.heyqing.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName:TagsController
 * Package:com.heyqing.blog.controller
 * Description:
 *
 * @Date:2023/10/18 17:19
 * @Author:HeYiQing
 */
@RestController
@RequestMapping("tags")
public class TagsController {
    @Autowired
    private TagsService tagService;


    //  /tags/hot
    @GetMapping("hot")
    public Result hot(){
        int limit = 6 ;
        return tagService.hot(limit);
    }

    @GetMapping
    public Result findAll(){
        return tagService.findAll();
    }

    @GetMapping("detail")
    public Result findAllDetail(){
        return tagService.findAllDetail();
    }

    @GetMapping("detail/{id}")
    public Result findDetailById(@PathVariable("id") Long id){
        return tagService.findDetailById(id);
    }
}

