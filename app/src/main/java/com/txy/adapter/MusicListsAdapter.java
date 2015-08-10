package com.txy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.txy.database.DBManager;
import com.txy.database.MyMusic;
import com.txy.txy_mcs.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/8/10.
 */
public class MusicListsAdapter extends BaseAdapter {

    private List<MyMusic> mMusicList = new ArrayList<MyMusic>();
    private Context mContext;
    private int mMode;// 当前的模式

    public MusicListsAdapter(Context context, List<MyMusic> musicList, int mode){
        mContext = context;
        mMode = mode;
        if (musicList != null) {
            mMusicList.addAll(musicList);
        }
    }

    @Override
    public int getCount() {
        return mMusicList.size() > 0 ? mMusicList.size() : 0;
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
    public View getView(final int position, View view, ViewGroup viewGroup) {

        ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.playlist_item, null);
            viewHolder.musicName = (TextView) view.findViewById(R.id.txt_musicname);
            viewHolder.musicTime = (TextView) view.findViewById(R.id.txt_musictime);
            viewHolder.deleteButton = (Button) view.findViewById(R.id.btn_jian);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        MyMusic myMusic = mMusicList.get(position);
        viewHolder.musicName.setText(myMusic.getTitle());

        Date date = new Date(myMusic.getDuration());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        viewHolder.musicTime.setText(simpleDateFormat.format(date));

        viewHolder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMusicList.remove(position);
                MusicListsAdapter.this.notifyDataSetChanged();
                DBManager.removeOneMusic(mMode,mMusicList.get(position).getPath());
            }
        });

        return view;
    }

    class ViewHolder{
        TextView musicName;
        TextView musicTime;
        Button deleteButton;
    }

    public List<MyMusic> getmMusicList() {
        return mMusicList;
    }

    public void setmMusicList(List<MyMusic> mMusicList) {
        this.mMusicList.clear();
        this.mMusicList.addAll(mMusicList);
    }

    public void setmMode(int mMode) {
        this.mMode = mMode;
    }
}

