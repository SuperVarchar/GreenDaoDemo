package com.hnshituo.icore_map.base.bean;

import com.google.gson.annotations.Expose;

/**
 * 成功的返回
 * @author Wzh
 * @date 2016/7/9  14:09
 */
public class ResultInfo<T> {
    /**
     * state : 200
     */
    //状态码  200成功
    @Expose
    public String state;
    @Expose
    public String msgInfo;
    @Expose
    public T data;
}
