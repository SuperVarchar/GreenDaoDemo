package com.hnshituo.icore_map.download.model;

import android.widget.BaseAdapter;

import com.hnshituo.icore_map.ICoreMapClient;
import com.hnshituo.icore_map.base.bean.DownloadItem;
import com.hnshituo.icore_map.download.adapter.DownloadAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 下载界面
 * @author Wzh
 * @date 2016/7/25  17:08
 */
public class DownloadMode {

    private static DownloadMode mode = null;

    private DownloadMode() {
    }

    public static DownloadMode getDownloadMode() {
        if (mode == null) {
            mode = new DownloadMode();
        }
        return mode;
    }



    private DownloadAdapter adapter;
    //正在下载列表
    private List<DownloadItem> list = new ArrayList<>();


    public BaseAdapter getAdapter() {
        adapter = new DownloadAdapter(ICoreMapClient.application);
        adapter.setData(list);
        return adapter;
    }

    public void remove(int position) {
        list.remove(position);
    }

    public void upData() {
        adapter.setData(list);
        adapter.notifyDataSetChanged();
    }

    public List<DownloadItem> getList() {
        return list;
    }

    public void addDownload(DownloadItem downloadItem){
        list.add(downloadItem);
    }

    public void removeDownload(DownloadItem downloadItem) {
        list.remove(downloadItem);
    }

    public void setList(List<DownloadItem> list) {
        this.list = list;
    }
}
