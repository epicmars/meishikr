package com.meishikr.app.domain.interactor.post;

import com.meishikr.app.domain.executor.PostExecutionThread;
import com.meishikr.app.domain.executor.ThreadExecutor;
import com.meishikr.app.domain.interactor.UseCase;

import rx.Observable;

/**
 * Created by yinhang on 16/7/18.
 */
public class GetLatestCommentsCase extends UseCase {

    public GetLatestCommentsCase(ThreadExecutor threadExecutor,
                                 PostExecutionThread postExecutionThread){
        super(threadExecutor, postExecutionThread);
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return null;
    }
}
