package com.txy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.txy.txy_mcs.R;

/**
 * Created by Administrator on 2015/10/22.
 */
public class TvChannelAdapter extends BaseAdapter {

    private final Context mContext;

    @Override
    public int getCount() {
        return 15;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public TvChannelAdapter(Context context) {
        mContext = context;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_tv_channal, null);
        return inflate;
    }
}
