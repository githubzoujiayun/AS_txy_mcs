package com.txy.udp.InitData;

import com.txy.database.AirCondition;
import com.txy.utils.ByteToBitUtils;

import java.util.ArrayList;
import java.util.Collections;
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

        int l = airCondition.temperature;
        int h = airCondition.status + airCondition.mode + airCondition.fanRate;

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
        byte j = 0;
        if (b >= 48 && b <= 57) {
            j = (byte) ( b - '0');
        } else if (b >= 97 && b <= 122) {
            j = (byte) (b - 'a' + 10);
        }

        byte[] bytes = ByteToBitUtils.getBooleanArray(j);
        ArrayList<Boolean> booleans = new ArrayList<Boolean>();
        int length = bytes.length;
        for (int i = length - 1; i >= 4; i--) {
            if (bytes[i] == 0) {
                booleans.add(false);
            } else {
                booleans.add(true);
            }
        }
//        Collections.reverse(booleans);
        return booleans;
    }
}
