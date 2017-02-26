package com.meishikr.app.domain.interactor.post;

import com.meishikr.app.domain.entity.post.Blog;
import com.meishikr.app.domain.executor.PostExecutionThread;
import com.meishikr.app.domain.executor.ThreadExecutor;
import com.meishikr.app.domain.interactor.UseCase;
import com.meishikr.app.domain.repository.BlogRepo;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by yinhang on 16/11/5.
 */

public class SaveBlogCase extends UseCase<Long> {

    private BlogRepo blogRepo;
    private Blog blog;

    @Inject
    public SaveBlogCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, BlogRepo blogRepo) {
        super(threadExecutor, postExecutionThread);
        this.blogRepo = blogRepo;
    }

    public SaveBlogCase setBlog(Blog blog) {
        this.blog = blog;
        return this;
    }

    @Override
    protected Observable<Long> buildUseCaseObservable() {
        return blogRepo.save(blog);
    }
}
