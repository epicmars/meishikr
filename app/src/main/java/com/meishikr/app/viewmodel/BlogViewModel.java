package com.meishikr.app.viewmodel;

import android.app.Activity;

import com.meishikr.app.base.FragmentViewModel;
import com.meishikr.app.domain.dto.res.ResLatestBlogs;
import com.meishikr.app.domain.interactor.post.GetLatestBlogsCase;
import com.meishikr.app.viewmodel.event.EventLoadBlogs;

import javax.inject.Inject;

import rx.Subscriber;
import timber.log.Timber;

/**
 * 
 * Created by yinhang on 2016/12/26.
 */

public class BlogViewModel extends FragmentViewModel {

    private static final int COUNT_PER_PAGE = 12;

    private int page = 1;

    @Inject
    GetLatestBlogsCase getLatestBlogsCase;

    @Inject
    public BlogViewModel(Activity context) {
        super(context);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        onRefresh();
    }

    public void onRefresh() {
        refreshBlogs(page = 1);
    }

    public void onLoadMore() {
        refreshBlogs(++page);
    }

    private void refreshBlogs(final int currentPage){
        getLatestBlogsCase.setPage(currentPage);
        getLatestBlogsCase.setCount(COUNT_PER_PAGE);
        getLatestBlogsCase.execute(new Subscriber<ResLatestBlogs>() {
            @Override
            public void onCompleted() {
                getBus().post(new EventLoadBlogs(EventLoadBlogs.Result.COMPLETED, page,  null));
            }

            @Override
            public void onError(Throwable e) {
                Timber.e(e, "");
                getBus().post(new EventLoadBlogs(EventLoadBlogs.Result.ERROR, page,  null));
            }

            @Override
            public void onNext(ResLatestBlogs resLatestBlogs) {
                getBus().post(new EventLoadBlogs(EventLoadBlogs.Result.SUCCEED, page, resLatestBlogs));
            }
        });
    }

}
