package com.ysj.milkyway.views.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ysj.milkyway.R;
import com.ysj.milkyway.models.GankMeizhi;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Yu Shaojian on 2015 12 14.
 */
public class GankMeizhiAdapter extends RecyclerView.Adapter<GankMeizhiAdapter.GViewHolder> {
    private Context mContext;
    private List<GankMeizhi> mData;

    public GankMeizhiAdapter(List<GankMeizhi> datas, Context mContext) {
        this.mData = datas;
        this.mContext = mContext;
    }

    @Override
    public GViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_meizhi, parent, false);
        return new GViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public void onBindViewHolder(GViewHolder holder, int position) {
        GankMeizhi gmei = mData.get(position);

        Glide.with(mContext).load(gmei.getUrl()).into(holder.meizhi);
    }

    static class GViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_item)
        ImageView meizhi;

        public GViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
