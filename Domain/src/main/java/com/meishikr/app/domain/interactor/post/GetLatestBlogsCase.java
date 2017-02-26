package com.meishikr.app.domain.interactor.post;

import com.meishikr.app.domain.dto.res.ResLatestBlogs;
import com.meishikr.app.domain.executor.PostExecutionThread;
import com.meishikr.app.domain.executor.ThreadExecutor;
import com.meishikr.app.domain.interactor.UseCase;
import com.meishikr.app.domain.repository.BlogRepo;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by yinhang on 16/7/10.
 */
public class GetLatestBlogsCase extends UseCase<ResLatestBlogs> {

    private final BlogRepo blogRepo;
    private int page;
    private int count;

    @Inject
    public GetLatestBlogsCase(ThreadExecutor threadExecutor,
                              PostExecutionThread postExecutionThread, BlogRepo blogRepo){
        super(threadExecutor, postExecutionThread);
        this.blogRepo = blogRepo;
    }

    /**
     * Builds an {@link Observable} which will be used when executing the current {@link UseCase}.
     */
    @Override
    protected Observable<ResLatestBlogs> buildUseCaseObservable() {
        return blogRepo.latestBlogs(page, count);
    }

    public GetLatestBlogsCase setPage(int page) {
        this.page = page;
        return this;
    }

    public GetLatestBlogsCase setCount(int count) {
        this.count = count;
        return this;
    }
}
