package com.hnshituo.icore_map.base.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hnshituo.icore_map.R;
import com.hnshituo.icore_map.okhttp.NetworkControl;
import com.hnshituo.icore_map.okhttp.OkHttpUtils;
import com.hnshituo.icore_map.okhttp.callback.BaseCallBack;
import com.hnshituo.icore_map.view.dialog.StyleDialog;
import com.hnshituo.icore_map.view.pickView.PickView;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * 用于BaseFragment中嵌套的Fragment基类
 *
 * @author Wzh
 * @date 2016/8/1  8:30
 */
public abstract class BaseChildFragment extends Fragment implements NetworkControl {

    protected View rootView;
    private Unbinder mBind;

    /**
     * 防止显示成功或者失败页面卡顿
     */
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    rootView.findViewById(R.id.include_fail).setVisibility(View.VISIBLE);
                    rootView.findViewById(R.id.success).setVisibility(View.GONE);
                    rootView.findViewById(R.id.include_loadding).setVisibility(View.GONE);
                    rootView.findViewById(R.id.include_fail).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            reTry();
                        }
                    });
                    break;
                case 2:
                    rootView.findViewById(R.id.success).setVisibility(View.VISIBLE);
                    rootView.findViewById(R.id.include_loadding).setVisibility(View.GONE);
                    rootView.findViewById(R.id.include_fail).setVisibility(View.GONE);
                    break;
                case 3:
                    StyleDialog.fastDismissProgressDialog();
                    break;
                case 4:
                    if (getContext() != null) {
                        StyleDialog.showErrorDialog(getContext(), (String) msg.obj);
                    }
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = initRootView(inflater, container);
        mBind = ButterKnife.bind(this, rootView);
        initData();
        return rootView;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        OkHttpUtils.getInstance().cancelTag(this);
        mBind.unbind();
        handler.removeCallbacksAndMessages(null);
    }


    /**
     * 初始化Framgent的根布局
     *
     * @param inflater
     * @return 跟布局的view对象
     */
    public abstract View initRootView(LayoutInflater inflater, ViewGroup container);

    public abstract void initData();

    /**
     * 显示错误页面
     *
     * @param msg  错误信息
     * @param type
     */
    @Override
    public void showFail(String msg, int type) {
        if (type == BaseCallBack.INNER) {
            handler.sendEmptyMessageDelayed(1, 500);
        } else {
            Message m = Message.obtain();
            m.what = 4;
            m.obj = msg;
            handler.sendMessageDelayed(m, 500);
        }

    }

    /**
     * 显示进度条
     *
     * @param type
     */
    @Override
    public void showLoading(int type) {
        if (type == BaseCallBack.INNER) {
            ImageView imageView = (ImageView) rootView.findViewById(R.id.loading_image);
            Glide.with(this).load(R.drawable.net_loading).asGif().into(imageView);
            rootView.findViewById(R.id.include_loadding).setVisibility(View.VISIBLE);
            rootView.findViewById(R.id.success).setVisibility(View.GONE);
            rootView.findViewById(R.id.include_fail).setVisibility(View.GONE);
        } else if (type == BaseCallBack.DIALOG) {
            StyleDialog.showProgressDialog(getContext());
        }
    }

    /**
     * 显示成功
     *
     * @param type
     */
    @Override
    public void showSuccessView(int type) {
        if (type == BaseCallBack.INNER) {
            handler.sendEmptyMessageDelayed(2, 500);
        } else if (type == BaseCallBack.DIALOG) {
            handler.sendEmptyMessageDelayed(3, 500);
        }

    }

    /**
     * 没有权限
     */
    @Override
    public void noPermission() {
        StyleDialog.showErrorDialog(getActivity(), "", "不具有访问权限,请联系管理员", null);
    }

    /**
     * session超时
     */
    @Override
    public void sessionTimeOut() {
        ((BaseFragment) getParentFragment()).sessionTimeOut();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(this);
        PickView.dismiss();
    }
}
