package com.hnshituo.icore_map.util;

import android.text.InputFilter;
import android.text.Spanned;

/**
 * Created by liuba
 * time:2017/3/13 15:55
 * describe --
 */
public class MoneyNineInputFilter implements InputFilter {
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            if(end==11){
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

