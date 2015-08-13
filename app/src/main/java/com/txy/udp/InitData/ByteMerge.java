package com.txy.udp.InitData;

import com.txy.database.AirCondition;
import com.txy.database.LightStatus;
import com.txy.utils.ByteToBitUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/8/12.
 */
public class ByteMerge {

    /**
     *
     * @param airCondition
     * @return
     */
    public static String AirConditionMerge(AirCondition airCondition){

        int l = airCondition.temperature + airCondition.fanRate_L;
        int h = airCondition.fanRate_H + airCondition.mode + airCondition.status;

        String s1 = Integer.toHexString(l);
        String s2 = Integer.toHexString(h);
        return s2 + s1;
    }

    public static String Curtain(String channel, String status) {

        int ch = Integer.parseInt(channel);
        int sta = Integer.parseInt(status);

        String s1 = Integer.toHexString(ch);
        String s2 = Integer.toHexString(sta);
        return s2 + s1;
    }


    public static List<Boolean> parseByteToBit(byte b) {
        byte[] bytes = ByteToBitUtils.getBooleanArray(b);
        ArrayList<Boolean> booleans = new ArrayList<Boolean>();
        int length = bytes.length;
        for (int i = 0; i < length; i++) {
            if (bytes[i] == 0) {
                booleans.add(false);
            } else {
                booleans.add(true);
            }
        }
        return booleans;
    }
}
