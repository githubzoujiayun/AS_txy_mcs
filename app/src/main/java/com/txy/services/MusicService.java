package com.txy.services;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

import com.txy.database.MyMusic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MusicService extends Service implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {

    private MediaPlayer mMyMediaPlayer;
    private List<MyMusic> myMusicList = new ArrayList<MyMusic>();// 播放列表
    private int mMode;// 当前的列表
    private boolean isPlaying = false;// 是否在播放
    private int mPosition;// 位置
    private OnPositionChangeListener mOnPositionChangeListener;


    public MusicService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (mMyMediaPlayer == null) {
            mMyMediaPlayer = new MediaPlayer();
        }
        mMyMediaPlayer.setOnCompletionListener(this);
    }

    public void beginMusic(String path){
        mMyMediaPlayer.reset(); // 设置reset状态，处于Idle状态
        try {
            mMyMediaPlayer.setDataSource(path);// 设置哪一首歌曲
            mMyMediaPlayer.prepareAsync();
            mMyMediaPlayer.setOnPreparedListener(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void playMusic(){
        mMyMediaPlayer.start(); // 歌曲开始
    }

    public void startPlay() {
        beginMusic(myMusicList.get(mPosition).getPath());
    }

    public void pauseMusic() {
        mMyMediaPlayer.pause();
    }

    public boolean isPlaying(){
        return mMyMediaPlayer.isPlaying();
    }

    public void nextMusic(){

        int size = myMusicList.size();
        if (mPosition == (size-1)) {
            mPosition = 0;
        } else {
            mPosition += 1;
        }

        startPlay();
    }

    public void preMusic(){

        int size = myMusicList.size();
        if (mPosition == 0) {
            mPosition = (size-1);
        } else {
            mPosition -= 1;
        }

        startPlay();
    }

    public int getDuration(){
        return (int) myMusicList.get(mPosition).getDuration();
    }

    public int getCurrentDuration(){
        return 10;
    }

    public void changProgress(int progress){
        mMyMediaPlayer.seekTo(progress);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new MusicBinder();
    }

    // 歌曲异步准备好了
    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        playMusic();
    }

    // 播放完一首歌
    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        nextMusic();
        mOnPositionChangeListener.onPositionChange(mPosition);
    }

    public class MusicBinder extends Binder {
        public MusicService getMusicService(){
            return MusicService.this;
        }
    }


    public void setMyMusicList(List<MyMusic> myMusicList) {
        this.myMusicList.clear();
        this.myMusicList.addAll(myMusicList);
    }

    public void setPosition(int position){
        mPosition = position;
    }

    public int  getPosition(){
        return mPosition;
    }

    public void setOnPositionChangeListener(OnPositionChangeListener onPositionChangeListener){
        mOnPositionChangeListener = onPositionChangeListener;
    }

    public interface OnPositionChangeListener{
        void onPositionChange(int position);
    }


}
