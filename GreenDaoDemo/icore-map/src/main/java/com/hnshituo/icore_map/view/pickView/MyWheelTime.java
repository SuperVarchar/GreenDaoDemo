package com.hnshituo.icore_map.view.pickView;

import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.adapter.ArrayWheelAdapter;
import com.bigkoo.pickerview.adapter.NumericWheelAdapter;
import com.bigkoo.pickerview.lib.WheelView;
import com.bigkoo.pickerview.listener.OnItemSelectedListener;
import com.bigkoo.pickerview.utils.ChinaDate;
import com.bigkoo.pickerview.utils.LunarCalendar;
import com.bigkoo.pickerview.view.WheelTime;
import com.hnshituo.icore_map.R;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class MyWheelTime {
    public static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private View view;
    private WheelView wv_year;
    private WheelView wv_month;
    private WheelView wv_day;
    private WheelView wv_hours;
    private WheelView wv_mins;
    private WheelView wv_seconds;

    private TextView weekTv;
    private int gravity;

    private boolean[] type;
    private static final int DEFAULT_START_YEAR = 1900;
    private static final int DEFAULT_END_YEAR = 2100;
    private static final int DEFAULT_START_MONTH = 1;
    private static final int DEFAULT_END_MONTH = 12;
    private static final int DEFAULT_START_DAY = 1;
    private static final int DEFAULT_END_DAY = 31;

    private int startYear = DEFAULT_START_YEAR;
    private int endYear = DEFAULT_END_YEAR;
    private int startMonth = DEFAULT_START_MONTH;
    private int endMonth = DEFAULT_END_MONTH;
    private int startDay = DEFAULT_START_DAY;
    private int endDay = DEFAULT_END_DAY; //表示31天的


    private int currentYear;


    private int starHour = 0;
    private int endHour = 23;

    private int starMinus = 0;
    private int endMinus = 59;


    private Boolean isNeedHalf;

    private Boolean isShowWeek;

    // 根据屏幕密度来指定选择器字体的大小(不同屏幕可能不同)
    private int textSize = 18;
    //文字的颜色和分割线的颜色
    int textColorOut;
    int textColorCenter;
    int dividerColor;
    // 条目间距倍数
    float lineSpacingMultiplier = 1.6F;

    private WheelView.DividerType dividerType;

    private boolean isLunarCalendar = false;

    public MyWheelTime(View view) {
        super();
        this.view = view;
        type = new boolean[]{true, true, true, true, true, true};
        setView(view);
    }

    public MyWheelTime(View view, boolean[] type, int gravity, int textSize) {
        super();
        this.view = view;
        this.type = type;
        this.gravity = gravity;
        this.textSize = textSize;
        setView(view);
    }


    public void setLunarCalendar(boolean isLunarCalendar) {
        this.isLunarCalendar = isLunarCalendar;
    }

    public boolean isLunarCalendar() {
        return isLunarCalendar;
    }

    public void setPicker(int year, int month, int day) {
        this.setPicker(year, month, day, 0, 0, 0, 0);
    }

    public void setPicker(int year, final int month, int day, int h, int m, int s, int week) {
        if (isLunarCalendar) {
            int[] lunar = LunarCalendar.solarToLunar(year, month + 1, day);
            setLunar(lunar[0], lunar[1], lunar[2], lunar[3] == 1, h, m, s);
        } else {
            setSolar(year, month, day, h, m, s, week);
        }
    }

    /**
     * 设置农历
     *
     * @param year
     * @param month
     * @param day
     * @param h
     * @param m
     * @param s
     */
    private void setLunar(int year, final int month, int day, boolean isLeap, int h, int m, int s) {
        // 年
        wv_year = (WheelView) view.findViewById(R.id.year);
        wv_year.setAdapter(new ArrayWheelAdapter(ChinaDate.getYears(startYear, endYear)));// 设置"年"的显示数据
        wv_year.setLabel("");// 添加文字
        wv_year.setCurrentItem(year - startYear);// 初始化时显示的数据
        wv_year.setGravity(gravity);

        // 月
        wv_month = (WheelView) view.findViewById(R.id.month);
        wv_month.setAdapter(new ArrayWheelAdapter(ChinaDate.getMonths(year)));
        wv_month.setLabel("");
        wv_month.setCurrentItem(month);
        wv_month.setGravity(gravity);

        // 日
        wv_day = (WheelView) view.findViewById(R.id.day);
        // 判断大小月及是否闰年,用来确定"日"的数据
        if (ChinaDate.leapMonth(year) == 0) {
            wv_day.setAdapter(new ArrayWheelAdapter(ChinaDate.getLunarDays(ChinaDate.monthDays(year, month))));
        } else {
            wv_day.setAdapter(new ArrayWheelAdapter(ChinaDate.getLunarDays(ChinaDate.leapDays(year))));
        }
        wv_day.setLabel("");
        wv_day.setCurrentItem(day - 1);
        wv_day.setGravity(gravity);

        wv_hours = (WheelView) view.findViewById(R.id.hour);
        wv_hours.setAdapter(new NumericWheelAdapter(0, 23));
        //wv_hours.setLabel(context.getString(R.string.pickerview_hours));// 添加文字
        wv_hours.setCurrentItem(h);
        wv_hours.setGravity(gravity);

        wv_mins = (WheelView) view.findViewById(R.id.min);
        wv_mins.setAdapter(new NumericWheelAdapter(0, 59));
        //wv_mins.setLabel(context.getString(R.string.pickerview_minutes));// 添加文字
        wv_mins.setCurrentItem(m);
        wv_mins.setGravity(gravity);

        wv_seconds = (WheelView) view.findViewById(R.id.second);
        wv_seconds.setAdapter(new NumericWheelAdapter(0, 59));
        //wv_seconds.setLabel(context.getString(R.string.pickerview_minutes));// 添加文字
        wv_seconds.setCurrentItem(m);
        wv_seconds.setGravity(gravity);

        // 添加"年"监听
        OnItemSelectedListener wheelListener_year = new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                int year_num = index + startYear;
                // 判断是不是闰年,来确定月和日的选择
                wv_month.setAdapter(new ArrayWheelAdapter(ChinaDate.getMonths(year_num)));
                if (ChinaDate.leapMonth(year_num) != 0 && wv_month.getCurrentItem() > ChinaDate.leapMonth(year_num) - 1) {
                    wv_month.setCurrentItem(wv_month.getCurrentItem() + 1);
                } else {
                    wv_month.setCurrentItem(wv_month.getCurrentItem());
                }

                int maxItem = 29;
                if (ChinaDate.leapMonth(year_num) != 0 && wv_month.getCurrentItem() > ChinaDate.leapMonth(year_num) - 1) {
                    if (wv_month.getCurrentItem() == ChinaDate.leapMonth(year_num) + 1) {
                        wv_day.setAdapter(new ArrayWheelAdapter(ChinaDate.getLunarDays(ChinaDate.leapDays(year_num))));
                        maxItem = ChinaDate.leapDays(year_num);
                    } else {
                        wv_day.setAdapter(new ArrayWheelAdapter(ChinaDate.getLunarDays(ChinaDate.monthDays(year_num, wv_month.getCurrentItem()))));
                        maxItem = ChinaDate.monthDays(year_num, wv_month.getCurrentItem());
                    }
                } else {
                    wv_day.setAdapter(new ArrayWheelAdapter(ChinaDate.getLunarDays(ChinaDate.monthDays(year_num, wv_month.getCurrentItem() + 1))));
                    maxItem = ChinaDate.monthDays(year_num, wv_month.getCurrentItem() + 1);
                }

                if (wv_day.getCurrentItem() > maxItem - 1) {
                    wv_day.setCurrentItem(maxItem - 1);
                }
            }
        };
        // 添加"月"监听
        OnItemSelectedListener wheelListener_month = new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                int month_num = index;
                int year_num = wv_year.getCurrentItem() + startYear;
                int maxItem = 29;
                if (ChinaDate.leapMonth(year_num) != 0 && month_num > ChinaDate.leapMonth(year_num) - 1) {
                    if (wv_month.getCurrentItem() == ChinaDate.leapMonth(year_num) + 1) {
                        wv_day.setAdapter(new ArrayWheelAdapter(ChinaDate.getLunarDays(ChinaDate.leapDays(year_num))));
                        maxItem = ChinaDate.leapDays(year_num);
                    } else {
                        wv_day.setAdapter(new ArrayWheelAdapter(ChinaDate.getLunarDays(ChinaDate.monthDays(year_num, month_num))));
                        maxItem = ChinaDate.monthDays(year_num, month_num);
                    }
                } else {
                    wv_day.setAdapter(new ArrayWheelAdapter(ChinaDate.getLunarDays(ChinaDate.monthDays(year_num, month_num + 1))));
                    maxItem = ChinaDate.monthDays(year_num, month_num + 1);
                }

                if (wv_day.getCurrentItem() > maxItem - 1) {
                    wv_day.setCurrentItem(maxItem - 1);
                }

            }
        };
        wv_year.setOnItemSelectedListener(wheelListener_year);
        wv_month.setOnItemSelectedListener(wheelListener_month);


        if (type.length != 6) {
            throw new RuntimeException("type[] length is not 6");
        }
        wv_year.setVisibility(type[0] ? View.VISIBLE : View.GONE);
        wv_month.setVisibility(type[1] ? View.VISIBLE : View.GONE);
        wv_day.setVisibility(type[2] ? View.VISIBLE : View.GONE);
        wv_hours.setVisibility(type[3] ? View.VISIBLE : View.GONE);
        wv_mins.setVisibility(type[4] ? View.VISIBLE : View.GONE);
        wv_seconds.setVisibility(type[5] ? View.VISIBLE : View.GONE);
        setContentTextSize();
    }

    /**
     * 设置公历
     *
     * @param year
     * @param month
     * @param day
     * @param h
     * @param m
     * @param s
     */
    private void setSolar(int year, final int month, final int day, int h, int m, int s, int w) {
        // 添加大小月月份并将其转换为list,方便之后的判断
        String[] months_big = {"1", "3", "5", "7", "8", "10", "12"};
        String[] months_little = {"4", "6", "9", "11"};
        final String[] week = {"星期天", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};


        weekTv = (TextView) view.findViewById(R.id.week);
        if (isShowWeek) {
            weekTv.setVisibility(View.VISIBLE);
            weekTv.setText(week[w - 1]);
        } else {
            weekTv.setVisibility(View.GONE);
        }

        final List<String> list_big = Arrays.asList(months_big);
        final List<String> list_little = Arrays.asList(months_little);

      /*  final Context context = view.getContext();*/
        currentYear = year;

        // 年
        wv_year = (WheelView) view.findViewById(R.id.year);
        wv_year.setAdapter(new NumericWheelAdapter(startYear, endYear));// 设置"年"的显示数据
        /*wv_year.setLabel(context.getString(R.string.pickerview_year));// 添加文字*/
        int yea = year - startYear;

        wv_year.setCurrentItem(year - startYear);// 初始化时显示的数据
        wv_year.setGravity(gravity);
        // 月
        wv_month = (WheelView) view.findViewById(R.id.month);
        if (startYear == endYear) {//开始年等于终止年
            wv_month.setAdapter(new NumericWheelAdapter(startMonth, endMonth));
            wv_month.setCurrentItem(month + 1 - startMonth);
        } else if (year == startYear) {
            //起始日期的月份控制
            wv_month.setAdapter(new NumericWheelAdapter(startMonth, 12));
            wv_month.setCurrentItem(month + 1 - startMonth);
        } else if (year == endYear) {
            //终止日期的月份控制
            wv_month.setAdapter(new NumericWheelAdapter(1, endMonth));
            wv_month.setCurrentItem(month);
        } else {
            wv_month.setAdapter(new NumericWheelAdapter(1, 12));
            wv_month.setCurrentItem(month);
        }
     /*   wv_month.setLabel(context.getString(R.string.pickerview_month));*/

        wv_month.setGravity(gravity);
        // 日
        wv_day = (WheelView) view.findViewById(R.id.day);

        if (startYear == endYear && startMonth == endMonth) {
            if (list_big.contains(String.valueOf(month + 1))) {
                if (endDay > 31) {
                    endDay = 31;
                }
                wv_day.setAdapter(new NumericWheelAdapter(startDay, endDay));
            } else if (list_little.contains(String.valueOf(month + 1))) {
                if (endDay > 30) {
                    endDay = 30;
                }
                wv_day.setAdapter(new NumericWheelAdapter(startDay, endDay));
            } else {
                // 闰年
                if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
                    if (endDay > 29) {
                        endDay = 29;
                    }
                    wv_day.setAdapter(new NumericWheelAdapter(startDay, endDay));
                } else {
                    if (endDay > 28) {
                        endDay = 28;
                    }
                    wv_day.setAdapter(new NumericWheelAdapter(startDay, endDay));
                }
            }
            wv_day.setCurrentItem(day - startDay);
        } else if (year == startYear && month + 1 == startMonth) {
            // 起始日期的天数控制
            if (list_big.contains(String.valueOf(month + 1))) {

                wv_day.setAdapter(new NumericWheelAdapter(startDay, 31));
            } else if (list_little.contains(String.valueOf(month + 1))) {

                wv_day.setAdapter(new NumericWheelAdapter(startDay, 30));
            } else {
                // 闰年
                if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {

                    wv_day.setAdapter(new NumericWheelAdapter(startDay, 29));
                } else {

                    wv_day.setAdapter(new NumericWheelAdapter(startDay, 28));
                }
            }
            wv_day.setCurrentItem(day - startDay);
        } else if (year == endYear && month + 1 == endMonth) {
            // 终止日期的天数控制
            if (list_big.contains(String.valueOf(month + 1))) {
                if (endDay > 31) {
                    endDay = 31;
                }
                wv_day.setAdapter(new NumericWheelAdapter(1, endDay));
            } else if (list_little.contains(String.valueOf(month + 1))) {
                if (endDay > 30) {
                    endDay = 30;
                }
                wv_day.setAdapter(new NumericWheelAdapter(1, endDay));
            } else {
                // 闰年
                if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
                    if (endDay > 29) {
                        endDay = 29;
                    }
                    wv_day.setAdapter(new NumericWheelAdapter(1, endDay));
                } else {
                    if (endDay > 28) {
                        endDay = 28;
                    }
                    wv_day.setAdapter(new NumericWheelAdapter(1, endDay));
                }
            }
            wv_day.setCurrentItem(day - 1);
        } else {
            // 判断大小月及是否闰年,用来确定"日"的数据
            if (list_big.contains(String.valueOf(month + 1))) {

                wv_day.setAdapter(new NumericWheelAdapter(1, 31));
            } else if (list_little.contains(String.valueOf(month + 1))) {

                wv_day.setAdapter(new NumericWheelAdapter(1, 30));
            } else {
                // 闰年
                if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {

                    wv_day.setAdapter(new NumericWheelAdapter(1, 29));
                } else {

                    wv_day.setAdapter(new NumericWheelAdapter(1, 28));
                }
            }
            wv_day.setCurrentItem(day - 1);
        }

       /* wv_day.setLabel(context.getString(R.string.pickerview_day));*/

        wv_day.setGravity(gravity);
        //时
        wv_hours = (WheelView) view.findViewById(R.id.hour);
        /**
         * 针对于时间设置到小时 todo
         *
         */
        // TODO: 2017/12/1   小时判断的本质
        if (startYear == endYear && (startMonth - 1) == (endMonth - 1) && startDay == endDay) {
            wv_hours.setAdapter(new NumericWheelAdapter(starHour, endHour));
            wv_hours.setCurrentItem(h - starHour);
        } else if (startYear == year && (startMonth - 1) == month && startDay == day) {
            wv_hours.setAdapter(new NumericWheelAdapter(starHour, 23));
            wv_hours.setCurrentItem(h - starHour);
        } else if (year == endYear && month == (endMonth - 1) && day == endDay) {
            wv_hours.setAdapter(new NumericWheelAdapter(0, endHour));
            wv_hours.setCurrentItem(h);
        } else {
            wv_hours.setAdapter(new NumericWheelAdapter(0, 23));
            wv_hours.setCurrentItem(h);
        }
      /*  wv_hours.setLabel(context.getString(R.string.pickerview_hours));// 添加文字*/

        wv_hours.setGravity(gravity);
        //分
        /**
         * 由于需求改动把分钟设置为半个小时
         */
        wv_mins = (WheelView) view.findViewById(R.id.min);
        if (startYear == endYear && (startMonth - 1) == (endMonth - 1) && startDay == endDay && starHour == endHour) {
            if (isNeedHalf) {
                wv_mins.setAdapter(new MyNumericWheelAdapter(starMinus == 0 ? 0 : 1, endMinus / 30));
                wv_mins.setCurrentItem(m / 30);
            } else {
                wv_mins.setAdapter(new NumericWheelAdapter(starMinus, endMinus));
                wv_mins.setCurrentItem(m - starMinus);
            }

        } else if (startYear == year && (startMonth - 1) == month && startDay == day && starHour == h) {

            if (isNeedHalf) {
                wv_mins.setAdapter(new MyNumericWheelAdapter(starMinus == 0 ? 0 : 1, 1));
                wv_mins.setCurrentItem(m / 30);
            } else {
                wv_mins.setAdapter(new NumericWheelAdapter(starMinus, 59));
                wv_mins.setCurrentItem(m - starMinus);
            }
        } else if (year == endYear && month == (endMonth - 1) && day == endDay && endHour == h) {

            if (isNeedHalf) {
                wv_mins.setAdapter(new MyNumericWheelAdapter(0, endMinus / 30));
                wv_mins.setCurrentItem(m / 30);
            } else {
                wv_mins.setAdapter(new NumericWheelAdapter(0, endMinus));
                wv_mins.setCurrentItem(m);
            }
        } else {
            if (isNeedHalf) {
                wv_mins.setAdapter(new MyNumericWheelAdapter(0, 1));
                wv_mins.setCurrentItem(m / 30);
            } else {
                wv_mins.setAdapter(new NumericWheelAdapter(0, 59));
                wv_mins.setCurrentItem(m);
            }
        }

       /* wv_mins.setLabel(context.getString(R.string.pickerview_minutes));// 添加文字*/

        wv_mins.setGravity(gravity);
        //秒
        wv_seconds = (WheelView) view.findViewById(R.id.second);
        wv_seconds.setAdapter(new NumericWheelAdapter(0, 59));
       /* wv_seconds.setLabel(context.getString(R.string.pickerview_seconds));// 添加文字*/
        wv_seconds.setCurrentItem(s);
        wv_seconds.setGravity(gravity);

        // 添加"年"监听
        OnItemSelectedListener wheelListener_year = new OnItemSelectedListener() {
            public void onItemSelected(int index) {
                int year_num = index + startYear;
                currentYear = year_num;
                int currentMonthItem = wv_month.getCurrentItem();
                int monthNum;
                if (startYear == endYear) {
                    wv_month.setAdapter(new NumericWheelAdapter(startMonth, endMonth));
                    if (currentMonthItem > wv_month.getAdapter().getItemsCount() - 1) {
                        currentMonthItem = wv_month.getAdapter().getItemsCount() - 1;
                        wv_month.setCurrentItem(currentMonthItem);
                    }

                    monthNum = currentMonthItem + startMonth;
                    if (startMonth == endMonth) {
                        setReDay(year_num, monthNum, startDay, endDay, list_big, list_little);
                    } else if (monthNum == startMonth) {
                        setReDay(year_num, monthNum, startDay, 31, list_big, list_little);
                    } else if (monthNum == endMonth) {
                        setReDay(year_num, monthNum, 1, endDay, list_big, list_little);
                    } else {
                        setReDay(year_num, monthNum, 1, 31, list_big, list_little);
                    }
                } else if (year_num == startYear) {
                    wv_month.setAdapter(new NumericWheelAdapter(startMonth, 12));
                    if (currentMonthItem > wv_month.getAdapter().getItemsCount() - 1) {
                        currentMonthItem = wv_month.getAdapter().getItemsCount() - 1;
                        wv_month.setCurrentItem(currentMonthItem);
                    }

                    monthNum = currentMonthItem + startMonth;
                    if (monthNum == startMonth) {
                        setReDay(year_num, monthNum, startDay, 31, list_big, list_little);
                    } else {
                        setReDay(year_num, monthNum, 1, 31, list_big, list_little);
                    }
                } else if (year_num == endYear) {
                    wv_month.setAdapter(new NumericWheelAdapter(1, endMonth));
                    if (currentMonthItem > wv_month.getAdapter().getItemsCount() - 1) {
                        currentMonthItem = wv_month.getAdapter().getItemsCount() - 1;
                        wv_month.setCurrentItem(currentMonthItem);
                    }

                    monthNum = currentMonthItem + 1;
                    if (monthNum == endMonth) {
                        setReDay(year_num, monthNum, 1, endDay, list_big, list_little);
                    } else {
                        setReDay(year_num, monthNum, 1, 31, list_big, list_little);
                    }
                } else {
                    wv_month.setAdapter(new NumericWheelAdapter(1, 12));
                    setReDay(year_num, wv_month.getCurrentItem() + 1, 1, 31, list_big, list_little);
                }

                int year, month, day, hour;
                year = wv_year.getCurrentItem() + startYear;
                if (wv_year.getCurrentItem() == 0) {
                    month = wv_month.getCurrentItem() + startMonth;
                } else {
                    month = wv_month.getCurrentItem() + 1;
                }
                if (wv_month.getCurrentItem() == 0) {
                    day = wv_day.getCurrentItem() + startDay;
                } else {
                    day = wv_day.getCurrentItem() + 1;
                }
                //设置小时
                setHour(year, month, day);
                if (wv_day.getCurrentItem() == 0) {
                    hour = wv_hours.getCurrentItem() + starHour;
                } else {
                    hour = wv_hours.getCurrentItem();
                }
                setMinus(year, month, day, hour);

                //设置星期几
                Date currentDate = new Date();
                try {
                    currentDate = WheelTime.dateFormat.parse(getTime());
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(currentDate);
                    weekTv.setText(week[calendar.get(Calendar.DAY_OF_WEEK) - 1]);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        };
        // 添加"月"监听
        OnItemSelectedListener wheelListener_month = new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                int month_num = index + 1;

                if (startYear == endYear) {
                    month_num = month_num + startMonth - 1;
                    if (startMonth == endMonth) {
                        //重新设置日
                        setReDay(currentYear, month_num, startDay, endDay, list_big, list_little);
                    } else if (startMonth == month_num) {

                        //重新设置日
                        setReDay(currentYear, month_num, startDay, 31, list_big, list_little);
                    } else if (endMonth == month_num) {
                        setReDay(currentYear, month_num, 1, endDay, list_big, list_little);
                    } else {
                        setReDay(currentYear, month_num, 1, 31, list_big, list_little);
                    }
                } else if (currentYear == startYear) {
                    month_num = month_num + startMonth - 1;
                    if (month_num == startMonth) {

                        //当月份回滚到开始的界面时 修改小时和分钟的滚轮
                        int hourIndex = wv_day.getCurrentItem();

                        int currentDay = wv_day.getCurrentItem() + startDay + 1;
                        if (currentDay == startDay) {
                            wv_hours.setAdapter(new NumericWheelAdapter(starHour, 23));

                            if (hourIndex > wv_day.getAdapter().getItemsCount() - 1) {
                                hourIndex = wv_day.getAdapter().getItemsCount() - 1;
                                wv_hours.setCurrentItem(hourIndex);
                            }

                            int minusIndex = wv_mins.getCurrentItem();

                            int currentHour = wv_hours.getCurrentItem() + starHour;
                            if (starHour == currentHour) {
                                if (isNeedHalf) {
                                    wv_mins.setAdapter(new MyNumericWheelAdapter(0, 1));
                                } else {
                                    wv_mins.setAdapter(new NumericWheelAdapter(starMinus, 59));
                                }
                                if (minusIndex > wv_mins.getAdapter().getItemsCount() - 1) {
                                    minusIndex = wv_mins.getAdapter().getItemsCount() - 1;
                                    wv_mins.setCurrentItem(minusIndex);
                                }
                            }

                        }

                        //重新设置日
                        setReDay(currentYear, month_num, startDay, 31, list_big, list_little);
                    } else {
                        //重新设置日
                        setReDay(currentYear, month_num, 1, 31, list_big, list_little);
                    }

                } else if (currentYear == endYear) {
                    if (month_num == endMonth) {

                        //当月份回滚到开始的界面时 修改小时和分钟的滚轮
                        int hourIndex = wv_day.getCurrentItem();

                        int currentDay = wv_day.getCurrentItem() + 1;
                        if (currentDay == endDay) {
                            wv_hours.setAdapter(new NumericWheelAdapter(0, endHour));

                            if (hourIndex > wv_day.getAdapter().getItemsCount() - 1) {
                                hourIndex = wv_day.getAdapter().getItemsCount() - 1;
                                wv_hours.setCurrentItem(hourIndex);
                            }

                            int minusIndex = wv_mins.getCurrentItem();

                            int currentHour = wv_hours.getCurrentItem();
                            if (currentHour == endHour) {
                                if (isNeedHalf) {
                                    wv_mins.setAdapter(new MyNumericWheelAdapter(0, 1));
                                } else {
                                    wv_mins.setAdapter(new NumericWheelAdapter(0, endMinus));
                                }
                                if (minusIndex > wv_mins.getAdapter().getItemsCount() - 1) {
                                    minusIndex = wv_mins.getAdapter().getItemsCount() - 1;
                                    wv_mins.setCurrentItem(minusIndex);
                                }
                            }

                        }
                        //重新设置日
                        setReDay(currentYear, wv_month.getCurrentItem() + 1, 1, endDay, list_big, list_little);
                    } else {
                        setReDay(currentYear, wv_month.getCurrentItem() + 1, 1, 31, list_big, list_little);
                    }

                } else {

                    wv_hours.setAdapter(new NumericWheelAdapter(0, 23));
                    if (isNeedHalf) {
                        wv_mins.setAdapter(new MyNumericWheelAdapter(0, 1));
                    } else {
                        wv_mins.setAdapter(new NumericWheelAdapter(0, 23));
                    }
                    //重新设置日
                    setReDay(currentYear, month_num, 1, 31, list_big, list_little);
                }
                /**************设置小时和分钟的设置问题***************/
                int year, month, day, hour;
                year = wv_year.getCurrentItem() + startYear;
                if (wv_year.getCurrentItem() == 0) {
                    month = wv_month.getCurrentItem() + startMonth;
                } else {
                    month = wv_month.getCurrentItem() + 1;
                }
                if (wv_month.getCurrentItem() == 0) {
                    day = wv_day.getCurrentItem() + startDay;
                } else {
                    day = wv_day.getCurrentItem() + 1;
                }
                //设置小时
                setHour(year, month, day);
                if (wv_day.getCurrentItem() == 0) {
                    hour = wv_hours.getCurrentItem() + starHour;
                } else {
                    hour = wv_hours.getCurrentItem();
                }
                setMinus(year, month, day, hour);

                /**************设置星期几***************/
                Date currentDate = new Date();
                try {
                    currentDate = WheelTime.dateFormat.parse(getTime());
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(currentDate);
                    weekTv.setText(week[calendar.get(Calendar.DAY_OF_WEEK) - 1]);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        };

        // TODO: 2017/12/1  设置天的点击事件
        OnItemSelectedListener listenter_day = new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                int currentItem = wv_hours.getCurrentItem();


                int selectYear = wv_year.getCurrentItem() + startYear;

                int selectMonth;
                if (wv_year.getCurrentItem() == 0) {
                    selectMonth = wv_month.getCurrentItem() + startMonth;
                } else {
                    selectMonth = wv_month.getCurrentItem() + 1;
                }

                int selectDay;
                if (wv_month.getCurrentItem() == 0) {
                    selectDay = index + startDay;
                } else {
                    selectDay = index + 1;
                }


                if (startYear == endYear && (startMonth) == (endMonth) && startDay == endDay) {
                    wv_hours.setAdapter(new NumericWheelAdapter(starHour, endHour));
                } else if (startYear == selectYear && startMonth == selectMonth && startDay == selectDay) {
                    wv_hours.setAdapter(new NumericWheelAdapter(starHour, 23));
                } else if (endYear == selectYear && selectMonth == endMonth && endDay == selectDay) {
                    wv_hours.setAdapter(new NumericWheelAdapter(0, endHour));
                } else {
                    wv_hours.setAdapter(new NumericWheelAdapter(0, 23));
                }

                //设置下标
                if (currentItem > wv_hours.getAdapter().getItemsCount() - 1) {
                    currentItem = wv_hours.getAdapter().getItemsCount() - 1;
                    wv_hours.setCurrentItem(currentItem);
                }

                /************* 改变分钟的显示********************/
                int hour;

                if (wv_day.getCurrentItem() == 0) {
                    hour = wv_hours.getCurrentItem() + starHour;
                } else {
                    hour = wv_hours.getCurrentItem();
                }
                setMinus(selectYear, selectMonth, selectDay, hour);


                /**************设置星期几***************/
                Date currentDate;
                try {
                    currentDate = WheelTime.dateFormat.parse(getTime());
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(currentDate);
                    weekTv.setText(week[calendar.get(Calendar.DAY_OF_WEEK) - 1]);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        };
        // TODO: 2017/12/1  设置小时的点击事件
        OnItemSelectedListener listener_hour = new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                int currentItem = wv_mins.getCurrentItem();

                int selectHour;
                if (wv_day.getCurrentItem() == 0) {
                    selectHour = index + starHour;
                } else {
                    selectHour = index;
                }

                int selectYear = wv_year.getCurrentItem() + startYear;

                int selectMonth;
                if (wv_year.getCurrentItem() == 0) {
                    selectMonth = wv_month.getCurrentItem() + startMonth - 1;
                } else {
                    selectMonth = wv_month.getCurrentItem();
                }

                int selectDay;
                if (wv_month.getCurrentItem() == 0) {
                    selectDay = wv_day.getCurrentItem() + startDay;
                } else {
                    selectDay = wv_day.getCurrentItem() + 1;
                }

                if (startYear == endYear && (startMonth - 1) == (endMonth - 1) && startDay == endDay && starHour == endHour) {
                    if (isNeedHalf) {
                        wv_mins.setAdapter(new MyNumericWheelAdapter(starMinus == 0 ? 0 : 1, endMinus / 30));
                    } else {
                        wv_mins.setAdapter(new NumericWheelAdapter(starMinus, endMinus));
                    }
                } else if (startYear == selectYear && (startMonth - 1) == selectMonth && startDay == selectDay && starHour == selectHour) {
                    if (isNeedHalf) {
                        wv_mins.setAdapter(new MyNumericWheelAdapter(starMinus == 0 ? 0 : 1, 1));
                    } else {
                        wv_mins.setAdapter(new NumericWheelAdapter(starMinus, 59));
                    }
                } else if (selectYear == endYear && selectMonth == (endMonth - 1) && endDay == selectDay && endHour == selectHour) {
                    if (isNeedHalf) {
                        wv_mins.setAdapter(new MyNumericWheelAdapter(0, endMinus / 30));
                    } else {
                        wv_mins.setAdapter(new NumericWheelAdapter(0, endMinus));
                    }
                } else {
                    if (isNeedHalf) {
                        wv_mins.setAdapter(new MyNumericWheelAdapter(0, 1));
                    } else {
                        wv_mins.setAdapter(new NumericWheelAdapter(0, 59));
                    }
                }
                //设置下标
                if (currentItem > wv_mins.getAdapter().getItemsCount() - 1) {
                    currentItem = wv_mins.getAdapter().getItemsCount() - 1;
                    wv_mins.setCurrentItem(currentItem);
                }
            }
        };
        wv_hours.setOnItemSelectedListener(listener_hour);


        wv_year.setOnItemSelectedListener(wheelListener_year);
        wv_month.setOnItemSelectedListener(wheelListener_month);
        wv_day.setOnItemSelectedListener(listenter_day);

        if (type.length != 6) {
            throw new IllegalArgumentException("type[] length is not 6");
        }
        wv_year.setVisibility(type[0] ? View.VISIBLE : View.GONE);
        wv_month.setVisibility(type[1] ? View.VISIBLE : View.GONE);
        wv_day.setVisibility(type[2] ? View.VISIBLE : View.GONE);
        wv_hours.setVisibility(type[3] ? View.VISIBLE : View.GONE);
        wv_mins.setVisibility(type[4] ? View.VISIBLE : View.GONE);
        wv_seconds.setVisibility(type[5] ? View.VISIBLE : View.GONE);
        setContentTextSize();
    }


    private void setReDay(int year_num, int monthNum, int startD, int endD, List<String> list_big, List<String> list_little) {
        int currentItem = wv_day.getCurrentItem();

//        int maxItem;
        if (list_big.contains(String.valueOf(monthNum))) {
            if (endD > 31) {
                endD = 31;
            }
            wv_day.setAdapter(new NumericWheelAdapter(startD, endD));
//            maxItem = endD;
        } else if (list_little.contains(String.valueOf(monthNum))) {
            if (endD > 30) {
                endD = 30;
            }
            wv_day.setAdapter(new NumericWheelAdapter(startD, endD));
//            maxItem = endD;
        } else {
            if ((year_num % 4 == 0 && year_num % 100 != 0)
                    || year_num % 400 == 0) {
                if (endD > 29) {
                    endD = 29;
                }
                wv_day.setAdapter(new NumericWheelAdapter(startD, endD));
//                maxItem = endD;
            } else {
                if (endD > 28) {
                    endD = 28;
                }
                wv_day.setAdapter(new NumericWheelAdapter(startD, endD));
//                maxItem = endD;
            }
        }

        if (currentItem > wv_day.getAdapter().getItemsCount() - 1) {
            currentItem = wv_day.getAdapter().getItemsCount() - 1;
            wv_day.setCurrentItem(currentItem);
        }
    }

    /**
     * 设置小时
     *
     * @param selectYear
     * @param selectMonth
     * @param selectDay
     */

    private void setHour(int selectYear, int selectMonth, int selectDay) {
        int currentItem = wv_hours.getCurrentItem();

        if (startYear == endYear && (startMonth) == (endMonth) && startDay == endDay) {
            wv_hours.setAdapter(new NumericWheelAdapter(starHour, endHour));
        } else if (startYear == selectYear && (startMonth) == selectMonth && startDay == selectDay) {
            wv_hours.setAdapter(new NumericWheelAdapter(starHour, 23));
        } else if (endYear == selectYear && selectMonth == (endMonth) && selectDay == endDay) {
            wv_hours.setAdapter(new NumericWheelAdapter(0, endHour));
        } else {
            wv_hours.setAdapter(new NumericWheelAdapter(0, 23));
        }

        if (currentItem > wv_hours.getAdapter().getItemsCount() - 1) {
            currentItem = wv_hours.getAdapter().getItemsCount() - 1;
            wv_hours.setCurrentItem(currentItem);
        }
    }

    /**
     * 设置分钟
     *
     * @param selectYear
     * @param selectMonth
     * @param selectDay
     * @param selectHour
     */
    private void setMinus(int selectYear, int selectMonth, int selectDay, int selectHour) {
        int currentItem = wv_mins.getCurrentItem();


        if (startYear == endYear && (startMonth) == (endMonth) && startDay == endDay && starHour == endHour) {
            if (isNeedHalf) {
                wv_mins.setAdapter(new MyNumericWheelAdapter(starMinus == 0 ? 0 : 1, endMinus / 30));
            } else {
                wv_mins.setAdapter(new NumericWheelAdapter(starMinus, endMinus));
            }

        } else if (startYear == selectYear && (startMonth) == selectMonth && startDay == selectDay && starHour == selectHour) {
            if (isNeedHalf) {
                wv_mins.setAdapter(new MyNumericWheelAdapter(starMinus == 0 ? 0 : 1, 1));
            } else {
                wv_mins.setAdapter(new NumericWheelAdapter(starMinus, 59));
            }

        } else if (selectYear == endYear && selectMonth == (endMonth) && selectDay == endDay && endHour == selectHour) {
            if (isNeedHalf) {
                wv_mins.setAdapter(new MyNumericWheelAdapter(0, endMinus / 30));
            } else {
                wv_mins.setAdapter(new NumericWheelAdapter(0, endMinus));
            }
        } else {
            if (isNeedHalf) {
                wv_mins.setAdapter(new MyNumericWheelAdapter(0, 1));
            } else {
                wv_mins.setAdapter(new NumericWheelAdapter(0, 59));
            }
        }
        //设置下标
        if (currentItem > wv_mins.getAdapter().getItemsCount() - 1) {
            currentItem = wv_mins.getAdapter().getItemsCount() - 1;
            wv_mins.setCurrentItem(currentItem);
        }

    }

    private void setContentTextSize() {
        wv_day.setTextSize(textSize);
        wv_month.setTextSize(textSize);
        wv_year.setTextSize(textSize);
        wv_hours.setTextSize(textSize);
        wv_mins.setTextSize(textSize);
        wv_seconds.setTextSize(textSize);
    }

    private void setTextColorOut() {
        wv_day.setTextColorOut(textColorOut);
        wv_month.setTextColorOut(textColorOut);
        wv_year.setTextColorOut(textColorOut);
        wv_hours.setTextColorOut(textColorOut);
        wv_mins.setTextColorOut(textColorOut);
        wv_seconds.setTextColorOut(textColorOut);
    }

    private void setTextColorCenter() {
        wv_day.setTextColorCenter(textColorCenter);
        wv_month.setTextColorCenter(textColorCenter);
        wv_year.setTextColorCenter(textColorCenter);
        wv_hours.setTextColorCenter(textColorCenter);
        wv_mins.setTextColorCenter(textColorCenter);
        wv_seconds.setTextColorCenter(textColorCenter);
    }

    private void setDividerColor() {
        wv_day.setDividerColor(dividerColor);
        wv_month.setDividerColor(dividerColor);
        wv_year.setDividerColor(dividerColor);
        wv_hours.setDividerColor(dividerColor);
        wv_mins.setDividerColor(dividerColor);
        wv_seconds.setDividerColor(dividerColor);
    }

    private void setDividerType() {
        wv_day.setDividerType(dividerType);
        wv_month.setDividerType(dividerType);
        wv_year.setDividerType(dividerType);
        wv_hours.setDividerType(dividerType);
        wv_mins.setDividerType(dividerType);
        wv_seconds.setDividerType(dividerType);

    }

    private void setLineSpacingMultiplier() {
        wv_day.setLineSpacingMultiplier(lineSpacingMultiplier);
        wv_month.setLineSpacingMultiplier(lineSpacingMultiplier);
        wv_year.setLineSpacingMultiplier(lineSpacingMultiplier);
        wv_hours.setLineSpacingMultiplier(lineSpacingMultiplier);
        wv_mins.setLineSpacingMultiplier(lineSpacingMultiplier);
        wv_seconds.setLineSpacingMultiplier(lineSpacingMultiplier);
    }

    public void setLabels(String label_year, String label_month, String label_day, String label_hours, String label_mins, String label_seconds) {
        if (isLunarCalendar) {
            return;
        }

        if (label_year != null) {
            wv_year.setLabel(label_year);
        } else {
            wv_year.setLabel(view.getContext().getString(R.string.pickerview_year));
        }
        if (label_month != null) {
            wv_month.setLabel(label_month);
        } else {
            wv_month.setLabel(view.getContext().getString(R.string.pickerview_month));
        }
        if (label_day != null) {
            wv_day.setLabel(label_day);
        } else {
            wv_day.setLabel(view.getContext().getString(R.string.pickerview_day));
        }
        if (label_hours != null) {
            wv_hours.setLabel(label_hours);
        } else {
            wv_hours.setLabel(view.getContext().getString(R.string.pickerview_hours));
        }
        if (label_mins != null) {
            wv_mins.setLabel(label_mins);
        } else {
            wv_mins.setLabel(view.getContext().getString(R.string.pickerview_minutes));
        }
        if (label_seconds != null) {
            wv_seconds.setLabel(label_seconds);
        } else {
            wv_seconds.setLabel(view.getContext().getString(R.string.pickerview_seconds));
        }

    }

    public void setTextXOffset(int xoffset_year, int xoffset_month, int xoffset_day, int xoffset_hours, int xoffset_mins, int xoffset_seconds) {
        wv_day.setTextXOffset(xoffset_year);
        wv_month.setTextXOffset(xoffset_month);
        wv_year.setTextXOffset(xoffset_day);
        wv_hours.setTextXOffset(xoffset_hours);
        wv_mins.setTextXOffset(xoffset_mins);
        wv_seconds.setTextXOffset(xoffset_seconds);
    }

    /**
     * 设置是否循环滚动
     *
     * @param cyclic
     */
    public void setCyclic(boolean cyclic) {
        wv_year.setCyclic(cyclic);
        wv_month.setCyclic(cyclic);
        wv_day.setCyclic(cyclic);
        wv_hours.setCyclic(cyclic);
        wv_mins.setCyclic(cyclic);
        wv_seconds.setCyclic(cyclic);
    }

    /**
     * 做一个特殊的处理 半个小时的返回情况
     *
     * @return
     */

    public String getTime() {

        if (isLunarCalendar) {
            //如果是农历 返回对应的公历时间
            return getLunarTime();
        }

        //分钟
        int currentMins = 0;
        //由于只有0和半个小时的选项


        StringBuffer sb = new StringBuffer();

        //返回年
        int returnYear = wv_year.getCurrentItem() + startYear;

        int returnMonth;
        int returnDay;
        int returnHour;

        /**
         *  只要管开始
         */
        //返回月
        if (wv_year.getCurrentItem() == 0) {
            returnMonth = wv_month.getCurrentItem() + startMonth;
        } else {
            returnMonth = wv_month.getCurrentItem() + 1;
        }
        //返回天
        if (wv_year.getCurrentItem() == 0 && wv_month.getCurrentItem() == 0) {
            returnDay = wv_day.getCurrentItem() + startDay;
        } else {
            returnDay = wv_day.getCurrentItem() + 1;
        }
        //返回小时
        if (wv_year.getCurrentItem() == 0 && wv_month.getCurrentItem() == 0 && wv_day.getCurrentItem() == 0) {
            returnHour = wv_hours.getCurrentItem() + starHour;
        } else {
            returnHour = wv_hours.getCurrentItem();
        }
        //返回分钟
        if (wv_year.getCurrentItem() == 0 && wv_month.getCurrentItem() == 0 && wv_day.getCurrentItem() == 0 && wv_hours.getCurrentItem() == 0) {
            if (isNeedHalf) {
                currentMins = wv_mins.getCurrentItem() * MyNumericWheelAdapter.MINS+starMinus;
            } else {
                currentMins = wv_mins.getCurrentItem() + starMinus;
            }
        } else {
            if (isNeedHalf) {
                currentMins = wv_mins.getCurrentItem() * MyNumericWheelAdapter.MINS;
            } else {
                currentMins = wv_mins.getCurrentItem();
            }
        }

        sb.append(returnYear).append("-")
                .append(returnMonth).append("-")
                .append(returnDay).append(" ")
                .append(returnHour).append(":")
                .append(currentMins).append(":")
                .append(wv_seconds.getCurrentItem());
//
//        if (currentYear == startYear) {
//
//            if ((wv_month.getCurrentItem() + startMonth) == startMonth) {
//                sb.append((wv_year.getCurrentItem() + startYear)).append("-")
//                        .append((wv_month.getCurrentItem() + startMonth)).append("-")
//                        .append((wv_day.getCurrentItem() + startDay)).append(" ")
//                        .append(wv_hours.getCurrentItem()+starHour).append(":")
//                        .append(currentMins).append(":")
//                        .append(wv_seconds.getCurrentItem());
//            } else {
//                sb.append((wv_year.getCurrentItem() + startYear)).append("-")
//                        .append((wv_month.getCurrentItem() + startMonth)).append("-")
//                        .append((wv_day.getCurrentItem() + 1)).append(" ")
//                        .append(wv_hours.getCurrentItem()+starHour).append(":")
//                        .append(currentMins).append(":")
//                        .append(wv_seconds.getCurrentItem());
//            }
//        } else {
//
//
//        }
        return sb.toString();
    }


    /**
     * 农历返回对应的公历时间
     * (农历暂未处理这个半个小时的问题)
     *
     * @return
     */
    private String getLunarTime() {
        StringBuffer sb = new StringBuffer();
        int year = wv_year.getCurrentItem() + startYear;
        int month = 1;
        boolean isLeapMonth = false;
        if (ChinaDate.leapMonth(year) == 0) {
            month = wv_month.getCurrentItem() + 1;
        } else {
            if ((wv_month.getCurrentItem() + 1) - ChinaDate.leapMonth(year) <= 0) {
                month = wv_month.getCurrentItem() + 1;
            } else if ((wv_month.getCurrentItem() + 1) - ChinaDate.leapMonth(year) == 1) {
                month = wv_month.getCurrentItem();
                isLeapMonth = true;
            } else {
                month = wv_month.getCurrentItem();
            }
        }
        int day = wv_day.getCurrentItem() + 1;
        int[] solar = LunarCalendar.lunarToSolar(year, month, day, isLeapMonth);

        sb.append(solar[0]).append("-")
                .append(solar[1]).append("-")
                .append(solar[2]).append(" ")
                .append(wv_hours.getCurrentItem()).append(":")
                .append(wv_mins.getCurrentItem()).append(":")
                .append(wv_seconds.getCurrentItem());
        return sb.toString();
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public int getStartYear() {
        return startYear;
    }

    public void setStartYear(int startYear) {
        this.startYear = startYear;
    }

    public int getEndYear() {
        return endYear;
    }

    public void setEndYear(int endYear) {
        this.endYear = endYear;
    }


    /**
     * 范围针对到分钟
     *
     * @param startDate
     * @param endDate
     */
    public void setRangDate(Calendar startDate, Calendar endDate) {

        if (startDate == null && endDate != null) {
            int year = endDate.get(Calendar.YEAR);
            int month = endDate.get(Calendar.MONTH) + 1;
            int day = endDate.get(Calendar.DAY_OF_MONTH);

            int hour = endDate.get(Calendar.HOUR_OF_DAY);
            int minus = endDate.get(Calendar.MINUTE);


            if (year > startYear) {
                this.endYear = year;
                this.endMonth = month;
                this.endDay = day;

                this.endHour = hour;
                this.endMinus = minus;
            } else if (year == startYear) {
                if (month > startMonth) {
                    this.endYear = year;
                    this.endMonth = month;
                    this.endDay = day;

                    this.endHour = hour;
                    this.endMinus = minus;
                } else if (month == startMonth) {
                    if (day > startDay) {
                        this.endYear = year;
                        this.endMonth = month;
                        this.endDay = day;

                        this.endHour = hour;
                        this.endMinus = minus;
                    }
                }
            }

        } else if (startDate != null && endDate == null) {
            int year = startDate.get(Calendar.YEAR);
            int month = startDate.get(Calendar.MONTH) + 1;
            int day = startDate.get(Calendar.DAY_OF_MONTH);

            int hour = startDate.get(Calendar.HOUR_OF_DAY);
            int minus = startDate.get(Calendar.MINUTE);
            if (year < endYear) {
                this.startMonth = month;
                this.startDay = day;
                this.startYear = year;

                this.starHour = hour;
                this.starMinus = minus;
            } else if (year == endYear) {
                if (month < endMonth) {
                    this.startMonth = month;
                    this.startDay = day;
                    this.startYear = year;

                    this.starHour = hour;
                    this.starMinus = minus;
                } else if (month == endMonth) {
                    if (day < endDay) {
                        this.startMonth = month;
                        this.startDay = day;
                        this.startYear = year;

                        this.starHour = hour;
                        this.starMinus = minus;
                    }
                }
            }

        } else if (startDate != null && endDate != null) {
            this.startYear = startDate.get(Calendar.YEAR);
            this.endYear = endDate.get(Calendar.YEAR);
            this.startMonth = startDate.get(Calendar.MONTH) + 1;
            this.endMonth = endDate.get(Calendar.MONTH) + 1;
            this.startDay = startDate.get(Calendar.DAY_OF_MONTH);
            this.endDay = endDate.get(Calendar.DAY_OF_MONTH);

            this.endHour = endDate.get(Calendar.HOUR_OF_DAY);
            this.endMinus = endDate.get(Calendar.MINUTE);

            this.starHour = startDate.get(Calendar.HOUR_OF_DAY);
            this.starMinus = startDate.get(Calendar.MINUTE);
        }

    }


    /**
     * 设置间距倍数,但是只能在1.0-2.0f之间
     *
     * @param lineSpacingMultiplier
     */
    public void setLineSpacingMultiplier(float lineSpacingMultiplier) {
        this.lineSpacingMultiplier = lineSpacingMultiplier;
        setLineSpacingMultiplier();
    }

    /**
     * 设置分割线的颜色
     *
     * @param dividerColor
     */
    public void setDividerColor(int dividerColor) {
        this.dividerColor = dividerColor;
        setDividerColor();
    }

    /**
     * 设置分割线的类型
     *
     * @param dividerType
     */
    public void setDividerType(WheelView.DividerType dividerType) {
        this.dividerType = dividerType;
        setDividerType();
    }

    /**
     * 设置分割线之间的文字的颜色
     *
     * @param textColorCenter
     */
    public void setTextColorCenter(int textColorCenter) {
        this.textColorCenter = textColorCenter;
        setTextColorCenter();
    }

    /**
     * 设置分割线以外文字的颜色
     *
     * @param textColorOut
     */
    public void setTextColorOut(int textColorOut) {
        this.textColorOut = textColorOut;
        setTextColorOut();
    }

    /**
     * Label 是否只显示中间选中项的
     *
     * @param isCenterLabel
     */

    public void isCenterLabel(Boolean isCenterLabel) {

        wv_day.isCenterLabel(isCenterLabel);
        wv_month.isCenterLabel(isCenterLabel);
        wv_year.isCenterLabel(isCenterLabel);
        wv_hours.isCenterLabel(isCenterLabel);
        wv_mins.isCenterLabel(isCenterLabel);
        wv_seconds.isCenterLabel(isCenterLabel);
    }

    /**
     * 是否需要显示半个小时
     *
     * @param halfHour
     */
    public void setHalfHour(boolean halfHour) {
        isNeedHalf = halfHour;
    }

    /**
     * 是否需要显示半个小时
     *
     * @param showWeek
     */
    public void setShowWeek(boolean showWeek) {
        isShowWeek = showWeek;
    }
}
