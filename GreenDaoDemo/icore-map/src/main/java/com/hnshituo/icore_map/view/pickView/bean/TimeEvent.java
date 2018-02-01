package com.hnshituo.icore_map.view.pickView.bean;


import com.hnshituo.icore_map.util.DateUtils;
import com.hnshituo.icore_map.view.pickView.TimeViewUtil;

import java.util.Date;

public class TimeEvent {
    /**
     * 等分
     */
    public int  divide=2;

    public final static String START = "1990-1-1";
    public final static String END = "2115-12-31";
    public Date starDate = DateUtils.formatStringToYMDData(START);

    public Date endDate = DateUtils.formatStringToYMDData(END);

    public Date selectedDate;


    public TimeViewUtil.TimeType type = TimeViewUtil.TimeType.ALL;

    public boolean isHalf=false;


    public boolean isShowWeek=false;


    public String morning_work_time;




    public TimeEvent(Date starDate, Date endDate, Date selectedDate, TimeViewUtil.TimeType type) {
        this.starDate = starDate;
        this.endDate = endDate;
        this.selectedDate = selectedDate;
        this.type = type;
    }

    public TimeEvent(Date starDate, Date endDate, Date selectedDate) {
        this.starDate = starDate;
        this.endDate = endDate;
        this.selectedDate = selectedDate;
    }



    public TimeEvent(Date starDate, Date selectedDate) {
        this.starDate = starDate;
        this.selectedDate = selectedDate;
    }

    /**
     * 开始时间 选中时间 类型
     *
     * @param starDate
     * @param selectedDate
     * @param type
     */
    public TimeEvent(Date starDate, Date selectedDate, TimeViewUtil.TimeType type) {
        this.starDate = starDate;
        this.selectedDate = selectedDate;
        this.type = type;
    }

    /**
     * 选中时间 类型
     *
     * @param selectedDate
     * @param type
     */
    public TimeEvent(Date selectedDate, TimeViewUtil.TimeType type) {
        this.selectedDate = selectedDate;
        this.type = type;
    }
     /**
     * 选中时间 类型
     *
     * @param selectedDate
     * @param type
     */
    public TimeEvent(Date selectedDate, TimeViewUtil.TimeType type, boolean isShowWeek) {
        this.selectedDate = selectedDate;
        this.type = type;
        this.isShowWeek=isShowWeek;
    }

    /**
     * 选中时间 类型
     *
     * @param selectedDate
     * @param type
     */
    public TimeEvent(Date selectedDate, String morn, TimeViewUtil.TimeType type, boolean isHalf) {
        this.selectedDate = selectedDate;
        this.type = type;
        this.morning_work_time=morn;
        this.isHalf=isHalf;
    }

    /**
     * 选中时间 类型
     *
     * @param selectedDate
     */
    public TimeEvent(Date selectedDate) {
        this.selectedDate = selectedDate;
    }

    public TimeEvent() {
    }
}