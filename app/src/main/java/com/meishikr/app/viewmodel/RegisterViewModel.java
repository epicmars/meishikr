package com.meishikr.app.viewmodel;

import android.app.Activity;
import android.view.View;

import com.meishikr.app.base.BaseViewModel;
import com.meishikr.app.domain.interactor.auth.RegisterCase;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by yinhang on 2016/12/26.
 */

public class RegisterViewModel extends BaseViewModel {

    private String identity;
    private String password;
    private String passwordConfirm;

    @Inject
    RegisterCase registerCase;

    @Inject
    public RegisterViewModel(Activity context) {
        super(context);
    }

    public void onRegister(View view) {
        final String secret = password;
        registerCase.set(identity, secret)
                .execute(new Subscriber() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Object o) {

                    }
                });
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }
}
