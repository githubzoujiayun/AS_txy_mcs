package com.txy.database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by Administrator on 2015/8/13.
 */
@Table(name = "AirCondition")
public class AirCondition extends Model {

    public AirCondition(){
        super();
    }

    @Column
    public int roomId;

    @Column
    public int position;

    @Column
    public int temperature;

    @Column
    public int fanRate;

    @Column
    public int mode;

    @Column
    public int status;
}
