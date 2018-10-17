package com.meishikr.app.view.viewholder;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.amap.api.location.AMapLocation;
import com.meishikr.app.R;
import com.sin2pi.brick.components.base.adapter.BaseViewHolder;
import com.sin2pi.brick.components.base.annotation.BindLayout;
import com.meishikr.app.databinding.ViewHolderMyLocationBinding;
import com.meishikr.app.domain.entity.lbs.Location;
import com.meishikr.app.utils.LocationMapper;
import com.meishikr.app.view.activity.lbs.LBSConsts;

/**
 * 展示所在位置。
 * Created by yinhang on 2016/12/14.
 */

@BindLayout(R.layout.view_holder_my_location)
public class AMapLocationViewHolder extends BaseViewHolder<AMapLocation, ViewHolderMyLocationBinding> {

    public AMapLocationViewHolder(View view) {
        super(view);
    }

    @Override
    public void render(AMapLocation data, int position) {

        binding.name.setText(data.getPoiName());
        binding.address.setText(data.getAddress());

        Location location = LocationMapper.newLocation(data);
        location.setName(binding.name.getText().toString());

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
