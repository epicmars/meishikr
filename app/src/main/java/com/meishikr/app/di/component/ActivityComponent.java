package com.meishikr.app.di.component;

import com.meishikr.app.di.module.ActivityModule;
import com.meishikr.app.di.scope.ActivityScope;
import com.meishikr.app.view.activity.auth.LoginActivity;
import com.meishikr.app.view.activity.auth.RegisterActivity;
import com.meishikr.app.view.activity.blog.BlogEditActivity;
import com.meishikr.app.view.activity.blog.BlogEditFragment;
import com.meishikr.app.view.activity.blog.BlogPageActivity;
import com.meishikr.app.view.activity.blog.MyBlogListActivity;
import com.meishikr.app.view.activity.home.BlogFragment;
import com.meishikr.app.view.activity.home.LampFragment;
import com.meishikr.app.view.activity.lamp.LampEditActivity;
import com.meishikr.app.view.activity.lbs.MapActivity;
import com.meishikr.app.view.fragment.BlogDetailFragment;
import com.meishikr.app.view.viewholder.MyBlogViewHolder;
import com.meishikr.app.view.viewholder.MyLampViewHolder;
import com.meishikr.app.view.viewholder.ParagraphItemViewHolder;

import dagger.Component;

/**
 * Created by yinhang on 16/7/5.
 */
@ActivityScope
@Component(dependencies = AppComponent.class, modules = {ActivityModule.class})
public interface ActivityComponent {

    void inject(MapActivity mapActivity);

    void inject(LoginActivity loginActivity);

    void inject(RegisterActivity registerActivity);

    void inject(MyBlogListActivity myBlogListActivity);

    void inject(BlogEditActivity blogEditActivity);

    void inject(BlogPageActivity blogActivity);

    // TODO 是否分开fragment
    void inject(BlogDetailFragment blogItemDetailFragment);

    void inject(BlogFragment blogListFragment);

    void inject(MyBlogViewHolder myBlogItemViewHolder);

    void inject(MyLampViewHolder myLampViewHolder);

    void inject(ParagraphItemViewHolder paragraphItemViewHolder);

    void inject(BlogEditFragment blogEditFragment);

    void inject(LampEditActivity lampEditActivity);

    void inject(LampFragment lampFragment);

}
