package com.meishikr.app.view.activity.blog;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;

import com.meishikr.app.R;
import com.meishikr.app.base.BaseActivity;
import com.sin2pi.brick.components.base.annotation.BindLayout;
import com.meishikr.app.databinding.ActivityBlogBinding;
import com.meishikr.app.viewmodel.BlogPageViewModel;
import com.meishikr.app.viewmodel.event.EventLoadBlog;
import com.sin2pi.brick.common.rxbus.Subscribe;

import javax.inject.Inject;

/**
 * 博客正文页面。
 */
@BindLayout(R.layout.activity_blog)
public class BlogPageActivity extends BaseActivity<ActivityBlogBinding> {

    private static final String BASE_URL = "file:///android_asset/www/templates/";

    @Inject
    BlogPageViewModel blogPageViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        component.inject(this);

        setSupportActionBar(binding.toolbar);
        binding.fabMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        WebSettings settings = binding.content.wvBlog.getSettings();
        settings.setJavaScriptEnabled(false);
        binding.content.wvBlog.setWebViewClient(new WebViewClient());

        blogPageViewModel.onCreate();
    }

    @Subscribe
    public void onLoadBlog(EventLoadBlog eventLoadBlog) {
        binding.content.wvBlog.loadDataWithBaseURL(BASE_URL, eventLoadBlog.getBlogStr(), "text/html", "UTF-8", null);
    }
}
