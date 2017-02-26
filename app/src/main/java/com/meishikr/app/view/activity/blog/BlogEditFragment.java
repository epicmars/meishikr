package com.meishikr.app.view.activity.blog;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import com.meishikr.app.R;
import com.meishikr.app.base.adapter.RecyclerAdapter;
import com.meishikr.app.base.annotation.BindLayout;
import com.meishikr.app.databinding.ContentBlogEditBinding;
import com.meishikr.app.domain.entity.lbs.Location;
import com.meishikr.app.domain.entity.post.Blog;
import com.meishikr.app.domain.entity.post.Photo;
import com.meishikr.app.domain.interactor.post.LoadOneBlogCase;
import com.meishikr.app.domain.interactor.post.SaveBlogCase;
import com.meishikr.app.domain.interactor.post.SavePhotosCase;
import com.meishikr.app.domain.repository.CurrentUserRepo;
import com.meishikr.app.view.activity.auth.LoginActivity;
import com.meishikr.app.view.activity.lbs.LBSConsts;
import com.meishikr.app.view.activity.lbs.MyLocationActivity;
import com.meishikr.app.base.BaseFragment;
import com.meishikr.app.view.viewholder.ParagraphItemViewHolder;
import com.meishikr.app.view.viewholder.items.ParagraphItem;
import com.sin2pi.brick.components.gallery.ImageFileEntry;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;

import static android.app.Activity.RESULT_OK;

/**
 * 博客编辑。
 * Created by yinhang on 2016/11/29.
 */

@BindLayout(R.layout.content_blog_edit)
public class BlogEditFragment extends BaseFragment<ContentBlogEditBinding> implements View.OnClickListener {

    public static int REQUEST_CODE_SELECT_PHOTOS = 101;
    public static int REQUEST_CODE_SELECT_LOCATION = 102;
    public static String EXTRAS_POST_UUID = "EXTRAS_POST_UUID";
    public static String EXTRAS_PARAGRAPH_INDEX = "EXTRAS_PARAGRAPH_INDEX";

    private static String NEW_LINE = "\n";
    @Inject
    SaveBlogCase saveBlogCase;

    @Inject
    SavePhotosCase savePhotosCase;

    @Inject
    LoadOneBlogCase loadOneBlogCase;

    @Inject
    CurrentUserRepo currentUserRepo;

    private Location location;
    private Blog post;
    private RecyclerAdapter adapter;

    public static BlogEditFragment newInstance(Location location) {
        Bundle args = new Bundle();
        args.putParcelable(LBSConsts.Extras.MARK_POS, location);

        BlogEditFragment fragment = new BlogEditFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        component.inject(this);
        location = (Location) getArguments().getSerializable(LBSConsts.Extras.MARK_POS);
        post = new Blog();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.fabPostBlog.setOnClickListener(this);
        binding.fabAddParagraph.setOnClickListener(this);

        adapter = new RecyclerAdapter();
        adapter.register(ParagraphItemViewHolder.class);
        binding.recycler.setLayoutManager(new LinearLayoutManager(this.getContext()));
        binding.recycler.setAdapter(adapter);

        // 当前文章
        saveBlog(post, null);

        // 显示一个段落编辑器
        addParagraph();

//        final GeoCoder geoCoder = GeoCoder.newInstance();
//        geoCoder.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
//            @Override
//            public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {
//
//            }
//
//            @Override
//            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
//                Address address = new Address(reverseGeoCodeResult.getAddressDetail());
//                Location location = new Location(reverseGeoCodeResult);
//                //
//                geoCoder.destroy();
//            }
//        });
//        geoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(pos));
//
        binding.etTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                loadOneBlogCase.set(post.getUuid())
                        .execute(new Subscriber<Blog>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(Blog post) {
                                post.setStatus(Blog.EDITING);
                                post.setTitle(s.toString());
                                post.setTimestamp(new Date());
                                // TODO 每次保存？
                                saveBlog(post, null);
                            }
                        });
            }
        });

        binding.tvLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MyLocationActivity.class);
                startActivityForResult(intent, REQUEST_CODE_SELECT_LOCATION);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (RESULT_OK == resultCode && REQUEST_CODE_SELECT_LOCATION == requestCode) {
            if (null == data)
                return;
            Serializable serializable = data.getSerializableExtra(LBSConsts.Extras.SEARCHED_LOCATION);
            if (null == serializable)
                return;
            if (serializable instanceof Location) {
                binding.tvLocation.setText(((Location) serializable).getName());
            }
        }
    }

    public void onActivityResult(String uuid, int index, List<ImageFileEntry> entries) {
        if (null == entries)
            return;
        ((ParagraphItem) adapter.getPayloads().get(index)).setImages(entries);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_add_paragraph:
                // 显示一个段落编辑器
                addParagraph();
                break;
            case R.id.fab_post_blog:
                // TODO 编辑界面带预览界面，编辑同时将blog保存到本地
                // 点击发表后跳转到博客列表界面，博客列表显示博客发表状态
                postBlog();
                break;
            default:
                break;
        }
    }

    private void addParagraph() {
        List<Object> items = adapter.getPayloads();
        ParagraphItem lastItem = items.size() >= 1 ? (ParagraphItem) items.get(items.size() - 1) : null;
        if (null != lastItem
                && (null == lastItem.getImages() || lastItem.getImages().isEmpty())
                && TextUtils.isEmpty(lastItem.getContent())) {
            return;
        }
        ParagraphItem item = new ParagraphItem();
        item.setPostUUID(post.getUuid());
        item.setIndex(adapter.getPayloads().size());
        adapter.addOne(item);
        binding.recycler.smoothScrollToPosition(item.getIndex());
        // todo 位置需要手动添加
        // todo 开启自动定位选项
    }

    private void saveBlog(Blog post, List<Photo> photos) {
        saveBlogCase.setBlog(post)
                .execute(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        if (null == post.getId())
                            post.setId(aLong);
                        if (null == photos || photos.isEmpty())
                            return;
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
                });
    }

    private void postBlog() {
        if (!currentUserRepo.isLogin()) {
            LoginActivity.launch(this.getContext());
            return;
        } else {
            // todo 保存到本地并提示
        }
        loadOneBlogCase.set(post.getUuid())
                .execute(new Subscriber<Blog>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Blog post) {
                        post.setStatus(Blog.POSTING);
//        post.setLocationId(location.getLo());
                        post.setTimestamp(new Date());
                        //
                        StringBuilder sb = new StringBuilder();
                        List<Photo> photos = new ArrayList<>();

                        for (Object o : adapter.getPayloads()) {
                            ParagraphItem item = (ParagraphItem) o;
                            List<ImageFileEntry> entries = item.getImages();
                            if (null == entries || entries.isEmpty())
                                continue;
                            if (entries.size() > 0) {
                                for (int i = 0; i < entries.size(); i++) {
                                    Photo photo = new Photo();
                                    photo.setLocalPath(entries.get(i).getFile().getPath());
                                    photos.add(photo);
                                    sb.append(Uri.fromFile(new File(photo.getLocalPath())).toString())
                                            .append(NEW_LINE);
                                }
                            }
                            sb.append(item.getContent()).append(NEW_LINE);
                        }
                        post.setBody(sb.toString());
                        saveBlog(post, photos);

                        MyBlogListActivity.launch(BlogEditFragment.this.getContext());

                    }
                });
    }

}
