package com.meishikr.app.view.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.meishikr.app.R;
import com.meishikr.app.view.activity.home.BlogFragment;
import com.meishikr.app.view.activity.home.LampFragment;
import com.meishikr.app.view.activity.home.NewsFragment;

/**
 * Created by yinhang on 16/3/24.
 */
public class HomePageAdapter extends FragmentStatePagerAdapter {

    private Context context;
    private static final int[] titles = {R.string.meishikr, R.string.blog, R.string.lamp, };

    public HomePageAdapter(Context context, FragmentManager manager){
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
                return new NewsFragment();
            case 1:
                return new BlogFragment();
            case 2:
                return LampFragment.newInstance("", "");
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
