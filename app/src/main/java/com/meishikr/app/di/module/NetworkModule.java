package com.meishikr.app.di.module;

import android.app.Application;
import android.util.Base64;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.meishikr.app.domain.entity.auth.Token;
import com.meishikr.app.model.BuildConfig;
import com.meishikr.app.model.http.retrofit.FastjsonConverterFactory;
import com.sin2pi.brick.components.xml.SharedPreferenceHelper;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * Created by yinhang on 16/11/4.
 */

@Module
public class NetworkModule {

    private static final long CONNECT_TIMEOUT = 5000;
    private static final long READ_TIMEOUT = 60000;
    private static final long WRITE_TIMEOUT = 100000;

    @Provides
    @Singleton
    @Named("okhttp")
    public OkHttpClient getOkHttpClient() {
        OkHttpClient client = new OkHttpClient.Builder()
                // 调试工具添加到最后
//                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
//                .readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS)
//                .writeTimeout(WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
                .addNetworkInterceptor(new StethoInterceptor())
                .build();

        return client;
    }

    @Provides
    @Singleton
    @Named("authorization")
    public OkHttpClient getAuthorizationClient(Application app) {
        Token token = SharedPreferenceHelper.instance(app).load(Token.class);
        final String authToken = "Basic " + Base64.encodeToString((token.accessToken + ":").getBytes(),
                Base64.NO_WRAP);

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request request = chain.request().newBuilder().addHeader("Authorization",
                                authToken).build();
                        return chain.proceed(request);
                    }
                })
                .addNetworkInterceptor(new StethoInterceptor())
                .build();

        return client;
    }

    @Provides
    @Singleton
    public Retrofit getRetrofit(@Named("okhttp") OkHttpClient client) {
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(BuildConfig.BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(FastjsonConverterFactory.create())
                .build();

        return retrofit;
    }

}
