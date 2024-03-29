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
    private OnStartMusicListener mOnStartMusicListener;
    private boolean mComplete;// 播放是否完成

    private boolean mFirstTime = true;// 第一次播放
    private boolean mSetProgressMax;


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

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopMediaPlayer();
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

    public void stopMediaPlayer(){
        if (mMyMediaPlayer != null){
            mMyMediaPlayer.stop();
            mMyMediaPlayer.release();
            mMyMediaPlayer = null;
        }
    }

    public void playMusic(){
        mMyMediaPlayer.start(); // 歌曲开始
        mOnStartMusicListener.onStartMusic(true);
    }

    public void startPlay() {
        beginMusic(myMusicList.get(mPosition).getPath());
    }

    public void pauseMusic() {
        mMyMediaPlayer.pause();
        mOnStartMusicListener.onStartMusic(true);
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
        return mMyMediaPlayer.getCurrentPosition();
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
        mSetProgressMax = true;
        mFirstTime = false;
    }

    // 播放完一首歌
    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        mComplete = true;
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

    public List<MyMusic> getMyMusicList() {
        return this.myMusicList;
    }

    public MediaPlayer getMusicPlayer() {
        return mMyMediaPlayer;
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

    public interface OnStartMusicListener {
        void onStartMusic(boolean start);
    }

    public void setOnStartMusicListener(OnStartMusicListener onStartMusicListener) {
        mOnStartMusicListener = onStartMusicListener;
    }

    public boolean isComplete() {
        return mComplete;
    }

    public boolean ismFirstTime() {
        return mFirstTime;
    }

    public boolean canSetProgressMax() {
        return mSetProgressMax;
    }

    public void setProgressMaxFalse() {
        mSetProgressMax = false;
    }

    public void setProgressMaxTrue() {
        mSetProgressMax = true;
    }
}
