package com.txy.fragment;

import com.txy.txy_mcs.R;
import com.txy.txy_mcs.R.layout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * 
 */
public class SetProjectionTimeFragment extends Fragment {

	public SetProjectionTimeFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_set_projection_time,
				container, false);
	}

}
