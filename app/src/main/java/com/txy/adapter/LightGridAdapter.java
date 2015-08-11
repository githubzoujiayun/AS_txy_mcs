package com.txy.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.txy.txy_mcs.R;

public class LightGridAdapter extends BaseAdapter{

    private Context mContext;
    private int mLightNum = 4;// 灯光的数量
    private ArrayList<Boolean> mLightStatus;// 灯的状态
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
    public View getView(int position, View convertView, ViewGroup parent) {
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
//        if (mLightStatus.size() > 0) {
//            holder.button.setChecked(mLightStatus.get(position));
//        }
//
//        if(mLightName.size() > 0) {
//            holder.name.setText(mLightName.get(position));
//        }

        return convertView;
    }

    public void setmLightNum(int mLightNum) {
        this.mLightNum = mLightNum;
    }

    class ViewHolder {
        CheckBox button;
        TextView name;
    }

}
