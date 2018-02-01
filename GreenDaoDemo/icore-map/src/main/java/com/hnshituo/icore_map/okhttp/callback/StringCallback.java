package com.hnshituo.icore_map.okhttp.callback;

import android.text.TextUtils;

import com.hnshituo.icore_map.okhttp.NetworkControl;


public abstract class StringCallback extends BaseCallBack<String> {
    public StringCallback(NetworkControl networkControl, int type) {
        super(networkControl, type);
    }


    @Override
    protected String parseResponse(String response) throws Exception {
        if (TextUtils.isEmpty(response)) {
            return null;
        } else {
            if (34 == response.charAt(0) && 34 == response.charAt(response.length() - 1)) {
                response = response.substring(1, response.length() - 1);
            }
            return response;
        }


    }


}
