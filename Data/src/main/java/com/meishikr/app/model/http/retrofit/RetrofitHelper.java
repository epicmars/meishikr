package com.meishikr.app.model.http.retrofit;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.meishikr.app.model.api.AuthApi;
import com.meishikr.app.model.api.BlogApi;
import com.meishikr.app.model.BuildConfig;
import com.sin2pi.brick.interfaces.http.IHttpRequest;
import com.sin2pi.brick.interfaces.http.IRequest;
import com.sin2pi.brick.interfaces.http.IResponse;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * Created by yinhang on 16/3/11.
 */
public class RetrofitHelper implements IHttpRequest {

    private static final long CONNECT_TIMEOUT = 5000;
    private static final long READ_TIMEOUT = 10000;
    private static final long WRITE_TIMEOUT = 10000;

    private static RetrofitHelper helper = new RetrofitHelper();
    private Retrofit retrofit;

    private RetrofitHelper(){
        OkHttpClient client = new OkHttpClient.Builder()
                // 调试工具添加到最后
//                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
//                .readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS)
//                .writeTimeout(WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
                .addNetworkInterceptor(new StethoInterceptor())
                .build();
        retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(BuildConfig.BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(FastjsonConverterFactory.create())
                .build();
    }

    @Override
    public IResponse request(IRequest request) {
        return null;
    }

    public static RetrofitHelper instance(){
        if(null == helper){
            helper = new RetrofitHelper();
        }
        return helper;
    }


    public Retrofit.Builder builder(){
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(FastjsonConverterFactory.create());

    }

    public AuthApi authApi(){
        return retrofit.create(AuthApi.class);
    }

    public BlogApi blogApi(){
        return retrofit.create(BlogApi.class);
    }
}
