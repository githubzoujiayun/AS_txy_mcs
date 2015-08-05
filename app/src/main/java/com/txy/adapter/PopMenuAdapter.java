package com.txy.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.txy.txy_mcs.R;

public class PopMenuAdapter extends BaseAdapter {

    ArrayList<Integer> itemList;
    Context mcon;

    public PopMenuAdapter(Context mcon,ArrayList<Integer> itemList){
        this.mcon=mcon;
        this.itemList=itemList;
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewholder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mcon).inflate(
                    R.layout.showpoplist, null);
            viewholder = new ViewHolder();
            convertView.setTag(viewholder);

            viewholder.groupItem = (ImageView) convertView
                    .findViewById(R.id.itemView);

        } else {
            viewholder = (ViewHolder) convertView.getTag();
        }

        viewholder.groupItem.setBackgroundResource( itemList.get(position));

        return convertView;
    }

    private final class ViewHolder {
        ImageView groupItem;
    }
}