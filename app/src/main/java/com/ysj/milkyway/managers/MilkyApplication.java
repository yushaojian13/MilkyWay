package com.ysj.milkyway.managers;

import android.app.Application;

import com.bumptech.glide.Glide;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Yu Shaojian on 2015 12 15.
 */
public class MilkyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Realm.setDefaultConfiguration(new RealmConfiguration.Builder(this)
                .schemaVersion(2)
                .deleteRealmIfMigrationNeeded()
                .build());
    }
}
