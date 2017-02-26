package com.meishikr.app.viewmodel;

import android.app.Activity;

import com.meishikr.app.application.Consts;
import com.meishikr.app.base.ActivityViewModel;
import com.meishikr.app.domain.entity.post.Blog;
import com.meishikr.app.domain.interactor.post.LoadOneBlogCase;
import com.meishikr.app.domain.repository.BlogRepo;
import com.meishikr.app.viewmodel.event.EventLoadBlog;
import com.sin2pi.brick.common.utils.DateUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by yinhang on 2016/12/27.
 */

public class BlogPageViewModel extends ActivityViewModel {


    @Inject
    BlogRepo blogRepo;

    @Inject
    LoadOneBlogCase loadOneBlogCase;

    @Inject
    public BlogPageViewModel(Activity context) {
        super(context);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        final String uuid = getActivity().getIntent().getStringExtra(Consts.Extras.BLOG_UUID);
        if (null == uuid) {

        }
        loadOneBlogCase.set(uuid).execute(new Subscriber<Blog>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Blog blog) {
                //
                BufferedReader br = null;
                try {
                    String blogRegex = "<blog></blog>";
                    String timeStampRegex = "<timestamp></timestamp>";
                    String coverRegex = "<cover></cover>";
                    br = new BufferedReader(new InputStreamReader(getContext().getAssets().open("www/templates/blog.html")));
                    String line = null;
                    StringBuilder template = new StringBuilder();
                    while ((line = br.readLine()) != null) {
                        template.append(line);
                    }

                    String blogStr = "";
                    if (null != blog.getBodyHtml()) {
                        blogStr = template.toString().replaceAll(blogRegex, blog.getBodyHtml());
                        blogStr = blogStr.replaceAll(timeStampRegex, DateUtil.formatDateTimeStandard(blog.getTimestamp()));
                        getBus().post(new EventLoadBlog(blogStr));
                    }
                } catch (IOException e) {

                } finally {
                    if (null != br) {
                        try {
                            br.close();
                        } catch (IOException e) {

                        }
                    }
                }
            }
        });
    }
}
