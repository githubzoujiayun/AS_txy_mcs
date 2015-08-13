package com.txy.database;

import java.io.Serializable;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "RoomList")
public class RoomList extends Model implements Serializable{
    private static final long serialVersionUID = 1L;

    // 确保每个model类中都有一个默认的构造方法
    public RoomList(){
        super();
    }

    @Column(name = "roomid", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    public int roomid;

    @Column(name = "roomName")
    public String roomName;

    @Column(name = "lightNum")
    public String lightNum;

    @Column(name = "windowNum")
    public String windowNum;

    @Column(name = "airNum")
    public String airNum;

    @Column(name = "projectionNum")
    public String projectionNum;

    @Column(name = "tvNum")
    public String tvNum;

    @Column(name = "soundNum")
    public String soundNum;

    @Column(name = "ip")
    public String ip;

    @Column(name = "sendPort")
    public int sendPort;

    @Column(name = "receivePort")
    public int receivePort;


}
