package com.meishikr.app.domain.interactor.post;

import com.meishikr.app.domain.dto.res.ResLatestLamps;
import com.meishikr.app.domain.executor.PostExecutionThread;
import com.meishikr.app.domain.executor.ThreadExecutor;
import com.meishikr.app.domain.interactor.UseCase;
import com.meishikr.app.domain.repository.LampRepo;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by yinhang on 2016/12/20.
 */

public class GetLatestLampsCase extends UseCase<ResLatestLamps>{

    private final LampRepo lampRepo;
    private int page;
    private int count;

    @Inject
    public GetLatestLampsCase(ThreadExecutor threadExecutor,
                              PostExecutionThread postExecutionThread, LampRepo lampRepo){
        super(threadExecutor, postExecutionThread);
        this.lampRepo = lampRepo;
    }

    /**
     * Builds an {@link Observable} which will be used when executing the current {@link UseCase}.
     */
    @Override
    protected Observable<ResLatestLamps> buildUseCaseObservable() {
        return lampRepo.latestLamps(page, count);
    }

    public GetLatestLampsCase setPage(int page) {
        this.page = page;
        return this;
    }

    public GetLatestLampsCase setCount(int count) {
        this.count = count;
        return this;
    }
}
