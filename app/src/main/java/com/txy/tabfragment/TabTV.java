package com.txy.tabfragment;

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

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * 
 */
public class TabTV extends Fragment {


    public TabTV() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_tab_tv, null);
        initUI(layout);
        return layout;
	}

    private void initUI(View layout) {
    }

    /**
     * 替换片段
     * @param fragment
     */
    private void replaceFragment(Fragment fragment) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.framelayout, fragment);
        ft.commit();
    }
}
