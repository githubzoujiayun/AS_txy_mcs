package com.txy.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.txy.fragment.AirConditionFragment;
import com.txy.fragment.HomeFragment;

/**
 * Created by Clearlove on 15/8/12.
 */
public class AirConditionAdapter extends FragmentPagerAdapter {

    private int mAirConditionNum;// 空调的数量

    public AirConditionAdapter(FragmentManager fm, int num) {
        super(fm);
        this.mAirConditionNum = num;
    }

    @Override
    public Fragment getItem(int position) {

        Fragment fragment = new AirConditionFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        bundle.putInt("airConditionNum", mAirConditionNum);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public int getCount() {
        return mAirConditionNum;
    }

    // 设置空调的数量
    public void setAirConditionNum(int num) {
        this.mAirConditionNum = num;
    }
}
