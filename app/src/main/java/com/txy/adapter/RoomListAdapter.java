package com.txy.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.txy.database.RoomList;
import com.txy.database.httpdata.BoardRoomEntity;
import com.txy.txy_mcs.R;

public class RoomListAdapter extends BaseAdapter {

    private List<BoardRoomEntity> mRoomListData;
    private Context mContext;
    private int mPosition;// 记录下选中的位置

    public RoomListAdapter(Context mcon,List<BoardRoomEntity> listData,int position) {
        mContext = mcon;
        mRoomListData = listData;
        mPosition = position;
    }

    @Override
    public int getCount() {
        return mRoomListData == null ? 0 : mRoomListData.size();
    }

    @Override
    public Object getItem(int position) {
        return mRoomListData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.room_row,null);
            holder = new ViewHolder();
            holder.room_name = (TextView) convertView.findViewById(R.id.txt_roomname);
            holder.selectedItem = (ImageView) convertView.findViewById(R.id.view_selectedroomitem);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.room_name.setText(mRoomListData.get(position).getTypeName());
        if (mPosition == position) {
            holder.selectedItem.setVisibility(View.VISIBLE);
        } else {
            holder.selectedItem.setVisibility(View.INVISIBLE);
        }

        return convertView;
    }

    private class ViewHolder {

        TextView room_name;
        ImageView selectedItem;
    }

    /**
     * 设置位置
     * @param position
     */
    public void setPosition(int position) {
        mPosition = position;
    }

}
