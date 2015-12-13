package com.ysj.milkyway.network;

import com.ysj.milkyway.models.GankResult;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by Yu Shaojian on 2015 12 14.
 */
public interface GankService {
    @GET("/api/data/{dateType}/10/{page}")
    Call<GankResult> getMeizhi(@Path("dateType") String dataType, @Path("page") int page);
}
