package com.meishikr.app.view.binding;

import android.databinding.BindingAdapter;

import com.liaoinstan.springview.widget.SpringView;

/**
 * Created by yinhang on 2016/12/26.
 */

public class SpringViewBindingAdapter {

    public interface OnRefresh {
        void onRefresh();
    }

    public interface OnLoadMore {
        void onLoadMore();
    }

    @BindingAdapter(value = {"onRefresh", "onLoadMore"}, requireAll = false)
    public static void setRefreshListener(SpringView springView, OnRefresh onRefresh, OnLoadMore onLoadMore) {
        final SpringView.OnFreshListener onFreshListener;
        if (null == onRefresh && null == onLoadMore) {
            onFreshListener = null;
        } else {
            onFreshListener = new SpringView.OnFreshListener() {
                @Override
                public void onRefresh() {
                    if (null != onRefresh)
                        onRefresh.onRefresh();
                }

                @Override
                public void onLoadmore() {
                    if (null != onLoadMore)
                        onLoadMore.onLoadMore();
                }
            };
        }

        if (null != onFreshListener)
            springView.setListener(onFreshListener);
    }
}
