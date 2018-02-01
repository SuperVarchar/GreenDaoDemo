package com.hnshituo.icore_map.okhttp.callback;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.hnshituo.icore_map.okhttp.NetworkControl;
import com.hnshituo.icore_map.okhttp.bean.ErrorResp;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/6/23.
 */
public abstract class BaseCallBack<T> extends Callback<T> {

    /**
     * 错误信息
     */
    protected String msg;
    /**
     * true是成功,false是失败
     */
    protected boolean isSuccess = false;
    /**
     * 网络处理的接口
     */
    protected NetworkControl mNetworkControl;
    /**
     * 显示进度条或者错误页的类型, 0: 不显示进度条  1:内嵌  2:对话框形式
     */
    protected int type;

    public static final int DIALOG = 2;
    public static final int INNER = 1;
    public static final int NONE = 0;

    public BaseCallBack(NetworkControl networkControl, int type) {
        mNetworkControl = networkControl;
        isSuccess = false;
        this.type = type;
    }

    @Override
    public void onError(Call call, Exception e,int id) {
//        if (e instanceof UnknownHostException) {
//            //UnknownHostException
//            if (TextUtils.isEmpty(msg)) {
//                msg = "网络链接异常,请检查网络环境";
//            }
//        } else if (e instanceof SocketTimeoutException) {
//            if (TextUtils.isEmpty(msg)) {
//                //SocketTimeoutException
//                msg = "网络链接异常,请检查网络环境";
//            }
//        } else {
//            if (TextUtils.isEmpty(msg)) {
//                msg = "网络链接异常,请检查网络环境";
//            }
//        }
        if (TextUtils.isEmpty(msg)) {
            msg = "网络链接异常,请检查网络环境";
        }
    }

    @Override
    public T parseNetworkResponse(Response response,int id) throws Exception {
        isSuccess = false;
        //如果请求没有返回数据
        String str = response.body().string();

        if (200 == response.code()) {
            isSuccess = true;
            return parseResponse(str);
        } else if (500 == response.code()) {
            Gson gson = new Gson();
            ErrorResp errorResp = gson.fromJson(str, ErrorResp.class);
            if ("-99".equals(errorResp.error.code)) { //session过期
                msg = "session过期,请重新登陆";
            } else if ("-98".equals(errorResp.error.code)) { //没有访问权限
                msg = "没有访问权限,请联系权限管理员";
            } else if ("-1".equals(errorResp.error.code)) {
//                msg = errorResp.error.message;
                msg = "服务器异常,请联系后台管理员";
            } else {
                msg = "服务器异常,请联系后台管理员";
            }
            return null;
        } else if (404 == response.code()) {
            msg = "网络异常,请检查网络环境";
            return null;
        }
        return null;
    }

    protected abstract T parseResponse(String response) throws Exception;

    @Override
    public void onBefore(Request request,int id) {
        super.onBefore(request,id);
        mNetworkControl.showLoading(type);
    }

    @Override
    public void onAfter(Call call,int id) {
        super.onAfter(call,id);
        if (isSuccess) {
            mNetworkControl.showSuccessView(type);
            mNetworkControl.showSuccess(call);
        } else {
            if ("session过期,请重新登陆".equals(msg)) {
                mNetworkControl.sessionTimeOut();
            } else if ("没有访问权限,请联系权限管理员".equals(msg)) {
                mNetworkControl.noPermission();
            } else {
                mNetworkControl.showFail(msg, type);
            }
        }
    }
}
