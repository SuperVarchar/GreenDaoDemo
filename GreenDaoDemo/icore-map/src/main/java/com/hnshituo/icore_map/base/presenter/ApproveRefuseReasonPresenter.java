package com.hnshituo.icore_map.base.presenter;

import com.hnshituo.icore_map.base.bean.ResultInfo;
import com.hnshituo.icore_map.base.iview.IApproveRefuseReasonView;
import com.hnshituo.icore_map.base.model.ApproveRefuseReasonModel;
import com.hnshituo.icore_map.okhttp.callback.BaseCallBack;
import com.hnshituo.icore_map.okhttp.callback.GsonCallback;

/**
 * Created by Administrator on 2016/7/12.
 */
public class ApproveRefuseReasonPresenter {

    private IApproveRefuseReasonView mIApproveRefuseReasonView;
    private ApproveRefuseReasonModel mApproveRefuseReasonModel;

    public ApproveRefuseReasonPresenter(IApproveRefuseReasonView IApproveRefuseReasonView) {
        mIApproveRefuseReasonView = IApproveRefuseReasonView;
        mApproveRefuseReasonModel = new ApproveRefuseReasonModel();
    }


    public void refuse(Object tag){
        mApproveRefuseReasonModel.setReason(mIApproveRefuseReasonView.getContent());
        mApproveRefuseReasonModel.setWmsBusinessInfo(mIApproveRefuseReasonView.getWmsBusinessInfo());
        mApproveRefuseReasonModel.refuse(tag, new GsonCallback<ResultInfo>(mIApproveRefuseReasonView, BaseCallBack.DIALOG) {
            @Override
            public void onResponse(ResultInfo response) {
                if ("200".equals(response.state)) {
                    mIApproveRefuseReasonView.startResultToApproveDetailFragment();
                }
            }


        });
    }

    public void agree(Object tag) {
        mApproveRefuseReasonModel.setReason(mIApproveRefuseReasonView.getContent());
        mApproveRefuseReasonModel.setWmsBusinessInfo(mIApproveRefuseReasonView.getWmsBusinessInfo());
        mApproveRefuseReasonModel.agree(tag, new GsonCallback<ResultInfo>(mIApproveRefuseReasonView,BaseCallBack.DIALOG) {
            @Override
            public void onResponse(ResultInfo response) {
                if ("200".equals(response.state)) {
                    mIApproveRefuseReasonView.startResultToApproveDetailFragment();
                }
            }


        });

    }
}
