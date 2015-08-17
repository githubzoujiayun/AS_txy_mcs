package com.txy.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.txy.txy_mcs.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TVChannelFragment extends Fragment {


    private GridView mChannelGridView;

    public TVChannelFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_tv_channel, container, false);
        initGridView(layout);
        return layout;
    }

    private void initGridView(View layout) {
        mChannelGridView = (GridView) layout.findViewById(R.id.channelGridView);
    }


}
