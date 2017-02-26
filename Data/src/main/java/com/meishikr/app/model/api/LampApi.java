package com.meishikr.app.model.api;

import com.meishikr.app.domain.dto.res.ResLatestLamps;
import com.meishikr.app.domain.dto.res.ResPostLamp;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by yinhang on 2016/12/18.
 */

public interface LampApi {

    @Multipart
    @POST("lamp/")
    Observable<ResPostLamp> postLamp(@PartMap Map<String, RequestBody> lamp);

    @GET("lamps/")
    Observable<ResLatestLamps> latestLamps(@Query("page") int page, @Query("count") int count);
}
