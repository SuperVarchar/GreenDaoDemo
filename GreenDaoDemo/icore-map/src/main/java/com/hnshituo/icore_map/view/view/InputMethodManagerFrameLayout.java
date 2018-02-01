package com.hnshituo.icore_map.view.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;

/**
 * 带键盘监听的自定义View
 * @author Wzh
 * @date 2016/11/26  12:07
 */
public class InputMethodManagerFrameLayout extends FrameLayout {

    private Context context;
    private InputMethodManagerListener mListener;

    public InputMethodManagerFrameLayout(Context context) {
        super(context);
        this.context = context;
    }

    public InputMethodManagerFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    public InputMethodManagerFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    public boolean dispatchKeyEventPreIme(KeyEvent event) {
        if (context!= null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm.isActive() && event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
                if(mListener != null){
                    mListener.InputMethodManagerHide();
                }
            }
        }

        return super.dispatchKeyEventPreIme(event);
    }

    public void setListener(InputMethodManagerListener listener) {
        mListener = listener;
    }

   public interface InputMethodManagerListener{
        void InputMethodManagerHide();
    }
}
