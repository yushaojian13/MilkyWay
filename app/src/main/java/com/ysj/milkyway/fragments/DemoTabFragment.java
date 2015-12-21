package com.ysj.milkyway.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ysj.milkyway.R;
import com.ysj.milkyway.views.adapters.DemoAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Yu Shaojian on 2015 12 21.
 */
public class DemoTabFragment extends Fragment {
    @Bind(R.id.rv_demo)
    RecyclerView demoRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_demo, container, false);
        ButterKnife.bind(this, view);

        demoRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        demoRecyclerView.setAdapter(new DemoAdapter());

        return view;
    }
}
