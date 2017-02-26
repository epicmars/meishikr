package com.meishikr.app.domain.interactor.auth;

import com.meishikr.app.domain.executor.PostExecutionThread;
import com.meishikr.app.domain.executor.ThreadExecutor;
import com.meishikr.app.domain.interactor.UseCase;
import com.meishikr.app.domain.repository.AuthRepo;

import javax.inject.Inject;

import rx.Observable;

/**
 * User login usecase.
 *
 * Created by yinhang on 16/7/10.
 */
public class LoginCase extends UseCase {

    private final AuthRepo authRepo;
    /**
     * A user's unique identity, like telephone number, email address.
     */
    private String identity;
    /**
     * The secret code of user's identity. It can be a password, a sms captcha。
     */
    private String secret;

    @Inject
    public LoginCase(ThreadExecutor threadExecutor,
                     PostExecutionThread postExecutionThread, AuthRepo authRepo){
        super(threadExecutor, postExecutionThread);
        this.authRepo = authRepo;
    }

    public LoginCase set(String identity, String secret){
        this.identity = identity;
        this.secret = secret;
        return this;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return authRepo.login(identity, secret);
    }
}
