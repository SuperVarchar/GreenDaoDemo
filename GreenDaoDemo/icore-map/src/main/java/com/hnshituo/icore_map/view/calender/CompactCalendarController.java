package com.hnshituo.icore_map.view.calender;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import android.widget.OverScroller;

import com.hnshituo.icore_map.R;
import com.hnshituo.icore_map.view.calender.domain.Event;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;


class CompactCalendarController {
    public static final int IDLE = 0;
    public static final int EXPOSE_CALENDAR_ANIMATION = 1;
    public static final int EXPAND_COLLAPSE_CALENDAR = 2;
    public static final int ANIMATE_INDICATORS = 3;
    public static final int VELOCITY_UNIT_PIXELS_PER_SECOND = 1000;
    public static final int LAST_FLING_THRESHOLD_MILLIS = 300;
    public static final int DAYS_IN_WEEK = 7;
    public static final float SNAP_VELOCITY_DIP_PER_SECOND = 400;
    public static final float ANIMATION_SCREEN_SET_DURATION_MILLIS = 700;

    public int paddingWidth = 40;
    public int paddingHeight = 40;
    public int textHeight;
    public int textWidth;
    public int widthPerDay;
    public int monthsScrolledSoFar;
    public int heightPerDay;
    public int textSize = 30;
    public int width;
    public int height;
    public int paddingRight;
    public int paddingLeft;
    public int maximumVelocity;
    public int densityAdjustedSnapVelocity;
    public int distanceThresholdForAutoScroll;
    public int targetHeight;
    public int animationStatus = 0;
    public float yIndicatorOffset;
    public float xIndicatorOffset;
    public float multiDayIndicatorStrokeWidth;
    public float bigCircleIndicatorRadius;
    public float smallIndicatorRadius;
    public float growFactor = 0f;
    public float screenDensity = 1;
    public float growfactorIndicator;
    public float distanceX;
    public long lastAutoScrollFromFling;

    public boolean shouldShowMondayAsFirstDay = true;
    public boolean useThreeLetterAbbreviation = false;
    public boolean isSmoothScrolling;
    public boolean isScrolling;
    public boolean shouldDrawDaysHeader = true;

    public CompactCalendarView.CompactCalendarViewListener listener;
    public Map<String, List<Events>> eventsByMonthAndYearMap = new HashMap<>();
    public VelocityTracker velocityTracker = null;
    public Direction currentDirection = Direction.NONE;
    public Date currentDate = new Date();
    public Locale locale = Locale.getDefault();
    public Calendar currentCalender = Calendar.getInstance(locale);
    public Calendar todayCalender = Calendar.getInstance(locale);
    public Calendar calendarWithFirstDayOfMonth = Calendar.getInstance(locale);
    public Calendar eventsCalendar = Calendar.getInstance(locale);
    public PointF accumulatedScrollOffset = new PointF();
    public OverScroller scroller;
    public Paint dayPaint = new Paint();
    public Paint background = new Paint();
    public Rect textSizeRect;
    public String[] dayColumnNames;

    // colors
    public int multiEventIndicatorColor;
    public int currentDayBackgroundColor;
    public int  calenderTextColor;
    public int currentSelectedDayBackgroundColor;
    public int calenderBackgroundColor = Color.WHITE;


    public Calendar clickData;

    public enum Direction {
        NONE, HORIZONTAL, VERTICAL
    }
    public CompactCalendarController(Paint dayPaint, OverScroller scroller, Rect textSizeRect, AttributeSet attrs,
                              Context context, int currentDayBackgroundColor, int calenderTextColor,
                              int currentSelectedDayBackgroundColor, VelocityTracker velocityTracker,
                              int multiEventIndicatorColor) {
        this.dayPaint = dayPaint;
        this.scroller = scroller;
        this.textSizeRect = textSizeRect;
        this.currentDayBackgroundColor = currentDayBackgroundColor;
        this.calenderTextColor = calenderTextColor;
        this.currentSelectedDayBackgroundColor = currentSelectedDayBackgroundColor;
        this.velocityTracker = velocityTracker;
        this.multiEventIndicatorColor = multiEventIndicatorColor;
        loadAttributes(attrs, context);
        init(context);
    }

    public void loadAttributes(AttributeSet attrs, Context context) {
        if (attrs != null && context != null) {
            TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CompactCalendarView, 0, 0);
            try {
                currentDayBackgroundColor = typedArray.getColor(R.styleable.CompactCalendarView_compactCalendarCurrentDayBackgroundColor, currentDayBackgroundColor);
                calenderTextColor = typedArray.getColor(R.styleable.CompactCalendarView_compactCalendarTextColor, calenderTextColor);
                currentSelectedDayBackgroundColor = typedArray.getColor(R.styleable.CompactCalendarView_compactCalendarCurrentSelectedDayBackgroundColor, currentSelectedDayBackgroundColor);
                calenderBackgroundColor = typedArray.getColor(R.styleable.CompactCalendarView_compactCalendarBackgroundColor, calenderBackgroundColor);
                multiEventIndicatorColor = typedArray.getColor(R.styleable.CompactCalendarView_compactCalendarMultiEventIndicatorColor, multiEventIndicatorColor);
                textSize = typedArray.getDimensionPixelSize(R.styleable.CompactCalendarView_compactCalendarTextSize,
                        (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, textSize, context.getResources().getDisplayMetrics()));
                targetHeight = typedArray.getDimensionPixelSize(R.styleable.CompactCalendarView_compactCalendarTargetHeight,
                        (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, targetHeight, context.getResources().getDisplayMetrics()));
            } finally {
                typedArray.recycle();
            }
        }
    }

    public void init(Context context) {
        setUseWeekDayAbbreviation(false);
        dayPaint.setTextAlign(Paint.Align.CENTER);
        dayPaint.setStyle(Paint.Style.STROKE);
        dayPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        dayPaint.setTypeface(Typeface.SANS_SERIF);
        dayPaint.setTextSize(textSize);
        dayPaint.setColor(calenderTextColor);
        dayPaint.getTextBounds("31", 0, "31".length(), textSizeRect);
        textHeight = textSizeRect.height() * 3;
        textWidth = textSizeRect.width() * 2;

        todayCalender.setTime(currentDate);
        setToMidnight(todayCalender);

        currentCalender.setTime(currentDate);
        setCalenderToFirstDayOfMonth(calendarWithFirstDayOfMonth, currentDate, -monthsScrolledSoFar, 0);

        eventsCalendar.setFirstDayOfWeek(Calendar.MONDAY);

        if (context != null) {
            screenDensity = context.getResources().getDisplayMetrics().density;
            final ViewConfiguration configuration = ViewConfiguration
                    .get(context);
            densityAdjustedSnapVelocity = (int) (screenDensity * SNAP_VELOCITY_DIP_PER_SECOND);
            maximumVelocity = configuration.getScaledMaximumFlingVelocity();

            final DisplayMetrics dm = context.getResources().getDisplayMetrics() ;
            multiDayIndicatorStrokeWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, dm);
        }

        yIndicatorOffset = 9 * screenDensity;
        xIndicatorOffset = 3.5f * screenDensity;

        //scale small indicator by screen density
        smallIndicatorRadius = 2.5f * screenDensity;

        //just set a default growFactor to draw full calendar when initialised
        growFactor = Integer.MAX_VALUE;
    }

    public void setCalenderToFirstDayOfMonth(Calendar calendarWithFirstDayOfMonth, Date currentDate, int scrollOffset, int monthOffset) {
        setMonthOffset(calendarWithFirstDayOfMonth, currentDate, scrollOffset, monthOffset);
        calendarWithFirstDayOfMonth.set(Calendar.DAY_OF_MONTH, 1);
    }

    public void setMonthOffset(Calendar calendarWithFirstDayOfMonth, Date currentDate, int scrollOffset, int monthOffset) {
        calendarWithFirstDayOfMonth.setTime(currentDate);
        calendarWithFirstDayOfMonth.add(Calendar.MONTH, scrollOffset + monthOffset);
        calendarWithFirstDayOfMonth.set(Calendar.HOUR_OF_DAY, 0);
        calendarWithFirstDayOfMonth.set(Calendar.MINUTE, 0);
        calendarWithFirstDayOfMonth.set(Calendar.SECOND, 0);
        calendarWithFirstDayOfMonth.set(Calendar.MILLISECOND, 0);
    }

    public float getScreenDensity(){
        return screenDensity;
    }

    public float getDayIndicatorRadius(){
        return bigCircleIndicatorRadius;
    }

    public void setGrowFactorIndicator(float growfactorIndicator) {
        this.growfactorIndicator = growfactorIndicator;
    }

    public float getGrowFactorIndicator() {
        return growfactorIndicator;
    }

    public void setAnimationStatus(int animationStatus) {
        this.animationStatus = animationStatus;
    }

    public int getTargetHeight() {
        return targetHeight;
    }

    public int getWidth(){
        return width;
    }

    public void setListener(CompactCalendarView.CompactCalendarViewListener listener) {
        this.listener = listener;
    }
    public void removeAllEvents() {
        eventsByMonthAndYearMap.clear();
    }
    public void setShouldShowMondayAsFirstDay(boolean shouldShowMondayAsFirstDay) {
        this.shouldShowMondayAsFirstDay = shouldShowMondayAsFirstDay;
        setUseWeekDayAbbreviation(useThreeLetterAbbreviation);
        if (shouldShowMondayAsFirstDay) {
            eventsCalendar.setFirstDayOfWeek(Calendar.MONDAY);
        } else {
            eventsCalendar.setFirstDayOfWeek(Calendar.SUNDAY);
        }
    }
    public void setCurrentSelectedDayBackgroundColor(int currentSelectedDayBackgroundColor) {
        this.currentSelectedDayBackgroundColor = currentSelectedDayBackgroundColor;
    }
    public void setCalenderBackgroundColor(int calenderBackgroundColor) {
        this.calenderBackgroundColor = calenderBackgroundColor;
    }
    public void setCurrentDayBackgroundColor(int currentDayBackgroundColor) {
        this.currentDayBackgroundColor = currentDayBackgroundColor;
    }
    public void showNextMonth() {
        setCalenderToFirstDayOfMonth(calendarWithFirstDayOfMonth, currentCalender.getTime(), 0,1);
        setCurrentDate(calendarWithFirstDayOfMonth.getTime());
        performMonthScrollCallback();
    }

    public void showPreviousMonth() {
        setCalenderToFirstDayOfMonth(calendarWithFirstDayOfMonth, currentCalender.getTime(), 0, -1);
        setCurrentDate(calendarWithFirstDayOfMonth.getTime());
        performMonthScrollCallback();
    }

    public void showPointMonth(Date date) {
        setCalenderToFirstDayOfMonth(calendarWithFirstDayOfMonth, date, 0, 0);
        setCurrentDate(calendarWithFirstDayOfMonth.getTime());
        performMonthScrollCallback();

    }
    public void setLocale(Locale locale) {
        if (locale == null) {
            throw new IllegalArgumentException("Locale cannot be null");
        }
        this.locale = locale;
    }

    public void setUseWeekDayAbbreviation(boolean useThreeLetterAbbreviation) {
        this.useThreeLetterAbbreviation = useThreeLetterAbbreviation;
        DateFormatSymbols dateFormatSymbols = new DateFormatSymbols(locale);
        String[] dayNames = dateFormatSymbols.getShortWeekdays();
        if (dayNames == null) {
            throw new IllegalStateException("Unable to determine weekday names from default locale");
        }
        if (dayNames.length != 8) {
            throw new IllegalStateException("Expected weekday names from default locale to be of size 7 but: "
                    + Arrays.toString(dayNames) + " with size " + dayNames.length + " was returned.");
        }

        if (useThreeLetterAbbreviation) {
            if (!shouldShowMondayAsFirstDay) {
                this.dayColumnNames = new String[]{dayNames[1], dayNames[2], dayNames[3], dayNames[4], dayNames[5], dayNames[6], dayNames[7]};
            } else {
                this.dayColumnNames = new String[]{dayNames[2], dayNames[3], dayNames[4], dayNames[5], dayNames[6], dayNames[7], dayNames[1]};
            }
        } else {
            if (!shouldShowMondayAsFirstDay) {
                this.dayColumnNames = new String[]{dayNames[1].substring(0, 1), dayNames[2].substring(0, 1),
                        dayNames[3].substring(0, 1), dayNames[4].substring(0, 1), dayNames[5].substring(0, 1), dayNames[6].substring(0, 1), dayNames[7].substring(0, 1)};
            } else {
                this.dayColumnNames = new String[]{dayNames[2].substring(0, 1), dayNames[3].substring(0, 1),
                        dayNames[4].substring(0, 1), dayNames[5].substring(0, 1), dayNames[6].substring(0, 1), dayNames[7].substring(0, 1), dayNames[1].substring(0, 1)};
            }
        }
    }

    public void setDayColumnNames(String[] dayColumnNames) {
        if (dayColumnNames == null || dayColumnNames.length != 7) {
            throw new IllegalArgumentException("Column names cannot be null and must contain a value for each day of the week");
        }
        this.dayColumnNames = dayColumnNames;
    }

    public void setShouldDrawDaysHeader(boolean shouldDrawDaysHeader) {
        this.shouldDrawDaysHeader = shouldDrawDaysHeader;
    }

    public void onMeasure(int width, int height, int paddingRight, int paddingLeft) {
        widthPerDay = (width) / DAYS_IN_WEEK;
        heightPerDay = targetHeight > 0 ? targetHeight / 7 : height / 7;
        this.width = width;
        this.distanceThresholdForAutoScroll = (int) (width * 0.50);
        this.height = height;
        this.paddingRight = paddingRight;
        this.paddingLeft = paddingLeft;

        //makes easier to find radius
        bigCircleIndicatorRadius = getInterpolatedBigCircleIndicator();
    }

    //assume square around each day of width and height = heightPerDay and get diagonal line length
    //interpolate height and radius
    //https://en.wikipedia.org/wiki/Linear_interpolation
    public float getInterpolatedBigCircleIndicator() {
        float x0 = textSizeRect.height();
        float x1 = (heightPerDay - yIndicatorOffset); // take into account indicator offset
        float x =  (x1 + textSizeRect.height()) / 1.7f; // pick a point which is almost half way through heightPerDay and textSizeRect
        double y1 = 0.5 * Math.sqrt((x1 * x1) + (x1 * x1));
        double y0 = 0.5 * Math.sqrt((x0 * x0) + (x0 * x0));

        return (float) (y0 + ((y1 - y0) * ((x - x0) / (x1 - x0))));
    }

    public void onDraw(Canvas canvas) {
        paddingWidth = widthPerDay / 2;
        paddingHeight = heightPerDay / 2;
        calculateXPositionOffset();

        if (animationStatus == EXPOSE_CALENDAR_ANIMATION) {
            drawCalendarWhileAnimating(canvas);
        } else if (animationStatus == ANIMATE_INDICATORS) {
            drawCalendarWhileAnimatingIndicators(canvas);
        } else {
            drawCalenderBackground(canvas);
            drawScrollableCalender(canvas);
        }
    }

    public void drawCalendarWhileAnimatingIndicators(Canvas canvas) {
        dayPaint.setColor(calenderBackgroundColor);
        dayPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(0, 0, growFactor, dayPaint);
        dayPaint.setStyle(Paint.Style.STROKE);
        dayPaint.setColor(Color.WHITE);
        drawScrollableCalender(canvas);
    }

    public void drawCalendarWhileAnimating(Canvas canvas) {
        background.setColor(calenderBackgroundColor);
        background.setStyle(Paint.Style.FILL);
        canvas.drawCircle(0, 0, growFactor, background);
        dayPaint.setStyle(Paint.Style.STROKE);
        dayPaint.setColor(Color.WHITE);
        drawScrollableCalender(canvas);
    }

    public void onSingleTapConfirmed(MotionEvent e) {
        //Don't handle single tap the calendar is scrolling and is not stationary
        if (Math.abs(accumulatedScrollOffset.x) != Math.abs(width * monthsScrolledSoFar)) {
            return;
        }

        int dayColumn = Math.round((paddingLeft + e.getX() - paddingWidth - paddingRight) / widthPerDay);
        int dayRow = Math.round((e.getY() - paddingHeight) / heightPerDay);

        setCalenderToFirstDayOfMonth(calendarWithFirstDayOfMonth, currentDate, -monthsScrolledSoFar, 0);

        //Start Monday as day 1 and Sunday as day 7. Not Sunday as day 1 and Monday as day 2
        int firstDayOfMonth = getDayOfWeek(calendarWithFirstDayOfMonth);

        int dayOfMonth = ((dayRow - 1) * 7 + dayColumn + 1) - firstDayOfMonth;

        if (dayOfMonth < calendarWithFirstDayOfMonth.getActualMaximum(Calendar.DAY_OF_MONTH)
                && dayOfMonth >= 0) {
            calendarWithFirstDayOfMonth.add(Calendar.DATE, dayOfMonth);

            currentCalender.setTimeInMillis(calendarWithFirstDayOfMonth.getTimeInMillis());
            performOnDayClickCallback(currentCalender.getTime());
        }
    }

    public void performOnDayClickCallback(Date date) {
        if (listener != null) {
            listener.onDayClick(date);
            clickData = Calendar.getInstance();
            clickData.setTime(date);
        }
    }

    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        //ignore scrolling callback if already smooth scrolling
        if (isSmoothScrolling) {
            return true;
        }

        if (currentDirection == Direction.NONE) {
            if (Math.abs(distanceX) > Math.abs(distanceY)) {
                currentDirection = Direction.HORIZONTAL;
            } else {
                currentDirection = Direction.VERTICAL;
            }
        }

        isScrolling = true;
        this.distanceX = distanceX;
        return true;
    }

    public  boolean onTouch(MotionEvent event) {
        if (velocityTracker == null) {
            velocityTracker = VelocityTracker.obtain();
        }

        velocityTracker.addMovement(event);

        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            if (!scroller.isFinished()) {
                scroller.abortAnimation();
            }
            isSmoothScrolling = false;

        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            velocityTracker.addMovement(event);
            velocityTracker.computeCurrentVelocity(500);

        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            handleHorizontalScrolling();
            velocityTracker.recycle();
            velocityTracker.clear();
            velocityTracker = null;
            isScrolling = false;
        }
        return false;
    }

    public void snapBackScroller() {
        float remainingScrollAfterFingerLifted1 = (accumulatedScrollOffset.x - (monthsScrolledSoFar * width));
        scroller.startScroll((int) accumulatedScrollOffset.x, 0, (int) -remainingScrollAfterFingerLifted1, 0);
    }

    public void handleHorizontalScrolling() {
        int velocityX = computeVelocity();
        handleSmoothScrolling(velocityX);

        currentDirection = Direction.NONE;
        setCalenderToFirstDayOfMonth(calendarWithFirstDayOfMonth, currentDate, -monthsScrolledSoFar, 0);

        if (calendarWithFirstDayOfMonth.get(Calendar.MONTH) != currentCalender.get(Calendar.MONTH)) {
            setCalenderToFirstDayOfMonth(currentCalender, currentDate, -monthsScrolledSoFar, 0);
        }

    }

    public int computeVelocity() {
        velocityTracker.computeCurrentVelocity(VELOCITY_UNIT_PIXELS_PER_SECOND, maximumVelocity);
        return (int) velocityTracker.getXVelocity();
    }

    public void handleSmoothScrolling(int velocityX) {
        int distanceScrolled = (int) (accumulatedScrollOffset.x - (width * monthsScrolledSoFar));
        boolean isEnoughTimeElapsedSinceLastSmoothScroll = System.currentTimeMillis() - lastAutoScrollFromFling > LAST_FLING_THRESHOLD_MILLIS;
        if (velocityX > densityAdjustedSnapVelocity && isEnoughTimeElapsedSinceLastSmoothScroll) {
            scrollPreviousMonth();
        } else if (velocityX < -densityAdjustedSnapVelocity && isEnoughTimeElapsedSinceLastSmoothScroll) {
            scrollNextMonth();
        } else if (isScrolling && distanceScrolled > distanceThresholdForAutoScroll) {
            scrollPreviousMonth();
        } else if (isScrolling && distanceScrolled < -distanceThresholdForAutoScroll) {
            scrollNextMonth();
        } else {
            isSmoothScrolling = false;
            snapBackScroller();
        }
    }

    public void scrollNextMonth() {
        lastAutoScrollFromFling = System.currentTimeMillis();
        monthsScrolledSoFar = monthsScrolledSoFar - 1;
        performScroll();
        isSmoothScrolling = true;
        performMonthScrollCallback();
    }

    public void scrollPreviousMonth() {
        lastAutoScrollFromFling = System.currentTimeMillis();
        monthsScrolledSoFar = monthsScrolledSoFar + 1;
        performScroll();
        isSmoothScrolling = true;
        performMonthScrollCallback();
    }

    public void performMonthScrollCallback() {
        if (listener != null) {
            listener.onMonthScroll(getFirstDayOfCurrentMonth());
        }
    }

    public void performScroll() {
        int targetScroll = monthsScrolledSoFar * width;
        float remainingScrollAfterFingerLifted = targetScroll - accumulatedScrollOffset.x;
        scroller.startScroll((int) accumulatedScrollOffset.x, 0, (int) (remainingScrollAfterFingerLifted), 0,
                (int) (Math.abs((int) (remainingScrollAfterFingerLifted)) / (float) width * ANIMATION_SCREEN_SET_DURATION_MILLIS));
    }

    public  int getHeightPerDay() {
        return heightPerDay;
    }

    public int getWeekNumberForCurrentMonth() {
        Calendar calendar = Calendar.getInstance(locale);
        calendar.setTime(currentDate);
        return calendar.get(Calendar.WEEK_OF_MONTH);
    }

    public Date getFirstDayOfCurrentMonth() {
        Calendar calendar = Calendar.getInstance(locale);
        calendar.setTime(currentDate);
        calendar.add(Calendar.MONTH, -monthsScrolledSoFar);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        setToMidnight(calendar);
        return calendar.getTime();
    }

    public  void setCurrentDate(Date dateTimeMonth) {
        distanceX = 0;
        monthsScrolledSoFar = 0;
        accumulatedScrollOffset.x = 0;
        scroller.startScroll(0, 0, 0, 0);
        currentDate = new Date(dateTimeMonth.getTime());
        currentCalender.setTime(currentDate);
        setToMidnight(currentCalender);
    }

    public void setToMidnight(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }

    public void addEvent(Event event) {
        eventsCalendar.setTimeInMillis(event.getTimeInMillis());
        String key = getKeyForCalendarEvent(eventsCalendar);
        List<Events> eventsForMonth = eventsByMonthAndYearMap.get(key);
        if (eventsForMonth == null) {
            eventsForMonth = new ArrayList<>();
        }
        Events eventsForTargetDay = getEventDayEvent(event.getTimeInMillis());
        if (eventsForTargetDay == null) {
            List<Event> events = new ArrayList<>();
            events.add(event);
            eventsForMonth.add(new Events(event.getTimeInMillis(), events));
        } else {
            eventsForTargetDay.getEvents().add(event);
        }
        eventsByMonthAndYearMap.put(key, eventsForMonth);
    }

    public void addEvents(List<Event> events) {
        int count = events.size();
        for (int i = 0; i < count; i++) {
            addEvent(events.get(i));
        }
    }

    public List<Event> getCalendarEventsFor(Date date) {
        return getCalendarEventsFor(date.getTime());
    }

    public List<Event> getCalendarEventsFor(long epochMillis) {
        Events events = getEventDayEvent(epochMillis);
        if (events == null) {
            return new ArrayList<>();
        } else {
            return events.getEvents();
        }
    }

    public Events getEventDayEvent(long eventTimeInMillis){
        eventsCalendar.setTimeInMillis(eventTimeInMillis);
        int dayInMonth = eventsCalendar.get(Calendar.DAY_OF_MONTH);
        String keyForCalendarEvent = getKeyForCalendarEvent(eventsCalendar);
        List<Events> eventsForMonthsAndYear = eventsByMonthAndYearMap.get(keyForCalendarEvent);
        if (eventsForMonthsAndYear != null) {
            for (Events events : eventsForMonthsAndYear) {
                eventsCalendar.setTimeInMillis(events.getTimeInMillis());
                int dayInMonthFromCache = eventsCalendar.get(Calendar.DAY_OF_MONTH);
                if (dayInMonthFromCache == dayInMonth) {
                    return events;
                }
            }
        }
        return null;
    }

    public void removeEventsByDate(Date dateToRemoveEventFor){
        removeEventByEpochMillis(dateToRemoveEventFor.getTime());
    }

    public void removeEventByEpochMillis(long epochMillis) {
        eventsCalendar.setTimeInMillis(epochMillis);
        int dayInMonth = eventsCalendar.get(Calendar.DAY_OF_MONTH);
        String key = getKeyForCalendarEvent(eventsCalendar);
        List<Events> eventsForMonthAndYear = eventsByMonthAndYearMap.get(key);
        if (eventsForMonthAndYear != null) {
            Iterator<Events> calendarDayEventIterator = eventsForMonthAndYear.iterator();
            while (calendarDayEventIterator.hasNext()) {
                Events next = calendarDayEventIterator.next();
                eventsCalendar.setTimeInMillis(next.getTimeInMillis());
                int dayInMonthFromCache = eventsCalendar.get(Calendar.DAY_OF_MONTH);
                if (dayInMonthFromCache == dayInMonth) {
                    calendarDayEventIterator.remove();
                    return;
                }
            }
        }
    }

    public void removeEvent(Event event) {
        eventsCalendar.setTimeInMillis(event.getTimeInMillis());
        String key = getKeyForCalendarEvent(eventsCalendar);
        List<Events> eventsForMonthAndYear = eventsByMonthAndYearMap.get(key);
        if (eventsForMonthAndYear != null) {
            for(Events events : eventsForMonthAndYear){
                int indexOfEvent = events.getEvents().indexOf(event);
                if (indexOfEvent >= 0) {
                    events.getEvents().remove(indexOfEvent);
                    return;
                }
            }
        }
    }

    public void removeEvents(List<Event> events) {
        int count = events.size();
        for (int i = 0; i < count; i++) {
            removeEvent(events.get(i));
        }
    }

    //E.g. 4 2016 becomes 2016_4
    public String getKeyForCalendarEvent(Calendar cal) {
        return cal.get(Calendar.YEAR) + "_" + cal.get(Calendar.MONTH);
    }


    public void setGrowProgress(float grow) {
        growFactor = grow;
    }

    public float getGrowFactor() {
        return growFactor;
    }

    public boolean onDown(MotionEvent e) {
        scroller.forceFinished(true);
        return true;
    }

    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        scroller.forceFinished(true);
        return true;
    }

    public boolean computeScroll() {
        if (scroller.computeScrollOffset()) {
            accumulatedScrollOffset.x = scroller.getCurrX();
            return true;
        }
        return false;
    }

    public void drawScrollableCalender(Canvas canvas) {
        drawPreviousMonth(canvas);
        drawCurrentMonth(canvas);
        drawNextMonth(canvas);
    }

    public void drawNextMonth(Canvas canvas) {
        setCalenderToFirstDayOfMonth(calendarWithFirstDayOfMonth, currentDate, -monthsScrolledSoFar, 1);
        drawMonth(canvas, calendarWithFirstDayOfMonth, (width * (-monthsScrolledSoFar + 1)));
    }

    public void drawCurrentMonth(Canvas canvas) {
        setCalenderToFirstDayOfMonth(calendarWithFirstDayOfMonth, currentDate, -monthsScrolledSoFar, 0);
        drawMonth(canvas, calendarWithFirstDayOfMonth, width * -monthsScrolledSoFar);
    }

    public void drawPreviousMonth(Canvas canvas) {
        setCalenderToFirstDayOfMonth(calendarWithFirstDayOfMonth, currentDate, -monthsScrolledSoFar, -1);
        drawMonth(canvas, calendarWithFirstDayOfMonth, (width * (-monthsScrolledSoFar - 1)));
    }

    public void calculateXPositionOffset() {
        if (currentDirection == Direction.HORIZONTAL) {
            accumulatedScrollOffset.x -= distanceX;
        }
    }

    public void drawCalenderBackground(Canvas canvas) {
        dayPaint.setColor(calenderBackgroundColor);
        dayPaint.setStyle(Paint.Style.FILL);
        canvas.drawRect(0, 0, width, height, dayPaint);
        dayPaint.setStyle(Paint.Style.STROKE);
        dayPaint.setColor(calenderTextColor);
    }

    public void drawEvents(Canvas canvas, Calendar currentMonthToDrawCalender, int offset) {
        List<Events> uniqEventses =
                eventsByMonthAndYearMap.get(getKeyForCalendarEvent(currentMonthToDrawCalender));

        boolean shouldDrawCurrentDayCircle = currentMonthToDrawCalender.get(Calendar.MONTH) == todayCalender.get(Calendar.MONTH);
        int todayDayOfMonth = todayCalender.get(Calendar.DAY_OF_MONTH);

        if (uniqEventses != null) {
            for (int i = 0; i < uniqEventses.size(); i++) {
                Events events = uniqEventses.get(i);
                long timeMillis = events.getTimeInMillis();
                eventsCalendar.setTimeInMillis(timeMillis);

                int dayOfWeek = getDayOfWeek(eventsCalendar) - 1;

                int weekNumberForMonth = eventsCalendar.get(Calendar.WEEK_OF_MONTH);
                float xPosition = widthPerDay * dayOfWeek + paddingWidth + paddingLeft + accumulatedScrollOffset.x + offset - paddingRight;
                float yPosition = weekNumberForMonth * heightPerDay + paddingHeight;

                if (((animationStatus == EXPOSE_CALENDAR_ANIMATION || animationStatus == ANIMATE_INDICATORS) && xPosition >= growFactor ) || yPosition >= growFactor) {
                    continue;
                } else if (animationStatus == EXPAND_COLLAPSE_CALENDAR && yPosition >= growFactor){
                    continue;
                }

                List<Event> eventsList = events.getEvents();
                int dayOfMonth = eventsCalendar.get(Calendar.DAY_OF_MONTH);
                boolean isSameDayAsCurrentDay = (todayDayOfMonth == dayOfMonth && shouldDrawCurrentDayCircle);
                boolean isCurrentSelectedDay = currentCalender.get(Calendar.DAY_OF_MONTH) == dayOfMonth;

//                if (!isSameDayAsCurrentDay && !isCurrentSelectedDay || animationStatus == EXPOSE_CALENDAR_ANIMATION) {
//                if (animationStatus == EXPOSE_CALENDAR_ANIMATION) {
                    if (eventsList.size() >= 3) {
                        drawEventsWithPlus(canvas, xPosition, yPosition, eventsList);
                    } else if (eventsList.size() == 2) {
                        drawTwoEvents(canvas, xPosition, yPosition, eventsList);
                    } else if (eventsList.size() == 1) {
                        drawSingleEvent(canvas, xPosition, yPosition, eventsList);
                    }
//                }
            }
        }
    }

    public void drawSingleEvent(Canvas canvas, float xPosition, float yPosition, List<Event> eventsList) {
        Event event = eventsList.get(0);
        drawSmallIndicatorCircle(canvas, xPosition, yPosition + yIndicatorOffset, event.getColor());
    }

    public void drawTwoEvents(Canvas canvas, float xPosition, float yPosition, List<Event> eventsList) {
        //draw fist event just left of center
        drawSmallIndicatorCircle(canvas, xPosition + (xIndicatorOffset * -1), yPosition + yIndicatorOffset, eventsList.get(0).getColor());
        //draw second event just right of center
        drawSmallIndicatorCircle(canvas, xPosition + (xIndicatorOffset * 1), yPosition + yIndicatorOffset, eventsList.get(1).getColor());
    }

    //draw 2 eventsByMonthAndYearMap followed by plus indicator to show there are more than 2 eventsByMonthAndYearMap
    public void drawEventsWithPlus(Canvas canvas, float xPosition, float yPosition, List<Event> eventsList) {
        // k = size() - 1, but since we don't want to draw more than 2 indicators, we just stop after 2 iterations so we can just hard k = -2 instead
        // we can use the below loop to draw arbitrary eventsByMonthAndYearMap based on the current screen size, for example, larger screens should be able to
        // display more than 2 evens before displaying plus indicator, but don't draw more than 3 indicators for now
        for (int j = 0, k = -2; j < 3; j++, k += 2) {
            Event event = eventsList.get(j);
            float xStartPosition = xPosition + (xIndicatorOffset * k);
            float yStartPosition = yPosition + yIndicatorOffset;
            if (j == 2) {
                dayPaint.setColor(multiEventIndicatorColor);
                dayPaint.setStrokeWidth(multiDayIndicatorStrokeWidth);
                canvas.drawLine(xStartPosition - smallIndicatorRadius, yStartPosition, xStartPosition + smallIndicatorRadius, yStartPosition, dayPaint);
                canvas.drawLine(xStartPosition, yStartPosition - smallIndicatorRadius, xStartPosition, yStartPosition + smallIndicatorRadius, dayPaint);
                dayPaint.setStrokeWidth(0);
            } else {
                drawSmallIndicatorCircle(canvas, xStartPosition, yStartPosition, event.getColor());
            }
        }
    }

    public int getDayOfWeek(Calendar calendar) {
        int dayOfWeek;
        if (!shouldShowMondayAsFirstDay) {
            return calendar.get(Calendar.DAY_OF_WEEK);
        } else {
            dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
            dayOfWeek = dayOfWeek <= 0 ? 7 : dayOfWeek;
        }
        return dayOfWeek;
    }

    public void drawMonth(Canvas canvas, Calendar monthToDrawCalender, int offset) {
        drawEvents(canvas, monthToDrawCalender, offset);

        //offset by one because we want to start from Monday
        int firstDayOfMonth = getDayOfWeek(monthToDrawCalender);

        //offset by one because of 0 index based calculations
        firstDayOfMonth = firstDayOfMonth - 1;
        boolean isSameMonthAsToday = monthToDrawCalender.get(Calendar.MONTH) == todayCalender.get(Calendar.MONTH);
        boolean isSameYearAsToday = monthToDrawCalender.get(Calendar.YEAR) == todayCalender.get(Calendar.YEAR);
        boolean isSameMonthAsCurrentCalendar = monthToDrawCalender.get(Calendar.MONTH) == currentCalender.get(Calendar.MONTH);
        int todayDayOfMonth = todayCalender.get(Calendar.DAY_OF_MONTH);
        boolean isAnimatingWithExpose = animationStatus == EXPOSE_CALENDAR_ANIMATION;

        for (int dayColumn = 0, dayRow = 0; dayColumn <= 6; dayRow++) {
            if (dayRow == 7) {
                dayRow = 0;
                if (dayColumn <= 6) {
                    dayColumn++;
                }
            }
            if (dayColumn==dayColumnNames.length) {
                break;
            }
            float xPosition = widthPerDay * dayColumn +paddingWidth+paddingLeft + accumulatedScrollOffset.x + offset - paddingRight;
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
                if (isSameYearAsToday && isSameMonthAsToday && todayDayOfMonth == day && !isAnimatingWithExpose) {
                    drawCircle(canvas, xPosition, yPosition, currentDayBackgroundColor);
                } else if (currentCalender.get(Calendar.DAY_OF_MONTH) == day && isSameMonthAsCurrentCalendar && !isAnimatingWithExpose) {
                    drawCircle(canvas, xPosition, yPosition, currentSelectedDayBackgroundColor);
                } else if (day == 1 && !isSameMonthAsCurrentCalendar && !isAnimatingWithExpose
                        ) {
                    drawCircle(canvas, xPosition, yPosition, currentSelectedDayBackgroundColor);
                }
                if (day <= monthToDrawCalender.getActualMaximum(Calendar.DAY_OF_MONTH) && day > 0) {
                    canvas.drawText(String.valueOf(day), xPosition, yPosition, dayPaint);
                }
            }
        }
    }

    // Draw Circle on certain days to highlight them
    public void drawCircle(Canvas canvas, float x, float y, int color) {
        dayPaint.setColor(color);
        if (animationStatus == ANIMATE_INDICATORS) {
            float maxRadius = bigCircleIndicatorRadius * 0.8f;
            drawCircle(canvas, growfactorIndicator > maxRadius ? maxRadius: growfactorIndicator, x, y - (textHeight / 6));
        } else {
            float maxRadius = bigCircleIndicatorRadius*0.8f;
            drawCircle(canvas, maxRadius, x, y - (textHeight / 6));
        }
    }
    public void drawSmallIndicatorCircle(Canvas canvas, float x, float y, int color) {
        dayPaint.setColor(color);
        drawCircle(canvas, smallIndicatorRadius, x, y);
    }
    public void drawCircle(Canvas canvas, float radius, float x, float y) {
        dayPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(x, y, radius, dayPaint);
        dayPaint.setStyle(Paint.Style.STROKE);
        dayPaint.setColor(calenderTextColor);
    }
}
