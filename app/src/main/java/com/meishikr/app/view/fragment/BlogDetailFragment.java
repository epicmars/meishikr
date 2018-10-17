package com.meishikr.app.view.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.view.View;

import com.meishikr.app.R;
import com.meishikr.app.base.BaseFragment;
import com.sin2pi.brick.components.base.annotation.BindLayout;
import com.meishikr.app.databinding.FragmentBlogDetailBinding;
import com.meishikr.app.domain.entity.post.Blog;
import com.meishikr.app.domain.interactor.post.LoadOneBlogCase;

import javax.inject.Inject;

import rx.Subscriber;

@BindLayout(R.layout.fragment_blog_detail)
public class BlogDetailFragment extends BaseFragment<FragmentBlogDetailBinding> {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String BLOG_UUID = "blog_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private Blog mItem;

    @Inject
    LoadOneBlogCase loadOneBlogCase;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public BlogDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        component.inject(this);

        if (getArguments().containsKey(BLOG_UUID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            String uuid = getArguments().getString(BLOG_UUID);
            // TODO: 16/7/11
            loadOneBlogCase.set(uuid).execute(new Subscriber<Blog>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onNext(Blog post) {
                    mItem = post;
                }
            });

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.getTitle());
            }
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mItem != null) {
            binding.tvDetail.setText(mItem.getBody());
        }
    }
}
