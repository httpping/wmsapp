package com.framework.remote.api;



import io.reactivex.Flowable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * XXX服务器
 */
public interface Api {

    /**
     * 获取产品评论列表
     *
     * @param params 请求体
     */
    @POST("post")
    Flowable<String> post(@Body RequestBody params);

    @POST("post")
    Flowable<String> postBean(@Body RequestBody params);

    @POST("common/initialization")
    Flowable<String> initialization(@Body RequestBody params);

    @POST("/appflyer_post_android/{eventName}")
    Flowable<String> appflyer_post_android(@Path("eventName") String eventName, @Body RequestBody params);

}
