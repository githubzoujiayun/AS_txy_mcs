package com.txy.adapter;

import com.txy.txy_mcs.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class WindowAdapter extends BaseAdapter {

    private int mWindowNum = 2;// 控制窗帘的数量
    private Context mContext;

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
        ViewHolder holder = null;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.window_ctr_row, null);
            holder = new ViewHolder();
            holder.textView = (TextView) convertView.findViewById(R.id.textView1);
            holder.windowOpen = (ImageView) convertView.findViewById(R.id.imageButton1);
            holder.windowClose = (ImageView) convertView.findViewById(R.id.imageView1);
            holder.windowPause = (ImageView) convertView.findViewById(R.id.imageView2);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.windowOpen.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });
        holder.windowClose.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });
        holder.windowPause.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });
        return convertView;
    }

    class ViewHolder{
        ImageView windowOpen;
        ImageView windowClose;
        ImageView windowPause;
        TextView  textView;
    }
    /**
     * 设置Window的数量
     * @param mNum
     */
    public void setmNum(int mNum) {
        this.mWindowNum = mNum;
    }

}
