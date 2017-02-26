package com.meishikr.app.domain.interactor.post;

import com.meishikr.app.domain.entity.post.Blog;
import com.meishikr.app.domain.executor.PostExecutionThread;
import com.meishikr.app.domain.executor.ThreadExecutor;
import com.meishikr.app.domain.interactor.UseCase;
import com.meishikr.app.domain.repository.BlogRepo;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by yinhang on 16/11/7.
 */

public class LoadOneBlogCase extends UseCase<Blog>{

    private BlogRepo blogRepo;
    private String uuid;
    private long id;

    @Inject
    public LoadOneBlogCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, BlogRepo blogRepo) {
        super(threadExecutor, postExecutionThread);
        this.blogRepo = blogRepo;
    }

    public LoadOneBlogCase set(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public LoadOneBlogCase set(long id) {
        this.id = id;
        return this;
    }

    @Override
    protected Observable<Blog> buildUseCaseObservable() {
        if(null != uuid && !uuid.isEmpty())
            return blogRepo.load(uuid);
        if(id > 0)
            return blogRepo.load(id);
        return null;
    }
}
