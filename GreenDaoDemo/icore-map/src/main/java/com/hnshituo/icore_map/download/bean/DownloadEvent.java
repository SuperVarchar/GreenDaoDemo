package com.hnshituo.icore_map.download.bean;

import com.google.gson.annotations.Expose;

/**
 * 服务传给界面的事件
 * @author Wzh
 * @date 2016/7/25  19:55
 */
public class DownloadEvent {
    @Expose
    public boolean isDelete;
    @Expose
    public int index;



    public DownloadEvent(int index,boolean isDelete) {
        this.index = index;
        this.isDelete = isDelete;
    }
}
