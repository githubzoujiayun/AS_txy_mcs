package com.txy.database;

import java.util.List;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;
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
     * 删除所有的房间列表
     */
    public static void deleteAllRoonList() {
        new Delete()
                .from(RoomList.class)
                .execute();
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
     * 保存音乐列表
     * @param musicList
     */
    public static void saveMusicList(List<MyMusic> musicList){
        if (musicList != null) {
            ActiveAndroid.beginTransaction();
            try{
                int size = musicList.size();
                for (int i = 0; i < size; i++){
                    musicList.get(i).save();
                }
                ActiveAndroid.setTransactionSuccessful();
            } finally {
                ActiveAndroid.endTransaction();
            }

        }
    }

    /**
     * 查询播放列表
     * @param mode
     * @return
     */
    public static List<MyMusic> getMusicLists(int mode){
        return new Select()
                .from(MyMusic.class)
                .where("mode = ?", mode)
                .execute();
    }

    /**
     * 依靠着每首歌的path不同区删除一首
     * @param mode
     * @param path
     */
    public static void removeOneMusic(int mode,String path){
        new Delete()
                .from(MyMusic.class)
                .where("mode = ? and path = ?",new Object[] {mode, path})
                .execute();
    }

    public static void removeAllMusic(int mode) {
        new Delete()
                .from(MyMusic.class)
                .where("mode = ?",new Object[] {mode})
                .execute();
    }



}
