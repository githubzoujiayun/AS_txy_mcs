package com.txy.tools;
 

import java.util.ArrayList;
import java.util.List;

import com.txy.adapter.AreaMenuAdapter;
import com.txy.database.httpdata.BoardRoomEntity;
import com.txy.database.httpdata.MachineCode;
import com.txy.txy_mcs.R;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.view.ViewPager.LayoutParams;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.PopupWindow;

public class AreaMenu {
	private ArrayList<String> itemList;
	private Context context;
	private PopupWindow popupWindow;
	private ListView listView;

	public AreaMenu(Context context) {
		this.context = context;
		itemList = new ArrayList<String>();

		View view = LayoutInflater.from(context)
				.inflate(R.layout.room_list_menu_pop, null);

		// 设置 listview
		listView = (ListView) view.findViewById(R.id.menu_listview);
		listView.setAdapter(new AreaMenuAdapter(context,itemList));
		listView.setFocusableInTouchMode(true);
		listView.setFocusable(true);/**/

		popupWindow = new PopupWindow(view, 100, LayoutParams.WRAP_CONTENT);
		popupWindow = new PopupWindow(view, context.getResources()
				.getDimensionPixelSize(R.dimen.popmenu_width),
				LayoutParams.WRAP_CONTENT);/**/

		// 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景（很神奇的）
		 popupWindow.setBackgroundDrawable(new BitmapDrawable());
	}

	// 设置菜单项点击监听器
	public void setOnItemClickListener(OnItemClickListener listener) {
		listView.setOnItemClickListener(listener);
	}

	// 批量添加菜单项
	public void addItems(String[] items) {
		for (String s : items) {
			itemList.add(s);
		}
	}

	public void addItemList(List<MachineCode> boardRoomEntityList) {
		for (int i = 0 ;i < boardRoomEntityList.size(); i++) {
			itemList.add(boardRoomEntityList.get(i).getBoardRoomName());
		}
	}

	// 单个添加菜单项
	public void addItem(String item) {
		itemList.add(item);
	}
	
	public void remove(){
		itemList.removeAll(itemList);
	}

	// 下拉式 弹出 pop菜单 parent 右下角
	public void showAsDropDown(View parent) {
		popupWindow.showAsDropDown(parent,
				10,
				// 保证尺寸是根据屏幕像素密度来的
				context.getResources().getDimensionPixelSize(
						R.dimen.popmenu_yoff));

		// 使其聚集
		popupWindow.setFocusable(true);
		// 设置允许在外点击消失
		popupWindow.setOutsideTouchable(true);
		// 刷新状态
		popupWindow.update();
	}

	// 隐藏菜单
	public void dismiss() {
		popupWindow.dismiss();
	}
}