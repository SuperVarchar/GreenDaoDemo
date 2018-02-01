package com.hnshituo.icore_map.view.view;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.hnshituo.icore_map.R;


/**
 * Coder：胡大石头
 * Time:  2016/8/11 16:48
 * Email：huxiaoliu0826@gmail.com
 * 自定义可消失的提示popupWindow
 */
public class TooltipWindow {
    //这里定义的handle发送的标志参数，不能和项目里面的其他参数重复
    private static final int MSG_DISMISS_TOOLTIP = 22001;
    private Context mContext;
    private PopupWindow tipWindow;
    private View contentView;
    private LayoutInflater inflater;
    private String mMessage;
    private ToolTipUtil.TYPE mTYPE;

    public TooltipWindow(Context ctx, String message, ToolTipUtil.TYPE TYPE) {
        this.mContext = ctx.getApplicationContext();
        tipWindow = new PopupWindow(mContext);
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contentView = inflater.inflate(R.layout.layout_tooptipwindow, null);
        this.mMessage = message;
        this.mTYPE = TYPE;
        TextView tv = (TextView) contentView.findViewById(R.id.toolTip_text);
        switch (mTYPE) {
            case TOP:
                contentView.findViewById(R.id.tooltip_nav_up).setVisibility(View.GONE);
                break;
            case CUSTOMER:
                contentView.findViewById(R.id.tooltip_nav_up).setVisibility(View.VISIBLE);
        }
        tv.setText(mMessage.trim());
    }

    public void showToolTip(View anchor) {
        if (tipWindow != null && tipWindow.isShowing()) {
            tipWindow.dismiss();
            tipWindow = new PopupWindow();
            inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            contentView = inflater.inflate(R.layout.layout_tooptipwindow, null);
            TextView tv = (TextView) contentView.findViewById(R.id.toolTip_text);
            switch (mTYPE) {
                case TOP:
                    contentView.findViewById(R.id.tooltip_nav_up).setVisibility(View.GONE);
                    break;
                case CUSTOMER:
                    contentView.findViewById(R.id.tooltip_nav_up).setVisibility(View.VISIBLE);
            }
            tv.setText(mMessage.trim());
        }
        if (tipWindow != null) {
            tipWindow.setHeight(ActionBar.LayoutParams.WRAP_CONTENT);
            tipWindow.setWidth(ActionBar.LayoutParams.MATCH_PARENT);
            tipWindow.setOutsideTouchable(false);
            tipWindow.setTouchable(true);
            tipWindow.setFocusable(true);
            tipWindow.setBackgroundDrawable(new BitmapDrawable());
            tipWindow.setContentView(contentView);

            int screen_pos[] = new int[2];
            //Get Location of anchor view on screen
            anchor.getLocationOnScreen(screen_pos);

            //Call view measure to calculate how big your view should be.
            contentView.measure(ActionBar.LayoutParams.WRAP_CONTENT,
                    ActionBar.LayoutParams.WRAP_CONTENT);

            tipWindow.showAsDropDown(anchor);
            mTipWindowHandle.sendEmptyMessageDelayed(MSG_DISMISS_TOOLTIP, 2000);
        }
    }

    public boolean isToolTipShown() {
        if (tipWindow != null && tipWindow.isShowing()) {
            return true;
        }
        return false;
    }

    public void dismissToolTip() {
        if (tipWindow != null && tipWindow.isShowing()) {
            tipWindow.dismiss();
        }
    }


    Handler mTipWindowHandle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_DISMISS_TOOLTIP:
                    if (tipWindow != null && tipWindow.isShowing())
                        mTipWindowHandle.removeMessages(MSG_DISMISS_TOOLTIP);
                        tipWindow.dismiss();
                    break;
                default:
                    break;
            }
        }
    };
}
