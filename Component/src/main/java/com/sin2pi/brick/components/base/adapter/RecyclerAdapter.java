package com.sin2pi.brick.components.base.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sin2pi.brick.components.base.annotation.BindLayout;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by yinhang on 16/6/27.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    protected List<Object> payloads;

    private SparseArray<Class<? extends BaseViewHolder>> viewHolderMap;

    public static RecyclerAdapter newInstance() {
        return new RecyclerAdapter();
    }

    public RecyclerAdapter() {
        this.payloads = new ArrayList<>();
        this.viewHolderMap = new SparseArray<>();
    }

    public RecyclerAdapter(List<Object> objs) {
        this();
        if (null == objs)
            return;
        this.payloads.addAll(objs);
    }

    public void addOne(Object obj) {
        if (null == obj)
            return;
        payloads.add(obj);
        notifyDataSetChanged();
    }

    public void addAll(Collection<?> objs) {
        addAll(objs, false);
    }

    public void addAll(Collection<?> objs, boolean clear) {
        if (null == objs)
            return;
        if (clear)
            payloads.clear();
        payloads.addAll(objs);
        notifyDataSetChanged();
    }

    public List<Object> getPayloads() {
        return payloads;
    }

    public void clear() {
        payloads.clear();
        notifyDataSetChanged();
    }

    /**
     * 不同数据，不同视图
     * 不同数据，相同视图
     * 相同数据，相同视图
     * @param viewHolder
     */
    public void register(Class<? extends BaseViewHolder> viewHolder) {
        Type[] types = ((ParameterizedType) viewHolder.getGenericSuperclass()).getActualTypeArguments();
        viewHolderMap.put(types[0].hashCode(), viewHolder);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Class<? extends BaseViewHolder> viewHolderClass = viewHolderMap.get(viewType);
        BindLayout layoutId = viewHolderClass.getAnnotation(BindLayout.class);
        View view = LayoutInflater.from(parent.getContext())
                .inflate(layoutId.value(), parent, false);
        try {
            Constructor<? extends BaseViewHolder> constructor = viewHolderClass.getConstructor(View.class);
            constructor.setAccessible(true);
            return constructor.newInstance(view);
        } catch (NoSuchMethodException |
                IllegalAccessException |
                InstantiationException |
                InvocationTargetException e) {
        }
        return null;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        if (null == payloads || payloads.isEmpty())
            return;
        holder.render(payloads.get(position), position);
    }

    @Override
    public int getItemCount() {
        if (null == payloads || payloads.isEmpty())
            return 0;
        return payloads.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (null == payloads || payloads.isEmpty())
            return super.getItemViewType(position);
        return payloads.get(position).getClass().hashCode();
    }
}