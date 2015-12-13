package com.ysj.milkyway.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ysj.milkyway.R;
import com.ysj.milkyway.callbacks.GankCallback;
import com.ysj.milkyway.helpers.GankServiceHelper;
import com.ysj.milkyway.models.GankMeizhi;
import com.ysj.milkyway.models.GankResult;
import com.ysj.milkyway.views.adapters.GankMeizhiAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Yu Shaojian on 2015 12 14.
 */
public class GankFragment extends Fragment {
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    @Bind(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    
    private List<GankMeizhi> meizhiList;
    private GankMeizhiAdapter meizhiAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gank, container, false);
        ButterKnife.bind(this, view);

        meizhiList = new ArrayList<>();
        meizhiAdapter = new GankMeizhiAdapter(meizhiList, getContext());

        LinearLayoutManager manager = new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(meizhiAdapter);

        GankServiceHelper.search("福利", 0, new GankCallback() {
            @Override
            public void onResult(GankResult result) {
                if (!result.error) {
                    meizhiList.clear();
                    meizhiList.addAll(result.results);
                    meizhiAdapter.notifyDataSetChanged();
                }
            }
        });
        return view;
    }
}
