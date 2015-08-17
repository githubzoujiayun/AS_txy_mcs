package com.txy.tabfragment;

import com.txy.fragment.TVChannelFragment;
import com.txy.fragment.TVKeyNumFragment;
import com.txy.txy_mcs.R;
import com.txy.txy_mcs.R.layout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * 
 */
public class TabTV extends Fragment implements View.OnClickListener {


    private ImageButton mPowerButton;
    private ImageButton mSourceButton;
    private ImageButton mMenuButton;
    private ImageButton mModeButton;

    private boolean isChannel;

    public TabTV() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_tab_tv, null);
        initUI(layout);
        initListener();
        replaceFragment(new TVKeyNumFragment());
        return layout;
	}

    private void initListener() {
        mPowerButton.setOnClickListener(this);
        mSourceButton.setOnClickListener(this);
        mMenuButton.setOnClickListener(this);
        mModeButton.setOnClickListener(this);
    }

    private void initUI(View layout) {
        mPowerButton = (ImageButton) layout.findViewById(R.id.btn_tvpower);
        mSourceButton = (ImageButton) layout.findViewById(R.id.btn_tvsource);
        mMenuButton = (ImageButton) layout.findViewById(R.id.btn_tvmenu);
        mModeButton = (ImageButton) layout.findViewById(R.id.btn_tvmode);
    }

    /**
     * 替换片段
     * @param fragment
     */
    private void replaceFragment(Fragment fragment) {
        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.relativeLayout1, fragment);
        ft.commit();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_tvmode:
                isChannel = !isChannel;
                if (isChannel) {
                    replaceFragment(new TVChannelFragment());
                } else {
                    replaceFragment(new TVKeyNumFragment());
                }
                break;
        }
    }
}
