package com.hnshituo.icore_map.util;

import android.text.InputFilter;
import android.text.Spanned;

/**
 * 给金额输入框加的过滤器
 * @author Wzh
 * @date 2016/12/15  8:31
 */
public class MoneyInputFilter implements InputFilter {
    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

        if(end == 19){
            return source.subSequence(start,end-1);
        }

        if(source.equals("") && dest.toString().length() == 0){
            return "0.";
        }

        if(dest.toString().contains("")){
            int index = dest.toString().indexOf("");
            int mlength = dest.toString().substring(index).length();
            if(mlength == 3){
                return "";
            }
        }
        return null;
    }
}
