package com.ysj.milkyway.helpers;

import android.os.AsyncTask;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ysj.milkyway.callbacks.GankCallback;
import com.ysj.milkyway.models.Image;
import com.ysj.milkyway.network.GankApi;

import java.io.IOException;
import java.util.List;

import io.realm.RealmObject;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by Yu Shaojian on 2015 12 14.
 */
public class GankServiceHelper {
    private static final Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            .setExclusionStrategies(new ExclusionStrategy() {
                @Override
                public boolean shouldSkipField(FieldAttributes f) {
                    return f.getDeclaringClass().equals(RealmObject.class);
                }

                @Override
                public boolean shouldSkipClass(Class<?> clazz) {
                    return false;
                }
            })
            .create();

    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://gank.avosapps.com")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

    private static final GankApi service = retrofit.create(GankApi.class);

    public static void search(final String type, final int page, final GankCallback callback) {
        if (callback == null) {
            // ignore this call if callback is null
            return;
        }

        new AsyncTask<Void, Void, GankApi.Result<List<Image>>>() {
            @Override
            protected GankApi.Result<List<Image>> doInBackground(Void... params) {
                try {
                    GankApi.Result<List<Image>> gankResult = service.latest(10).execute().body();
                    return gankResult;
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(GankApi.Result<List<Image>> result) {
            }
        }.execute();
    }

}
