package com.txy.database;

import com.activeandroid.ActiveAndroid;
import com.txy.database.httpdata.BoardRoomEntity;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/8/26.
 */
public class BoardRoomDB {

    public static boolean saveBoardRoom(ArrayList<BoardRoomEntity> boardRoomEntityArrayList) {

        if (boardRoomEntityArrayList == null) {
            return false;
        }

        ActiveAndroid.beginTransaction();
        try{
            int boardRoomSize = boardRoomEntityArrayList.size();
            for (int i = 0; i < boardRoomSize; i++) {

                BoardRoomEntity boardRoomEntity = boardRoomEntityArrayList.get(i);
                boardRoomEntity.save();

                if (boardRoomEntity.getAir() != null) {
                    int size = boardRoomEntity.getAir().size();
                    for (int j = 0; j < size; j++) {
                        boardRoomEntity.getAir().get(j).save();
                    }
                }

                if (boardRoomEntity.getLight() != null) {
                    int size = boardRoomEntity.getLight().size();
                    for (int j = 0; j < size; j++) {
                        boardRoomEntity.getLight().get(j).save();
                    }
                }

                if (boardRoomEntity.getAudio() != null) {
                    int size = boardRoomEntity.getAudio().size();
                    for (int j = 0; j < size; j++) {
                        boardRoomEntity.getAudio().get(j).save();
                    }
                }

                if (boardRoomEntity.getCurtain() != null) {
                    int size = boardRoomEntity.getCurtain().size();
                    for (int j = 0; j < size; j++) {
                        boardRoomEntity.getCurtain().get(j).save();
                    }
                }

                if (boardRoomEntity.getProjector() != null) {
                    int size = boardRoomEntity.getProjector().size();
                    for (int j = 0; j < size; j++) {
                        boardRoomEntity.getProjector().get(j).save();
                    }
                }

                if (boardRoomEntity.getTv() != null) {
                    int size = boardRoomEntity.getTv().size();
                    for (int j = 0; j < size; j++) {
                        boardRoomEntity.getTv().get(j).save();
                    }
                }

                if (boardRoomEntity.getModel() != null) {
                    int size = boardRoomEntity.getModel().size();
                    for (int j = 0; j < size; j++) {
                        boardRoomEntity.getModel().get(j).save();
                    }
                }

            }
            ActiveAndroid.setTransactionSuccessful();
        } finally {
            ActiveAndroid.endTransaction();
        }
        return true;
    }
}
