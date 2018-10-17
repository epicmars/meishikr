package com.meishikr.app.view.activity.blog;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.meishikr.app.R;
import com.sin2pi.brick.components.base.adapter.RecyclerAdapter;
import com.sin2pi.brick.components.base.annotation.BindLayout;
import com.meishikr.app.databinding.ActivityBlogListBinding;
import com.meishikr.app.domain.entity.post.Blog;
import com.meishikr.app.domain.entity.post.Lamp;
import com.meishikr.app.domain.interactor.post.GetMyBlogsCase;
import com.meishikr.app.domain.interactor.post.GetMyLampsCase;
import com.meishikr.app.base.BaseActivity;
import com.meishikr.app.view.viewholder.MyBlogViewHolder;
import com.meishikr.app.view.viewholder.MyLampViewHolder;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * 展示当前用户博客，本地的和网络的。本地博客主要是最新发表的（多个）博客，
 * 以本地webview方式显示，显示发表的状态，包括：正在发表中（图片较多，在后台上传），
 * 发表成功，发表失败。加上网络已发表博客，共4种状态。对已发表博客的修改后再发表，
 * 对应同样有3种状态，那么一共有7种状态。
 */
@BindLayout(R.layout.activity_blog_list)
public class MyBlogListActivity extends BaseActivity<ActivityBlogListBinding> {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    public boolean mTwoPane;

    RecyclerAdapter adapter;

    @Inject
    GetMyBlogsCase getMyBlogsCase;

    @Inject
    GetMyLampsCase getMyLampsCase;

    public static void launch(Context context) {
        Intent intent = new Intent(context, MyBlogListActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(binding.toolbar);
        component.inject(this);
        binding.toolbar.setTitle(getTitle());
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        adapter = new RecyclerAdapter();
        adapter.register(MyBlogViewHolder.class);
        adapter.register(MyLampViewHolder.class);
        binding.content.blogitemList.setAdapter(adapter);
        binding.content.blogitemList.setLayoutManager(new LinearLayoutManager(this));

        if (findViewById(R.id.blogitem_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        getMyBlogsCase.execute(new Subscriber<List<Blog>>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<Blog> posts) {
                adapter.addAll(posts);
            }
        });

        getMyLampsCase.execute(new Subscriber<List<Lamp>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<Lamp> lamps) {
                adapter.addAll(lamps);
            }
        });

    }
}
