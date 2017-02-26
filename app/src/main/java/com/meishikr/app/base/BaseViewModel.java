package com.meishikr.app.base;

import android.content.Context;

import com.meishikr.app.di.component.ActivityComponent;
import com.sin2pi.brick.common.rxbus.Bus;

/**
 * Created by yinhang on 16/7/7.
 */
public abstract class BaseViewModel {

    private Context context;

    private Bus bus;

    private ActivityComponent component;

    public BaseViewModel(Context context){
        this.context = context;
        this.bus = ((BaseActivity) context).getBus();
        this.component = ((BaseActivity) context).getComponent();
    }

    public Context getContext() {
        return context;
    }

    public Bus getBus() {
        return bus;
    }

    public ActivityComponent getComponent() {
        return component;
    }
}
