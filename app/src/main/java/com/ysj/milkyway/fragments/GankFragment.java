package com.ysj.milkyway.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ysj.log.L;
import com.ysj.milkyway.R;
import com.ysj.milkyway.activities.ViewerActivity;
import com.ysj.milkyway.models.Image;
import com.ysj.milkyway.models.ImageWrapper;
import com.ysj.milkyway.services.MeizhiFetchingService;
import com.ysj.milkyway.views.adapters.GankMeizhiAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmChangeListener;

/**
 * Created by Yu Shaojian on 2015 12 14.
 */
public class GankFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener,
        RealmChangeListener, GankMeizhiAdapter.OnItemClickListener {
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    @Bind(R.id.refresh_layout)
    SwipeRefreshLayout refresher;

    private Realm realm;

    private LocalBroadcastManager localBroadcastManager;

    private UpdateResultReceiver updateResultReceiver = new UpdateResultReceiver();

    private boolean isFetching = false;
    
    private GankMeizhiAdapter meizhiAdapter;
    private StaggeredGridLayoutManager layoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gank, container, false);
        ButterKnife.bind(this, view);

        refresher.setOnRefreshListener(this);

        meizhiAdapter = new GankMeizhiAdapter(getContext());
        layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(meizhiAdapter);

        meizhiAdapter.setOnItemClickListener(this);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    GankFragment.this.onScrolled();
                }
            }
        });

        realm = Realm.getDefaultInstance();
        realm.addChangeListener(this);

        localBroadcastManager = LocalBroadcastManager.getInstance(getContext());

        if (populate() == 0) {
            fetchForward();
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        localBroadcastManager.registerReceiver(updateResultReceiver,
                new IntentFilter(MeizhiFetchingService.ACTION_UPDATE_RESULT));
    }

    @Override
    public void onPause() {
        super.onPause();
        localBroadcastManager.unregisterReceiver(updateResultReceiver);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        realm.removeChangeListener(this);
        realm.close();
    }

    @Override
    public void onRefresh() {
        fetchForward();
    }

    @Override
    public void onChange() {
        populate();
    }

    private int populate() {
        List<ImageWrapper> wrappers = new ArrayList<>();

        for (Image image : Image.all(realm)) {
            wrappers.add(ImageWrapper.from(image));
        }

        meizhiAdapter.replaceWith(wrappers);

        return wrappers.size();
    }

    private void onScrolled() {
        int[] positions = new int[layoutManager.getSpanCount()];
        layoutManager.findLastVisibleItemPositions(positions);

        for (int position : positions) {
            if (position == layoutManager.getItemCount() - 1) {
                L.d(position + " of " + layoutManager.getItemCount());
                fetchBackward();
                break;
            }
        }
    }

    private void onFetched(int fetched, @NonNull String trigger) {
        refresher.setRefreshing(false);
        isFetching = false;

        if (MeizhiFetchingService.ACTION_FETCH_FORWARD.equals(trigger)) {
            maybeFetchMoreToFill();
        } else if (MeizhiFetchingService.ACTION_FETCH_BACKWARD.equals(trigger)) {
            if (fetched > 0) {
                maybeFetchMoreToFill();
            }
        }
    }

    private void maybeFetchMoreToFill() {
        int itemCount = layoutManager.getItemCount();
        int childCount = layoutManager.getChildCount();

        // 一开始数据少时尝试不停加载旧数据直到填满屏幕
        if (itemCount == childCount || childCount == 0) {
            fetchBackward();
        }
    }

    private void fetchForward() {
        if (isFetching) {
            return;
        }

        Intent intent = new Intent(getContext(), MeizhiFetchingService.class);
        intent.setAction(MeizhiFetchingService.ACTION_FETCH_FORWARD);
        getActivity().startService(intent);

        refresher.post(() -> refresher.setRefreshing(true));

        isFetching = true;
    }

    private void fetchBackward() {
        if (isFetching) {
            return;
        }

        Intent intent = new Intent(getContext(), MeizhiFetchingService.class);
        intent.setAction(MeizhiFetchingService.ACTION_FETCH_BACKWARD);
        getActivity().startService(intent);

        refresher.post(() -> refresher.setRefreshing(true));

        isFetching = true;
    }

    @Override
    public void onItemClick(View itemView, int position) {
        Intent intent = new Intent(getContext(), ViewerActivity.class);
        intent.putExtra("index", position);

        ActivityOptionsCompat options = ActivityOptionsCompat
                .makeSceneTransitionAnimation(getActivity(), itemView, meizhiAdapter.get(position).getUrl());

        getActivity().startActivity(intent, options.toBundle());
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
