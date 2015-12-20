/*
 * Copyright 2015 XiNGRZ <chenxingyu92@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
