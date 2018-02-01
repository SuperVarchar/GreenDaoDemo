package com.hnshituo.icore_map.okhttp.callback;


import com.hnshituo.icore_map.okhttp.OkHttpUtils;
import com.hnshituo.icore_map.okhttp.request.RequestCall;

import java.io.File;
import java.util.Map;


public class RequestCallFactory {

    /**
     * get请求
     * @param url    url地址
     * @param params 上传的参数键值对的map集合
     * @return RequestCall对象
     */
    public static RequestCall getHttpGet(String url, Map<String, String> params, Object tag) {
        return OkHttpUtils
                .get()
                .url(url)
                .params(params)
                .tag(tag)
                .build();
    }

    /**
     * 多文件上传同时可以携带参数请求
     * @param url    url地址
     * @param objs   需要上传的对象
     * @param extras 需要上传的其他字符串
     * @param files  文件上传的集合
     * @return RequestCall
     */
    public static RequestCall getHttpPostAndUpload(String url, Object[] objs, String[] extras, Map<String, File> files, Object tag) {
        return OkHttpUtils.post().bodyExtras(extras)
                .bodyObjects(objs)
                .url(url)
                .files("uploadfile", files)
                .tag(tag)
                .build();
    }

    /**
     * post请求
     * @param url    url地址
     * @param objs   需要上传的对象
     * @param extras 需要上传的其他字符串
     * @return RequestCall
     */
    public static RequestCall getHttpPost(String url, Object[] objs, String[] extras, Object tag) {
        return OkHttpUtils
                .post()
                .bodyExtras(extras)
                .bodyObjects(objs)
                .url(url)
                .tag(tag)
                .build();
    }


    /**
     * 单文件上传的请求
     * @param url    url地址
     * @return RequestCall
     */
    public static RequestCall getHttpUpload(String url, File file, Object tag) {
        return OkHttpUtils
                .post()
                .url(url)
                .addFile(file)
                .tag(tag)
                .build();
    }


}
