package com.txy.udp.InitData;

import com.txy.constants.Constants;

/**
 * Created by Administrator on 2015/8/12.
 */
public class StringMerge {


    /**
     * 场景模式的控制数据拼接
     * @param situationMode
     * @param mode
     * @return
     */
    public String  situationControl(int situationMode, int mode){
        String msg = UdpSend.SITUATION_FRAME_LENGTH
                + UdpSend.HMIS
                + UdpSend.TARGET_EQUIPMENT
                + UdpSend.SOURCE_EQUIPMENT
                + UdpSend.MESSAGE_NUM
                + UdpSend.PROJECT_NUM
                + UdpSend.TARGET_MODULE
                + UdpSend.SITUAION_CONTROL_ORDER_CODE
                + UdpSend.SEND_ASK
                + UdpSend.TARGET_HOST_CODE
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
    public String getSituation(){
        String msg = UdpSend.GET_SITUATION_FRAME_LENGTH
                + UdpSend.HMIS
                + UdpSend.TARGET_EQUIPMENT
                + UdpSend.SOURCE_EQUIPMENT
                + UdpSend.MESSAGE_NUM
                + UdpSend.PROJECT_NUM
                + UdpSend.TARGET_MODULE
                + UdpSend.GET_SITUATION_ORDER_CODE
                + UdpSend.SEND_ASK
                + UdpSend.TARGET_HOST_CODE
                + "01";
        return msg;
    }

    public String getAllEquipMentStatus(){
        String msg = UdpSend.GET_EQUIPMENT_STATUS_FRAME_LENGTH
                + UdpSend.HMIS
                + UdpSend.TARGET_EQUIPMENT
                + UdpSend.SOURCE_EQUIPMENT
                + UdpSend.MESSAGE_NUM
                + UdpSend.PROJECT_NUM
                + UdpSend.TARGET_MODULE
                + UdpSend.GET_EQUIPMENT_STATUS_ORDER_CODE
                + UdpSend.SEND_ASK
                + UdpSend.TARGET_HOST_CODE
                + "01" ;

        return msg + CRC16.ccr16(msg);
    }

    /**
     * 灯光的控制
     * @param position
     * @param tag
     * @return
     */
    public String lightControl(int position, boolean tag){

        String msg = UdpSend.LIGHT_CONTROL_FRAME_LENGTH
                + UdpSend.HMIS
                + UdpSend.TARGET_EQUIPMENT
                + UdpSend.SOURCE_EQUIPMENT
                + UdpSend.MESSAGE_NUM
                + UdpSend.PROJECT_NUM
                + UdpSend.TARGET_MODULE
                + UdpSend.LIGHT_CONTROL_ORDER_CODE
                + UdpSend.SEND_ASK
                + UdpSend.TARGET_HOST_CODE
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
     * 红外控制命令
     * @param equip
     * @param position
     * @param orderCode
     * @return
     */
    public String InfraredControl(String equip,String position, String orderCode){
        String msg = UdpSend.INFRARED_CONTROL_FRAME_LENGTH
                + UdpSend.HMIS
                + UdpSend.TARGET_EQUIPMENT
                + UdpSend.SOURCE_EQUIPMENT
                + UdpSend.MESSAGE_NUM
                + UdpSend.PROJECT_NUM
                + UdpSend.TARGET_MODULE
                + UdpSend.INGRARED_CONTROL_ORDER_CODE
                + UdpSend.SEND_ASK
                + UdpSend.TARGET_HOST_CODE
                + "04"
                + equip
                + position
                + orderCode;

        return msg + CRC16.ccr16(msg);
    }

    public String curtainControl(String orderCode){
        String msg = UdpSend.CURTAIN_CONTROL_FRAME_LENGTH
                + UdpSend.HMIS
                + UdpSend.TARGET_EQUIPMENT
                + UdpSend.SOURCE_EQUIPMENT
                + UdpSend.MESSAGE_NUM
                + UdpSend.PROJECT_NUM
                + UdpSend.TARGET_MODULE
                + UdpSend.CURTAIN_CONTROL_ORDER_CODE
                + UdpSend.SEND_ASK
                + UdpSend.TARGET_HOST_CODE
                + "02"
                + orderCode;
        return msg + CRC16.ccr16(msg);
    }
}
