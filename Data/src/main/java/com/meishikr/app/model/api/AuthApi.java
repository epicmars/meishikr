package com.meishikr.app.model.api;

import com.meishikr.app.domain.dto.req.ReqLogin;
import com.meishikr.app.domain.dto.req.ReqRegister;
import com.meishikr.app.domain.dto.res.ResAccessToken;
import com.meishikr.app.domain.dto.res.ResLogin;
import com.meishikr.app.domain.dto.res.ResRegister;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by yinhang on 16/3/11.
 */
public interface AuthApi {

    @POST("auth/register")
    Observable<ResRegister> register(@Body ReqRegister reqRegister);

    @POST("auth/login")
    Observable<ResLogin> login(@Body ReqLogin reqLogin);

    @GET("auth/access_token")
    Observable<ResAccessToken> accessToken();
}
