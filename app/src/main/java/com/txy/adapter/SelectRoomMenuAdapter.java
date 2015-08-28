package com.txy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/8/28.
 */
public class SelectRoomMenuAdapter extends BaseAdapter {

    private ArrayList<String> stringArrayList = new ArrayList<>();
    private Context context;
    public SelectRoomMenuAdapter(Context context, ArrayList<String> stringArrayList) {
        this.context = context;
        this.stringArrayList = stringArrayList;
    }

    @Override
    public int getCount() {
        return stringArrayList == null ? 0 : stringArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View layout = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, null);
        TextView textView = (TextView) layout.findViewById(android.R.id.text1);
        textView.setText(stringArrayList.get(i));
        return layout;
    }

}
