package com.meishikr.app.view.activity.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.meishikr.app.R;
import com.meishikr.app.base.annotation.BindLayout;
import com.meishikr.app.databinding.FragmentHomeBinding;
import com.meishikr.app.view.activity.lbs.MapActivity;
import com.meishikr.app.view.adapter.HomePageAdapter;
import com.meishikr.app.base.BaseFragment;

/**
 * 首页的主页。
 */
@BindLayout(R.layout.fragment_home)
public class HomeFragment extends BaseFragment<FragmentHomeBinding> {

    private static final int OFF_SCREEN_PAGES = 3;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.content.fabMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MapActivity.class);
                startActivity(intent);
            }
        });

        HomePageAdapter adapter = new HomePageAdapter(getContext(), getChildFragmentManager());
        binding.content.pager.setOffscreenPageLimit(OFF_SCREEN_PAGES);
        binding.content.pager.setAdapter(adapter);
        binding.content.tabs.setupWithViewPager(binding.content.pager);
    }
}
