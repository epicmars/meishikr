package com.meishikr.app.model.http.retrofit;

import com.alibaba.fastjson.JSON;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Converter;

/**
 * Created by yinhang on 16/3/11.
 */
public class FastjsonRequestBodyConverter<T> implements Converter<T, RequestBody> {

    @Override
    public RequestBody convert(T value) throws IOException {
        return RequestBody.create(MediaType.parse("application/json"), JSON.toJSONString(value));
    }
}
