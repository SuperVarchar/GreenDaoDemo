package com.hnshituo.icore_map.okhttp.callback;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.hnshituo.icore_map.okhttp.NetworkControl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by Administrator on 2016/6/21.
 * callback的Gson解析封装类,
 * 使用此类必须传入泛型,将返回的json数据解析成对应泛型的实体对象
 */
public abstract class GsonCallback<T> extends BaseCallBack<T> {

    public GsonCallback(NetworkControl networkControl, int type) {
        super(networkControl,type);
        mNetworkControl = networkControl;
    }

    @Override
    protected T parseResponse(String response) throws Exception {
        if (!TextUtils.isEmpty(response)) {
            if (34 == response.charAt(0) && 34 == response.charAt(response.length() - 1)) {
                response = response.substring(1, response.length() - 1);
            }
            Type mySuperClass = getClass().getGenericSuperclass();
            Type type = ((ParameterizedType) mySuperClass).getActualTypeArguments()[0];
            Gson gson = new Gson();
            return gson.fromJson(response, type);
        }else {
            return null;
        }
    }


}
