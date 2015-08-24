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
public class TVKeyNumFragment extends Fragment implements View.OnClickListener {


    public TVKeyNumFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_tvkey_num, container, false);
        initListener(layout);
        return layout;
    }

    private void initListener(View layout) {

        layout.findViewById(R.id.btn_pd0).setOnClickListener(this);
        layout.findViewById(R.id.btn_pd1).setOnClickListener(this);
        layout.findViewById(R.id.btn_pd2).setOnClickListener(this);
        layout.findViewById(R.id.btn_pd3).setOnClickListener(this);
        layout.findViewById(R.id.btn_pd4).setOnClickListener(this);
        layout.findViewById(R.id.btn_pd5).setOnClickListener(this);
        layout.findViewById(R.id.btn_pd6).setOnClickListener(this);
        layout.findViewById(R.id.btn_pd7).setOnClickListener(this);
        layout.findViewById(R.id.btn_pd8).setOnClickListener(this);
        layout.findViewById(R.id.btn_pd9).setOnClickListener(this);
        layout.findViewById(R.id.btn_tvhk).setOnClickListener(this);
        layout.findViewById(R.id.btn_tvd).setOnClickListener(this);

        layout.findViewById(R.id.imgbtn_tvup).setOnClickListener(this);
        layout.findViewById(R.id.imgbtn_tvdown).setOnClickListener(this);
        layout.findViewById(R.id.imgbtn_voldown).setOnClickListener(this);
        layout.findViewById(R.id.imgbtn_volup).setOnClickListener(this);
        layout.findViewById(R.id.imgbtn_tventer).setOnClickListener(this);


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_pd0:
                break;
            case R.id.btn_pd1:
                break;
            case R.id.btn_pd2:
                break;
            case R.id.btn_pd3:
                break;
            case R.id.btn_pd4:
                break;
            case R.id.btn_pd5:
                break;
            case R.id.btn_pd6:
                break;
            case R.id.btn_pd7:
                break;
            case R.id.btn_pd8:
                break;
            case R.id.btn_pd9:
                break;
            case R.id.btn_tvhk:
                break;
            case R.id.btn_tvd:
                break;
            case R.id.imgbtn_tvup:
                break;
            case R.id.imgbtn_tvdown:
                break;
            case R.id.imgbtn_voldown:
                break;
            case R.id.imgbtn_volup:
                break;
            case R.id.imgbtn_tventer:
                break;
        }
    }
}
