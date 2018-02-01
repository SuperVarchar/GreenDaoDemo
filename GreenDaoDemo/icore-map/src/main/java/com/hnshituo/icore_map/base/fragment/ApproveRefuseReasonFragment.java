package com.hnshituo.icore_map.base.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.hnshituo.icore_map.R;
import com.hnshituo.icore_map.base.Constant;
import com.hnshituo.icore_map.base.bean.WmsBusinessInfo;
import com.hnshituo.icore_map.base.iview.IApproveRefuseReasonView;
import com.hnshituo.icore_map.base.presenter.ApproveRefuseReasonPresenter;

import okhttp3.Call;


/**
 * Created by Administrator on 2016/7/12.
 */
public class ApproveRefuseReasonFragment extends BaseFragment implements IApproveRefuseReasonView {

    EditText mRefuseReasonContent;
    TextView mRefuseReasonSumbit;
    private ApproveRefuseReasonPresenter mPresenter;
    private boolean mIsAgree;

    @Override
    public View initRootView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_approve_refuse_reason, container, false);
    }

    @Override
    public void initData() {

        mRefuseReasonContent = (EditText) rootView.findViewById(R.id.refuse_reason_content);
        mRefuseReasonSumbit = (TextView) rootView.findViewById(R.id.refuse_reason_sumbit);

        mRefuseReasonSumbit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIsAgree) {
                    mPresenter.agree(this);
                }else {
                    if (TextUtils.isEmpty(mRefuseReasonContent.getText().toString().trim())) {
                        alert(getString(R.string.refuse_reason_is_null));
                        return;
                    }
                    mPresenter.refuse(this);
                }
            }
        });

        mIsAgree = getArguments().getBoolean("isAgree");
        if (mIsAgree) {
            setTitleText(R.string.agree_reason,null);
        }else {
            setTitleText(R.string.refuse_reason, null);
        }

        setBackButton();

        mPresenter = new ApproveRefuseReasonPresenter(this);
    }

    /**
     * 获取拒绝原因
     * @return
     */
    public String getContent(){
        return mRefuseReasonContent.getText().toString().trim();
    }


    @Override

    public void showSuccess(Call call) {

    }

    @Override
    public void reTry() {

    }


    public static ApproveRefuseReasonFragment newInstance(WmsBusinessInfo wmsBusinessInfo, boolean isAgree) {
        Bundle args = new Bundle();
        args.putParcelable("WmsBusinessInfo",wmsBusinessInfo);
        args.putBoolean("isAgree",isAgree);
        ApproveRefuseReasonFragment fragment = new ApproveRefuseReasonFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void startResultToApproveDetailFragment(){
        setFragmentResult(Constant.ResultCode.REFUSE_REASON_OK,new Bundle());
    }

    @Override
    public WmsBusinessInfo getWmsBusinessInfo() {
        return getArguments().getParcelable("WmsBusinessInfo");
    }
}
