package com.hnshituo.icore_map.okhttp.builder;


import com.hnshituo.icore_map.okhttp.OkHttpUtils;
import com.hnshituo.icore_map.okhttp.request.OtherRequest;
import com.hnshituo.icore_map.okhttp.request.RequestCall;

/**
 * Created by zhy on 16/3/2.
 */
public class HeadBuilder extends GetBuilder
{
    @Override
    public RequestCall build()
    {
        return new OtherRequest(null, null, OkHttpUtils.METHOD.HEAD, url, tag, params, headers,id).build();
    }
}
