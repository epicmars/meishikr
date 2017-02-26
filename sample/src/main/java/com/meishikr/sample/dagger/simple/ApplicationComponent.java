package com.meishikr.sample.dagger.simple;

import com.meishikr.sample.dagger.simple.ui.HomeActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by yinhang on 16/7/5.
 */
@Singleton
@Component(modules = AndroidModule.class)
public interface ApplicationComponent {
    void inject(DemoApplication application);

    void inject(HomeActivity homeActivity);

    void inject(DemoActivity demoActivity);
}
