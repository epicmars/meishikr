package com.meishikr.app.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.meishikr.app.view.activity.blog.ParagraphImageFragment;
import com.sin2pi.brick.components.gallery.ImageFileEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yinhang on 2016/12/1.
 */

public class ParagraphImageStateAdapter extends FragmentStatePagerAdapter{

        private List<ImageFileEntry> imageFileEntries;

        public ParagraphImageStateAdapter(FragmentManager fm) {
            super(fm);
            imageFileEntries = new ArrayList<>();
        }

        public ParagraphImageStateAdapter(FragmentManager fm, List<ImageFileEntry> imageFileEntries) {
            super(fm);
            this.imageFileEntries = imageFileEntries;
        }

        @Override
        public int getCount() {
            return imageFileEntries.size();
        }


        @Override
        public Fragment getItem(int position) {
            return ParagraphImageFragment.newInstance(imageFileEntries.get(position));
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        public void addAll(List<ImageFileEntry> entries) {
            if(null == entries || entries.isEmpty())
                return;
            imageFileEntries.addAll(entries);
            notifyDataSetChanged();
        }

        public void clear() {
            imageFileEntries.clear();
            notifyDataSetChanged();
        }

        public void setImageFileEntries(List<ImageFileEntry> entries) {
            if(null == entries)
                return;
            imageFileEntries = entries;
            notifyDataSetChanged();
        }

}
