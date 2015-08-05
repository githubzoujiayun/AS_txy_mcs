package com.txy.tabfragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.txy.txy_mcs.R;
import com.txy.view.WheelMenu;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * 
 */
public class TabSound extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View layout = inflater.inflate(R.layout.fragment_tab_sound, container, false);
		WheelMenu mWheelMenu = (WheelMenu) layout.findViewById(R.id.wheelMenu);
		mWheelMenu.setDivCount(285);
		mWheelMenu.setWheelImage(R.drawable.an);
		return layout;
	}

}
