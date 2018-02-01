package com.hnshituo.icore_map.okhttp.builder;


import com.hnshituo.icore_map.okhttp.request.RequestCall;

import java.util.Map;


public abstract class OkHttpRequestBuilder<T extends OkHttpRequestBuilder>
{
    protected String url;
    protected Object tag;
    protected Map<String, String> headers;
    protected Object[] objs;
    protected String[] extras;
    protected Map<String, String> params;
    protected int id;

//    public abstract OkHttpRequestBuilder url(String url);
//    public abstract OkHttpRequestBuilder id(String id);
//
//    public abstract OkHttpRequestBuilder tag(Object tag);
//
//    public abstract OkHttpRequestBuilder headers(Map<String, String> headers);

//    public abstract OkHttpRequestBuilder addHeader(String key, String val);

    public abstract RequestCall build();


}
