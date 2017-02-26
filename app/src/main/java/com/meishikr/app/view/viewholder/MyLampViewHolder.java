package com.meishikr.app.view.viewholder;

import android.util.Base64;
import android.view.View;
import android.widget.Toast;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.meishikr.app.R;
import com.meishikr.app.base.adapter.BaseViewHolder;
import com.meishikr.app.base.annotation.BindLayout;
import com.meishikr.app.databinding.ViewHolderMyLampBinding;
import com.meishikr.app.model.api.LampApi;
import com.meishikr.app.domain.dto.req.ReqPostLamp;
import com.meishikr.app.domain.dto.res.ResPostLamp;
import com.meishikr.app.domain.entity.auth.Token;
import com.meishikr.app.domain.entity.post.Lamp;
import com.meishikr.app.domain.interactor.post.SaveLampCase;
import com.meishikr.app.model.http.retrofit.RetrofitHelper;
import com.meishikr.app.base.BaseActivity;
import com.sin2pi.brick.components.xml.SharedPreferenceHelper;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by yinhang on 2016/12/18.
 */

@BindLayout(R.layout.view_holder_my_lamp)
public class MyLampViewHolder extends BaseViewHolder<Lamp, ViewHolderMyLampBinding>{

    @Inject
    SaveLampCase saveLampCase;

    public MyLampViewHolder(View view) {
        super(view);
        ((BaseActivity) view.getContext()).getComponent().inject(this);

    }

    @Override
    public void render(Lamp data, int position) {
        if(Lamp.POSTING == data.getPostStatus()){
            binding.progress.setVisibility(View.VISIBLE);
            // TODO 请求初始化放到构造函数中
            ReqPostLamp reqPostLamp = new ReqPostLamp(data);
            // TODO 重新封装
            Token token = SharedPreferenceHelper.instance(binding.getRoot().getContext()).load(Token.class);
            final String authToken = "Basic " + Base64.encodeToString((token.accessToken + ":").getBytes(),
                    Base64.NO_WRAP);
            OkHttpClient client = new OkHttpClient.Builder()
                    .readTimeout(1000, TimeUnit.SECONDS)
                    .writeTimeout(1000, TimeUnit.SECONDS)
                    .addNetworkInterceptor(new StethoInterceptor())
                    .addInterceptor(new Interceptor() {
                        @Override
                        public okhttp3.Response intercept(Chain chain) throws IOException {
                            Request request = chain.request().newBuilder().addHeader("Authorization",
                                    authToken).build();
                            return chain.proceed(request);
                        }
                    }).build();
            Retrofit retrofit = RetrofitHelper.instance().builder().client(client).build();
            final Observable<ResPostLamp> res = retrofit.create(LampApi.class)
                    .postLamp(reqPostLamp.multiPart());
            res.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ResPostLamp>() {
                        @Override
                        public void onCompleted() {
                            binding.progress.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError(Throwable e) {
//                            blogDao.update(blog);
                            binding.progress.setVisibility(View.GONE);
                            Toast.makeText(binding.getRoot().getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                            Timber.e(e, "");
                        }

                        @Override
                        public void onNext(ResPostLamp resToken) {
                            binding.progress.setVisibility(View.GONE);
                            resToken.lamp.setId(data.getId());
                            resToken.lamp.setPostStatus(Lamp.POSTED);
                            saveLampCase.setLamp(resToken.lamp).execute(new Subscriber<Long>() {
                                @Override
                                public void onCompleted() {

                                }

                                @Override
                                public void onError(Throwable e) {

                                }

                                @Override
                                public void onNext(Long aLong) {

                                }
                            });
                            Timber.d(resToken.toString());
                        }
                    });
            //
        }

    }
}
