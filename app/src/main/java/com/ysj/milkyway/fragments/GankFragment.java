package com.ysj.milkyway.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ysj.milkyway.R;
import com.ysj.milkyway.callbacks.GankCallback;
import com.ysj.milkyway.helpers.GankServiceHelper;
import com.ysj.milkyway.services.MeizhiFetchingService;
import com.ysj.milkyway.views.adapters.GankMeizhiAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmChangeListener;

/**
 * Created by Yu Shaojian on 2015 12 14.
 */
public class GankFragment extends Fragment implements RealmChangeListener {
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    @Bind(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;

    private Realm realm;

    private LocalBroadcastManager localBroadcastManager;

    private UpdateResultReceiver updateResultReceiver = new UpdateResultReceiver();

    private boolean isFetching = false;
    
    private GankMeizhiAdapter meizhiAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gank, container, false);
        ButterKnife.bind(this, view);

        meizhiAdapter = new GankMeizhiAdapter(getContext());

        LinearLayoutManager manager = new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(meizhiAdapter);

        realm = Realm.getDefaultInstance();
        realm.addChangeListener(this);

        return view;
    }
    
    @Override
    public void onChange() {
        populate();
    }

    private class UpdateResultReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            onFetched(
                    intent.getIntExtra(MeizhiFetchingService.EXTRA_FETCHED, 0),
                    intent.getStringExtra(MeizhiFetchingService.EXTRA_TRIGGER)
            );
        }
    }
}
