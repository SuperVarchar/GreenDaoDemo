package com.hnshituo.icore_map.view.pickView;

import com.bigkoo.pickerview.adapter.WheelAdapter;

/**
 * Created by Administrator on 2016/9/21.
 */
public class MinNumericWheelApapter implements WheelAdapter {
    /**
     * The default min value
     */
    public static final int DEFAULT_MAX_VALUE = 9;

    /**
     * The default max value
     */
    private static final int DEFAULT_MIN_VALUE = 0;

    // Values
    private int minValue;
    private int maxValue;

    /**
     * Default constructor
     */
    public MinNumericWheelApapter() {
        this(DEFAULT_MIN_VALUE, DEFAULT_MAX_VALUE);
    }

    /**
     * Constructor
     *
     * @param minValue the wheel min value
     * @param maxValue the wheel max value
     */
    public MinNumericWheelApapter(int minValue, int maxValue) {
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    @Override
    public Object getItem(int index) {
        if (index >= 0 && index < getItemsCount()) {
            int value = minValue + index;
            return value * CustomerTimerPickerView.MINS;
        }
        return 0;
    }

    @Override
    public int getItemsCount() {
        return maxValue - minValue + 1;
    }

    @Override
    public int indexOf(Object o) {
        return ((int) o)/CustomerTimerPickerView.MINS - minValue;
    }

}
