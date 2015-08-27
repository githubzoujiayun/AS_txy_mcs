package com.txy.database;

import com.activeandroid.ActiveAndroid;
import com.txy.gson.BoardRoom;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/8/26.
 */
public class BoardRoomDB {

//    public static void saveBoardRoom(ArrayList<BoardRoom> boardRoomArrayList) {
//        ActiveAndroid.beginTransaction();
//
//        try{
//            int size = boardRoomArrayList.size();
//            for (int i = 0; i < size; i++){
//
//                BoardRoom boardRoom = boardRoomArrayList.get(i);
//
//                int lightSize = boardRoom.getLight().size();
//                for (int j = 0;j < lightSize;j++) {
//                    boardRoom.getLight().get(j).save();
//                }
//
//                int airSize = boardRoom.getAir().size();
//                for (int j = 0;j < airSize;j++) {
//                    boardRoom.getAir().get(j).save();
//                }
//
//                int tvSize = boardRoom.getTv().size();
//                for (int j = 0;j < tvSize;j++) {
//                    boardRoom.getTv().get(j).save();
//                }
//
//            }
//
//            ActiveAndroid.setTransactionSuccessful();
//        } finally {
//            ActiveAndroid.endTransaction();
//        }
//    }
}
