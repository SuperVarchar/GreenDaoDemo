package com.hnshituo.icore_map.view.calender;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.VelocityTracker;
import android.widget.OverScroller;

import com.hnshituo.icore_map.view.calender.domain.DataEntry;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * Created by liuba
 * time:2016/11/16 16:49
 * describe --
 */

public class MyController extends CompactCalendarController {
    public MyController(Paint dayPaint, OverScroller scroller, Rect textSizeRect, AttributeSet attrs, Context context, int currentDayBackgroundColor, int calenderTextColor, int currentSelectedDayBackgroundColor, VelocityTracker velocityTracker, int multiEventIndicatorColor) {
        super(dayPaint, scroller, textSizeRect, attrs, context, currentDayBackgroundColor, calenderTextColor, currentSelectedDayBackgroundColor, velocityTracker, multiEventIndicatorColor);
    }

    public void setmHeightLightDay(List<DataEntry> heightLightDay) {
        this.mHeightLightDay = heightLightDay;
    }

    public List<DataEntry> mHeightLightDay = new ArrayList<>();

    @Override
    public void drawMonth(Canvas canvas, Calendar monthToDrawCalender, int offset) {
        drawEvents(canvas, monthToDrawCalender, offset);
        boolean isAnimatingWithExpose = animationStatus == EXPOSE_CALENDAR_ANIMATION;
        boolean isSameYearAsToday = monthToDrawCalender.get(Calendar.YEAR) == todayCalender.get(Calendar.YEAR);
        boolean isSameMonthAsCurrentCalendar = monthToDrawCalender.get(Calendar.MONTH) == currentCalender.get(Calendar.MONTH);
        //offset by one because we want to start from Monday
        int firstDayOfMonth = getDayOfWeek(monthToDrawCalender);
        //offset by one because of 0 index based calculations
        firstDayOfMonth = firstDayOfMonth - 1;
        for (int dayColumn = 0, dayRow = 0; dayColumn <= 6; dayRow++) {
            if (dayRow == 7) {
                dayRow = 0;
                if (dayColumn <= 6) {
                    dayColumn++;
                }
            }
            if (dayColumn == dayColumnNames.length) {
                break;
            }
            float xPosition = widthPerDay * dayColumn + paddingWidth + paddingLeft + accumulatedScrollOffset.x + offset - paddingRight;
            float yPosition = dayRow * heightPerDay + paddingHeight;
            if (xPosition >= growFactor && isAnimatingWithExpose || yPosition >= growFactor) {
                continue;
            }
            if (dayRow == 0) {
                // first row, so draw the first letter of the day
                if (shouldDrawDaysHeader) {
                    dayPaint.setColor(calenderTextColor);
                    dayPaint.setTypeface(Typeface.DEFAULT_BOLD);
                    canvas.drawText(dayColumnNames[dayColumn], xPosition, paddingHeight, dayPaint);
                    dayPaint.setTypeface(Typeface.DEFAULT);
                }
            } else {
                int day = ((dayRow - 1) * 7 + dayColumn + 1) - firstDayOfMonth;

//                else if (day == 1 && !isSameMonthAsCurrentCalendar && !isAnimatingWithExpose
//                        ) {
//                    drawCircle(canvas, xPosition, yPosition, currentSelectedDayBackgroundColor);
//                }
                if (mHeightLightDay != null) {
                    for (DataEntry dayinfo : mHeightLightDay) {

                        if (day == dayinfo.day) {
//                            drawCircle(canvas,xPosition,yPosition,Color.rgb(255,125,23));
//                            drawCircle(canvas,xPosition,yPosition,dayinfo.color);
                            if (dayinfo.isComptime) {
                                drawCircle(canvas, xPosition, yPosition, dayinfo.colors[2]);
                            }
                            if (dayinfo.isLeave) {
                                drawCircle(canvas, xPosition, yPosition, dayinfo.colors[3]);
                            }
                            if (dayinfo.isSign) {
                                drawSmallIndicatorCircle(canvas, xPosition, yPosition + yIndicatorOffset, dayinfo.colors[1]);
                            }
                        }
                    }
                }
                if (currentCalender.get(Calendar.DAY_OF_MONTH) == day && isSameMonthAsCurrentCalendar && !isAnimatingWithExpose) {
                    if (clickData != null) {
                        if (
                                clickData.get(Calendar.YEAR) == monthToDrawCalender.get(Calendar.YEAR)
                                        && clickData.get(Calendar.MONTH) == monthToDrawCalender.get(Calendar.MONTH)
                                        && clickData.get(Calendar.DAY_OF_MONTH) == day){
                            drawCircle(canvas, xPosition, yPosition, currentSelectedDayBackgroundColor);
                        }
                    }


                }

                if (day <= monthToDrawCalender.getActualMaximum(Calendar.DAY_OF_MONTH) && day > 0) {
                    canvas.drawText(String.valueOf(day), xPosition, yPosition, dayPaint);
                }
            }
        }
    }

    //清空数据
    public void clearDateMapForData() {
        mHeightLightDay.clear();
    }
}
