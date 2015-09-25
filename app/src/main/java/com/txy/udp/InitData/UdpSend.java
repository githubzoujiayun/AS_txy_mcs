package com.txy.udp.InitData;

/**
 * Created by Administrator on 2015/8/5.
 */
public class UdpSend {

    public static String SITUATION_FRAME_LENGTH = "2020";// 场景模式的帧长度
    public static String GET_SITUATION_FRAME_LENGTH = "1b1b";// 获取场景模式帧长度
    public static String GET_EQUIPMENT_STATUS_FRAME_LENGTH = "1b1b";// 获取设备状态帧长度
    public static String LIGHT_CONTROL_FRAME_LENGTH = "1d1d";// 灯光控制帧长度
    public static String INFRARED_CONTROL_FRAME_LENGTH = "1e1e";// 红外控制帧长度
    public static String CURTAIN_CONTROL_FRAME_LENGTH = "1c1c";//窗帘控制帧长度
    public static String IP_SET_FRAME_LENGTH = "1d1d";// IP设置帧长度

    public static String HMIS = "54585041524b";// 系统标识码“HMIS”
    public static String TARGET_EQUIPMENT = "00";// 目标设备号
    public static String SOURCE_EQUIPMENT = "08";// 源设备号
    public static String MESSAGE_NUM = "0000";// 报文号
    public static String PROJECT_NUM = "ffffffff";// 工程号
    public static String TARGET_MODULE = "00";// 目标模块

    // 命令代号
    public static String SITUATION_CONTROL_ORDER_CODE = "00a0";// 场景模式控制命令
    public static String GET_SITUATION_ORDER_CODE = "00a1";// 获取场景模式命令
    public static String GET_EQUIPMENT_STATUS_ORDER_CODE = "00a2";// 获取设备状态命令
    public static String LIGHT_CONTROL_ORDER_CODE = "00a3";// 灯光控制命令
    public static String INFRARED_CONTROL_ORDER_CODE = "00a4";// 红外控制命令
    public static String CURTAIN_CONTROL_ORDER_CODE = "00a5";// 窗帘控制命令
    public static String IP_SET_ORDER_CODE = "00ae";// IP设置命令

    public static String SEND_ASK = "01";// ASK
    public static String TARGET_HOST_CODE = "ffffffff";// 目标主机编码


    public static final class AIRCONDITION{
        public static String AIRCONDITION = "00";// 空调
        // 第四位
        public static String TEMPERATURE16 = "0";// 温度16
        public static String TEMPERATURE17 = "1";// 温度17
        public static String TEMPERATURE18 = "2";// 温度18
        public static String TEMPERATURE19 = "3";// 温度19
        public static String TEMPERATURE20 = "4";// 温度20
        public static String TEMPERATURE21 = "5";// 温度21
        public static String TEMPERATURE22 = "6";// 温度22
        public static String TEMPERATURE23 = "7";// 温度23
        public static String TEMPERATURE24 = "8";// 温度24
        public static String TEMPERATURE25 = "9";// 温度25
        public static String TEMPERATURE26 = "10";// 温度26
        public static String TEMPERATURE27 = "11";// 温度27
        public static String TEMPERATURE28 = "12";// 温度28
        public static String TEMPERATURE29 = "13";// 温度29
        public static String TEMPERATURE30 = "14";// 温度30

        // 风扇占据了分别占了高低四位的一位
        // 风扇低位
        public static String FAN_RATE_LOW_L = "0";
        public static String FAN_RATE_MID_L = "8";
        public static String FAN_RATE_HIGH_L = "0";
        public static String FAN_RATE_HOT_L = "8";
        // 风扇高位
        public static String FAN_RATE_LOW_H = "0";
        public static String FAN_RATE_MID_H = "0";
        public static String FAN_RATE_HIGH_H = "1";
        public static String FAN_RATE_HOT_H = "1";

        // 风扇
        public static String FAN_RATE_LOW = "0";
        public static String FAN_RATE_MID = "1";
        public static String FAN_RATE_HIGH = "2";
        public static String FAN_RATE_HOT = "15";

        // 模式
        public static String COLD = "0";
        public static String HOT = "4";

        // 开关机
        public static String CLOSE = "0";
        public static String OPEN = "8";
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

    public static final class CURTAIN {
        public static String ONE = "0";
        public static String TWO = "1";
        public static String THREE = "2";
        public static String FOUR = "3";
        public static String FIVE = "4";
        public static String SIX = "5";

        public static String OPEN = "4";
        public static String CLOSE = "0";
        public static String PAUSE = "8";
    }
}
