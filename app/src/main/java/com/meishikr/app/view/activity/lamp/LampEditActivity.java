package com.meishikr.app.view.activity.lamp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import com.meishikr.app.R;
import com.meishikr.app.application.Consts;
import com.meishikr.app.base.adapter.RecyclerAdapter;
import com.meishikr.app.base.annotation.BindLayout;
import com.meishikr.app.databinding.ActivityLampEditBinding;
import com.meishikr.app.domain.entity.post.Lamp;
import com.meishikr.app.domain.entity.post.Photo;
import com.meishikr.app.domain.interactor.post.SaveLampCase;
import com.meishikr.app.base.BaseActivity;
import com.meishikr.app.domain.interactor.post.SavePhotosCase;
import com.meishikr.app.view.activity.blog.MyBlogListActivity;
import com.meishikr.app.view.activity.gallery.GalleryActivity;
import com.meishikr.app.view.activity.video.ShootingActivity;
import com.meishikr.app.view.viewholder.ImageFileEntryViewHolder;
import com.sin2pi.brick.components.gallery.ImageFileEntry;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;

@BindLayout(R.layout.activity_lamp_edit)
public class LampEditActivity extends BaseActivity<ActivityLampEditBinding> implements ImageFileEntryViewHolder.OnImageFileEntryOperationListener {

    private static final int MAX_PHOTO_NUM = 9;
    private static final int REQUEST_CODE_SELECT_PHOTOS = 101;

    private RecyclerAdapter adapter;

    @Inject
    SaveLampCase saveLampCase;

    @Inject
    SavePhotosCase savePhotosCase;

    List<ImageFileEntry> entries;

    public static void launch(Context context, String postType) {
        Intent intent = new Intent(context, LampEditActivity.class);
        intent.putExtra(Consts.Extras.LAMP_POST_TYPE, postType);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        component.inject(this);

        setSupportActionBar(binding.toolbar);

        adapter = new RecyclerAdapter();
        adapter.register(ImageFileEntryViewHolder.class);
        binding.content.recycler.setLayoutManager(new GridLayoutManager(this, 3));
        binding.content.recycler.setAdapter(adapter);

        final String postType = getIntent().getStringExtra(Consts.Extras.LAMP_POST_TYPE);
        if (TextUtils.isEmpty(postType))
            return;
        switch (postType) {
            case Consts.PostType.POST_TYPE_LAMP_PHOTO:
                GalleryActivity.launchForResult(this, REQUEST_CODE_SELECT_PHOTOS, MAX_PHOTO_NUM);
                break;
            case Consts.PostType.POST_TYPE_LAMP_VIDEO:
                ShootingActivity.launch(this);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (REQUEST_CODE_SELECT_PHOTOS == requestCode && RESULT_OK == resultCode) {
            if (null == data)
                return;
            entries = data.getParcelableArrayListExtra(Consts.Extras.SELECTED_IMAGE_FILE_ENTRIES);
            adapter.addAll(entries);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lamp_edit, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_post:
                Lamp lamp = new Lamp();
                lamp.setPostStatus(Lamp.POSTING);
                lamp.setContent(binding.content.etLamp.getText().toString());
                List<Photo> photos = null;
                if (null != entries) {
                    photos = new ArrayList<>();
                    for (ImageFileEntry entry : entries) {
                        Photo photo = new Photo();
                        photo.setLocalPath(entry.getFile().getPath());
                        photos.add(photo);
                    }
                }
                saveLamp(lamp, photos);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void saveLamp(Lamp lamp, List<Photo> photos) {
        saveLampCase.setLamp(lamp)
                .execute(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        if (null != photos && !photos.isEmpty()) {
                            for (Photo photo : photos) {
                                photo.setTopicId(aLong);
                            }
                            savePhotosCase.setPhotos(photos)
                                    .execute(new Subscriber<List<Photo>>() {
                                        @Override
                                        public void onCompleted() {

                                        }

                                        @Override
                                        public void onError(Throwable e) {

                                        }

                                        @Override
                                        public void onNext(List<Photo> photos) {

                                        }
                                    });
                        }
                        LampEditActivity.this.finish();
                        MyBlogListActivity.launch(LampEditActivity.this);
                    }
                });
    }

    @Override
    public void onImageFileEntryClick(ImageFileEntry entry) {

    }

    @Override
    public void onImageFileEntryCheck(ImageFileEntry entry, boolean isChecked) {

    }
}
