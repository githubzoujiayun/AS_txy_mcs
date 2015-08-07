package com.txy.database;

import java.util.List;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;

public class DBManager {

    private DBManager(){}

    /**
     * 保存房间列表
     * @param roomList
     */
    public static void saveRoomList(RoomList roomList){
        roomList.save();
    }

    /**
     * 按照ID查询房间列表
     * @param roomId
     * @return
     */
    public static RoomList getRoom(String roomId){
        return new Select()
                .from(RoomList.class)
                .where("roomid = ?", roomId)
                .executeSingle();

    }

    /**
     * 查询所有的房间列表
     * @return
     */
    public static List<RoomList> getAllRoomList(){
        return new Select()
                .from(RoomList.class)
                .execute();
    }


}
