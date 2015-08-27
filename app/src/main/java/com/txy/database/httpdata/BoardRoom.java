package com.txy.database.httpdata;

import com.activeandroid.Model;

import java.util.List;

/**
 * Created by Administrator on 2015/8/26.
 */
public class BoardRoom extends Model{

    /**
     * boardRoom : [{"typeName":"大型会议室","projector":[{"name":"SD","code":"13_1"},{"name":"D","code":"13_4"},{"name":"WE","code":"13_6"}],"model":[{"name":"速度","code":"13_1"},{"name":"力量","code":"13_4"},{"name":"敏捷","code":"13_8"}],"audio":[{"name":"电视","code":"13_1"}],"tv":[{"name":"电视","code":"13_1"}],"air":[{"name":"左墙","code":"13_1"},{"name":"右墙","code":"13_2"},{"name":"是","code":"13_8"}],"typeId":13,"light":[{"name":"死猴子","code":"13_1"},{"name":"叶妹子","code":"13_2"},{"name":"大橙子","code":"13_3"}],"curtain":[{"name":"大窗帘","code":"13_1"},{"name":"小窗帘","code":"13_2"},{"name":"窗帘8","code":"13_8"}]},{"typeName":"小型会议室","model":[{"name":"啊啊","code":"14_5"}],"audio":[{"name":"音响1","code":"14_1"},{"name":"音响2","code":"14_2"},{"name":"音响7","code":"14_7"}],"tv":[{"name":"长虹","code":"14_1"},{"name":"落日","code":"14_2"},{"name":"乐视","code":"14_3"},{"name":"三洋","code":"14_8"}],"air":[{"name":"上墙","code":"14_1"}],"typeId":14,"light":[{"name":"啊","code":"14_1"},{"name":"是","code":"14_2"}],"curtain":[{"name":"右窗帘","code":"14_1"},{"name":"左窗帘","code":"14_2"},{"name":"上窗帘","code":"14_3"}]}]
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


}
