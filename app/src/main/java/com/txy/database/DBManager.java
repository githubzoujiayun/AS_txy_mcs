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
     * 批量保存灯
     * @param light
     * @return
     */
    public static boolean saveLights(List<Light> light){
        ActiveAndroid.beginTransaction();
        try {
            if(light != null){
                int size = light.size();
                for (int i = 0; i < size; i++) {
                    light.get(i).save();
                }
            } else {
                return false;
            }
            ActiveAndroid.setTransactionSuccessful();
        } finally {
            ActiveAndroid.endTransaction();
        }
        return true;
    }

    /**
     * 保存一个
     * @param light
     */
    public static void saveOneLight(Light light){
        light.save();
    }

    /**
     * 保存窗帘
     * @param window
     * @return
     */
    public static boolean saveWindows(List<Windows> window){
        ActiveAndroid.beginTransaction();
        try {
            if(window != null){
                int size = window.size();
                for (int i = 0; i < size; i++) {
                    window.get(i).save();
                }
            } else {
                return false;
            }
            ActiveAndroid.setTransactionSuccessful();
        } finally {
            ActiveAndroid.endTransaction();
        }
        return true;
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

    /**
     * 查询灯
     * @param roomId
     * @param tag
     * @return
     */
    public static List<Light> getLights(int roomId,String tag, String situation){
        return new Select()
                .from(Light.class)
                .where("roomid = ? and tag = ? and situation = ?",new Object[]{roomId, tag, situation})
                .execute();
    }

    /**
     * 批量更新灯的状态
     * @param roomid
     * @param tag
     * @param situation
     * @param status
     * @return
     */
    public static boolean updateLightStatus(int roomid, String tag, String situation, List<Boolean> status){
        List<Light> execute = new Select()
                .from(Light.class)
                .where("roomid = ? and tag = ? and situation = ?", new Object[]{roomid, tag, situation})
                .execute();
        int size = execute.size();
        ActiveAndroid.beginTransaction();
        try {
            for (int i = 0; i < size; i++) {
                execute.get(i).status = status.get(i);
                execute.get(i).save();
            }
            ActiveAndroid.setTransactionSuccessful();
        } finally {
            ActiveAndroid.endTransaction();
        }
        return true;
    }
}
