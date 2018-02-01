package com.hnshituo.icore_map.base.iview;

import android.widget.BaseAdapter;

import com.hnshituo.icore_map.okhttp.NetworkControl;


/**
 * 列表的基类View
 * Coder：wzh
 * Time:  2016/9/9 16:28
 * Email：274590753@qq.com
 */
public interface BaseListIView<T> extends NetworkControl {

    void showEmpty();

    void showNotEmpty();

    void setAdapter(BaseAdapter adapter);

    void startFragment(T info);

    void setListViewLoadMore(int size);
}
