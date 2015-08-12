package com.txy.udp.InitData;

/**
 * Created by Administrator on 2015/8/5.
 */
public class UdpSend {

    public static String SITUATION_FRAME_LENGTH = "1e1e";// 场景模式的帧长度
    public static String GET_SITUATION_FRAME_LENGTH = "1919";// 获取场景模式帧长度
    public static String GET_EQUIPMENT_STATUS_FRAME_LENGTH = "1919";// 获取设备状态帧长度
    public static String LIGHT_CONTROL_FRAME_LENGTH = "1d1d";// 灯光控制帧长度
    public static String INFRARED_CONTROL_FRAME_LENGTH = "1c1c";// 红外控制帧长度
    public static String CURTAIN_CONTROL_FRAME_LENGTH = "1a1a";//窗帘控制帧长度
    public static String IP_SET_FRAME_LENGTH = "1d1d";// IP设置帧长度

    public static String HMIS = "484d4953";// 系统标识码“HMIS”
    public static String TARGET_EQUIPMENT = "00";// 目标设备号
    public static String SOURCE_EQUIPMENT = "08";// 源设备号
    public static String MESSAGE_NUM = "0000";// 报文号
    public static String PROJECT_NUM = "ffffffff";// 工程号
    public static String TARGET_MODULE = "00";// 目标模块

    // 命令代号
    public static String SITUATION_CONTROL_ORDER_CODE = "0050";// 场景模式控制命令
    public static String GET_SITUATION_ORDER_CODE = "0051";// 获取场景模式命令
    public static String GET_EQUIPMENT_STATUS_ORDER_CODE = "0052";// 获取设备状态命令
    public static String LIGHT_CONTROL_ORDER_CODE = "0053";// 灯光控制命令
    public static String INFRARED_CONTROL_ORDER_CODE = "0054";// 红外控制命令
    public static String CURTAIN_CONTROL_ORDER_CODE = "0055";// 窗帘控制命令
    public static String IP_SET_ORDER_CODE = "005e";// IP设置命令

    public static String SEND_ASK = "01";// ASK
    public static String TARGET_HOST_CODE = "ffffffff";// 目标主机编码


    public static final class AIRCONDITION{
        public static String AIRCONDITION = "00";// 空调
        public static String TEMPERATURE22 = "";// 温度22
        public static String TEMPERATURE23 = "";// 温度23
    }

    public static final class PROJECTION{
        public static String PROJECTION = "01";// 投影仪
        public static String OPEN = "00";// 开
        public static String SOURCE = "01";// 信号源
        public static String VOL_PLUS = "02";// 音量加
        public static String VOL_DESC = "03";// 音量减
        public static String UP = "04";// 上
        public static String DOWN = "05";// 下
        public static String LEFT = "06";// 左
        public static String RIGHT = "07";// 右

        public static String OK_BUTTON = "08";// 确认
        public static String MODE_BUTTON = "09";// 模式
        public static String CLOSE = "0a";// 关
        public static String UP_LONG_BUTTON = "0b";// 投影布升长按按键
        public static String DOWN_LONG_BUTTON = "0c";// 投影布降长按按键
        public static String UP_BUTTON = "0d";// 投影布升短按按键
        public static String DOWN_BUTTON = "0e";// 投影布降短按按键

    }

    public static final class FAN {
        public static String FAN = "02";// 风扇
        public static String SWITCH = "00";// 开关
        public static String AIR_RATE = "01";// 风量
        public static String ROCK = "02";// 摇摆
        public static String TIME = "03";// 定时
    }

    public static final class TV {
        public static String TV = "03"; // 电视
        public static String OPEN = "00";
        public static String CLOSE = "01";
        public static String VOL_PLUS = "02";
        public static String VOL_DESC = "03";//
        public static String CHANNEL_PLUS = "04";
        public static String CHANNEL_DESC = "05";
        public static String BACK = "06";
        public static String MODE = "07";
        public static String SOURCE = "08";
        public static String BUTTON = "09";// -/--
        public static String OK_BUTTON = "0a";
        public static String FIRST_CHANNEL = "0f";
    }
}
