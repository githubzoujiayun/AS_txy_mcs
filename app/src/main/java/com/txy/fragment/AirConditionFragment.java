package com.txy.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.txy.txy_mcs.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AirConditionFragment extends Fragment {

    private int mPosition;// 第几台空调
    private int mAirConditionNum;// 空调的总数


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_tab_air_condition, null);
        initParams();
        return null;
    }

    private void initParams() {
        Bundle bundle = getArguments();
        mPosition = bundle.getInt("position");
        mAirConditionNum = bundle.getInt("airConditionNum");
    }

}
