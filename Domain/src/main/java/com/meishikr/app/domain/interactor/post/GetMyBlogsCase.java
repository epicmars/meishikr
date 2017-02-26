package com.meishikr.app.domain.interactor.post;

import com.meishikr.app.domain.entity.post.Blog;
import com.meishikr.app.domain.executor.PostExecutionThread;
import com.meishikr.app.domain.executor.ThreadExecutor;
import com.meishikr.app.domain.interactor.UseCase;
import com.meishikr.app.domain.repository.BlogRepo;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by yinhang on 16/11/6.
 */

public class GetMyBlogsCase extends UseCase<List<Blog>> {

    BlogRepo blogRepo;

    @Inject
    public GetMyBlogsCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, BlogRepo blogRepo) {
        super(threadExecutor, postExecutionThread);
        this.blogRepo = blogRepo;
    }

    @Override
    protected Observable<List<Blog>> buildUseCaseObservable() {
        return blogRepo.myBlogsLocal();
    }
}
