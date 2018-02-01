package com.hnshituo.icore_map.base.iview;

import android.view.View;

/**
 * @author Wzh
 * @date 2016/7/4  10:02
 */
public interface BaseIView {
    void alert(String message);
    void showSweetDialog(String title, View.OnClickListener listener);
}
