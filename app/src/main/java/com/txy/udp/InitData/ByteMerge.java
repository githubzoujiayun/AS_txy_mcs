package com.txy.udp.InitData;

import com.txy.database.AirCondition;

/**
 * Created by Administrator on 2015/8/12.
 */
public class ByteMerge {

    public String AirConditionMerge(AirCondition airCondition){

        int l = airCondition.temperature + airCondition.fanRate_L;
        int h = airCondition.fanRate_H + airCondition.mode + airCondition.status;

        String s1 = Integer.toHexString(l);
        String s2 = Integer.toHexString(h);
        return s2 + s1;
    }
}
