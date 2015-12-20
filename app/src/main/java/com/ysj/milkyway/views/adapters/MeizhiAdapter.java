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

package com.ysj.milkyway.views.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ysj.milkyway.R;
import com.ysj.milkyway.models.ImageWrapper;
import com.ysj.milkyway.views.widgets.RatioImageView;

import butterknife.Bind;
import butterknife.ButterKnife;

public abstract class MeizhiAdapter extends ArrayRecyclerAdapter<ImageWrapper, MeizhiAdapter.ViewHolder> {

    private final Context context;
    private final LayoutInflater inflater;

    public MeizhiAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        setHasStableIds(true);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(R.layout.item_meizhi, parent);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ImageWrapper image = get(position);

        holder.imageView.setOriginalSize(image.width, image.height);

        Glide.with(context)
                .load(image.url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imageView);

        ViewCompat.setTransitionName(holder.imageView, image.url);
    }

    @Override
    public long getItemId(int position) {
        return get(position).url.hashCode();
    }

    protected abstract void onItemClick(View v, int position);

    public class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.picture)
        public RatioImageView imageView;

        public ViewHolder(@LayoutRes int resource, ViewGroup parent) {
            super(inflater.inflate(resource, parent, false));
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(v -> onItemClick(v, getAdapterPosition()));
        }

    }

}
