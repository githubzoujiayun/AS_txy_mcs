package com.txy.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.txy.constants.Constants;
import com.txy.txy_mcs.R;
import com.txy.udp.InitData.StringMerge;
import com.txy.udp.Sender;
import com.txy.utils.SPUtils;

public class LightGridAdapter extends BaseAdapter{

    private Context mContext;
    private int mLightNum = 0;// 灯光的数量
    private ArrayList<Boolean> mLightStatus = new ArrayList<Boolean>();// 灯的状态
    private ArrayList<String> mLightName;// 灯的名字

    public LightGridAdapter(Context context, ArrayList<Boolean> lightStatus, ArrayList<String> lightName) {
        mContext  = context;
        mLightStatus = lightStatus;
        mLightName = lightName;
    }

    @Override
    public int getCount() {
        return mLightNum;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.gridview_light_item, null);
            holder = new ViewHolder();
            holder.button = (CheckBox) convertView.findViewById(R.id.lightbutton);
            holder.name  =(TextView) convertView.findViewById(R.id.lightname);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.name.setText("灯"+position);

        if (mLightStatus.get(position)) {
            holder.button.setChecked(true);
        } else {
            holder.button.setChecked(false);
        }

        final ViewHolder finalHolder = holder;
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = "";
                if (finalHolder.button.isChecked()) {
                    msg = StringMerge.lightControl(position, true);
                } else {
                    msg = StringMerge.lightControl(position, false);
                }
                String ip = (String) SPUtils.get(mContext, Constants.IP, Constants.DEFAULT_IP);
                int port =(Integer) SPUtils.get(mContext, Constants.SENDPORT, Constants.DEFAULT_SENDPORT);
                new Sender(msg, ip, port).send();
            }
        });

        return convertView;
    }

    public void setmLightNum(int mLightNum) {
        this.mLightNum = mLightNum;
    }

    public void setLightStatus(ArrayList<Boolean> list) {
        mLightStatus.clear();
        mLightStatus.addAll(list);
    }

    class ViewHolder {
        CheckBox button;
        TextView name;
    }

}
