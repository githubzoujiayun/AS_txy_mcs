package com.txy.database.httpdata;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.List;

/**
 * Created by Administrator on 2015/8/27.
 */
@Table(name = "MachineCode")
public class MachineCode extends Model{


    /**
     * buildName : A 栋
     * floorName : F2
     * macCode : 800000071
     * roomId : 32
     * boardRoomName :
     * typeId : 13
     * ip : 192.168.1.17
     */
    @Column
    private String buildName;
    @Column
    private String floorName;
    @Column
    private String macCode;
    @Column
    private int roomId;
    @Column
    private String boardRoomName;
    @Column
    private int typeId;
    @Column
    private String ip;

    public void setBuildName(String buildName) {
        this.buildName = buildName;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

    public void setMacCode(String macCode) {
        this.macCode = macCode;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public void setBoardRoomName(String boardRoomName) {
        this.boardRoomName = boardRoomName;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getBuildName() {
        return buildName;
    }

    public String getFloorName() {
        return floorName;
    }

    public String getMacCode() {
        return macCode;
    }

    public int getRoomId() {
        return roomId;
    }

    public String getBoardRoomName() {
        return boardRoomName;
    }

    public int getTypeId() {
        return typeId;
    }

    public String getIp() {
        return ip;
    }

}
