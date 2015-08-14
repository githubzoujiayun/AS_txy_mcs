package com.txy.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.txy.txy_mcs.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TVKeyNumFragment extends Fragment {


    public TVKeyNumFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tvkey_num, container, false);
    }


}
