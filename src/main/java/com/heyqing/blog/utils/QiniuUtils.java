package com.heyqing.blog.utils;

import com.alibaba.fastjson.JSON;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * ClassName:QiniuUtils
 * Package:com.heyqing.blog.utils
 * Description:
 *
 * @Date:2023/10/25
 * @Author:HeYiQing
 */
@Component
public class QiniuUtils {

    public static  final String url = "http://s3357od1j.hb-bkt.clouddn.com/";

    public  boolean upload(MultipartFile file,String fileName){

        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.huabei());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        String accessKey = "xo1q8TNQlOTjqW2gPkXb9EYmPbRthZ_6DFk_h_m9";
        String accessSecretKey = "Vcy_y7nPc8HRUYCexvJyz_7o0H7cz26N3orxv0Gl";
        String bucket = "heyqing-blog";
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        try {
            byte[] uploadBytes = file.getBytes();
            Auth auth = Auth.create(accessKey, accessSecretKey);
            String upToken = auth.uploadToken(bucket);
            Response response = uploadManager.put(uploadBytes, fileName, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = JSON.parseObject(response.bodyString(), DefaultPutRet.class);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
