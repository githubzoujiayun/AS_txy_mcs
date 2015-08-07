package com.txy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.txy.txy_mcs.R;

import java.util.ArrayList;

public class MenuListViewAdapter extends BaseAdapter {

    private Context mContext;
    private int mPosition = 0;
    private ArrayList<Integer> mEquipList;
    private int[] mBackGround = new int[]{
            R.drawable.ctrl_mode_off,// 情景设置
            R.drawable.ctrl_win_off,// 窗帘控制
            R.drawable.ctrl_ty_off,// 投影
            R.drawable.ctrl_kg_off,// 空调
            R.drawable.ctrl_tv_off,// 电视
            R.drawable.ctrl_sound_off,// 音响
            R.drawable.ctrl_music_off,// 音乐控制
            R.drawable.ctrl_ppt_off// 同屏
    };

    public MenuListViewAdapter(Context context,ArrayList<Integer> equipList){
        mContext = context;
        mEquipList = equipList;
    }

    @Override
    public int getCount() {
        return mEquipList.size();
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
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = new ViewHolder();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(R.layout.menu_list_row, null);
        viewHolder.groupItem = convertView.findViewById(R.id.itemView);
        viewHolder.selectedItem = (ImageView) convertView.findViewById(R.id.view_selecteditem);

        viewHolder.groupItem.setBackgroundResource(mBackGround[mEquipList.get(position)]);

        if (position == mPosition) {
            viewHolder.selectedItem.setVisibility(View.VISIBLE);
        } else {
            viewHolder.selectedItem.setVisibility(View.INVISIBLE);
        }

        return convertView;

    }

    private class ViewHolder {
        View groupItem;
        ImageView selectedItem;
    }

    public void setPosition(int position) {
        mPosition = position;
    }

}
