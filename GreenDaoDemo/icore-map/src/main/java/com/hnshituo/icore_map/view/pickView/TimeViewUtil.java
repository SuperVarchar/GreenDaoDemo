package com.hnshituo.icore_map.view.pickView;

import android.content.Context;

import com.hnshituo.icore_map.R;
import com.hnshituo.icore_map.util.DateUtils;
import com.hnshituo.icore_map.view.pickView.bean.TimeEvent;

import java.util.Calendar;
import java.util.Date;

import static com.hnshituo.icore_map.util.DateUtils.getPreFiveDay;
import static com.hnshituo.icore_map.view.pickView.TimeViewUtil.TimeType.YMD_HM;


/**
 * create by liuba
 */

public class TimeViewUtil {

    static TimeViewUtil mPickViewUtil;
    MyTimePickerView pvTime;


    MyTimePickerView mMyTimePickerView;


    MyTimePickerView mMyTimeHourMinus;

    public static TimeViewUtil getInstance() {
        if (mPickViewUtil == null) {
            mPickViewUtil = new TimeViewUtil();
        }
        return mPickViewUtil;
    }

    public enum TimeType {
        YYYY, MM, YM, YMD, ALL, YMD_HM, HM
    }

    /**
     *  当前时间前5天
     * @param context
     * @param event
     * @param listener
     */
    public void showPreFiveTime(Context context,TimeEvent event,MyTimePickerView.OnTimeSelectListener listener) {
        //设置当前选中的时间 默认是当前时间
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        //startDate.set(2013,1,1);
        Calendar endDate = Calendar.getInstance();
        if(event.type==YMD_HM){
            String str= DateUtils.getYearMonthDay(DateUtils.getPreFiveDay(new Date()))+" "+event.morning_work_time;
            //默认只能到分钟
            event.starDate=DateUtils.formatStringToYMDHMDate(str);
        }else {
            event.starDate= getPreFiveDay(new Date());
        }


        if (event == null) {
            //endDate.set(2020,1,1);
            //正确设置方式 原因：注意事项有说明 设置年月日
            startDate.set(1990, 0, 1, 0, 0, 0);
            endDate.set(2015, 11, 31, 23, 59, 59);

            int slectY = Integer.parseInt(DateUtils.getYear(new Date()));
            int slectM = Integer.parseInt(DateUtils.getMonth(new Date())) - 1;
            int slectD = Integer.parseInt(DateUtils.getDay(new Date()));
            int slectHour = Integer.parseInt(DateUtils.getHour(new Date()));
            int slectMin = Integer.parseInt(DateUtils.getMin(new Date()));

            selectedDate.set(slectY, slectM, slectD, slectHour, slectMin);
        } else {
            if(event.selectedDate==null){
                event.selectedDate=new Date();
            }

            int starY = Integer.parseInt(DateUtils.getYear(event.starDate));
            int starM = Integer.parseInt(DateUtils.getMonth(event.starDate)) - 1;
            int starD = Integer.parseInt(DateUtils.getDay(event.starDate));
            int starHour = Integer.parseInt(DateUtils.getHour(event.starDate));
            int starmm = Integer.parseInt(DateUtils.getMin(event.starDate));
            int starS = Integer.parseInt(DateUtils.getSecond(event.starDate));
            startDate.set(starY, starM, starD, starHour, starmm);


            int endY = Integer.parseInt(DateUtils.getYear(event.endDate));
            int endM = Integer.parseInt(DateUtils.getMonth(event.endDate)) - 1;
            int endD = Integer.parseInt(DateUtils.getDay(event.endDate));
            int endHour = Integer.parseInt(DateUtils.getHour(event.endDate));
            int endmm = Integer.parseInt(DateUtils.getMin(event.endDate));

            int endS = Integer.parseInt(DateUtils.getSecond(event.endDate));
            endDate.set(endY, endM, endD, endHour, endmm);

            int slectY = Integer.parseInt(DateUtils.getYear(event.selectedDate));
            int slectM = Integer.parseInt(DateUtils.getMonth(event.selectedDate)) - 1;
            int slectD = Integer.parseInt(DateUtils.getDay(event.selectedDate));
            int slectHour = Integer.parseInt(DateUtils.getHour(event.selectedDate));
            int slectMin = Integer.parseInt(DateUtils.getMin(event.selectedDate));

            selectedDate.set(slectY, slectM, slectD, slectHour, slectMin);
        }

        pvTime = new MyTimePickerView.Builder(context, listener)
                .setType(getType(event.type))// 默认全部显示
                .setCancelText(context.getResources().getString(R.string.cancel))//取消按钮文字
                .setSubmitText(context.getResources().getString(R.string.sure))//确认按钮文字
                .setContentSize(16)//滚轮文字大小
                .setTitleSize(20)//标题文字大小
                .setTitleText("请选择时间")//标题文字
                .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(false)//是否循环滚动
                .setTitleColor(context.getResources().getColor(R.color.color_808080))//标题文字颜色
                .setSubmitColor(context.getResources().getColor(R.color.color_ff474e))//确定按钮文字颜色
                .setCancelColor(context.getResources().getColor(R.color.color_ff474e))//取消按钮文字颜色
                .setTitleBgColor(0xFFF5F5F5)//标题背景颜色 Night mode
                .setBgColor(0xFFFFFFFF)//滚轮背景颜色 Night mode
                .setDate(selectedDate)// 如果不设置的话，默认是系统时间
                .setRangDate(startDate, endDate)//起始终止年月日设定
                .setLabel("年", "月", "日", "时", "分", "秒")//默认设置为年月日时分秒
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isDialog(false)//是否显示为对话框样式
                .setHalfHour(event.isHalf)
                .build();
        pvTime.show();
    }

    /**
     * 限制到时分
     *
     * @param context
     * @param event
     * @param listener
     */
    public void showTime(Context context, TimeEvent event, MyTimePickerView.OnTimeSelectListener listener) {
        //设置当前选中的时间 默认是当前时间
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        //startDate.set(2013,1,1);
        Calendar endDate = Calendar.getInstance();
        if (event == null) {
            //endDate.set(2020,1,1);
            //正确设置方式 原因：注意事项有说明 设置年月日
            startDate.set(1990, 0, 1, 0, 0, 0);
            endDate.set(2015, 11, 31, 23, 59, 59);

            int slectY = Integer.parseInt(DateUtils.getYear(new Date()));
            int slectM = Integer.parseInt(DateUtils.getMonth(new Date())) - 1;
            int slectD = Integer.parseInt(DateUtils.getDay(new Date()));
            int slectHour = Integer.parseInt(DateUtils.getHour(new Date()));
            int slectMin = Integer.parseInt(DateUtils.getMin(new Date()));

            selectedDate.set(slectY, slectM, slectD, slectHour, slectMin);
        } else {
            if(event.selectedDate==null){
                event.selectedDate=new Date();
            }


            int starY = Integer.parseInt(DateUtils.getYear(event.starDate));
            int starM = Integer.parseInt(DateUtils.getMonth(event.starDate)) - 1;
            int starD = Integer.parseInt(DateUtils.getDay(event.starDate));
            int starHour = Integer.parseInt(DateUtils.getHour(event.starDate));
            int starmm = Integer.parseInt(DateUtils.getMin(event.starDate));
            int starS = Integer.parseInt(DateUtils.getSecond(event.starDate));
            startDate.set(starY, starM, starD, starHour, starmm);


            int endY = Integer.parseInt(DateUtils.getYear(event.endDate));
            int endM = Integer.parseInt(DateUtils.getMonth(event.endDate)) - 1;
            int endD = Integer.parseInt(DateUtils.getDay(event.endDate));
            int endHour = Integer.parseInt(DateUtils.getHour(event.endDate));
            int endmm = Integer.parseInt(DateUtils.getMin(event.endDate));

            int endS = Integer.parseInt(DateUtils.getSecond(event.endDate));
            endDate.set(endY, endM, endD, endHour, endmm);

            int slectY = Integer.parseInt(DateUtils.getYear(event.selectedDate));
            int slectM = Integer.parseInt(DateUtils.getMonth(event.selectedDate)) - 1;
            int slectD = Integer.parseInt(DateUtils.getDay(event.selectedDate));
            int slectHour = Integer.parseInt(DateUtils.getHour(event.selectedDate));
            int slectMin = Integer.parseInt(DateUtils.getMin(event.selectedDate));

            selectedDate.set(slectY, slectM, slectD, slectHour, slectMin);
        }

        mMyTimeHourMinus = new MyTimePickerView.Builder(context, listener)
                .setType(getType(event.type))// 默认全部显示
                .setCancelText(context.getResources().getString(R.string.cancel))//取消按钮文字
                .setSubmitText(context.getResources().getString(R.string.sure))//确认按钮文字
                .setContentSize(16)//滚轮文字大小
                .setTitleSize(20)//标题文字大小
                .setTitleText("请选择时间")//标题文字
                .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(false)//是否循环滚动
                .setTitleColor(context.getResources().getColor(R.color.color_808080))//标题文字颜色
                .setSubmitColor(context.getResources().getColor(R.color.color_ff474e))//确定按钮文字颜色
                .setCancelColor(context.getResources().getColor(R.color.color_ff474e))//取消按钮文字颜色
                .setTitleBgColor(0xFFF5F5F5)//标题背景颜色 Night mode
                .setBgColor(0xFFFFFFFF)//滚轮背景颜色 Night mode
                .setDate(selectedDate)// 如果不设置的话，默认是系统时间
                .setRangDate(startDate, endDate)//起始终止年月日设定
                .setLabel("年", "月", "日", "时", "分", "秒")//默认设置为年月日时分秒
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isDialog(false)//是否显示为对话框样式
                .setHalfHour(false)
                .setIsShowWeek(event.isShowWeek)//是否显示为星期几
                .build();
        mMyTimeHourMinus.show();
    }

    private boolean[] getType(TimeType type) {
        boolean[] types;
        switch (type) {
            case YYYY:
                types = new boolean[]{true, false, false, false, false, false};
                break;
            case MM:
                types = new boolean[]{false, true, false, false, false, false};
                break;
            case YM:
                types = new boolean[]{true, true, false, false, false, false};
                break;
            case YMD:
                types = new boolean[]{true, true, true, false, false, false};
                break;
            case ALL:
                types = new boolean[]{true, true, true, true, true, true};
                break;
            case YMD_HM:
                types = new boolean[]{true, true, true, true, true, false};
                break;
            case HM:
                types = new boolean[]{false, false, false, false, true, true};
                break;

            default:
                types = new boolean[]{true, true, true, true, true, true};
                break;
        }
        return types;
    }

    public void close() {
        if (pvTime != null) {
            pvTime.dismiss();
        }
        if (mMyTimePickerView != null) {
            mMyTimePickerView.dismiss();
        }
    }

    /**
     * @param context
     * @param event
     * @param listener 只显示0和半个小时
     */
    public void showHalfTime(Context context, TimeEvent event, MyTimePickerView.OnTimeSelectListener listener) {
        //设置当前选中的时间 默认是当前时间
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        //startDate.set(2013,1,1);
        Calendar endDate = Calendar.getInstance();
        if (event == null) {
            //endDate.set(2020,1,1);
            //正确设置方式 原因：注意事项有说明 设置年月日
            startDate.set(1990, 0, 1, 0, 0, 0);
            endDate.set(2015, 11, 31, 23, 59, 59);

            int slectY = Integer.parseInt(DateUtils.getYear(new Date()));
            int slectM = Integer.parseInt(DateUtils.getMonth(new Date())) - 1;
            int slectD = Integer.parseInt(DateUtils.getDay(new Date()));
            int slectHour = Integer.parseInt(DateUtils.getHour(new Date()));
            int slectMin = Integer.parseInt(DateUtils.getMin(new Date()));

            selectedDate.set(slectY, slectM, slectD, slectHour, slectMin);
        } else {
            if(event.selectedDate==null){
                event.selectedDate=new Date();
            }

            int starY = Integer.parseInt(DateUtils.getYear(event.starDate));
            int starM = Integer.parseInt(DateUtils.getMonth(event.starDate)) - 1;
            int starD = Integer.parseInt(DateUtils.getDay(event.starDate));
            int starHour = Integer.parseInt(DateUtils.getHour(event.starDate));
            int starmm = Integer.parseInt(DateUtils.getMin(event.starDate));
            int starS = Integer.parseInt(DateUtils.getSecond(event.starDate));
            startDate.set(starY, starM, starD, starHour, starmm);


            int endY = Integer.parseInt(DateUtils.getYear(event.endDate));
            int endM = Integer.parseInt(DateUtils.getMonth(event.endDate)) - 1;
            int endD = Integer.parseInt(DateUtils.getDay(event.endDate));
            int endHour = Integer.parseInt(DateUtils.getHour(event.endDate));
            int endmm = Integer.parseInt(DateUtils.getMin(event.endDate));

            int endS = Integer.parseInt(DateUtils.getSecond(event.endDate));
            endDate.set(endY, endM, endD, endHour, endmm);

            int slectY = Integer.parseInt(DateUtils.getYear(event.selectedDate));
            int slectM = Integer.parseInt(DateUtils.getMonth(event.selectedDate)) - 1;
            int slectD = Integer.parseInt(DateUtils.getDay(event.selectedDate));
            int slectHour = Integer.parseInt(DateUtils.getHour(event.selectedDate));
            int slectMin = Integer.parseInt(DateUtils.getMin(event.selectedDate));

            selectedDate.set(slectY, slectM, slectD, slectHour, slectMin);
        }
        mMyTimePickerView = new MyTimePickerView.Builder(context, listener)
                .setType(getType(YMD_HM))// 默认全部显示 设置分钟的显示
                .setCancelText(context.getResources().getString(R.string.cancel))//取消按钮文字
                .setSubmitText(context.getResources().getString(R.string.sure))//确认按钮文字
                .setContentSize(16)//滚轮文字大小
                .setTitleSize(20)//标题文字大小
                .setTitleText("请选择时间")//标题文字
                .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(false)//是否循环滚动
                .setTitleColor(context.getResources().getColor(R.color.color_808080))//标题文字颜色
                .setSubmitColor(context.getResources().getColor(R.color.color_ff474e))//确定按钮文字颜色
                .setCancelColor(context.getResources().getColor(R.color.color_ff474e))//取消按钮文字颜色
                .setTitleBgColor(0xFFF5F5F5)//标题背景颜色 Night mode
                .setBgColor(0xFFFFFFFF)//滚轮背景颜色 Night mode
                .setDate(selectedDate)// 如果不设置的话，默认是系统时间
                .setRangDate(startDate, endDate)//起始终止年月日设定
                .setLabel("年", "月", "日", "时", "分", "秒")//默认设置为年月日时分秒
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isDialog(false)//是否显示为对话框样式
                .setHalfHour(true)
                .build();
        mMyTimePickerView.show();
    }
}
