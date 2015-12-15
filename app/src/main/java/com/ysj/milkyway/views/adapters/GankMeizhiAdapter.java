package com.ysj.milkyway.views.adapters;

import android.content.Context;
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

/**
 * Created by Yu Shaojian on 2015 12 14.
 */
public class GankMeizhiAdapter extends ArrayRecyclerAdapter<ImageWrapper, GankMeizhiAdapter.ViewHolder> {
    private Context mContext;

    public GankMeizhiAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_meizhi, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ImageWrapper image = get(position);

        holder.imageView.setOriginalSize(image.width, image.height);

        Glide.with(mContext)
                .load(image.getUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imageView);

        ViewCompat.setTransitionName(holder.imageView, image.getUrl());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_item)
        RatioImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
