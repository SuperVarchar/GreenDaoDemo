package com.hnshituo.icore_map.view.calender;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.view.VelocityTracker;
import android.widget.OverScroller;

import com.hnshituo.icore_map.view.calender.domain.DataEntry;

import java.util.List;


/**
 * Created by liuba
 * time:2016/11/16 16:46
 * describe --
 */

public class MyCompactCalendarView extends CompactCalendarView {
    public MyCompactCalendarView(Context context) {
        super(context);
    }
//    MyController myController;
    public MyCompactCalendarView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyCompactCalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        compactCalendarController=new MyController(new Paint(), new OverScroller(getContext()),
                new Rect(), attrs, getContext(),  Color.argb(255, 233, 84, 81),
                Color.argb(255, 64, 64, 64), Color.argb(255, 219, 159, 219), VelocityTracker.obtain(),
                Color.argb(255, 100, 100, 65));
        gestureDetector = new GestureDetectorCompat(getContext(), gestureListener);
        animationHandler = new AnimationHandler(compactCalendarController, this);
    }
    @Override
    public void onDraw(Canvas canvas) {
        compactCalendarController.onDraw(canvas);
    }
    public void setDateMapForData(List<DataEntry> entry){
        ((MyController)compactCalendarController).setmHeightLightDay(entry);
    }
    public void clearDateMapForData(){
        ((MyController)compactCalendarController).clearDateMapForData();
    }
}
