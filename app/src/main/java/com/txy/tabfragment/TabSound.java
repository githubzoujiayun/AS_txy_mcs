package com.txy.tabfragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anupcowkur.wheelmenu.WheelMenu;
import com.txy.txy_mcs.R;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 */
public class TabSound extends Fragment implements WheelMenu.WheelChangeListener {

    private WheelMenu mWheelMenu;

    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View layout = inflater.inflate(R.layout.fragment_tab_sound, container, false);
        initWheelMenu(layout);

		return layout;
	}

    private void initWheelMenu(View layout) {
        mWheelMenu = (WheelMenu) layout.findViewById(R.id.wheelMenu);
        mWheelMenu.setDivCount(6);
        mWheelMenu.setWheelImage(R.drawable.an);
        mWheelMenu.setWheelChangeListener(this);
    }

    @Override
    public void onSelectionChange(int i) {
        
    }
}
