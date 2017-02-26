package com.meishikr.app.domain.repository;

import com.meishikr.app.domain.entity.auth.Token;

import rx.Observable;

/**
 * Created by yinhang on 16/7/10.
 */
public interface AuthRepo {

    /**
     * Register.
     * @return
     */
    Observable<Token> register(String identity, String secret);

    /**
     * Login.
     * @return
     * @param identity
     * @param secret
     */
    Observable<Token> login(String identity, String secret);
}
