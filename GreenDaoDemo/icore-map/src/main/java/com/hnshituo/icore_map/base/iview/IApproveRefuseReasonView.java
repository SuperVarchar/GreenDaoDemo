package com.hnshituo.icore_map.base.iview;

import com.hnshituo.icore_map.base.bean.WmsBusinessInfo;
import com.hnshituo.icore_map.okhttp.NetworkControl;

/**
 * Created by Administrator on 2016/7/12.
 */
public interface IApproveRefuseReasonView extends NetworkControl {
    String getContent();
    void startResultToApproveDetailFragment();

    WmsBusinessInfo getWmsBusinessInfo();
}
