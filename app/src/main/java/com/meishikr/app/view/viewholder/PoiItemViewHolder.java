package com.meishikr.app.view.viewholder;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.amap.api.services.core.PoiItem;
import com.meishikr.app.R;
import com.sin2pi.brick.components.base.adapter.BaseViewHolder;
import com.sin2pi.brick.components.base.annotation.BindLayout;
import com.meishikr.app.databinding.ViewHolderPoiItemBinding;
import com.meishikr.app.domain.entity.lbs.Location;
import com.meishikr.app.utils.LocationMapper;
import com.meishikr.app.view.activity.lbs.LBSConsts;

/**
 * Created by yinhang on 2016/12/15.
 */

@BindLayout(R.layout.view_holder_poi_item)
public class PoiItemViewHolder extends BaseViewHolder<PoiItem, ViewHolderPoiItemBinding> {

    public PoiItemViewHolder(View view) {
        super(view);
    }

    @Override
    public void render(PoiItem data, int position) {
        //
        binding.name.setText(data.getTitle());
        binding.address.setText(new StringBuilder()
                .append(data.getProvinceName())
                .append(data.getCityName())
                .append(data.getAdName())
                .append(data.getSnippet()).toString());


        Location location = LocationMapper.newLocation(data);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra(LBSConsts.Extras.SEARCHED_LOCATION, location);
                Activity activity = (Activity) itemView.getContext();
                activity.setResult(Activity.RESULT_OK, intent);
                activity.finish();
            }
        });
    }
}
