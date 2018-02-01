package com.hnshituo.icore_map.util;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * @author Wzh
 * @date 2016/12/15  9:28
 */
public class MoneyTextWatcher implements TextWatcher {

    private boolean isRun = false;
    private EditText mEditText;


    public MoneyTextWatcher(EditText editText){
        this.mEditText = editText;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if(isRun){//这几句要加，不然每输入一个值都会执行两次onTextChanged()，导致堆栈溢出，原因不明
            isRun = false;
            return;
        }

        if((!(s.toString().endsWith("")) && (!(s.toString().endsWith(".0"))))){
            String text = AmountUtils.getString(s.toString().replace(",", ""));
            isRun = true;
            mEditText.setText(text);
            //此语句不可少，否则输入的光标会出现在最左边，不会随输入的值往右移动
            mEditText.setSelection(mEditText.getText().length());
        }

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
