package com.txy.fragment;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.txy.adapter.LightGridAdapter;
import com.txy.txy_mcs.R;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 *
 */
public class LightControlFragment extends Fragment {

    private GridView mGridView;
    private ArrayList<Boolean> mLightStatus;// 灯的状态
    private ArrayList<String> mLightName;// 灯的名字
    private LightGridAdapter mLightGridAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_light_control, null);

        initParameter();
        initGridView(layout);
        return layout;
    }

    /**
     * 初始化参数
     */
    private void initParameter() {

    }

    /**
     * 初始化GridView
     * @param layout
     */
    private void initGridView(View layout) {
        mGridView = (GridView) layout.findViewById(R.id.gridView);
        mLightGridAdapter = new LightGridAdapter(getActivity(), mLightStatus, mLightName);
        mGridView.setAdapter(mLightGridAdapter);
    }


}
