package com.meishikr.app.base;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.meishikr.app.application.MeishikrApp;
import com.meishikr.app.base.annotation.BindLayout;
import com.meishikr.app.base.fragment.AbstractFragment;
import com.meishikr.app.di.component.ActivityComponent;
import com.meishikr.app.di.component.DaggerActivityComponent;
import com.meishikr.app.di.module.ActivityModule;
import com.sin2pi.brick.common.rxbus.Bus;

/**
 * Created by yinhang on 16/8/8.
 */
public abstract class BaseFragment<T extends ViewDataBinding> extends AbstractFragment {

    protected ActivityComponent component;
    protected Bus bus;

    protected T binding;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        bus = ((BaseActivity) context).getBus();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        component = DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this.getActivity(), bus))
                .appComponent(getApp().getAppComponent())
                .build();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(getClass().isAnnotationPresent(BindLayout.class)){
            BindLayout layout = getClass().getAnnotation(BindLayout.class);
            if(null != layout){
                View root = inflater.inflate(layout.value(), null);
                binding = DataBindingUtil.bind(root);
                return root;
            }
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        bus.register(this);
    }

    @Override
    public void onStop() {
        bus.unregister(this);
        super.onStop();
    }

    protected MeishikrApp getApp(){
        return (MeishikrApp) getActivity().getApplicationContext();
    }

    public ActivityComponent getComponent(){
        return component;
    }

    public void toast(int strRes, int duration) {
        Toast.makeText(this.getContext(), strRes, duration).show();
    }

    public void toast(String str, int duration) {
        Toast.makeText(this.getContext(), str, duration).show();
    }

    public Bus getBus() {
        return bus;
    }
}
