package com.meishikr.app.view.activity.video;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.meishikr.app.R;
import com.sin2pi.brick.components.base.annotation.BindLayout;
import com.meishikr.app.databinding.ActivityShootingBinding;
import com.meishikr.app.base.BaseActivity;

@BindLayout(R.layout.activity_shooting)
public class ShootingActivity extends BaseActivity<ActivityShootingBinding> {

    public static void launch(Context context) {
        Intent intent = new Intent(context, ShootingActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(binding.toolbar);

    }

}
