package com.txy.gson;

import com.activeandroid.Model;

import java.util.List;

/**
 * Created by Administrator on 2015/8/26.
 */
public class BoardRoom extends Model{

    /**
     * boardRoom : [{"typeName":"大型会议室","tv":[{"name":"电视","code":"13_1"}],"light":[{"name":"死猴子","code":"13_1"},{"name":"叶妹子","code":"13_2"},{"name":"大橙子","code":"13_3"}],"typeId":13},{"typeName":"小型会议室","tv":[{"name":"长虹","code":"14_1"},{"name":"落日","code":"14_2"},{"name":"乐视","code":"14_3"}],"light":[{"name":"啊","code":"14_1"},{"name":"是","code":"14_2"}],"typeId":14}]
     * version : 1
     */

    private int version;
    private List<BoardRoomEntity> boardRoom;

    public void setVersion(int version) {
        this.version = version;
    }

    public void setBoardRoom(List<BoardRoomEntity> boardRoom) {
        this.boardRoom = boardRoom;
    }

    public int getVersion() {
        return version;
    }

    public List<BoardRoomEntity> getBoardRoom() {
        return boardRoom;
    }

    public static class BoardRoomEntity {
        /**
         * typeName : 大型会议室
         * tv : [{"name":"电视","code":"13_1"}]
         * light : [{"name":"死猴子","code":"13_1"},{"name":"叶妹子","code":"13_2"},{"name":"大橙子","code":"13_3"}]
         * typeId : 13
         */

        private String typeName;
        private int typeId;
        private List<TvEntity> tv;
        private List<LightEntity> light;

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public void setTypeId(int typeId) {
            this.typeId = typeId;
        }

        public void setTv(List<TvEntity> tv) {
            this.tv = tv;
        }

        public void setLight(List<LightEntity> light) {
            this.light = light;
        }

        public String getTypeName() {
            return typeName;
        }

        public int getTypeId() {
            return typeId;
        }

        public List<TvEntity> getTv() {
            return tv;
        }

        public List<LightEntity> getLight() {
            return light;
        }

        public static class TvEntity {
            /**
             * name : 电视
             * code : 13_1
             */

            private String name;
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

        public static class LightEntity {
            /**
             * name : 死猴子
             * code : 13_1
             */

            private String name;
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
    }
}
