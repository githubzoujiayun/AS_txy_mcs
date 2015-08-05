package com.txy.adapter;

import com.txy.fragment.HomeFragment;
import com.txy.fragment.LightControlFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class SituationPagerAdapter extends FragmentPagerAdapter {

    private int mPagerNum = 2;

    public SituationPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0) {
            fragment = new HomeFragment();
        } else if (position == 1) {
            fragment = new LightControlFragment();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return mPagerNum;
    }

    /**
     * 设置pager的数量
     */
    public void setPagerNum(int num) {
        mPagerNum = num;
    }
}
