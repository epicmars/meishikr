package com.meishikr.app.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.widget.Toast;

import com.meishikr.app.application.MeishikrApp;
import com.sin2pi.brick.components.base.activity.AbstractActivity;
import com.sin2pi.brick.components.base.annotation.BindLayout;
import com.meishikr.app.di.component.ActivityComponent;
import com.meishikr.app.di.component.DaggerActivityComponent;
import com.meishikr.app.di.module.ActivityModule;
import com.sin2pi.brick.common.rxbus.Bus;

/**
 * Created by yinhang on 16/7/5.
 */
public class BaseActivity<T extends ViewDataBinding> extends AbstractActivity {

    protected T binding;
    protected ActivityComponent component;
    protected Bus bus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getClass().isAnnotationPresent(BindLayout.class)){
            BindLayout layout = getClass().getAnnotation(BindLayout.class);
            if(null != layout){
                binding = DataBindingUtil.setContentView(this, layout.value());
            }
        }

        bus = new Bus();
        component = DaggerActivityComponent.builder()
                .appComponent(MeishikrApp.get(this).getAppComponent())
                .activityModule(new ActivityModule(this, bus))
                .build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        bus.register(this);
    }

    @Override
    protected void onStop() {
        bus.unregister(this);
        super.onStop();
    }

    public ActivityComponent getComponent(){
        return component;
    }

    public Bus getBus() {
        return bus;
    }

    public void toast(CharSequence str, int duration) {
        Toast.makeText(this, str, duration).show();
    }

    public void toast(int strRes, int duration) {
        Toast.makeText(this, strRes, duration).show();
    }
}
