package com.meishikr.app.di.module;

import android.app.Activity;

import com.meishikr.app.di.scope.ActivityScope;
import com.sin2pi.brick.common.rxbus.Bus;

import dagger.Module;
import dagger.Provides;

/**
 * Created by yinhang on 16/7/5.
 */
@Module
public class ActivityModule {

    final Activity activity;
    final Bus bus;

    public ActivityModule(Activity activity, Bus bus){
        this.activity = activity;
        this.bus = bus;
    }

    @Provides
    @ActivityScope
    public Activity activity(){
        return activity;
    }

    @Provides
    @ActivityScope
    public Bus bus(){
        return bus;
    }

}
