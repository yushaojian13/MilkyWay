package com.ysj.milkyway.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.ysj.milkyway.R;
import com.ysj.milkyway.views.adapters.DemoPagerAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    @Bind(R.id.sliding_tabs)
    TabLayout tabLayout;
    @Bind(R.id.vp_demo)
    ViewPager viewPager;
    @Bind(R.id.fab)
    FloatingActionButton fab;

    private DemoPagerAdapter pagerAdapter;

    public static final String POSITION = "position";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setupToolbar();

        pagerAdapter = new DemoPagerAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        updateTabs();

        viewPager.addOnPageChangeListener(this);
        fab.setOnClickListener(v -> Snackbar.make(v, R.string.app_name, Snackbar.LENGTH_SHORT).show());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(POSITION, tabLayout.getSelectedTabPosition());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        viewPager.setCurrentItem(savedInstanceState.getInt(POSITION));
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void updateTabs() {
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);

            tab.setIcon(tab.isSelected() ? R.drawable.circle_p : R.drawable.circle_n);
        // 使用自定义Tab
//            tab.setCustomView(pagerAdapter.getTabView(viewPager.getCurrentItem()));
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        updateTabs();
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }
}
