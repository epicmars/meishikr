package com.meishikr.app.view.viewholder;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Toast;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.meishikr.app.R;
import com.sin2pi.brick.components.base.adapter.BaseViewHolder;
import com.sin2pi.brick.components.base.annotation.BindLayout;
import com.meishikr.app.databinding.ViewHolderBlogBinding;
import com.meishikr.app.model.api.BlogApi;
import com.meishikr.app.domain.dto.req.ReqPostBlog;
import com.meishikr.app.domain.dto.res.ResPostBlog;
import com.meishikr.app.domain.entity.auth.Token;
import com.meishikr.app.domain.entity.post.Blog;
import com.meishikr.app.domain.interactor.post.SaveBlogCase;
import com.meishikr.app.model.http.retrofit.RetrofitHelper;
import com.meishikr.app.base.BaseActivity;
import com.meishikr.app.view.activity.blog.BlogDetailActivity;
import com.meishikr.app.view.activity.blog.MyBlogListActivity;
import com.meishikr.app.view.fragment.BlogDetailFragment;
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
 * Created by yinhang on 16/3/26.
 */
@BindLayout(R.layout.view_holder_blog)
public class MyBlogViewHolder extends BaseViewHolder<Blog, ViewHolderBlogBinding> {

    @Inject
    SaveBlogCase saveBlogCase;

    public MyBlogViewHolder(View view) {
        super(view);
        ((BaseActivity) view.getContext()).getComponent().inject(this);
    }

    @Override
    public void render(final Blog data, int position) {
        binding.tvTitle.setText(data.getTitle());
        binding.tvContent.setText(data.getBody());
        if(Blog.POSTING == data.getStatus()){
            binding.progress.setVisibility(View.VISIBLE);
            // TODO 请求初始化放到构造函数中
            ReqPostBlog reqBlogUpload = new ReqPostBlog(data);
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
            final Observable<ResPostBlog> res = retrofit.create(BlogApi.class)
                    .postBlog(reqBlogUpload.multiPart());
            res.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ResPostBlog>() {
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
                        public void onNext(ResPostBlog resToken) {
                            resToken.blog.setId(data.getId());
                            resToken.blog.setStatus(Blog.POSTED);
                            saveBlogCase.setBlog(resToken.blog).execute(new Subscriber<Long>() {
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

        //
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    MyBlogListActivity context = (MyBlogListActivity)v.getContext();
                    if (context.mTwoPane) {
                        Bundle arguments = new Bundle();
                        arguments.putString(BlogDetailFragment.BLOG_UUID, data.getUuid());
                        BlogDetailFragment fragment = new BlogDetailFragment();
                        fragment.setArguments(arguments);
                        ((BaseActivity) context).getSupportFragmentManager().beginTransaction()
                                .replace(R.id.blogitem_detail_container, fragment)
                                .commit();
                    } else {
                        Intent intent = new Intent(context, BlogDetailActivity.class);
                        intent.putExtra(BlogDetailFragment.BLOG_UUID, data.getUuid());

                        context.startActivity(intent);
                    }
                } catch (ClassCastException e) {

                }

            }
        });
    }

    @Override
    public String toString() {
        return super.toString() + " '" + binding.tvContent.getText() + "'";
    }
}
