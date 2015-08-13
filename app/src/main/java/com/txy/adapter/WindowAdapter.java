package com.txy.adapter;

import com.txy.constants.Constants;
import com.txy.txy_mcs.R;
import com.txy.udp.InitData.StringMerge;
import com.txy.udp.InitData.UdpSend;
import com.txy.udp.Sender;
import com.txy.utils.SPUtils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;

public class WindowAdapter extends BaseAdapter {

    private int mWindowNum = 4;// 控制窗帘的数量
    private Context mContext;
    private HashMap<Integer, Boolean> isCheckedMap = new HashMap<Integer, Boolean>();

    public WindowAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return mWindowNum;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressWarnings("null")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View layout = LayoutInflater.from(mContext).inflate(R.layout.window_ctr_row, null);
        final ViewHolder holder = new ViewHolder();
        holder.textView = (TextView) layout.findViewById(R.id.textView1);
        holder.windowOpen = (ImageView) layout.findViewById(R.id.openWindow);
        holder.windowClose = (ImageView) layout.findViewById(R.id.closeWindow);
        holder.windowPause = (ImageView) layout.findViewById(R.id.pauseWindow);


        holder.windowOpen.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String channel = String.valueOf(position);
                String msg = StringMerge.curtainControl(channel, UdpSend.CURTAIN.OPEN);
                String ip = (String) SPUtils.get(mContext, Constants.IP, Constants.DEFAULT_IP);
                int port =(Integer) SPUtils.get(mContext, Constants.SENDPORT, Constants.DEFAULT_SENDPORT);
                new Sender(msg, ip, port).send();
            }
        });

        holder.windowClose.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String channel = String.valueOf(position);
                String msg = StringMerge.curtainControl(channel, UdpSend.CURTAIN.CLOSE);
                String ip = (String) SPUtils.get(mContext, Constants.IP, Constants.DEFAULT_IP);
                int port =(Integer) SPUtils.get(mContext, Constants.SENDPORT, Constants.DEFAULT_SENDPORT);
                new Sender(msg, ip, port).send();
            }
        });

        holder.windowPause.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String channel = String.valueOf(position);
                String msg = StringMerge.curtainControl(channel, UdpSend.CURTAIN.PAUSE);
                String ip = (String) SPUtils.get(mContext, Constants.IP, Constants.DEFAULT_IP);
                int port =(Integer) SPUtils.get(mContext, Constants.SENDPORT, Constants.DEFAULT_SENDPORT);
                new Sender(msg, ip, port).send();
            }
        });
        holder.textView.setText("窗帘");

        return layout;
    }

    final class ViewHolder{
        ImageView windowOpen;
        ImageView windowClose;
        ImageView windowPause;
        TextView  textView;
    }
    /**
     * 设置Window的数量
     * @param mNum
     */
    public void setNum(int mNum) {
        this.mWindowNum = mNum;
    }

}
