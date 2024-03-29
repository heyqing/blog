package com.heyqing.blog.controller;

import com.heyqing.blog.utils.QiniuUtils;
import com.heyqing.blog.vo.ErrorCode;
import com.heyqing.blog.vo.Result;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.annotation.Around;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * ClassName:UploadController
 * Package:com.heyqing.blog.controller
 * Description:
 *
 * @Date:2023/10/25
 * @Author:HeYiQing
 */
@RestController
@RequestMapping("upload")
public class UploadController {

    @Autowired
    private QiniuUtils qiniuUtils;
    @PostMapping
    public Result upload(@RequestParam("image")MultipartFile file){
        //原始文件名称
        String originalFilename = file.getOriginalFilename();
        //唯一文件名称
        String fileName = UUID.randomUUID().toString() + "." + StringUtils.substringAfter(originalFilename, ".");
        //上传文件 七牛云 云服务器 按量计算
        boolean upload = qiniuUtils.upload(file, fileName);
        if(upload){
            return Result.success(QiniuUtils.url+fileName);
        }
        return Result.fail(ErrorCode.UPLOAD_FAIL.getCode(),ErrorCode.UPLOAD_FAIL.getMsg());
    }
}
