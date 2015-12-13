package com.ysj.milkyway.helpers;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ysj.log.L;
import com.ysj.milkyway.callbacks.GankCallback;
import com.ysj.milkyway.models.GankResult;
import com.ysj.milkyway.network.GankService;

import java.io.IOException;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by Yu Shaojian on 2015 12 14.
 */
public class GankServiceHelper {
    private static final Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();

    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://gank.avosapps.com")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

    private static final GankService service = retrofit.create(GankService.class);

    public static void search(final String type, final int page, final GankCallback callback) {
        if (callback == null) {
            // ignore this call if callback is null
            return;
        }

        new AsyncTask<Void, Void, GankResult>() {
            @Override
            protected GankResult doInBackground(Void... params) {
                try {
                    GankResult gankResult = service.getMeizhi(type, page).execute().body();
                    L.e(gankResult);
                    return gankResult;
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(GankResult result) {
                callback.onResult(result);
            }
        }.execute();
    }

}
