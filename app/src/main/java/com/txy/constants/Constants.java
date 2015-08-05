package com.txy.constants;

public final class Constants {

    public static int DAYMODE = 0;// 白天模式
    public static int NIGHTMODE = 1;// 夜晚模式

    public static int SAVEMODE = 0;// 节能模式
    public static int MEETMODE = 1;// 会议模式
    public static int PLAYMODE = 2;// 投影模式
    public static int SHOWMODE = 3;// 展示模式

    public static String IP = "192.168.1.201";// 发送的IP
    public static int SENDPORT = 3341;// 发送的端口
    public static int RECEIVEPORT = 3339;// 接收的端口

    /**
     * 数据库的常量
     * @author Administrator
     *
     */
    public static final class DB{

    }


    public static final class BROADCAST{
        public static String SETFRAGMENT2SET = "COM.TXY.SETFRAGMENT2SET";
        public static String SAVESETTING = "COM.TXY.SAVESETTING";
    }

    public static final class SP{
        public static String MODE = "MODE";
        public static String SITUATION = "SITUATION";
    }

}
