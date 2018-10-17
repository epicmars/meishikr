package com.meishikr.app.view.binding;

import android.databinding.BindingAdapter;

import com.facebook.drawee.view.SimpleDraweeView;

/**
 *
 * Created by yinhang on 2016/12/26.
 */

public class SimpleDraweeViewBindingAdapter {

    @BindingAdapter({"actualImageUri"})
    public static void loadImage(SimpleDraweeView view, String url) {
        view.setImageURI(url);
    }
}
