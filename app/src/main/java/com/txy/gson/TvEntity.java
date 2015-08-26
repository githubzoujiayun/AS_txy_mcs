package com.txy.gson;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "TvEntity")
public  class TvEntity extends Model{
        /**
         * name : 大电视
         * code : 1
         */
        @Column
        private String name;
        @Column
        private int code;

        public void setName(String name) {
            this.name = name;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public int getCode() {
            return code;
        }
    }