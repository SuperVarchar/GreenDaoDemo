package com.hnshituo.icore_map.okhttp.bean;

/**
 * Created by Administrator on 2016/6/21.
 */
public class ErrorResp {

    /**
     * code : -1
     * message : 解析方法参数出错，请检查请求参数及参数格式是否合法（JSON格式）
     */

    public ErrorBean error;

    public static class ErrorBean {
        public String code;
        public String message;
    }
}
