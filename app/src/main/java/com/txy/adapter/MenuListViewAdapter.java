package com.txy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.txy.txy_mcs.R;

public class MenuListViewAdapter extends BaseAdapter {

    private Context mContext;
    private int mPosition = 0;
    private int[] mBackGround = new int[]{
            R.drawable.ctrl_mode_off,
            R.drawable.ctrl_win_off,
            R.drawable.ctrl_ty_off,
            R.drawable.ctrl_kg_off,
            R.drawable.ctrl_music_off,
            R.drawable.ctrl_ppt_off,
            R.drawable.ctrl_tv_off,
            R.drawable.ctrl_sound_off};

    public MenuListViewAdapter(Context context){
        mContext = context;
    }

    @Override
    public int getCount() {
        return mBackGround.length;
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
        ViewHolder viewHolder = null;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.menu_list_row, null);
            viewHolder = new ViewHolder();
            viewHolder.groupItem = convertView.findViewById(R.id.itemView);
            viewHolder.selecteditem = (ImageView) convertView.findViewById(R.id.view_selecteditem);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.groupItem.setBackgroundResource(mBackGround[position]);
        if (position == mPosition) {
            viewHolder.selecteditem.setVisibility(View.VISIBLE);
        } else {
            viewHolder.selecteditem.setVisibility(View.INVISIBLE);
        }

        return convertView;
    }

    private class ViewHolder {
        View groupItem;
        ImageView selecteditem;
    }

    public void setPosition(int position) {
        mPosition = position;
    }

}
