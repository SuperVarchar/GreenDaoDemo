package com.hnshituo.icore_map.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

/**
 * 日期工具类
 *
 */
public class DateUtils {

    public static Date formatStringToData(String data){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.CHINA);
        try {
            return sdf.parse(data);
        } catch (ParseException e) {
            return  null;
        }
    }
    public static Date formatStringToYMDHMDate(String data){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm",Locale.CHINA);
        try {
            return sdf.parse(data);
        } catch (ParseException e) {
            return  null;
        }
    }
    public static Date formatStringToYMDData(String data){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",Locale.CHINA);
        try {
            return sdf.parse(data);
        } catch (ParseException e) {
            return  null;
        }
    }

    public static Date formatStringToYData(String data){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy",Locale.CHINA);
        try {
            return sdf.parse(data);
        } catch (ParseException e) {
            return  null;
        }
    }
    public static Date formatStringToYMData(String data){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM",Locale.CHINA);
        try {
            return sdf.parse(data);
        } catch (ParseException e) {
            return  null;
        }
    }


    public static String getMonth(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("MM",Locale.CHINA);
        return format.format(date);
    }

    public static String getDay(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("dd",Locale.CHINA);
        return format.format(date);
    }

    public static String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm",Locale.CHINA);
        return format.format(date);
    }
    public static String getMin(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("mm",Locale.CHINA);
        return format.format(date);
    }
    public static String getHour(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("HH",Locale.CHINA);
        return format.format(date);
    }
    public static String getSecond(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("ss",Locale.CHINA);
        return format.format(date);
    }

    public static String getYear(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy",Locale.CHINA);
        return format.format(date);
    }

    /**
     * 获取完整的时间
     * @param date
     * @return
     */
    public static String getAllTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.CHINA);
        return format.format(date);

    }

    /**
     * 获取年月日
     * @param date
     * @return
     */
    public static String getYearMonthDay(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd",Locale.CHINA);
        return format.format(date);
    }
   /**
     * 获取年月日时
     * @param date
     * @return
     */
    public static String getYMDHourDay(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH",Locale.CHINA);
        return format.format(date);
    }
    /**
     * 获取年月
     * @param date
     * @return
     */
    public static String getYearMonth(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM",Locale.CHINA);
        return format.format(date);
    }
    /**
     * 获取时分秒
     * @param date
     * @return
     */
    public static String getHourMinS(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss",Locale.CHINA);
        return format.format(date);
    }

    /**
     * 获取时分秒
     * @param date
     * @return
     */
    public static String getYMDHourMin(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm",Locale.CHINA);
        return format.format(date);
    }

    /**
     * 获取日期所在月的最开始时间
     * @return
     */
    public static String getFirstDay(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
        gcLast.setTime(date);
        gcLast.set(Calendar.DAY_OF_MONTH, 1);
        String day_first = df.format(gcLast.getTime());
        StringBuffer str = new StringBuffer().append(day_first).append(" 00:00:00");
        return str.toString();

    }

    /**
     * 获取日期所在月的最后时间
     * @return
     */
    public static String getLastDay(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        ca.set(Calendar.DAY_OF_MONTH,
                ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        return format.format(ca.getTime()) +" 23:59:59" ;

    }

    /**
     * 获取年月日时分的日期
     * @return
     */
    public static Date getYMDHM(Date date) {
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        instance.set(Calendar.SECOND,0);
        instance.set(Calendar.MILLISECOND,0);
        return instance.getTime();

    }

    /**
     * 获取年月日时分的日期
     * @return
     */
    public static int getWeek(Date date)   {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cl = Calendar.getInstance();
        try {
            cl.setTime(sdf.parse(DateUtils.getYearMonthDay(date)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return cl.get(Calendar.WEEK_OF_YEAR);
    }
    // 获取当前时间所在年的周数
    public static int getWeekOfYear(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setMinimalDaysInFirstWeek(7);
        c.setTime(date);
        return c.get(Calendar.WEEK_OF_YEAR);
    }
    // 获取当前时间所在年的最大周数
    public static int getMaxWeekNumOfYear(Date date) {
        Calendar c = new GregorianCalendar();
        c.set(Integer.parseInt(DateUtils.getYear(date)),Calendar.DECEMBER, 31, 23, 59, 59);
        return getWeekOfYear(c.getTime());
    }

    //取日期所在星期
    public static int getWeekOfDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return (calendar.get(Calendar.DAY_OF_WEEK) - 1) == 0 ? 7 : calendar.get(Calendar.DAY_OF_WEEK) - 1;
    }



    /**
     * 获取一个时间段的月份
     *
     * @return
     */
    public static List<Date> getRangeMonth() {
        List<Date> dates = new ArrayList<>();

        try {
            Date d1 = new SimpleDateFormat("yyyy-MM").parse("1970-1");
            Date d2 = new SimpleDateFormat("yyyy-MM").parse("2050-12");//定义结束日期
            Calendar dd = Calendar.getInstance();//定义日期实例
            dd.setTime(d1);//设置日期起始时间
            while (dd.getTime().before(d2)) {//判断是否到结束日期
                dates.add(dd.getTime());
                dd.add(Calendar.MONTH, 1);//进行当前日期月份加1

            }
            return dates;
        } catch (ParseException e) {
            e.printStackTrace();
            return dates;
        }

    }

    /**
     * 获取当前时间与目标时间的月份差
     *
     * @param date2
     * @return
     */
    public static int countMonths(String date1, String date2) {

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");

            Calendar c1 = Calendar.getInstance();
            Calendar c2 = Calendar.getInstance();

            c1.setTime(sdf.parse(date1));
            c2.setTime(sdf.parse(date2));

            int year = c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR);

            //开始日期若小月结束日期
            if (year < 0) {
                year = -year;
                return year * 12 + c1.get(Calendar.MONTH) - c2.get(Calendar.MONTH);
            }
            return year * 12 + c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH);
        } catch (ParseException e) {
            e.printStackTrace();
            return -1;
        }
    }
    /**
     * 获取当前月的时间
     *
     * @param date
     * @return
     */
    public static String getCurrentMonth(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        return sdf.format(date);

    }



    /**
     * 获取下一个月的时间
     *
     * @param date
     * @return
     */
    public static String getNextMonth(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Calendar c1 = Calendar.getInstance();
        c1.setTime(date);
        c1.add(Calendar.MONTH, 1);
        return sdf.format(c1.getTime());
    }
    /**
     * 获取前一个月的时间
     *
     * @param date
     * @return
     */
    public static String getPreMonth(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Calendar c1 = Calendar.getInstance();
        c1.setTime(date);
        c1.add(Calendar.MONTH, -1);
        return sdf.format(c1.getTime());
    }
    /**
     * 获取日期年月
     *
     * @return
     */
    public static String getYM(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
        return format.format(date);

    }

    public static String formatYMDHMDataToString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        return sdf.format(date);
    }

    public static int daysBetween(Date early, Date late) {

        Calendar calst = Calendar.getInstance();
        Calendar caled = Calendar.getInstance();
        calst.setTime(early);
        caled.setTime(late);
        //设置时间为0时
        calst.set(Calendar.HOUR_OF_DAY, 0);
        calst.set(Calendar.MINUTE, 0);
        calst.set(Calendar.SECOND, 0);
        caled.set(Calendar.HOUR_OF_DAY, 0);
        caled.set(Calendar.MINUTE, 0);
        caled.set(Calendar.SECOND, 0);

        if (!caled.after(calst)) {
            return 0;
        }

        //得到两个日期相差的天数
        int days = ((int) (caled.getTime().getTime() / 1000) - (int) (calst
                .getTime().getTime() / 1000)) / 3600 / 24;

        return days;
    }


    public static String getHM(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(date);

    }

    public  static String getHM(String date) {
        Date time = formatStringToData(date);
        if (time!=null) {
            return getHM(time);
        }else {
            return "";
        }

    }


    public  static String getYMDHM(String date) {
        Date time = formatStringToData(date);
        if (time!=null) {
            return getYMDHourMin(time);
        }else {
            return "";
        }

    }
    //获取前5天
    public static Date getPreFiveDay(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE,-5);
        return calendar.getTime();
    }
    //获取前7天
    public static Date getPreSevenDay(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE,-7);
        return calendar.getTime();
    }
     //获取后7天
    public static Date getNextSevenDay(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE,+7);
        return calendar.getTime();
    }   //获取前7天
    public static Date getPreSixDay(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE,-6);
        return calendar.getTime();
    }
     //获取后7天
    public static Date getNextSixDay(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE,+6);
        return calendar.getTime();
    }



    //获取前5天

    // 获取当前时间所在周的开始日期
    public static Date getFirstDayOfWeek(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday
        return c.getTime();
    }
    // 获取当前时间下周的开始日期
    public static Date getFirstDayOfNextWeek(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday
        c.add(Calendar.DATE,7);
        return c.getTime();
    }

    // 获取当前时间所在周的结束日期
    public static Date getLastDayOfWeek(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK,(c.getFirstDayOfWeek() + 6)); // Sunday

        return c.getTime();
    }
  // 获取当前时间下周的结束日期
    public static Date getLastDayOfNextWeek(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, (c.getFirstDayOfWeek() + 6)); // Sunday
        c.add(Calendar.DATE,7);
        return c.getTime();
    }

    public static Date getCurrentMonthFirstDay(){
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天
        return c.getTime();
    }
    public static Date getCurrentMonthLastDay(){
           //获取当前月最后一天
           Calendar ca = Calendar.getInstance();
        ca.add(Calendar.MONTH, 0);
           ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        return ca.getTime();
    }
    public static Date getNextMonthFirstDay(){
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 1);
        c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天
        return c.getTime();
    }
    public static Date getNextMonthLastDay(){
           //获取当前月最后一天
           Calendar ca = Calendar.getInstance();
            ca.add(Calendar.MONTH, 1);
           ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        return ca.getTime();
    }

    public static Date getMondayOfThisWeek() {
        Calendar c = Calendar.getInstance();
        int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0)
            day_of_week = 7;
        c.add(Calendar.DATE, -day_of_week + 1);
        return c.getTime();
    }

    /**
     * 得到本周周日
     *
     * @return yyyy-MM-dd
     */
    public static Date getSundayOfThisWeek() {
        Calendar c = Calendar.getInstance();
        int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0)
            day_of_week = 7;
        c.add(Calendar.DATE, -day_of_week + 7);
        return c.getTime();
    }
}
