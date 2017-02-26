package com.meishikr.app.view.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.meishikr.app.R;
import com.meishikr.app.view.activity.home.HomeFragment;
import com.meishikr.app.view.activity.home.MineFragment;
import com.meishikr.app.view.activity.home.TopicFragment;

/**
 * Created by yinhang on 2016/12/24.
 */

public class HomeNavigationAdapter extends FragmentPagerAdapter{

    // // TODO: 2016/12/24 为什么不能直接持有fragment
    private Context context;
    private static final int[] titles = {R.string.home, R.string.topic, R.string.mine};

    public HomeNavigationAdapter(Context context, FragmentManager manager){
        super(manager);
        this.context = context;
    }

    /**
     * Return the Fragment associated with a specified position.
     *
     * @param position
     */
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return HomeFragment.newInstance();
            case 1:
                return TopicFragment.newInstance(1);
            case 2:
                return MineFragment.newInstance("", "");
        }
        return new Fragment();
    }

    /**
     * Return the number of views available.
     */
    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return context.getString(titles[position]);
    }

    //
//    @Override
//    public int getItemPosition(Object object) {
//        return POSITION_NONE;
//    }
}
