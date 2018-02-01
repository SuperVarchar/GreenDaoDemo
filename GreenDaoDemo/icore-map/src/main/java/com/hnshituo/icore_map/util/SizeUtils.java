package com.hnshituo.icore_map.util;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

/**
 * Created by hulei on 2016/5/4.
 *
 */
public class SizeUtils {

    /**
     * The absolute width of the display in pixels
     *
     * @param context Context
     * @return px(int)
     */
    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * Size tool to convert dp to px
     *
     * @param context Context
     * @param dpValue dp unit
     * @return The converted size in pixel
     */
    public static int convertDp2Px(Context context, int dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }



    public static int roundDIP(Context context, int i) {
        return Math.round(TypedValue.applyDimension(1, (float)i, context.getResources().getDisplayMetrics()));
    }

    public static float dp2px(Resources resources, float dp) {
        final float scale = resources.getDisplayMetrics().density;
        return  dp * scale + 0.5f;
    }

    public static float sp2px(Resources resources, float sp){
        final float scale = resources.getDisplayMetrics().scaledDensity;
        return sp * scale;
    }


}
