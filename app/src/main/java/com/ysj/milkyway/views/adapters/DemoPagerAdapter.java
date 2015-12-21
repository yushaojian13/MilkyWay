package com.ysj.milkyway.views.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;

import com.ysj.milkyway.fragments.DemoTabFragment;

/**
 * Created by Yu Shaojian on 2015 12 21.
 */
public class DemoPagerAdapter extends FragmentPagerAdapter {
    private Context context;

    private String tabTitles[] = new String[] { "Tab1", "Tab2", "Tab3" };

    public DemoPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        return new DemoTabFragment();
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // 文字标签
        return tabTitles[position];

        // 图片标签
//        return ImageCharSequence.create(context, R.drawable.circle_p);

        // 文字+图片标签
//        return ImageCharSequence.create(context, R.drawable.circle_p, tabTitles[position]);

//        return null;
    }

    // 使用自定义Tab
    public View getTabView(int position) {
        View view = null;
        return view;
    }
}
