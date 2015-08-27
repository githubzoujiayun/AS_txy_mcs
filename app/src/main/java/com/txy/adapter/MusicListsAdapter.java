package com.txy.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.txy.SPdata;
import com.txy.database.DBManager;
import com.txy.database.MyMusic;
import com.txy.txy_mcs.R;
import com.txy.utils.SPUtils;

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
    private ImageButton mOkButton;
    private ImageButton mCancelButton;
    private AlertDialog mDialog;
    private int mPressPosition = -1;// 被选中歌曲的位置

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
            viewHolder.deleteButton = (ImageButton) view.findViewById(R.id.btn_jian);
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
                showDialog(position);

            }
        });

        if (changeTextColor()) {
            if (mPressPosition == position) {
                viewHolder.musicName.setTextColor(mContext.getResources().getColor(R.color.blue));
                viewHolder.musicTime.setTextColor(mContext.getResources().getColor(R.color.blue));
            } else {
                viewHolder.musicName.setTextColor(mContext.getResources().getColor(R.color.black));
                viewHolder.musicTime.setTextColor(mContext.getResources().getColor(R.color.black));
            }
        } else {
            viewHolder.musicName.setTextColor(mContext.getResources().getColor(R.color.black));
            viewHolder.musicTime.setTextColor(mContext.getResources().getColor(R.color.black));
        }


        return view;
    }

    class ViewHolder{
        TextView musicName;
        TextView musicTime;
        ImageButton deleteButton;
    }

    public List<MyMusic> getmMusicList() {
        return mMusicList;
    }

    public void setMusicList(List<MyMusic> mMusicList) {
        this.mMusicList.clear();
        this.mMusicList.addAll(mMusicList);
    }

    public void setMode(int mMode) {
        this.mMode = mMode;
    }

    /**
     * 弹出自定义对话框
     */
    private void showDialog(final int position) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        RelativeLayout layout = (RelativeLayout) inflater.inflate(
                R.layout.music_delete_dialog, null);
        mOkButton = (ImageButton) layout.findViewById(R.id.btn_powerdownok);
        mCancelButton = (ImageButton) layout
                .findViewById(R.id.btn_powerdowncancel);

        mDialog = new AlertDialog.Builder(mContext).create();
        mDialog.setCancelable(false);
        mDialog.show();
        mDialog.getWindow().setContentView(layout);
        mOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBManager.removeOneMusic(mMode,mMusicList.get(position).getPath());
                mMusicList.remove(position);
                MusicListsAdapter.this.notifyDataSetChanged();
                mDialog.dismiss();
            }
        });
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
            }
        });
    }

    public void setPressPosition(int position) {
        mPressPosition = position;
    }

    public boolean changeTextColor(){
        if (SPdata.readMusicMode(mContext) == mMode) {
            return true;
        }
        return false;
    }
}

