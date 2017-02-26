package com.meishikr.app.domain.dto.req;


import com.alibaba.fastjson.JSON;
import com.meishikr.app.domain.dto.res.ResPostBlog;
import com.meishikr.app.domain.entity.post.Photo;
import com.meishikr.app.domain.entity.post.Blog;
import com.sin2pi.brick.common.http.BaseRequest;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by yinhang on 16/3/13.
 */
public class ReqPostBlog extends BaseRequest<ResPostBlog> {

    public List<File> photos;
    public Blog post;

    public ReqPostBlog(Blog post){
        this.post = post;
        // TODO html文本在服务器端生成
        if(null != post.getPhotos() && post.getPhotos().size() != 0){
            this.photos = new ArrayList<>();
            for(Photo photo : post.getPhotos()){
                this.photos.add(new File(photo.getLocalPath()));
            }
        }
    }

    /**
     * TODO 多张图片返回413的问题
     * @return
     */
    public Map<String, RequestBody> multiPart(){
        Map<String, RequestBody> blogMultipart = new HashMap<>();
        blogMultipart.put("post", RequestBody.create(MediaType.parse("text/plain"), JSON.toJSONString(post)));
        if(null == photos || photos.isEmpty())
            return blogMultipart;
        for(File photo : photos){
            // TODO 为什么已拍的照片不存在
            if(photo.exists()){
                blogMultipart.put(photo.getName() + "\"; filename=\"" + photo.getName(),
                        RequestBody.create(MediaType.parse("image/jpeg"), photo));
            }
        }
        return blogMultipart;
    }
/*
    public MultipartBody multiPart(){
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.addFormDataPart("body", body);
        builder.addFormDataPart("uuid", uuid);
        //
        if(null == photos || photos.isEmpty()){
            return builder.build();
        }
        for(File photo : photos){
            if(photo.exists())
                builder.addFormDataPart(photo.getName(), photo.getName(),RequestBody.create(MediaType.parse("image/jpeg"), photo));
        }
        return builder.build();
    }*/

}
