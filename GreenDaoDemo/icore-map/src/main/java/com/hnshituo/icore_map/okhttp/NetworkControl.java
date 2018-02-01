package com.hnshituo.icore_map.okhttp;

import okhttp3.Call;

/**
 * 网络请求结果处理的回调
*/
public interface NetworkControl {
    /**
     * 开始加载
     * @param type  可以作为界面显示的风格的标示
     */
     void showLoading(int type);

    /**
     * 该次网络请求已经请求完成,并且成功的回调
     *  @param call 请求
    */
      void showSuccess(Call call);

    /**
     * 该次网络请求已经完成,并且请求失败
     * @param msg   错误信息
     * @param type  可以作为界面显示的风格的标示
     */
      void showFail(String msg, int type);

    /**
     *重连
    */
     void reTry();

    /**
     * 成功的View
     *  @param type  可以作为界面显示的风格的标示
    */
    void showSuccessView(int type);

    /**
     * session过期的处理
     */
    void sessionTimeOut();
    /**
     * 没有访问权限的处理
     */
    void noPermission();
}
