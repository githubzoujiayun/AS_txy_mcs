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

public class SetCheckBoxAdapter extends BaseAdapter{

    private Context mContext;
    private int mNum;// 数量
    private ArrayList<Boolean> mStatus;// 状态
    private ArrayList<Boolean> mNowStatus = new ArrayList<Boolean>();// 现在的状态，保存数据库的时候用
    private ArrayList<String> mName;// 名字

    public SetCheckBoxAdapter(Context context, ArrayList<Boolean> Status, ArrayList<String> Name) {
        mContext  = context;
        mStatus = Status;
        mName = Name;
    }

    @Override
    public int getCount() {
        return mNum;
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
            convertView = inflater.inflate(R.layout.modesetcheckbox_row, null);
            holder = new ViewHolder();
            holder.button = (CheckBox) convertView.findViewById(R.id.cb_setitem);
            holder.name  =(TextView) convertView.findViewById(R.id.textView1);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // 记录下checkbox的状态
        if (holder.button.isChecked()){
            mNowStatus.add(true);
        }else {
            mNowStatus.add(false);
        }

        if (mStatus != null){
            holder.button.setChecked(mStatus.get(position));
        }

        if (mName == null){
            holder.name.setText("灯光"+position+1);
        } else {
            holder.name.setText(mName.get(position));
        }

        return convertView;
    }

    public void setNum(String num) {
        this.mNum = Integer.parseInt(num);
    }

    /**
     * 返回当前checkbox的状态
     * @return
     */
    public ArrayList<Boolean> getmStatus() {
        mNowStatus.clear();// 记录前先清零
        this.notifyDataSetChanged();
        return mNowStatus;
    }

    public ArrayList<String> getmName() {
        return mName;
    }

    class ViewHolder {
        CheckBox button;
        TextView name;
    }

}
