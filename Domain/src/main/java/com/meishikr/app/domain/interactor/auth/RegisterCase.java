package com.meishikr.app.domain.interactor.auth;

import com.meishikr.app.domain.executor.PostExecutionThread;
import com.meishikr.app.domain.executor.ThreadExecutor;
import com.meishikr.app.domain.interactor.UseCase;
import com.meishikr.app.domain.repository.AuthRepo;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by yinhang on 16/7/10.
 */
public class RegisterCase extends UseCase {

    private final AuthRepo authRepo;
    private String identity, secret;

    @Inject
    public RegisterCase(ThreadExecutor threadExecutor,
                        PostExecutionThread postExecutionThread, AuthRepo authRepo){
        super(threadExecutor, postExecutionThread);
        this.authRepo = authRepo;
    }

    public RegisterCase set(String identity, String secret) {
        this.identity = identity;
        this.secret = secret;
        return this;
    }

    /**
     * Builds an {@link Observable} which will be used when executing the current {@link UseCase}.
     */
    @Override
    protected Observable buildUseCaseObservable() {
        return authRepo.register(identity, secret);
    }
}
