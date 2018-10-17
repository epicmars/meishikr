package com.meishikr.app.view.activity.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.liaoinstan.springview.container.AliFooter;
import com.liaoinstan.springview.container.AliHeader;
import com.meishikr.app.R;
import com.meishikr.app.base.BaseFragment;
import com.sin2pi.brick.components.base.adapter.RecyclerAdapter;
import com.sin2pi.brick.components.base.annotation.BindLayout;
import com.meishikr.app.databinding.FragmentBlogBinding;
import com.meishikr.app.domain.dto.res.ResLatestBlogs;
import com.meishikr.app.view.viewholder.BlogViewHolder;
import com.meishikr.app.viewmodel.BlogViewModel;
import com.meishikr.app.viewmodel.event.EventLoadBlogs;
import com.sin2pi.brick.common.rxbus.Subscribe;

import javax.inject.Inject;

/**
 * 主页博客列表页面。
 */
@BindLayout(R.layout.fragment_blog)
public class BlogFragment extends BaseFragment<FragmentBlogBinding> {

    private RecyclerAdapter adapter;

    @Inject
    BlogViewModel blogViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        component.inject(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.content.setBlogViewModel(blogViewModel);

        adapter = new RecyclerAdapter();
        adapter.register(BlogViewHolder.class);
        binding.content.recycler.setAdapter(adapter);
        binding.content.recycler.setLayoutManager(new LinearLayoutManager(this.getContext()));

        binding.content.spring.setHeader(new AliHeader(getContext(), true));
        binding.content.spring.setFooter(new AliFooter(getContext()));
        binding.content.spring.callFresh();

        blogViewModel.onRefresh();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Subscribe
    public void onRefresh(EventLoadBlogs eventLoadBlogs) {
        final EventLoadBlogs.Result result = eventLoadBlogs.getResult();
        if (null == result)
            return;
        switch (result) {
            case ERROR:
            case COMPLETED:
                binding.content.spring.onFinishFreshAndLoad();
                break;
            case SUCCEED:
                ResLatestBlogs resLatestBlogs = eventLoadBlogs.getResLatestBlogs();
                if(null == resLatestBlogs)
                    return;
                if(null != resLatestBlogs.blogs && !resLatestBlogs.blogs.isEmpty())
                    adapter.addAll(resLatestBlogs.blogs, eventLoadBlogs.getPage() == 1);
                break;
        }
    }

}
