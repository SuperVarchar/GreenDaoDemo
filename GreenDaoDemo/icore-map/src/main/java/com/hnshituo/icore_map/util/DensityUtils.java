package com.hnshituo.icore_map.util;

import android.content.Context;
import android.util.TypedValue;

/** 
 * 常用单位转换的辅助类 
 *  
 *  
 *  
 */  
public class DensityUtils  
{  
    private DensityUtils()  
    {  
        /* cannot be instantiated */  
        throw new UnsupportedOperationException("cannot be instantiated");  
    }  

  
    /** 
     * sp转px 
     *  
     * @param context 
     * @param
     * @return 
     */  
    public static int sp2px(Context context, float spVal)  
    {  
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,  
                spVal, context.getResources().getDisplayMetrics());  
    }  

}  