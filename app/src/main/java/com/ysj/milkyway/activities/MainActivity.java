package com.ysj.milkyway.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.ysj.milkyway.R;
import com.ysj.milkyway.managers.FloatWindowManager;
import com.ysj.milkyway.views.adapters.DemoAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @Bind(R.id.rv_demo)
    RecyclerView demoRecyclerView;
    @Bind(R.id.fab)
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setupToolbar();

        demoRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        demoRecyclerView.setAdapter(new DemoAdapter());

        fab.setOnClickListener(v -> startHack());
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void startHack() {
        getApplicationContext().setTheme(R.style.AppTheme);
        if (!FloatWindowManager.isWindowShowing()) {
            FloatWindowManager.createSmallWindow(getApplicationContext());
        }
        finish();
    }
}
