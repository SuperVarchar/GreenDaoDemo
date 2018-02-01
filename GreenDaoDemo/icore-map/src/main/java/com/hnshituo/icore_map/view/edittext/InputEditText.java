package com.hnshituo.icore_map.view.edittext;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;

/**
 * 带软键盘监听的EditText
 * @author Wzh
 * @date 2017/4/14  9:05
 */
public class InputEditText extends AppCompatEditText {

    private InputManageListener mListener;

    public InputEditText(Context context) {
        super(context);
    }

    public InputEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public InputEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setInputManageListener(InputManageListener listener) {
        mListener = listener;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(mListener != null){
            mListener.inputIsShow(true);
        }
        return super.onTouchEvent(event);
    }


    @Override
    public boolean onKeyPreIme (int keyCode, KeyEvent event){
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN ) {
            if(mListener != null){
                mListener.inputIsShow(false);
            }
        }

        return super.onKeyPreIme(keyCode, event);
    }

    public interface InputManageListener {
        void inputIsShow(boolean isShow);
    }

}
