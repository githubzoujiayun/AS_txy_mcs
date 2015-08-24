package com.txy;

import android.content.Context;

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
}
