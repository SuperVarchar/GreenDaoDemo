package com.hnshituo.icore_map.base.bean;

import com.google.gson.annotations.Expose;

/**
 * 打开同级Fragment的事件
 * @author Wzh
 * @date 2016/7/5  17:04
 */
public class OperateBarEvent {
    @Expose
    public boolean isShow;

    public OperateBarEvent(boolean isShow) {
        this.isShow = isShow;
    }
}
