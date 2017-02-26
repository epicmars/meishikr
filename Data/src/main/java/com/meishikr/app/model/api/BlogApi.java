package com.meishikr.app.model.api;

import com.meishikr.app.domain.dto.res.ResPostBlog;
import com.meishikr.app.domain.dto.res.ResLatestBlogs;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by yinhang on 16/3/11.
 */
public interface BlogApi {

    // TODO 采用服务端一致的路径表示
    @Multipart
    @POST("blog/")
    Observable<ResPostBlog> postBlog(@PartMap Map<String, RequestBody> blogPart);

    @GET("blogs/")
    Observable<ResLatestBlogs> latestBlogs(@Query("page") int page, @Query("count") int count);

}
