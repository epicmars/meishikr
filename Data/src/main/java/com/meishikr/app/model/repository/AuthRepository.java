package com.meishikr.app.model.repository;

import com.meishikr.app.model.api.AuthApi;
import com.meishikr.app.domain.dto.req.ReqLogin;
import com.meishikr.app.domain.dto.req.ReqRegister;
import com.meishikr.app.domain.dto.res.ResLogin;
import com.meishikr.app.domain.dto.res.ResRegister;
import com.meishikr.app.domain.entity.auth.Token;
import com.meishikr.app.domain.repository.AuthRepo;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by yinhang on 16/7/10.
 */
public class AuthRepository implements AuthRepo {

    private AuthApi authApi;

    @Inject
    public AuthRepository(AuthApi authApi){
        this.authApi = authApi;
    }

    /**
     * Register.
     *
     * @return
     */
    @Override
    public Observable<Token> register(String identity, String secret) {
        ReqRegister reqRegister = new ReqRegister(identity, secret);
        return authApi.register(reqRegister).map(new Func1<ResRegister, Token>() {
            @Override
            public Token call(ResRegister resRegister) {
                Token token = new Token();
                token.timestamp = System.currentTimeMillis();
                token.refreshToken = resRegister.refreshToken;
                token.accessToken = resRegister.accessToken;
                return token;
            }
        });
    }

    /**
     * Login.
     *
     * @param identity
     * @param secret
     * @return
     */
    @Override
    public Observable<Token> login(String identity, String secret) {
        ReqLogin login = new ReqLogin(identity, secret);
        return authApi.login(login).map(new Func1<ResLogin, Token>() {
            @Override
            public Token call(ResLogin resLogin) {
                Token token = new Token();
                token.timestamp = System.currentTimeMillis();
                token.refreshToken = resLogin.refreshToken;
                token.accessToken = resLogin.accessToken;
                return token;
            }
        });
    }
}
