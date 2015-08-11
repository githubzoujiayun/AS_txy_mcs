package com.txy.adapter;

import com.txy.txy_mcs.R;

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
        initCheck();
    }

    private void initCheck() {

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
        holder.windowOpen = (CheckBox) layout.findViewById(R.id.openWindow);
        holder.windowClose = (CheckBox) layout.findViewById(R.id.closeWindow);
        holder.windowPause = (CheckBox) layout.findViewById(R.id.pauseWindow);

        holder.textView.setText("窗帘");

        holder.windowOpen.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                if (isChecked) {
                    holder.windowClose.setChecked(false);
                    holder.windowPause.setChecked(false);
                }
            }
        });

        holder.windowPause.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    holder.windowClose.setChecked(false);
                    holder.windowOpen.setChecked(false);
                }
            }
        });

        holder.windowClose.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    holder.windowPause.setChecked(false);
                    holder.windowOpen.setChecked(false);
                }
            }
        });
        return layout;
    }

    final class ViewHolder{
        CheckBox windowOpen;
        CheckBox windowClose;
        CheckBox windowPause;
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
