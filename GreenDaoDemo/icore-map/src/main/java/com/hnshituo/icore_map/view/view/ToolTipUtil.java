package com.hnshituo.icore_map.view.view;

import android.content.Context;
import android.view.View;

/**
 * Coder：胡大石头
 * Time:  2016/8/11 20:41
 * Email：huxiaoliu0826@gmail.com
 * 自定义提示的popupWindow的工具类
 */
public class ToolTipUtil {

    private static TooltipWindow tooltipWindow;

    /**
     * TOP: popupWindow在顶部显示时  不显示小箭头
     * CUSTOMER：popupWindow不在顶部显示时，显示小箭头
     */
    public enum TYPE {
        TOP, CUSTOMER
    }

    public ToolTipUtil() {
    }


    /**
     * 显示自定义的popupWindow
     *
     * @param context  上下文数据
     * @param message  显示的message
     * @param dropView 显示的位置，在某个view的下方
     * @param type     popupWindow的显示风格，TOP为在顶端时使用没有箭头显示，CUSTOMER有箭头显示
     */
    public static void showToolTipWindow(Context context, String message,
                                         View dropView, TYPE type) {
        tooltipWindow = new TooltipWindow(context, message, type);
        tooltipWindow.showToolTip(dropView);
    }

    /**
     * 在Activity或者fragment销毁时调用
     */
    public static void dismissTipWindow(){
        if (tooltipWindow!=null){
            if (tooltipWindow.isToolTipShown()){
                tooltipWindow.dismissToolTip();
            }
        }
    }


}
