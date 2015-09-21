package com.txy.udp.InitData;

import android.content.Context;
import android.util.Log;

import com.txy.SPdata;
import com.txy.constants.Constants;
import com.txy.database.AirCondition;
import com.txy.database.BoardRoomDB;
import com.txy.database.httpdata.BoardRoomMachineCode;
import com.txy.database.httpdata.MachineCode;

import java.util.List;

/**
 * Created by Administrator on 2015/8/12.
 */
public class StringMerge {


    public static String readProjectorNum(Context context) {
        int i = SPdata.readSelectBoardRoomPosition(context);
        List<MachineCode> machineCodeList = BoardRoomDB.getMachineCodeList();
        MachineCode machineCode = null;
        if (machineCodeList != null && machineCodeList.size() > 0) {
            if (i < machineCodeList.size()) {
                machineCode = machineCodeList.get(0);
            } else {
                machineCode = machineCodeList.get(i);
            }
        } else {
            Log.e("StringMerge", "------机器代码的数据库为空-----");
            return "";
        }
        String macCode = machineCode.getMacCode();
        int i1 = Integer.parseInt(macCode);
        String s = Integer.toHexString(i1);
        return s;
    }


    /**
     * 场景模式的控制数据拼接
     * @param situationMode
     * @param mode
     * @return
     */
    public static String  situationControl(Context context,int situationMode, int mode){
        String msg = UdpSend.SITUATION_FRAME_LENGTH
                + UdpSend.HMIS
                + UdpSend.TARGET_EQUIPMENT
                + UdpSend.SOURCE_EQUIPMENT
                + UdpSend.MESSAGE_NUM
                + UdpSend.PROJECT_NUM
                + UdpSend.TARGET_MODULE
                + UdpSend.SITUATION_CONTROL_ORDER_CODE
                + UdpSend.SEND_ASK
                + readProjectorNum(context)
                + "0600000000";
        switch (mode) {
            case 0:
                msg = msg + "0" + situationMode;
                break;
            case 1:
                if (situationMode == Constants.OFFMODE) {
                    msg = msg + "0" + situationMode;
                } else {
                    msg = msg + "8" + situationMode;
                }
                break;
        }
        return msg + CRC16.ccr16(msg);
    }

    /**
     * 获取场景的状态
     * @return
     */
    public static String getSituation(Context context){
        String msg = UdpSend.GET_SITUATION_FRAME_LENGTH
                + UdpSend.HMIS
                + UdpSend.TARGET_EQUIPMENT
                + UdpSend.SOURCE_EQUIPMENT
                + UdpSend.MESSAGE_NUM
                + UdpSend.PROJECT_NUM
                + UdpSend.TARGET_MODULE
                + UdpSend.GET_SITUATION_ORDER_CODE
                + UdpSend.SEND_ASK
                + readProjectorNum(context)
                + "01";
        return msg;
    }


    /**
     * 获取所有设备的状态
     * @return
     */
    public static String getAllEquipMentStatus(Context context){
        String msg = UdpSend.GET_EQUIPMENT_STATUS_FRAME_LENGTH
                + UdpSend.HMIS
                + UdpSend.TARGET_EQUIPMENT
                + UdpSend.SOURCE_EQUIPMENT
                + UdpSend.MESSAGE_NUM
                + UdpSend.PROJECT_NUM
                + UdpSend.TARGET_MODULE
                + UdpSend.GET_EQUIPMENT_STATUS_ORDER_CODE
                + UdpSend.SEND_ASK
                + readProjectorNum(context)
                + "01" ;

        return msg + CRC16.ccr16(msg);
    }

    /**
     * 灯光的控制
     * @param position
     * @param tag
     * @return
     */
    public static String lightControl(Context context,int position, boolean tag){

        String msg = UdpSend.LIGHT_CONTROL_FRAME_LENGTH
                + UdpSend.HMIS
                + UdpSend.TARGET_EQUIPMENT
                + UdpSend.SOURCE_EQUIPMENT
                + UdpSend.MESSAGE_NUM
                + UdpSend.PROJECT_NUM
                + UdpSend.TARGET_MODULE
                + UdpSend.LIGHT_CONTROL_ORDER_CODE
                + UdpSend.SEND_ASK
                + readProjectorNum(context)
                + "03" ;

        if (position >= 10) {
            msg = msg + position;
        } else {
            msg = msg + "0" + position;
        }

        if (tag) {
            msg = msg + "01";// 开
        } else {
            msg = msg + "00";
        }

        return msg + CRC16.ccr16(msg);
    }

    /**
     * 空调控制命令
     * @param equip
     * @param position
     * @param airCondition
     * @return
     */
    public static String airConditionControl(Context context,String equip,String position, AirCondition airCondition){

        String s = ByteMerge.AirConditionMerge(airCondition);
        Log.e("airConditionControl", s);
        String msg = UdpSend.INFRARED_CONTROL_FRAME_LENGTH
                + UdpSend.HMIS
                + UdpSend.TARGET_EQUIPMENT
                + UdpSend.SOURCE_EQUIPMENT
                + UdpSend.MESSAGE_NUM
                + UdpSend.PROJECT_NUM
                + UdpSend.TARGET_MODULE
                + UdpSend.INFRARED_CONTROL_ORDER_CODE
                + UdpSend.SEND_ASK
                + readProjectorNum(context)
                + "04"
                + equip
                + position
                + s;

        return msg + CRC16.ccr16(msg);
    }

    /**
     * 红外控制
     * @param equip
     * @param position
     * @param orderCode
     * @return
     */
    public static String infrafedControl(Context context,String equip,String position,String orderCode){
        String msg = UdpSend.INFRARED_CONTROL_FRAME_LENGTH
                + UdpSend.HMIS
                + UdpSend.TARGET_EQUIPMENT
                + UdpSend.SOURCE_EQUIPMENT
                + UdpSend.MESSAGE_NUM
                + UdpSend.PROJECT_NUM
                + UdpSend.TARGET_MODULE
                + UdpSend.INFRARED_CONTROL_ORDER_CODE
                + UdpSend.SEND_ASK
                + readProjectorNum(context)
                + "04"
                + equip
                + position
                + orderCode;

        return msg + CRC16.ccr16(msg);
    }

    public static String curtainControl(Context context,String channel, String status){

        String orderCode = ByteMerge.Curtain(channel, status);
        String msg = UdpSend.CURTAIN_CONTROL_FRAME_LENGTH
                + UdpSend.HMIS
                + UdpSend.TARGET_EQUIPMENT
                + UdpSend.SOURCE_EQUIPMENT
                + UdpSend.MESSAGE_NUM
                + UdpSend.PROJECT_NUM
                + UdpSend.TARGET_MODULE
                + UdpSend.CURTAIN_CONTROL_ORDER_CODE
                + UdpSend.SEND_ASK
                + readProjectorNum(context)
                + "02"
                + orderCode;
        return msg + CRC16.ccr16(msg);
    }


}
