package com.txy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.txy.database.MyMusic;
import com.txy.txy_mcs.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/8/7.
 */
public class MusicQueryListAdapter extends BaseAdapter {

    private Context mContext;
    private List<MyMusic> mAllMusicList;
    private List<MyMusic> mMusicList = new ArrayList<MyMusic>();// 被勾选上的音乐列表

    public MusicQueryListAdapter(Context context, List<MyMusic> musicList){
        mContext = context;
        mAllMusicList = musicList;
    }

    @Override
    public int getCount() {
        return mAllMusicList == null ? 0 : mAllMusicList.size();
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
        ViewHolder viewHolder = new ViewHolder();
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.add_music_list, null);
        viewHolder.musicId = (TextView) inflate.findViewById(R.id.music_id);
        viewHolder.musicName = (TextView) inflate.findViewById(R.id.music_name);
        viewHolder.singerName = (TextView) inflate.findViewById(R.id.singer_name);
        viewHolder.checkBox = (CheckBox) inflate.findViewById(R.id.checkBox);


        MyMusic myMusic = mAllMusicList.get(position);
        viewHolder.musicName.setText(myMusic.getTitle());
        viewHolder.singerName.setText(myMusic.getArtist());
        viewHolder.musicId.setText(1+position+"、");

        viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked){
                    mMusicList.add(mAllMusicList.get(position));
                } else {
                    mMusicList.remove(mAllMusicList.get(position));
                }
            }
        });

        return inflate;
    }

    class ViewHolder {
        TextView musicId;
        TextView musicName;
        TextView singerName;
        CheckBox checkBox;
    }

    public List<MyMusic> getMusicList(){
        return mMusicList;
    }

    public void clearMusicList(){
        mMusicList.clear();
    }
}
