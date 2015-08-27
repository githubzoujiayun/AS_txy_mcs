package com.txy.database.httpdata;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "AirEntity")
public  class AirEntity extends Model{
        /**
         * name : 左墙
         * code : 13_1
         */

        @Column
        private String name;
        @Column
        private String code;
        @Column
        private int boardRoomId;

        public int getBoardRoomId() {
            return boardRoomId;
        }

        public void setBoardRoomId(int boardRoomId) {
            this.boardRoomId = boardRoomId;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public String getCode() {
            return code;
        }

    }