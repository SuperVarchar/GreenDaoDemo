package com.hnshituo.icore_map.download.fragment.iview;

import android.widget.BaseAdapter;

/**
 * 下载界面
 * @author Wzh
 * @date 2016/7/25  17:07
 */
public interface IDownloadView {
    void showNotEmpty();

    void showEmpty();

    void setAdapter(BaseAdapter adapter);

}
