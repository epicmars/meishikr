package com.sin2pi.brick.common.http;

import com.alibaba.fastjson.JSON;
import com.sin2pi.brick.common.http.annotation.ReqMethod;
import com.sin2pi.brick.common.http.annotation.Req;
import com.sin2pi.brick.interfaces.http.IHttpRequest;
import com.sin2pi.brick.interfaces.http.IRequest;
import com.sin2pi.brick.interfaces.http.IResponse;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by christopher on 15-10-29.
 */
public class HttpRequestHelper implements IHttpRequest {

    private static final int CONNECT_TIMEOUT = 5000;
    private static final int READ_TIMEOUT = 100000;

    public IResponse request(IRequest request) {
        Req req = request.getClass().getAnnotation(Req.class);
        URL url = null;
        HttpURLConnection connection = null;
        BufferedOutputStream bos = null;
        BufferedReader br = null;
        try{
            url = new URL(request.url());
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(CONNECT_TIMEOUT);
            connection.setReadTimeout(READ_TIMEOUT);
            connection.setRequestMethod(req.method().method);
            connection.setDoInput(true);
            connection.setChunkedStreamingMode(0);
            connection.setRequestProperty("Content-Type", req.contentType());
            connection.setRequestProperty("User-Agent", "Dalvik/2.1.0 (Linux; U; Android 5.1; m2 note Build/LMY47D)");

            if (ReqMethod.POST == req.method()) {
                connection.setDoOutput(true);
                bos = new BufferedOutputStream(connection.getOutputStream());
                bos.write(request.getData().getBytes());
                if(null != bos){
                    try{
                        // XXX 此处关闭流后才可正常获得响应
                        bos.flush();
                        bos.close();
                    } catch (IOException e){
                    }
                }
            }

            br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder builder = new StringBuilder();
            while((line = br.readLine()) != null){
                builder.append(line);
            }
            return parseResponseData(builder.toString(), request.getClass());

        } catch (MalformedURLException e){
        } catch (IOException e){
        } finally {
            if(null != connection)
                connection.disconnect();



            if(null != br){
                try{
                    br.close();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public IResponse parseResponseData(final String json, final Class<?> reqClazz){
        Type type = reqClazz.getGenericSuperclass();
        if(type instanceof ParameterizedType){
            Type[] types = ((ParameterizedType) type).getActualTypeArguments();
            if(null != types && types.length == 1){
                return (IResponse) JSON.parseObject(json, types[0]);
            }
        }
        return null;
    }


}
