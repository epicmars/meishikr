package com.meishikr.app.domain.interactor.post;

import com.meishikr.app.domain.entity.post.Lamp;
import com.meishikr.app.domain.executor.PostExecutionThread;
import com.meishikr.app.domain.executor.ThreadExecutor;
import com.meishikr.app.domain.interactor.UseCase;
import com.meishikr.app.domain.repository.LampRepo;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by yinhang on 2016/12/19.
 */

public class SaveLampCase extends UseCase<Long> {

    private LampRepo lampRepo;
    private Lamp lamp;

    @Inject
    public SaveLampCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, LampRepo lampRepo) {
        super(threadExecutor, postExecutionThread);
        this.lampRepo = lampRepo;
    }

    public SaveLampCase setLamp(Lamp lamp) {
        this.lamp = lamp;
        return this;
    }

    @Override
    protected Observable<Long> buildUseCaseObservable() {
        return lampRepo.save(lamp);
    }
}
