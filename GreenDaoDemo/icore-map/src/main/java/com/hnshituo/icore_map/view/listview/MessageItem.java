package com.hnshituo.icore_map.view.listview;

import com.hnshituo.icore_map.view.view.SlideView;

/**
 * @author Wzh
 * @date 2016/7/5  14:44
 */
public class MessageItem {
    private Object data;
    private SlideView slideView;

    public MessageItem() {
    }

    public Object getData() {
        return this.data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public SlideView getSlideView() {
        return this.slideView;
    }

    public void setSlideView(SlideView slideView) {
        this.slideView = slideView;
    }
}