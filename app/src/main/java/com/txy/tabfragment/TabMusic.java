package com.txy.tabfragment;

import com.txy.MusicContentProvider.ReadDataFromContentProvider;
import com.txy.adapter.MusicListsAdapter;
import com.txy.adapter.MusicQueryListAdapter;
import com.txy.database.DBManager;
import com.txy.database.MyMusic;
import com.txy.services.MusicService;
import com.txy.txy_mcs.R;
import com.txy.util.SPUtils;
import com.txy.util.ToastUtils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * 
 */
public class TabMusic extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener, MusicService.OnPositionChangeListener, SeekBar.OnSeekBarChangeListener {

    private TextView mAddMusicButton;
    private List<MyMusic> mAllMusicList;// 扫描到的音乐
    private List<MyMusic> mBefore = new ArrayList<MyMusic>();// 会议前

    private int mNowMode = 0; // 当前的模式

    private ListView mMusicListView;
    private PopupWindow mPopUpWindow;
    private View layout;
    private MusicQueryListAdapter mAddMusicListAdapter;
    private SpotsDialog mSpotsDialog;
    private ImageButton mBeforeMeetButton;
    private ImageButton mMeetButton;
    private ImageButton mRelaxButton;
    private ImageButton mSportButton;
    private ImageButton mAwardButton;
    private ListView mMusicList;
    private MusicListsAdapter mMusicListAdapter;
    private FragmentActivity mActivity;
    private ServiceConnection conn;
    private MusicService musicService;
    private ImageButton mPreButton;
    private ImageButton mNextButton;
    private ImageButton mPlayButton;
    private TextView mMusicName;
    private TextView mSingerName;
    private SeekBar mSeekBar;

    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
        mActivity = getActivity();
        layout = inflater.inflate(R.layout.fragment_tab_music, container, false);

        initUI(layout);
        initListener();
        initStatus();
        initListView(layout);
		return layout;
	}

    @Override
    public void onStart() {
        super.onStart();
        Intent service = new Intent(mActivity, MusicService.class);
        mActivity.startService(service);
        conn = new MyServicesConnect();
        mActivity.bindService(service,conn, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onStop() {
        super.onStop();
        mActivity.unbindService(conn);
    }

    /**
     * 初始化一些界面的状态
     */
    private void initStatus() {
        mNowMode = (Integer)SPUtils.get(getActivity(), "musicMode", 0);
        changImage(mNowMode);
        mBefore = DBManager.getMusicLists(mNowMode);
    }

    private void initListView(View layout) {

        mMusicList = (ListView) layout.findViewById(R.id.lv_playlist);
        mMusicListAdapter = new MusicListsAdapter(getActivity(), mBefore, mNowMode);
        mMusicList.setAdapter(mMusicListAdapter);

        mMusicList.setOnItemClickListener(this);
    }

    private void initListener() {

        mAddMusicButton.setOnClickListener(this);
        mBeforeMeetButton.setOnClickListener(this);
        mMeetButton.setOnClickListener(this);
        mRelaxButton.setOnClickListener(this);
        mSportButton.setOnClickListener(this);
        mAwardButton.setOnClickListener(this);

        mPreButton.setOnClickListener(this);
        mNextButton.setOnClickListener(this);
        mPlayButton.setOnClickListener(this);

        mSeekBar.setOnSeekBarChangeListener(this);

    }

    private void initUI(View layout) {

        mAddMusicButton = (TextView) layout.findViewById(R.id.txt_readMusic);
        mBeforeMeetButton = (ImageButton) layout.findViewById(R.id.imgBtn_beforeMeet);
        mMeetButton = (ImageButton) layout.findViewById(R.id.imgBtn_Meet);
        mRelaxButton = (ImageButton) layout.findViewById(R.id.imgBtn_rest);
        mSportButton = (ImageButton) layout.findViewById(R.id.imgBtn_Hd);
        mAwardButton = (ImageButton) layout.findViewById(R.id.imgBtn_Bj);

        mPreButton = (ImageButton) layout.findViewById(R.id.imgBtn_pre);
        mNextButton = (ImageButton) layout.findViewById(R.id.imgBtn_next);
        mPlayButton = (ImageButton) layout.findViewById(R.id.imgBtn_play);

        mMusicName = (TextView) layout.findViewById(R.id.txt_currentmusic);
        mSingerName = (TextView) layout.findViewById(R.id.txt_currentmusic);

        mSeekBar = (SeekBar) layout.findViewById(R.id.sb_playseekbar);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.txt_readMusic:
                readMusic();
                break;

            case R.id.okButton:
                addMusic();
                break;
            case R.id.cancelButton:
                dismiss();
                break;

            case R.id.imgBtn_beforeMeet:// 会议前
                mNowMode = 0;
                changImage(mNowMode);
                changeMode();
                break;

            case R.id.imgBtn_Meet:// 会议中
                mNowMode = 1;
                changImage(mNowMode);
                changeMode();
                break;

            case R.id.imgBtn_rest:// 休闲
                mNowMode = 2;
                changImage(mNowMode);
                changeMode();
                break;

            case R.id.imgBtn_Hd:// 活动
                mNowMode = 3;
                changImage(mNowMode);
                changeMode();
                break;

            case R.id.imgBtn_Bj:// 颁奖
                mNowMode = 4;
                changImage(mNowMode);
                changeMode();
                break;

            case R.id.imgBtn_pre:// 上一首
                musicService.preMusic();
                mMusicName.setText(mBefore.get(musicService.getPosition()).getTitle());
                mSingerName.setText(mBefore.get(musicService.getPosition()).getArtist());
                mSeekBar.setMax(musicService.getDuration());
                break;
            case R.id.imgBtn_play:// 播放/暂停

                if (musicService.isPlaying()) {
                    musicService.pauseMusic();
                    mPlayButton.setBackgroundResource(R.drawable.btn_play_off);
                } else {
                    musicService.playMusic();
                    mPlayButton.setBackgroundResource(R.drawable.btn_play_on);
                }

                break;
            case R.id.imgBtn_next:// 下一首
                musicService.nextMusic();
                mMusicName.setText(mBefore.get(musicService.getPosition()).getTitle());
                mSingerName.setText(mBefore.get(musicService.getPosition()).getArtist());
                mSeekBar.setMax(musicService.getDuration());
                break;
        }
    }

    /**
     * 添加音乐到相应的模式
     */
    private void addMusic() {
        List<MyMusic> musicList = mAddMusicListAdapter.getMusicList();
        if (musicList == null) {
            return;
        }

        mBefore.clear();

        mBefore.addAll(musicList);
        int size = musicList.size();
        for (int i = 0;i < size;i++){
            mBefore.get(i).setMode(mNowMode);
        }

        DBManager.saveMusicList(mBefore);

        mMusicListAdapter.setmMusicList(mBefore);
        mMusicListAdapter.notifyDataSetChanged();

        dismiss();
    }

    /**
     * 改变图标
     * @param mode
     */
    public void changImage(int mode){

        SPUtils.put(getActivity(), "musicMode", mode);

        switch (mode) {
            case 0:
                mBeforeMeetButton.setBackgroundResource(R.drawable.btn_beforemeet_on);
                mMeetButton.setBackgroundResource(R.drawable.btn_meeting_off);
                mRelaxButton.setBackgroundResource(R.drawable.btn_rest_off);
                mSportButton.setBackgroundResource(R.drawable.btn_hd_off);
                mAwardButton.setBackgroundResource(R.drawable.btn_prize_off);
                break;
            case 1:
                mBeforeMeetButton.setBackgroundResource(R.drawable.btn_beforemeet_off);
                mMeetButton.setBackgroundResource(R.drawable.btn_meeting_on);
                mRelaxButton.setBackgroundResource(R.drawable.btn_rest_off);
                mSportButton.setBackgroundResource(R.drawable.btn_hd_off);
                mAwardButton.setBackgroundResource(R.drawable.btn_prize_off);
                break;
            case 2:
                mBeforeMeetButton.setBackgroundResource(R.drawable.btn_beforemeet_off);
                mMeetButton.setBackgroundResource(R.drawable.btn_meeting_off);
                mRelaxButton.setBackgroundResource(R.drawable.btn_rest_on);
                mSportButton.setBackgroundResource(R.drawable.btn_hd_off);
                mAwardButton.setBackgroundResource(R.drawable.btn_prize_off);
                break;
            case 3:
                mBeforeMeetButton.setBackgroundResource(R.drawable.btn_beforemeet_off);
                mMeetButton.setBackgroundResource(R.drawable.btn_meeting_off);
                mRelaxButton.setBackgroundResource(R.drawable.btn_rest_off);
                mSportButton.setBackgroundResource(R.drawable.btn_hd_on);
                mAwardButton.setBackgroundResource(R.drawable.btn_prize_off);
                break;
            case 4:
                mBeforeMeetButton.setBackgroundResource(R.drawable.btn_beforemeet_off);
                mMeetButton.setBackgroundResource(R.drawable.btn_meeting_off);
                mRelaxButton.setBackgroundResource(R.drawable.btn_rest_off);
                mSportButton.setBackgroundResource(R.drawable.btn_hd_off);
                mAwardButton.setBackgroundResource(R.drawable.btn_prize_on);
                break;
        }


    }

    /**
     * 改变模式
     */
    private void changeMode(){
        List<MyMusic> musicLists = DBManager.getMusicLists(mNowMode);
        mBefore.clear();
        mBefore.addAll(musicLists);

        mMusicListAdapter.setmMusicList(mBefore);
        mMusicListAdapter.setmMode(mNowMode);
        mMusicListAdapter.notifyDataSetChanged();

        musicService.setMyMusicList(mBefore);// 更新服务里面的播放列表
    }

    /**
     * 扫描音乐
     */
    private void readMusic() {

        if (mSpotsDialog == null) {
            mSpotsDialog = new SpotsDialog(getActivity(), R.style.Custom);
            mSpotsDialog.show();
        }

        mAllMusicList = ReadDataFromContentProvider.readAudio(getActivity());
        if (null == mAllMusicList || mAllMusicList.size() == 0) {
            disMissSpotsDialog();
            ToastUtils.showShort(getActivity(), "没有扫描到音频文件!");
            return;
        }

        show();

    }

    private void show() {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.music_pop_list, null);

        mMusicListView = (ListView) view.findViewById(R.id.listView);
        view.findViewById(R.id.okButton).setOnClickListener(this);
        view.findViewById(R.id.cancelButton).setOnClickListener(this);
        mAddMusicListAdapter = new MusicQueryListAdapter(getActivity(), mAllMusicList);
        mMusicListView.setAdapter(mAddMusicListAdapter);
        mMusicListView.setFocusableInTouchMode(true);
        mMusicListView.setFocusable(true);

        if (mPopUpWindow == null) {
            mPopUpWindow = new PopupWindow(view, getActivity().getResources().getDimensionPixelSize(R.dimen.popmenu_width),
                    ViewPager.LayoutParams.WRAP_CONTENT);

            // 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景（很神奇的）
            mPopUpWindow.setBackgroundDrawable(new BitmapDrawable());

            disMissSpotsDialog();
            showAsDropDown(mAddMusicButton);
        }

    }

    public void showAsDropDown(View parent) {
        mPopUpWindow.showAtLocation(parent,
                10,
                // 保证尺寸是根据屏幕像素密度来的
                getActivity().getResources().getDimensionPixelSize(R.dimen.popmenu_yoff),
                getActivity().getResources().getDimensionPixelSize(R.dimen.popmenu_yoff));

        // 使其聚集
        mPopUpWindow.setFocusable(true);
        // 设置允许在外点击消失
        mPopUpWindow.setOutsideTouchable(true);
    }

    // 隐藏菜单
    public void dismiss() {
        mPopUpWindow.dismiss();
        mAddMusicListAdapter.clearMusicList();
        mPopUpWindow = null;
    }

    public void disMissSpotsDialog(){
        mSpotsDialog.dismiss();
        mSpotsDialog = null;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        musicService.setPosition(i);
        musicService.startPlay();
        mPlayButton.setBackgroundResource(R.drawable.btn_play_on);
        mMusicName.setText(mBefore.get(i).getTitle());
        mSingerName.setText(mBefore.get(i).getArtist());
    }

    @Override
    public void onPositionChange(int position) {
        mSeekBar.setMax(musicService.getDuration());
        mMusicName.setText(mBefore.get(musicService.getPosition()).getTitle());
        mSingerName.setText(mBefore.get(musicService.getPosition()).getArtist());
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        musicService.changProgress(i);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    class MyServicesConnect implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            MusicService.MusicBinder musicBinder = (MusicService.MusicBinder) iBinder;
            musicService = musicBinder.getMusicService();
            musicService.setMyMusicList(mBefore);
            musicService.setOnPositionChangeListener(TabMusic.this);

            if (musicService.isPlaying()) {
                mPlayButton.setBackgroundResource(R.drawable.btn_play_on);
//                mSeekBar.setMax(musicService.getDuration());
//                int currentDuration = musicService.getCurrentDuration();
//                if (currentDuration > 0) {
//                    mSeekBar.setProgress(currentDuration);
//                }
            }

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    }
}
