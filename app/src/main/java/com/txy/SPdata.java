package com.txy;

import android.content.Context;

import com.txy.constants.Constants;
import com.txy.utils.SPUtils;

/**
 * Created by Administrator on 2015/8/24.
 */
public class SPdata {

    public static void writeMusicMode(Context context, int mode){
        SPUtils.put(context, "nowMusicPlayMode",mode);
    }

    public static int readMusicMode(Context context) {
        return (Integer)SPUtils.get(context,"nowMusicPlayMode",0);
    }

    public static int readSelectBoardRoomPosition(Context context) {
        return (int) SPUtils.get(context, "selectBoardRoomPosition", 0);
    }

    public static void writeSelectBoardRoomPosition(Context context, int position) {
        SPUtils.put(context, "selectBoardRoomPosition", position);
    }

    public static void writeSendIp(Context context, String ip) {
        SPUtils.put(context, Constants.IP, ip);
    }
}
