package com.hnshituo.icore_map.okhttp.callback;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import com.hnshituo.icore_map.okhttp.NetworkControl;


public abstract class BitmapCallback extends BaseCallBack<Bitmap>
{
    public BitmapCallback(NetworkControl networkControl, int type) {
        super(networkControl,type);
    }


    @Override
    protected Bitmap parseResponse(String response) throws Exception {
        if (TextUtils.isEmpty(response)) {
            return null;
        }else {

            byte[] bytes = response.getBytes("utf-8");
            return BitmapFactory.decodeByteArray(bytes,0,bytes.length);
        }
    }
    @Override
    public void onResponse(Bitmap response) {

    }
}
