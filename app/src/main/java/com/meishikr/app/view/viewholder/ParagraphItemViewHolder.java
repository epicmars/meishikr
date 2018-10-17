package com.meishikr.app.view.viewholder;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.meishikr.app.R;
import com.sin2pi.brick.components.base.adapter.BaseViewHolder;
import com.sin2pi.brick.components.base.annotation.BindLayout;
import com.meishikr.app.databinding.ViewHolderParagraphItemBinding;
import com.meishikr.app.domain.entity.post.Blog;
import com.meishikr.app.domain.interactor.post.LoadOneBlogCase;
import com.meishikr.app.domain.interactor.post.SaveBlogCase;
import com.meishikr.app.base.BaseActivity;
import com.meishikr.app.view.activity.blog.BlogEditFragment;
import com.meishikr.app.view.activity.gallery.GalleryActivity;
import com.meishikr.app.view.adapter.ParagraphImageAdapter;
import com.meishikr.app.view.viewholder.items.ParagraphItem;
import com.sin2pi.brick.components.gallery.ImageFileEntry;
import com.sin2pi.brick.components.utils.FileUtil;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by yinhang on 2016/11/13.
 */
@BindLayout(R.layout.view_holder_paragraph_item)
public class ParagraphItemViewHolder extends BaseViewHolder<ParagraphItem, ViewHolderParagraphItemBinding> {

    @Inject
    SaveBlogCase saveBlogCase;

    @Inject
    LoadOneBlogCase loadOneBlogCase;

    public ParagraphItemViewHolder(View view) {
        super(view);
        ((BaseActivity) view.getContext()).getComponent().inject(this);
    }

    @Override
    public void render(ParagraphItem data, int position) {
        if (null == data)
            return;
        List<ImageFileEntry> entries = data.getImages();
        if (null == entries || entries.isEmpty()) {
            binding.imageAddPhoto.setVisibility(View.VISIBLE);
            binding.imageAddPhoto.setOnClickListener((View v) -> {
                selectPhotos(data);
            });
        } else {
            binding.imageAddPhoto.setVisibility(View.GONE);
            ParagraphImageAdapter adapter = new ParagraphImageAdapter(context, entries);
//            ParagraphImageStateAdapter adapter = new ParagraphImageStateAdapter(((BaseActivity)itemView.getContext()).getSupportFragmentManager(), entries);
            // xxx 在RecyclerView中使用FragmentPagerAdapter和ViewPager显示错乱的问题
            // xxx http://stackoverflow.com/questions/37789091/viewpager-inside-recyclerview-as-row-item
            // xxx http://stackoverflow.com/questions/37801078/viewpager-inside-cardview-inside-recyclerview-android
            // xxx http://stackoverflow.com/questions/7746652/fragment-in-viewpager-using-fragmentpageradapter-is-blank-the-second-time-it-is
            binding.imagePager.setAdapter(adapter);
        }

        binding.paragraph.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                data.setContent(s.toString());
            }
        });
    }

    public void selectPhotos(ParagraphItem item) {
        // todo 检查sd卡与主存
        BaseActivity context = (BaseActivity) itemView.getContext();
        File root = FileUtil.getExternalStorageDirectory();
        if (root == null || !root.exists()) {
            context.toast("请检查是否正确安装了sd卡", Toast.LENGTH_SHORT);
            return;
        }
        Intent intent = new Intent(context, GalleryActivity.class);
        intent.putExtra(BlogEditFragment.EXTRAS_POST_UUID, item.getPostUUID());
        intent.putExtra(BlogEditFragment.EXTRAS_PARAGRAPH_INDEX, item.getIndex());
        context.startActivityForResult(intent, BlogEditFragment.REQUEST_CODE_SELECT_PHOTOS);
    }


    private void saveBlog(Blog post) {
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
                        if(null == post.getId())
                            post.setId(aLong);
                    }
                });
    }



}
