package com.txy.tabfragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.txy.adapter.SituationPagerAdapter;
import com.txy.txy_mcs.R;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * 
 */
public class TabSituation extends Fragment {

	private View layout;
	private SituationPagerAdapter mMyPagerAdapter;
	private ViewPager mViewPager;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (layout == null) {
			layout = inflater.inflate(R.layout.fragment_situation, container, false);
			initPager();
		}
		return layout;
	}

	private void initPager() {
		mViewPager = (ViewPager) layout.findViewById(R.id.viewpager);
		mMyPagerAdapter = new SituationPagerAdapter(getChildFragmentManager());
		mViewPager.setAdapter(mMyPagerAdapter);
		
	}
	
	@Override
	public void onDestroyView() {
		super.onDestroyView();
		ViewGroup parent = (ViewGroup) layout.getParent();
		parent.removeView(layout);
	}

}
