package com.meishikr.app.view.activity.blog;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.widget.Toast;

import com.meishikr.app.R;
import com.meishikr.app.application.Consts;
import com.sin2pi.brick.components.base.annotation.BindLayout;
import com.meishikr.app.databinding.ActivityBlogEditBinding;
import com.meishikr.app.domain.entity.lbs.Location;
import com.meishikr.app.base.BaseActivity;
import com.sin2pi.brick.components.gallery.ImageFileEntry;

import java.util.List;

import static com.meishikr.app.view.activity.blog.BlogEditFragment.REQUEST_CODE_SELECT_PHOTOS;

/**
 * 博客发表页面
 */
@BindLayout(R.layout.activity_blog_edit)
public class BlogEditActivity extends BaseActivity<ActivityBlogEditBinding>{

    private long lastBackpressTime = -1;
    private Location location;
    private BlogEditPagerAdapter adapter;

    public static void launch(Context context) {
        Intent intent = new Intent(context, BlogEditActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        adapter = new BlogEditPagerAdapter(getSupportFragmentManager());
        binding.blogEditorPager.setAdapter(adapter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (REQUEST_CODE_SELECT_PHOTOS == requestCode) {
            if (RESULT_OK == resultCode) {
                if (null == data)
                    return;
                String uuid = data.getStringExtra(BlogEditFragment.EXTRAS_POST_UUID);
                int index = data.getIntExtra(BlogEditFragment.EXTRAS_PARAGRAPH_INDEX, -1);
                List<ImageFileEntry> entries = data.getParcelableArrayListExtra(Consts.Extras.SELECTED_IMAGE_FILE_ENTRIES);
                if (null == entries)
                    return;
                BlogEditFragment blogEditFragment = (BlogEditFragment) getSupportFragmentManager().findFragmentByTag(makeFragmentName(binding.blogEditorPager.getId(), adapter.getItemId(0)));
                if(null == blogEditFragment)
                    return;
                blogEditFragment.onActivityResult(uuid, index, entries);
            } else if (RESULT_CANCELED == resultCode) {
                // 用户取消拍摄
            } else {
                // 拍照失败
            }
        }
    }

    @Override
    public void onBackPressed() {
        long now = System.currentTimeMillis();
        if (lastBackpressTime < 0 || (now - lastBackpressTime) > 3 * Consts.DateTime.ONE_SECOND) {
            // TODO 自动保存草稿功能
            toast("再按一次退出编辑", Toast.LENGTH_SHORT);
        } else {
            super.onBackPressed();
        }
        lastBackpressTime = now;
    }

    private String makeFragmentName(int viewId, long id) {
        return "android:switcher:" + viewId + ":" + id;
    }

    public class BlogEditPagerAdapter extends FragmentPagerAdapter {

        public BlogEditPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (0 == position) {
                return BlogEditFragment.newInstance(location);
            } else {
                return BlogPreviewFragment.newInstance();
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

    }

}
