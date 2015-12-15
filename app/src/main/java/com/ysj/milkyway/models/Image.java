package com.ysj.milkyway.models;

import android.graphics.Point;

import com.google.gson.annotations.SerializedName;
import com.ysj.milkyway.network.ImageFetcher;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Yu Shaojian on 2015 12 14.
 */
public class Image extends RealmObject {
    @PrimaryKey
    @SerializedName("objectId")
    private String id;

    private String url;
    private int width;
    private int height;
    private Date publishedAt;

    public static RealmResults<Image> all(Realm realm) {
        return realm.where(Image.class)
                .findAllSorted("publishedAt", RealmResults.SORT_ORDER_DESCENDING);
    }

    public static Image persist(Image image, ImageFetcher imageFetcher)
            throws IOException, InterruptedException, ExecutionException {
        Point size = new Point();

        imageFetcher.prefetchImage(image.getUrl(), size);

        image.setWidth(size.x);
        image.setHeight(size.y);

        return image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Date getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Date publishedAt) {
        this.publishedAt = publishedAt;
    }
}
