package com.ysj.milkyway.network;

import com.google.gson.annotations.SerializedName;
import com.ysj.milkyway.models.Image;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by Yu Shaojian on 2015 12 14.
 */
public interface GankApi {
    @GET("data/%E7%A6%8F%E5%88%A9/{count}/1")
    Call<Result<List<Image>>> latest(@Path("count") int count);

    @GET("get/{count}/since/{year}/{month}/{day}")
    Call<Result<List<String>>> since(@Path("count") int count,
                                     @Path("year") String year,
                                     @Path("month") String month,
                                     @Path("day") String day);

    @GET("get/{count}/before/{year}/{month}/{day}")
    Call<Result<List<String>>> before(@Path("count") int count,
                                      @Path("year") String year,
                                      @Path("month") String month,
                                      @Path("day") String day);

    @GET("day/{year}/{month}/{day}")
    Call<Result<Article>> get(@Path("year") String year,
                              @Path("month") String month,
                              @Path("day") String day);

    class Result<T> {

        public boolean error;

        public T results;

    }

    class Article {

        @SerializedName("福利")
        public List<Image> images;

    }
}
