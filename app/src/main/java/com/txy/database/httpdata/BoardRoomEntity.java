package com.txy.database.httpdata;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.List;

@Table(name = "BoardRoomEntity")
public  class BoardRoomEntity extends Model{
        /**
         * typeName : 大型会议室
         * projector : [{"name":"SD","code":"13_1"},{"name":"D","code":"13_4"},{"name":"WE","code":"13_6"}]
         * model : [{"name":"速度","code":"13_1"},{"name":"力量","code":"13_4"},{"name":"敏捷","code":"13_8"}]
         * audio : [{"name":"电视","code":"13_1"}]
         * tv : [{"name":"电视","code":"13_1"}]
         * air : [{"name":"左墙","code":"13_1"},{"name":"右墙","code":"13_2"},{"name":"是","code":"13_8"}]
         * typeId : 13
         * light : [{"name":"死猴子","code":"13_1"},{"name":"叶妹子","code":"13_2"},{"name":"大橙子","code":"13_3"}]
         * curtain : [{"name":"大窗帘","code":"13_1"},{"name":"小窗帘","code":"13_2"},{"name":"窗帘8","code":"13_8"}]
         */

        @Column
        private String typeName;
        @Column
        private int typeId;
        private List<ProjectorEntity> projector;
        private List<ModelEntity> model;
        private List<AudioEntity> audio;
        private List<TvEntity> tv;
        private List<AirEntity> air;
        private List<LightEntity> light;
        private List<CurtainEntity> curtain;

    public List<SetAirEntity> getSet() {
        return set;
    }

    public void setSet(List<SetAirEntity> set) {
        this.set = set;
    }

    private List<SetAirEntity> set;

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public void setTypeId(int typeId) {
            this.typeId = typeId;
        }

        public void setProjector(List<ProjectorEntity> projector) {
            this.projector = projector;
        }

        public void setModel(List<ModelEntity> model) {
            this.model = model;
        }

        public void setAudio(List<AudioEntity> audio) {
            this.audio = audio;
        }

        public void setTv(List<TvEntity> tv) {
            this.tv = tv;
        }

        public void setAir(List<AirEntity> air) {
            this.air = air;
        }

        public void setLight(List<LightEntity> light) {
            this.light = light;
        }

        public void setCurtain(List<CurtainEntity> curtain) {
            this.curtain = curtain;
        }

        public String getTypeName() {
            return typeName;
        }

        public int getTypeId() {
            return typeId;
        }

        public List<ProjectorEntity> getProjector() {
            return projector;
        }

        public List<ModelEntity> getModel() {
            return model;
        }

        public List<AudioEntity> getAudio() {
            return audio;
        }

        public List<TvEntity> getTv() {
            return tv;
        }

        public List<AirEntity> getAir() {
            return air;
        }

        public List<LightEntity> getLight() {
            return light;
        }

        public List<CurtainEntity> getCurtain() {
            return curtain;
        }

    }