package com.txy.database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "lights")
public class Light extends Model{

    public Light(){
        super();
    }

    public Light(int roomid, String tag, String situation, String name,
                 boolean status) {
        super();
        this.roomid = roomid;
        this.tag = tag;
        this.situation = situation;
        this.name = name;
        this.status = status;
    }

    @Column
    public int roomid;

    @Column
    public String tag;// 靠房间的ID跟这个tag来查询,tag标记白天还是黑夜

    @Column
    public String situation;// 模式(四种情景模式)

    @Column(name = "name")
    public String name;

    @Column(name = "status")
    public boolean status;

}
