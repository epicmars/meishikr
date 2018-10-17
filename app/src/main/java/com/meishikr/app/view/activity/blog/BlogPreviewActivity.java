package com.meishikr.app.view.activity.blog;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.meishikr.app.R;
import com.sin2pi.brick.components.base.annotation.BindLayout;
import com.meishikr.app.databinding.ActivityBlogPreviewBinding;
import com.meishikr.app.base.BaseActivity;

/**
 * 博客预览页面，设置可选的web页面模板用于预览编辑中的文章。
 */
@BindLayout(R.layout.activity_blog_preview)
public class BlogPreviewActivity extends BaseActivity<ActivityBlogPreviewBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(binding.toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
