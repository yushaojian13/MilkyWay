package com.ysj.milkyway.views.widgets;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ysj.milkyway.R;
import com.ysj.milkyway.managers.FloatWindowManager;
import com.ysj.milkyway.models.Image;
import com.ysj.milkyway.models.ImageWrapper;
import com.ysj.milkyway.services.MeizhiFetchingService;
import com.ysj.milkyway.views.adapters.MeizhiAdapter;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;

public class FloatWindowBigView extends LinearLayout implements RealmChangeListener {

	/**
	 * 记录大悬浮窗的宽度
	 */
	public static int viewWidth;

	/**
	 * 记录大悬浮窗的高度
	 */
	public static int viewHeight;

    private Realm realm;
    private LocalBroadcastManager localBroadcastManager;
    private UpdateResultReceiver updateResultReceiver = new UpdateResultReceiver();
    private MeizhiAdapter adapter;
    private StaggeredGridLayoutManager layoutManager;

    private boolean isFetching = false;

	public FloatWindowBigView(final Context context) {
		super(context);
		LayoutInflater.from(context).inflate(R.layout.float_window_big, this);
		View view = findViewById(R.id.big_window_layout);
		viewWidth = view.getLayoutParams().width;
		viewHeight = view.getLayoutParams().height;

        RecyclerView demoRecyclerView = (RecyclerView) findViewById(R.id.rv_demo);

        layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
		demoRecyclerView.setLayoutManager(layoutManager);

        adapter = new MeizhiAdapter(getContext()) {
            @Override
            protected void onItemClick(View v, int position) {
                Toast.makeText(getContext(), "position " + position, Toast.LENGTH_SHORT).show();
            }
        };
		demoRecyclerView.setAdapter(adapter);

		View close = findViewById(R.id.close);
		View back = findViewById(R.id.back);
		close.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 点击关闭悬浮窗的时候，移除所有悬浮窗
				FloatWindowManager.removeBigWindow(context);
				FloatWindowManager.removeSmallWindow(context);
			}
		});
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 点击返回的时候，移除大悬浮窗，创建小悬浮窗
				FloatWindowManager.removeBigWindow(context);
				FloatWindowManager.createSmallWindow(context);
			}
		});

        realm = Realm.getDefaultInstance();
        realm.addChangeListener(this);
        localBroadcastManager = LocalBroadcastManager.getInstance(getContext());

        if (populate() == 0) {
            fetchForward();
        } else {
            fetchForward();
        }
	}

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        localBroadcastManager.registerReceiver(updateResultReceiver,
                new IntentFilter(MeizhiFetchingService.ACTION_UPDATE_RESULT));
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        localBroadcastManager.unregisterReceiver(updateResultReceiver);
        realm.removeChangeListener(this);
        realm.close();
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

        adapter.replaceWith(wrappers);

        return wrappers.size();
    }

    private void onFetched(int fetched, @NonNull String trigger) {
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
        getContext().startService(intent);

        isFetching = true;
    }

    private void fetchBackward() {
        if (isFetching) {
            return;
        }

        Intent intent = new Intent(getContext(), MeizhiFetchingService.class);
        intent.setAction(MeizhiFetchingService.ACTION_FETCH_BACKWARD);
        getContext().startService(intent);

        isFetching = true;
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
