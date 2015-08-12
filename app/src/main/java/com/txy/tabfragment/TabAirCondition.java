package com.txy.tabfragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.txy.adapter.AirConditionAdapter;
import com.txy.txy_mcs.R;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * 
 */
public class TabAirCondition extends Fragment {

	private View layout;
	private FragmentActivity mActivity;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mActivity = getActivity();
		layout = inflater.inflate(R.layout.frame_aircondition,null);
		initViewPager();
		return layout;
	}



	private void initViewPager() {
		ViewPager mViewPager = (ViewPager) layout.findViewById(R.id.viewpager);
		new AirConditionAdapter(getChildFragmentManager(),4);

	}

}
