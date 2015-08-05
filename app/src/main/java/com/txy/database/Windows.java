package com.txy.database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
@Table(name = "Windows")
public class Windows extends Model{

    public Windows(){
        super();
    }

    @Column(name="roomId")
    public int roomid;

    @Column
    public String tag;// 靠房间的ID跟这个tag来查询,tag标记白天还是黑夜

    @Column
    public String situation;// 模式(四种情景模式)

    @Column(name = "name")
    public String name;

    /*
     * 三种状态
     * 开：open
     * 关：close
     * 暂停：pause
     */
    @Column(name = "status")
    public String status;

}
