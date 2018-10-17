package com.meishikr.app.view.viewholder;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.meishikr.app.R;
import com.sin2pi.brick.components.base.adapter.BaseViewHolder;
import com.sin2pi.brick.components.base.annotation.BindLayout;
import com.meishikr.app.databinding.ViewHolderMyLocationBinding;
import com.meishikr.app.view.activity.lbs.LBSConsts;
import com.meishikr.app.view.viewholder.items.AoiItem;

/**
 * Created by yinhang on 2016/12/16.
 */

@BindLayout(R.layout.view_holder_my_location)
public class AoiItemViewHolder extends BaseViewHolder<AoiItem, ViewHolderMyLocationBinding> {

    public AoiItemViewHolder(View view) {
        super(view);
    }

    @Override
    public void render(AoiItem data, int position) {

        binding.name.setText(data.getName());
        binding.address.setText(data.getAddress());
        data.getLocation().setName(binding.name.getText().toString());

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra(LBSConsts.Extras.SEARCHED_LOCATION, data.getLocation());
                Activity activity = (Activity) itemView.getContext();
                activity.setResult(Activity.RESULT_OK, intent);
                activity.finish();
            }
        });
    }
}
