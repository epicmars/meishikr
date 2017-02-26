package com.meishikr.app.viewmodel;

import android.app.Activity;
import android.view.View;

import com.meishikr.app.base.BaseViewModel;
import com.meishikr.app.domain.entity.auth.Token;
import com.meishikr.app.domain.interactor.auth.LoginCase;
import com.meishikr.app.viewmodel.event.EventLogin;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;
import com.sin2pi.brick.components.xml.SharedPreferenceHelper;

import java.text.ParseException;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by yinhang on 16/7/6.
 */
public class LoginViewModel extends BaseViewModel {

    private String email;
    private String password;

    @Inject
    LoginCase loginCase;

    @Inject
    public LoginViewModel(Activity context) {
        super(context);
    }

    public void onLogin(View view) {
        loginCase.set(email, password)
                .execute(new Subscriber<Token>() {

                    @Override
                    public void onCompleted() {
                        getBus().post(new EventLogin(EventLogin.Result.COMPLETED));
                    }

                    @Override
                    public void onError(Throwable e) {
                        getBus().post(new EventLogin(EventLogin.Result.ERROR));
                    }

                    @Override
                    public void onNext(Token token) {
                        if (null == token) {
                            getBus().post(new EventLogin(EventLogin.Result.ERROR));
                        }
                        if (token != null) {
                            try {
                                JWT jwt = JWTParser.parse(token.accessToken);
                                JWTClaimsSet jwtClaimsSet = jwt.getJWTClaimsSet();
                                long uid = jwtClaimsSet.getLongClaim("id");
                                SharedPreferenceHelper.instance(getContext()).save(token);
                            } catch (ParseException e) {

                            }
                            getBus().post(new EventLogin(EventLogin.Result.SUCCEED));
                        } else {
                            getBus().post(new EventLogin(EventLogin.Result.ACCOUNT_OR_PASSWORD_INCORRECT));
                        }
                    }
                });
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
