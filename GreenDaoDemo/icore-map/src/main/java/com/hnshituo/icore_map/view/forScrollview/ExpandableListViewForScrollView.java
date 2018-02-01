package com.hnshituo.icore_map.view.forScrollview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ExpandableListView;

/**
 * 镶嵌在ScrollView中的ExpandableListView
 * @author Wzh
 * @date 2016/11/3  10:35
 */
public class ExpandableListViewForScrollView extends ExpandableListView {
    public ExpandableListViewForScrollView(Context context) {
        super(context);
    }

    public ExpandableListViewForScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ExpandableListViewForScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measureSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, measureSpec);
    }
}
