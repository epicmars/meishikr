package com.meishikr.app.domain.dto.req;

import com.alibaba.fastjson.JSON;
import com.meishikr.app.domain.dto.res.ResPostLamp;
import com.meishikr.app.domain.entity.post.Lamp;
import com.meishikr.app.domain.entity.post.Photo;
import com.sin2pi.brick.common.http.BaseRequest;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by yinhang on 2016/12/18.
 */

public class ReqPostLamp extends BaseRequest<ResPostLamp> {

    public List<File> photos;
    public Lamp lamp;

    public ReqPostLamp(Lamp lamp) {
        this.lamp = lamp;
        if (null != lamp.getPhotos() && lamp.getPhotos().size() != 0) {
            this.photos = new ArrayList<>();
            for (Photo photo : lamp.getPhotos()) {
                this.photos.add(new File(photo.getLocalPath()));
            }
        }
    }

    /**
     *
     * @return
     */
    public Map<String, RequestBody> multiPart() {
        Map<String, RequestBody> lampMultipart = new HashMap<>();
        lampMultipart.put("lamp", RequestBody.create(MediaType.parse("text/plain"), JSON.toJSONString(lamp)));
        if (null == photos || photos.isEmpty())
            return lampMultipart;
        for (File photo : photos) {
            // TODO 为什么已拍的照片不存在
            if (photo.exists()) {
                lampMultipart.put(photo.getName() + "\"; filename=\"" + photo.getName(),
                        RequestBody.create(MediaType.parse("image/jpeg"), photo));
            }
        }
        return lampMultipart;
    }
}
