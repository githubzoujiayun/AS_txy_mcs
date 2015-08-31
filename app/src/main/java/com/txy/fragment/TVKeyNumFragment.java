package com.txy.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.txy.constants.Constants;
import com.txy.txy_mcs.R;
import com.txy.udp.InitData.StringMerge;
import com.txy.udp.InitData.UdpSend;
import com.txy.udp.Sender;
import com.txy.utils.BytesUtils;
import com.txy.utils.SPUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class TVKeyNumFragment extends Fragment implements View.OnClickListener {

    private String mNowChannel = UdpSend.TV.FIRST_CHANNEL;

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
                changeChannelPlus(0);
                break;
            case R.id.btn_pd1:
                changeChannelPlus(1);
                break;
            case R.id.btn_pd2:
                changeChannelPlus(2);
                break;
            case R.id.btn_pd3:
                changeChannelPlus(3);
                break;
            case R.id.btn_pd4:
                changeChannelPlus(4);
                break;
            case R.id.btn_pd5:
                changeChannelPlus(5);
                break;
            case R.id.btn_pd6:
                changeChannelPlus(6);
                break;
            case R.id.btn_pd7:
                changeChannelPlus(7);
                break;
            case R.id.btn_pd8:
                changeChannelPlus(8);
                break;
            case R.id.btn_pd9:
                changeChannelPlus(9);
                break;
            case R.id.btn_tvhk:
                send(0,UdpSend.TV.BACK);
                break;
            case R.id.btn_tvd:
                send(0,UdpSend.TV.BUTTON);// TODO
                break;
            case R.id.imgbtn_tvup:
                send(0,UdpSend.TV.CHANNEL_PLUS);
                break;
            case R.id.imgbtn_tvdown:
                send(0,UdpSend.TV.CHANNEL_DESC);
                break;
            case R.id.imgbtn_voldown:
                send(0,UdpSend.TV.VOL_DESC);
                break;
            case R.id.imgbtn_volup:
                send(0,UdpSend.TV.VOL_PLUS);
                break;
            case R.id.imgbtn_tventer:
                send(0,UdpSend.TV.OK_BUTTON);
                break;
        }
    }

    /**
     * 发送指令
     */
    private void send(int position,String orderCode) {
        String s = null;
        if (position < 10) {
            s = "0"+String.valueOf(position);
        } else {
            s = String.valueOf(position);
        }
        String msg = StringMerge.infrafedControl(getActivity(), UdpSend.TV.TV, s, orderCode);
        String ip = (String) SPUtils.get(getActivity(), Constants.IP, Constants.DEFAULT_IP);
        int port =(Integer) SPUtils.get(getActivity(), Constants.SENDPORT, Constants.DEFAULT_SENDPORT);
        new Sender(msg, ip,port).send();
    }

    private void changeChannelPlus(int position) {
        byte[] bytes = BytesUtils.hexStringToBytes(mNowChannel);
        bytes[0] += position;
        mNowChannel = BytesUtils.bytesToHexString(bytes);
        send(1, mNowChannel);
    }

}
