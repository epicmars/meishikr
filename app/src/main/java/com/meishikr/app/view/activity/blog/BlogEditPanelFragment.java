package com.meishikr.app.view.activity.blog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CompoundButton;

import com.meishikr.app.R;
import com.meishikr.app.base.annotation.BindLayout;
import com.meishikr.app.databinding.FragmentBlogEditSettingBinding;
import com.meishikr.app.base.BaseFragment;

/**
 * Created by yinhang on 2016/12/1.
 */

@BindLayout(R.layout.fragment_blog_edit_setting)
public class BlogEditPanelFragment extends BaseFragment<FragmentBlogEditSettingBinding>{

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.autoRecordLocation.setOnCheckedChangeListener((CompoundButton compoundButton, boolean b) -> {

        });
    }
}
