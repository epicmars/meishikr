package com.meishikr.app.view.viewholder;

import android.view.View;

import com.meishikr.app.R;
import com.meishikr.app.base.adapter.BaseViewHolder;
import com.meishikr.app.base.annotation.BindLayout;
import com.meishikr.app.databinding.ViewHolderLampBinding;
import com.meishikr.app.domain.entity.post.Lamp;

/**
 * Created by yinhang on 2016/12/20.
 */
@BindLayout(R.layout.view_holder_lamp)
public class LampViewHolder extends BaseViewHolder<Lamp, ViewHolderLampBinding>{

    public LampViewHolder(View view) {
        super(view);
    }

    @Override
    public void render(Lamp data, int position) {
        binding.setData(data);
//        binding.content.setText(data.getContent());
//        List<Photo> photos = data.getPhotos();
    }
}
