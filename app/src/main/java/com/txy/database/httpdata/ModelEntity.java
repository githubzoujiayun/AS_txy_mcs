package com.txy.database.httpdata;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "ModelEntity")
public  class ModelEntity extends Model{
            /**
             * name : 速度
             * code : 13_1
             */

            @Column
            private String name;
            @Column
            private String code;

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