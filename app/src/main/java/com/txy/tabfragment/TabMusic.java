package com.txy.tabfragment;

import com.txy.MusicContentProvider.ReadDataFromContentProvider;
import com.txy.adapter.MusicListAdapter;
import com.txy.adapter.PopMenuAdapter;
import com.txy.jsondata.MyMusic;
import com.txy.txy_mcs.R;
import com.txy.util.ToastUtils;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.List;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * 
 */
public class TabMusic extends Fragment implements View.OnClickListener {

    private TextView mAddMusicButton;
    private List<MyMusic> mMusicList;
    private List<MyMusic> mBefore;// 会议前
    private List<MyMusic> mIng;// 会议中
    private List<MyMusic> mRelax;// 休闲
    private List<MyMusic> mSport;// 活动
    private List<MyMusic> mAward;// 颁奖
    private ListView mMusicListView;
    private PopupWindow mPopUpWindow;
    private View layout;
    private MusicListAdapter mAddMusicListAdapter;

    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
        layout = inflater.inflate(R.layout.fragment_tab_music, container, false);
        initUI(layout);
        initListener();
        initListView(layout);
		return layout;
	}

    private void initListView(View layout) {

    }

    private void initListener() {
        mAddMusicButton.setOnClickListener(this);
    }

    private void initUI(View layout) {
        mAddMusicButton = (TextView) layout.findViewById(R.id.txt_readMusic);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.txt_readMusic:
                readMusic();
                break;

            case R.id.okButton:

                break;
            case R.id.cancelButton:
                dismiss();
                break;
        }
    }

    private void readMusic() {

        mMusicList = ReadDataFromContentProvider.readAudio(getActivity());

        if (null == mMusicList || mMusicList.size() == 0) {
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
        mAddMusicListAdapter = new MusicListAdapter(getActivity(), mMusicList);
        mMusicListView.setAdapter(mAddMusicListAdapter);
        mMusicListView.setFocusableInTouchMode(true);
        mMusicListView.setFocusable(true);

        mPopUpWindow = new PopupWindow(view, 100, ViewPager.LayoutParams.WRAP_CONTENT);
        mPopUpWindow = new PopupWindow(view, getActivity().getResources().getDimensionPixelSize(R.dimen.popmenu_width),
                ViewPager.LayoutParams.WRAP_CONTENT);

        // 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景（很神奇的）
        mPopUpWindow.setBackgroundDrawable(new BitmapDrawable());

        showAsDropDown(mAddMusicButton);
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
    }
}
