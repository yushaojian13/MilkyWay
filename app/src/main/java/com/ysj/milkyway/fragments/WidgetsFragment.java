package com.ysj.milkyway.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ysj.milkyway.R;

/**
 * Created by Yu Shaojian on 2015 12 22.
 */
public class WidgetsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_widgets, container, false);
        return view;
    }
}
