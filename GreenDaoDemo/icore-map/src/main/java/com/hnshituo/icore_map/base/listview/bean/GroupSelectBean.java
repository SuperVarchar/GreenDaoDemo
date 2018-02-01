package com.hnshituo.icore_map.base.listview.bean;

/**
 * Created by Administrator on 2017/1/20.
 */

public class GroupSelectBean<T> {
    public boolean isSelect;
    public String sortLetters;
    public String mainField;
    public String subField;
    public String extraField;

    public T data;

    public String getSortLetters() {
        return sortLetters;
    }

    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
