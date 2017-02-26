package com.meishikr.app.domain.interactor.post;

import com.meishikr.app.domain.entity.post.Lamp;
import com.meishikr.app.domain.executor.PostExecutionThread;
import com.meishikr.app.domain.executor.ThreadExecutor;
import com.meishikr.app.domain.interactor.UseCase;
import com.meishikr.app.domain.repository.LampRepo;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by yinhang on 2016/12/19.
 */

public class GetMyLampsCase extends UseCase<List<Lamp>>{

    LampRepo lampRepo;

    @Inject
    public GetMyLampsCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, LampRepo lampRepo) {
        super(threadExecutor, postExecutionThread);
        this.lampRepo = lampRepo;
    }

    @Override
    protected Observable<List<Lamp>> buildUseCaseObservable() {
        return lampRepo.myLocalLamps();
    }
}
