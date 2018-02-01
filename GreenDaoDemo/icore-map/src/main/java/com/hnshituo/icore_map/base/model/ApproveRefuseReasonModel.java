package com.hnshituo.icore_map.base.model;

import com.hnshituo.icore_map.base.Constant;
import com.hnshituo.icore_map.base.bean.WmsBusinessInfo;
import com.hnshituo.icore_map.okhttp.callback.Callback;
import com.hnshituo.icore_map.okhttp.callback.RequestCallFactory;

/**
 * Created by Administrator on 2016/7/12.
 */
public class ApproveRefuseReasonModel {
    private WmsBusinessInfo mWmsBusinessInfo;

    private String reason;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public WmsBusinessInfo getWmsBusinessInfo() {
        return mWmsBusinessInfo;
    }

    public void setWmsBusinessInfo(WmsBusinessInfo wmsBusinessInfo) {
        mWmsBusinessInfo = wmsBusinessInfo;
    }


    public void refuse(Object tag, Callback callback) {
        mWmsBusinessInfo.valid_flag = "1";
        mWmsBusinessInfo.content = reason;
        RequestCallFactory.getHttpPost(Constant.APPROVE_CONTROL,
                new Object[]{mWmsBusinessInfo},null, tag).execute(callback);
    }

    public void agree(Object tag, Callback callback) {
        mWmsBusinessInfo.valid_flag = "2";
        mWmsBusinessInfo.content = reason;
        RequestCallFactory.getHttpPost(Constant.APPROVE_CONTROL,
                new Object[]{mWmsBusinessInfo},null, tag).execute(callback);
    }
}
