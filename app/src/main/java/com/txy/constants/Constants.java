package com.txy.constants;

public final class Constants {

    public static int STARTAPP_DELAY = 2000;// 延时3秒
    public static int MY_SOCKET_TIMEOUT_MS = 3000;

    public static int DAYMODE = 0;// 白天模式
    public static int NIGHTMODE = 1;// 夜晚模式

    public static int SAVEMODE = 0;// 节能模式
    public static int MEETMODE = 1;// 会议模式
    public static int PLAYMODE = 2;// 投影模式
    public static int SHOWMODE = 3;// 展示模式
    public static int OFFMODE = 4;// 总关模式

    public static String SERVERIP = "SERVERIP";// 服务器的IP
    public static String SERVERPORT = "SERVERPORT";// 服务器的PORT

    public static String IP = "IP";// 发送的IP
    public static String SENDPORT = "SENDPORT";// 发送的端口
    public static String RECEIVEPORT = "RECEIVEPORT";// 接收的端口

    public static String DEFAULT_IP = "192.168.1.102";// 默认的发送IP
    public static int DEFAULT_SENDPORT = 3341;// 默认的发送端口号
    public static int DEFAULT_RECEIVEPORT = 3339;// 默认的接收端口号

    public static String DEFAULT_SERVER_IP = "192.168.1.2";// 默认的服务器IP
    public static String DEFAULT_SERVER_PORT = "8888";// 默认的服务器端口号


    public static final class EQUIPMENT{

        public static int CTR_MODE = 0;
        public static int CTR_WINDOW = 1;
        public static int CTR_PROJECTION = 2;
        public static int CTR_AIR = 3;
        public static int CTR_MUSIC = 4;
        public static int CTR_PPT = 5;
        public static int CTR_TV = 6;
        public static int CTR_SOUND = 7;

    }

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

    public static final class URL{
        public static String INIT_DATA = "http://192.168.1.248:8820/tx/api/getAppStatus.action";
    }

}
