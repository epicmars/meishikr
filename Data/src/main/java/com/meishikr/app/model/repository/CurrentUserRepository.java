package com.meishikr.app.model.repository;

import android.app.Application;
import android.content.Context;

import com.meishikr.app.domain.entity.auth.Token;
import com.meishikr.app.domain.repository.CurrentUserRepo;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;
import com.sin2pi.brick.components.xml.SharedPreferenceHelper;

import java.text.ParseException;

import javax.inject.Inject;

/**
 * Created by yinhang on 16/11/6.
 */

public class CurrentUserRepository implements CurrentUserRepo{

    private Context context;
    private SharedPreferenceHelper helper;

    @Inject
    public CurrentUserRepository(Application context) {
        this.context = context;
        this.helper = SharedPreferenceHelper.instance(context);
    }

    public boolean isLogin() {
        // 获取用户id
        if(getUserId() > 0) {
            return true;
        }
        // 本地判断是否需要重新登录

        return false;
    }

    public long getUserId() {
        Token token = helper.load(Token.class);
        if(null == token || null == token.accessToken)
            return -1;
        try {
            JWT jwt = JWTParser.parse(token.accessToken);
            JWTClaimsSet jwtClaimsSet = jwt.getJWTClaimsSet();
            return jwtClaimsSet.getLongClaim("id");
        } catch (ParseException e) {

        }
        return -1;
    }
}
