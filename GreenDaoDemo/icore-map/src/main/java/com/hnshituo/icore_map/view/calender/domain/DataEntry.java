package com.hnshituo.icore_map.view.calender.domain;

/**
 * Created by liuba
 * time:2016/11/17 15:44
 * descri --
 */

public class DataEntry {
    public int[] colors = {0xffffff,0xff0092fe,0xffffb533,0xffff6833};
    //对应哪一天
    public  int day;
    //对应的颜色
//    public int color;
    public boolean isSign;

    public boolean isComptime;

    public boolean isLeave;

//    public int type;
//    public int type; //0 为空  //1为出勤 //2为调休  //3为请假

    public DataEntry(int day, boolean isSign, boolean isComptime ,boolean isLeave) {
        this.day = day;
        this.isSign = isSign;
        this.isComptime = isComptime;
        this.isLeave = isLeave;
    }

    public void setColors(int[] colors) {
        this.colors= colors;
    }

}
