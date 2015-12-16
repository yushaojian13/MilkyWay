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

package com.ysj.milkyway.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.ysj.milkyway.R;
import com.ysj.milkyway.activities.ViewerActivity;
import com.ysj.milkyway.views.widgets.TouchImageView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ViewerFragment extends Fragment implements RequestListener<String, GlideDrawable> {

    private static final String TAG = "ViewerFragment";

    @Bind(R.id.picture)
    TouchImageView image;

    private ViewerActivity activity;

    private String url;
    private boolean initialShown;

    public static ViewerFragment newFragment(String url, boolean initialShown) {
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        bundle.putBoolean("initial_shown", initialShown);

        ViewerFragment fragment = new ViewerFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = (ViewerActivity) getActivity();

        url = getArguments().getString("url");
        initialShown = getArguments().getBoolean("initial_shown", false);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_viewer, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        ViewCompat.setTransitionName(image, url);
    }

    @Override
    public void onResume() {
        super.onResume();
        Glide.with(this)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade(0)
                .listener(this)
                .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
    }

    @Override
    public boolean onException(Exception e,
                               String model,
                               Target<GlideDrawable> target,
                               boolean isFirstResource) {
        maybeStartPostponedEnterTransition();
        return true;
    }

    @Override
    public boolean onResourceReady(GlideDrawable resource,
                                   String model,
                                   Target<GlideDrawable> target,
                                   boolean isFromMemoryCache,
                                   boolean isFirstResource) {
        image.setImageDrawable(resource);
        maybeStartPostponedEnterTransition();
        return true;
    }

    private void maybeStartPostponedEnterTransition() {
        if (initialShown) {
            activity.supportStartPostponedEnterTransition();
        }
    }

    @OnClick(R.id.picture)
    void toggleToolbar() {
        activity.toggleToolbar();
    }

    public View getSharedElement() {
        return image;
    }

    public boolean isZoomed() {
        return image.isZoomed();
    }

}
