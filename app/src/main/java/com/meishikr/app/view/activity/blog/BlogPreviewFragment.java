package com.meishikr.app.view.activity.blog;

import android.os.Bundle;

import com.meishikr.app.R;
import com.meishikr.app.base.annotation.BindLayout;
import com.meishikr.app.databinding.ContentBlogPreviewBinding;
import com.meishikr.app.base.BaseFragment;

/**
 * A placeholder fragment containing a simple view.
 */
@BindLayout(R.layout.content_blog_preview)
public class BlogPreviewFragment extends BaseFragment<ContentBlogPreviewBinding> {

    public static BlogPreviewFragment newInstance() {

        Bundle args = new Bundle();

        BlogPreviewFragment fragment = new BlogPreviewFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
