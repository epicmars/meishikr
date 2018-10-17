package com.meishikr.app.base;

import com.sin2pi.brick.components.base.adapter.BaseViewHolder;
import com.sin2pi.brick.components.base.adapter.RecyclerAdapter;

/**
 * Created by yinhang on 2016/12/22.
 */

public class BindingRecyclerAdapter extends RecyclerAdapter{

    public static BindingRecyclerAdapter newInstance() {
        return new BindingRecyclerAdapter();
    }

    public BindingRecyclerAdapter() {
        super();
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
    }
}
