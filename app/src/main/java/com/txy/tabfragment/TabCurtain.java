package com.txy.tabfragment;

import com.txy.SPdata;
import com.txy.adapter.WindowAdapter;
import com.txy.database.BoardRoomDB;
import com.txy.database.httpdata.BoardRoomEntity;
import com.txy.database.httpdata.CurtainEntity;
import com.txy.database.httpdata.MachineCode;
import com.txy.txy_mcs.R;
import com.txy.txy_mcs.R.layout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 */
public class TabCurtain extends Fragment {


	private ListView mListView;
	private WindowAdapter windowAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View layout = inflater.inflate(R.layout.fragment_tab_window, container, false);
		initListView(layout);
		return layout;
	}
	
	/**
	 * @param layout
	 */
	private void initListView(View layout) {
		List<MachineCode> machineCodeList = BoardRoomDB.getMachineCodeList();
		int selectBoardRoomPosition = SPdata.readSelectBoardRoomPosition(getActivity());
		List<CurtainEntity> curtain = BoardRoomDB.getCurtain(machineCodeList.get(selectBoardRoomPosition).getTypeId());
		mListView = (ListView) layout.findViewById(R.id.listView1);
		windowAdapter = new WindowAdapter(getActivity(), curtain);
		mListView.setAdapter(windowAdapter);

	}

}
