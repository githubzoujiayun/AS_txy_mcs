package com.txy.activity;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.txy.SPdata;
import com.txy.adapter.MenuListViewAdapter;
import com.txy.adapter.PopMenuAdapter;
import com.txy.adapter.SelectRoomMenuAdapter;
import com.txy.application.MyApplication;
import com.txy.constants.Constants;
import com.txy.database.BoardRoomDB;
import com.txy.database.RoomList;
import com.txy.database.httpdata.AirEntity;
import com.txy.database.httpdata.BoardRoomEntity;
import com.txy.database.httpdata.CurtainEntity;
import com.txy.database.httpdata.MachineCode;
import com.txy.database.httpdata.ModelEntity;
import com.txy.database.httpdata.ProjectorEntity;
import com.txy.database.httpdata.TvEntity;
import com.txy.fragment.AboutUsFragment;
import com.txy.tools.AreaMenu;
import com.txy.udp.InitData.StringMerge;
import com.txy.udp.InitData.UdpSend;
import com.txy.services.ReceiverService;
import com.txy.services.ReceiverService.MyBinder;
import com.txy.services.ReceiverService.OnReceiveSuccessListener;
import com.txy.tabfragment.TabAirCondition;
import com.txy.tabfragment.TabMusic;
import com.txy.tabfragment.TabProjector;
import com.txy.tabfragment.TabScreen;
import com.txy.tabfragment.TabSituation;
import com.txy.tabfragment.TabSound;
import com.txy.tabfragment.TabTV;
import com.txy.tabfragment.TabCurtain;
import com.txy.tools.PopMenu;
import com.txy.txy_mcs.R;
import com.txy.udp.Sender;
import com.txy.utils.SPUtils;
import com.umeng.update.UmengUpdateAgent;

import java.util.ArrayList;
import java.util.List;


public class IndexActivity extends FragmentActivity implements OnClickListener,
        OnItemClickListener {

    private TextView mTVHeader;
    private ImageButton mIbHome;
    private ImageButton mIbDevice;
    private ImageButton mIbSetting;
    private ImageView mImAddButton;
    private ListView mMenuListView;
    private MenuListViewAdapter mMenuListViewAdapter;
    private int mNowPosition = 1;// 当前的控制模式
    private PopMenu mSetMenu;
    private ReceiverService receiveService;
    private ServiceConnection conn = new MyServiceConnection();
    private ArrayList<Integer> mEquipList; // 当前房间拥有的设备

    private ArrayList<RoomList> mRoomList = new ArrayList<RoomList>();// 所有房间的的信息
    private TextView mTextModeSheet;
//    private List<BoardRoomEntity> mBoardRoomList;
    private PopupWindow mRoomPopupWindow;
    private AreaMenu areaMenu;
    private List<MachineCode> machineCodeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        UmengUpdateAgent.update(this);
        initUI();
        initListener();
        initParameter(0);
        initListView();
        initFragment();
    }

    @Override
    protected void onStart() {
        super.onStart();

        Intent service = new Intent(this,ReceiverService.class);
        startService(service);
        bindService(service, conn, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(conn);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        receiveService.stopTask();// 界面关闭停止接收
    }

    /**
     * 设置默认选择的Fragment
     */
    private void initFragment() {
        if (machineCodeList == null || machineCodeList.size() == 0) {
            return;
        }
        tabMenu(0);
    }

    /**
     * 初始化参数
     */
    private void initParameter(int position) {
        mEquipList = new ArrayList<Integer>();
        machineCodeList = BoardRoomDB.getMachineCodeList();
        if (machineCodeList == null || machineCodeList.size() == 0) {
            return;
        }



        int i = SPdata.readSelectBoardRoomPosition(this);


        int size = machineCodeList.size();
        BoardRoomEntity oneBoardRoom;
        MachineCode machineCode;
        if (size < i) {
            machineCode = machineCodeList.get(position);
            SPdata.writeSendIp(this, machineCode.getIp());
            oneBoardRoom = BoardRoomDB.getOneBoardRoom(machineCode.getTypeId());

        } else {
            machineCode = machineCodeList.get(i);
            SPdata.writeSendIp(this, machineCode.getIp());
            oneBoardRoom = BoardRoomDB.getOneBoardRoom(machineCode.getTypeId());
        }

        if (oneBoardRoom == null) {
            return;
        }
        MyApplication application = (MyApplication) getApplication();
        application.setNowRoomId(machineCode.getRoomId());

        mTVHeader.setText(machineCode.getBoardRoomName());

        mEquipList.add(Constants.EQUIPMENT.CTR_MODE);
//        List<ModelEntity> model = BoardRoomDB.getModel(oneBoardRoom.getTypeId());
//        if (model != null && model.size() != 0) {
//
//        }

        List<CurtainEntity> curtain = BoardRoomDB.getCurtain(oneBoardRoom.getTypeId());
        if (curtain != null && curtain.size() != 0) {
            mEquipList.add(Constants.EQUIPMENT.CTR_WINDOW);
        }

        List<TvEntity> tv = BoardRoomDB.getTv(oneBoardRoom.getTypeId());
        if (tv != null && tv.size() != 0) {
//            mEquipList.add(Constants.EQUIPMENT.CTR_TV);
        }

        List<ProjectorEntity> projector = BoardRoomDB.getProjector(oneBoardRoom.getTypeId());
        if (projector != null && projector.size() != 0) {
            mEquipList.add(Constants.EQUIPMENT.CTR_PROJECTION);
        }

        List<AirEntity> air = BoardRoomDB.getAir(oneBoardRoom.getTypeId());
        if (air != null && air.size() != 0) {
            mEquipList.add(Constants.EQUIPMENT.CTR_AIR);
        }

        mEquipList.add(Constants.EQUIPMENT.CTR_MUSIC);

//        mEquipList.add(Constants.EQUIPMENT.CTR_PPT);
    }

    /**
     * ListView的初始化
     */
    private void initListView() {
        mMenuListView = (ListView) findViewById(R.id.menulist);
        mMenuListViewAdapter = new MenuListViewAdapter(this, mEquipList);
        mMenuListView.setAdapter(mMenuListViewAdapter);
        mMenuListView.setOnItemClickListener(this);
    }

    /**
     * 设置按键的监听
     */
    private void initListener() {
        mIbHome.setOnClickListener(this);
        mIbSetting.setOnClickListener(this);
    }

    /**
     * 找到界面上所有的控件
     */
    private void initUI() {
        mTVHeader = (TextView) findViewById(R.id.header);
        mIbHome = (ImageButton) findViewById(R.id.btn_selectarea);
        mIbSetting = (ImageButton) findViewById(R.id.menubtn);
        mImAddButton = (ImageView) findViewById(R.id.im_addbutton);
        mTextModeSheet = (TextView) findViewById(R.id.txt_modesheet);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_selectarea:// 区域选择
                showRoomList(v);
                break;
            case R.id.menubtn:// 设置
                showSetMenu(v);
                break;

            default:
                break;
        }
    }

    private void showRoomList(View v) {
        List<MachineCode> machineCodeList = BoardRoomDB.getMachineCodeList();
        if (machineCodeList == null || machineCodeList.size() == 0) {
            return;
        }
        areaMenu = new AreaMenu(this);
        areaMenu.addItemList(machineCodeList);
        areaMenu.showAsDropDown(v);
        areaMenu.setOnItemClickListener(this);
    }

    /**
     * 显示popwindow
     *
     * @param v
     */
    private void showSetMenu(View v) {
        mSetMenu = new PopMenu(this);
        mSetMenu.addItems(new int[] { R.drawable.isetinfoselector,
                R.drawable.ihelpselector, R.drawable.iaboutselector,
                R.drawable.iexitselector });
        mSetMenu.showAsDropDown(v);
        mSetMenu.setOnItemClickListener(this);
    }

    /**
     * 关闭popwindow
     */
    private void closeSetMenu() {
        mSetMenu.dismiss();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        switch (parent.getId()) {

            case R.id.menulist:// 左边选项菜单的点击
                if (!tabMenu(position)) {// 替换片段不成功直接return
                    return;
                }
                break;

            case R.id.room_menu_listview:
                setMenuItemClick(position);
                break;

            case R.id.menu_listview:

                closeRoomPopUpWindow();
                tabMenu(0);
                SPdata.writeSelectBoardRoomPosition(this,position);
                initParameter(position);
                mMenuListViewAdapter.clearEquipList();
                mMenuListViewAdapter.setEquipList(mEquipList);
                mMenuListViewAdapter.notifyDataSetChanged();

                break;

            default:
                break;
        }

    }

    private void closeRoomPopUpWindow() {
        areaMenu.dismiss();
    }

    /**
     * 设置菜单的点击
     * @param position
     */
    private void setMenuItemClick(int position) {

        switch (position) {
            case 0:// 设置
                Intent intent = new Intent(this,SetActivity.class);
                startActivity(intent);
                closeSetMenu();
                overridePendingTransition(R.anim.right_to_left_in, R.anim.right_to_left_out);
                break;
            case 1:// 帮助
                closeSetMenu();
                break;
            case 2:// 关于我们
                replaceFragment(new AboutUsFragment());
                closeSetMenu();
                break;
            case 3:// 退出
                finish();
                break;

            default:
                break;
        }
    }

    /**
     * 选项菜单点击，片段的切换，要是之前已经选中，直接返回false
     * @param position
     * @return
     */
    private boolean tabMenu(int position) {
        if (mNowPosition == position) {
            return false;
        } else {
            mNowPosition = position;
        }

        Fragment fragment = null;
        mMenuListViewAdapter.setPosition(position);
        mMenuListViewAdapter.notifyDataSetChanged();
        if (mEquipList == null) {
            return false;
        }
        switch (mEquipList.get(position)) {
            case 0:// 情景控制
                fragment = new TabSituation();
                mTextModeSheet.setText(R.string.situation_sheet);
                break;
            case 1:// 窗帘控制
                fragment = new TabCurtain();
                mTextModeSheet.setText(R.string.curtain_sheet);
                break;
            case 2:// 投影控制
                fragment = new TabProjector();
                mTextModeSheet.setText(R.string.projector_sheet);
                break;
            case 3:// 空调控制
                fragment = new TabAirCondition();
                mTextModeSheet.setText(R.string.air_sheet);
                break;
            case 4:// 电视控制
                fragment = new TabMusic();
                mTextModeSheet.setText(R.string.music_sheet);

                break;
            case 5:// 音响输出
                fragment = new TabTV();
                mTextModeSheet.setText(R.string.tv_sheet);

                break;
            case 6:// 音乐控制
                fragment = new TabScreen();
                mTextModeSheet.setText(R.string.tong_sheet);
                break;
            case 7:// 同屏输出
                fragment = new TabSound();
                mTextModeSheet.setText(R.string.sound_sheet);
                break;

            default:
                break;
        }
        replaceFragment(fragment);
        return true;
    }

    /**
     * 替换片段
     * @param fragment
     */
    private void replaceFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.framelayout, fragment);
        ft.commit();
    }

    /**
     * 发送命令去获取所有设备的状态
     */
    private void getAllEquipStatus() {
        String allEquipStatus = StringMerge.getAllEquipMentStatus(this);
        String ip = (String) SPUtils.get(this, Constants.IP, Constants.DEFAULT_IP);
        int port =(Integer) SPUtils.get(this, Constants.SENDPORT, Constants.DEFAULT_SENDPORT);
        new Sender(allEquipStatus,ip,port).send();
    }

//    /**
//     * 获取当前的情景模式
//     */
//    private void getSituation() {
//        String situation = StringMerge.getSituation(this);
//        String ip = (String) SPUtils.get(this, Constants.IP, Constants.DEFAULT_IP);
//        int port =(Integer) SPUtils.get(this, Constants.SENDPORT, Constants.DEFAULT_SENDPORT);
//        new Sender(situation,ip,port).send();
//    }

    /**
     * 跟服务的绑定的连接
     * 通过这个类可以跟服务进行数据的交互
     */
    class MyServiceConnection implements ServiceConnection, OnReceiveSuccessListener {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            MyBinder myBinder = (MyBinder) iBinder;
            receiveService = myBinder.getReceiveService();
            receiveService.startTask();
            receiveService.setOnReceiveSuccessListener(this);

            getAllEquipStatus();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
        }

        @Override// 成功接收到数据
        public void onSuccessData(String msg) {
            String orderCode = msg.substring(51, 56).replaceAll(" ","");
            // 场景模式控制命令
            if (orderCode.equalsIgnoreCase(UdpSend.SITUATION_CONTROL_ORDER_CODE))
            {

            }
            // 获取场景模式命令
            else if (orderCode.equalsIgnoreCase(UdpSend.GET_SITUATION_ORDER_CODE))
            {
                SPUtils.put(IndexActivity.this, "situationMode", msg);
                Intent intent = new Intent("txPark.updateSituation");
                intent.putExtra("updateSituation",msg);
                LocalBroadcastManager.getInstance(IndexActivity.this).sendBroadcast(intent);
            }
            // 获取设备状态命令
            else if (orderCode.equalsIgnoreCase(UdpSend.GET_EQUIPMENT_STATUS_ORDER_CODE))
            {

                SPUtils.put(IndexActivity.this, "equipStatus", msg);// 保存到SP
                Intent intent = new Intent("txPark.updateEquipStatus");
                intent.putExtra("equipStatus",msg);
                LocalBroadcastManager.getInstance(IndexActivity.this).sendBroadcast(intent);

            }
            // 灯光控制命令
            else if (orderCode.equalsIgnoreCase(UdpSend.LIGHT_CONTROL_ORDER_CODE))
            {

            }
            // 红外控制命令
            else if (orderCode.equalsIgnoreCase(UdpSend.INFRARED_CONTROL_ORDER_CODE))
            {

            }
            // 窗帘控制命令
            else if (orderCode.equalsIgnoreCase(UdpSend.CURTAIN_CONTROL_ORDER_CODE))
            {

            }
            // IP设置命令
            else if (orderCode.equalsIgnoreCase(UdpSend.IP_SET_ORDER_CODE))
            {

            }
        }
    }



}
