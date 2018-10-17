package com.sin2pi.brick.common.http.retrofit;

import com.alibaba.fastjson.JSONReader;
import com.sin2pi.brick.common.http.exception.HttpException;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by yinhang on 16/3/11.
 */
public class FastjsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {

    private Type type;

    public FastjsonResponseBodyConverter(Type type){
        this.type = type;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
//      BufferedReader reader = new BufferedReader(value.charStream());
        JSONReader reader = new JSONReader(value.charStream());
        try{
            T response = (T) reader.readObject(type);
            if(null == response)
                throw new HttpException("Json string parse failed!");
            return response;
        } finally {
            if(null != value)
                value.close();
        }
    }
}
