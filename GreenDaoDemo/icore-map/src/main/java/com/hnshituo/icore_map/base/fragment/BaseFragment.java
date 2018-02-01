package com.hnshituo.icore_map.base.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hnshituo.icore_map.ICoreMapClient;
import com.hnshituo.icore_map.R;
import com.hnshituo.icore_map.base.activity.ICoreBaseActivity;
import com.hnshituo.icore_map.base.bean.OperateBarEvent;
import com.hnshituo.icore_map.fragmention.SupportFragment;
import com.hnshituo.icore_map.okhttp.NetworkControl;
import com.hnshituo.icore_map.okhttp.OkHttpUtils;
import com.hnshituo.icore_map.okhttp.callback.BaseCallBack;
import com.hnshituo.icore_map.util.BitmapBase64;
import com.hnshituo.icore_map.view.dialog.StyleDialog;
import com.hnshituo.icore_map.view.pickView.PickView;
import com.hnshituo.icore_map.view.view.CircleImageView;
import com.hnshituo.icore_map.view.view.ToolTipUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public abstract class BaseFragment extends SupportFragment implements NetworkControl {
    protected ICoreBaseActivity mActivity;
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
                    ImageView imageView = (ImageView) rootView.findViewById(R.id.loading_image);
                    Glide.clear(imageView);
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
                    ImageView image = (ImageView) rootView.findViewById(R.id.loading_image);
                    Glide.clear(image);
                    break;
                case 3:
                    StyleDialog.fastDismissProgressDialog();
                    break;
                case 4:
                    StyleDialog.showErrorDialog(getContext(), (String) msg.obj);
                    break;
            }
        }
    };

    /**
     * 获取Activity
     *
     * @return baseActity对象
     */
    public ICoreBaseActivity getBaseActivity() {
        if (mActivity == null) {
            mActivity = (ICoreBaseActivity) getActivity();
        }
        return mActivity;
    }

    /**
     * 初始化Framgent的根布局
     *
     * @param inflater
     * @return 跟布局的view对象
     */
    public abstract View initRootView(LayoutInflater inflater, ViewGroup container);

    public abstract void initData();


    @Override
    public Context getContext() {
        return getActivity();
    }

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
        ToolTipUtil.dismissTipWindow();
    }

    protected void setLoginImage(View.OnClickListener clickListener, String image){
        CircleImageView view = (CircleImageView) rootView.findViewById(R.id.title_logo);
        view.setVisibility(View.VISIBLE);
        if(TextUtils.isEmpty(image)){
            view.setImageResource(R.drawable.head_portrait);
        }else {
            view.setImageBitmap(BitmapBase64.stringtoBitmap(image));
        }
        view.setOnClickListener(clickListener);
        /*Glide.with(mContext)
                .load(Constant.Url.UPLOAD + path)
                .asBitmap()
                .placeholder(R.drawable.head_portrait)
                .error(R.drawable.head_portrait)
                .into(new BitmapImageViewTarget(view) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(mContext.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        view.setImageDrawable(circularBitmapDrawable);
                    }
                });*/
    }

    /**
     * 设置标题
     */
    protected void setTitleText(int titleId, Integer textColor) {
        setTitleText(getString(titleId), textColor);
    }

    /**
     * 设置标题
     * 如果textcolor传null则显示默认颜色
     *
     * @param title
     * @param textColor
     */
    protected void setTitleText(String title, Integer textColor) {
        try {
            ((TextView) rootView.findViewById(R.id.include_title_name)).setText(title);
            if (textColor != null) {
                ((TextView) rootView.findViewById(R.id.include_title_name)).setTextColor(getResources()
                        .getColor(textColor));
            }
            rootView.findViewById(R.id.include_title_name).setVisibility(View.VISIBLE);

        } catch (Exception e) {
        }
    }

    /**
     * 设置标题下拉标题
     * 如果textcolor传null则显示默认颜色
     *
     * @param title
     * @param textColor
     */
    protected void setTitleTextPull(String title, Integer textColor, final View.OnClickListener listener) {
        try {
            TextView textView = (TextView) rootView.findViewById(R.id.include_title_name_pull);
            textView.setText(title);
            textView.setVisibility(View.VISIBLE);
            if (textColor != null) {
                textView.setTextColor(getResources()
                        .getColor(textColor));
            }

            if (listener != null) {
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onClick(v);
                    }
                });
            }

            rootView.findViewById(R.id.include_title_name).setVisibility(View.GONE);


        } catch (Exception e) {
        }
    }

    /**
     * 设置标题  白底黑字
     */
    protected void setTitleWhite(int titleId) {
        String title = getString(titleId);
        try {
            rootView.findViewById(R.id.main_title_layout).setBackgroundColor(getResources().getColor(R.color.white));
            ((TextView) rootView.findViewById(R.id.include_title_name)).setText(title);
            ((TextView) rootView.findViewById(R.id.include_title_name)).setTextColor(getResources()
                    .getColor(R.color.black_deep));

            rootView.findViewById(R.id.include_title_name).setVisibility(View.VISIBLE);

        } catch (Exception e) {
        }
    }


    /**
     * 默认的白色返回
     */
    protected void setWhiteBackButton() {
        setWhiteBackButton(null);
    }

    /**
     * 默认的白色返回
     */
    protected void setWhiteBackButton(final View.OnClickListener listener) {
        TextView back = (TextView) rootView.findViewById(R.id.back);
        back.setTextColor(getResources().getColor(R.color.color_00C4F6));
        Drawable drawable = this.getResources().getDrawable(R.drawable.blue_back);
        drawable.setBounds(0, 0, (int) this.getResources().getDimension(R.dimen.base10dp), (int) this.getResources().getDimension(R.dimen.base10dp));
        back.setCompoundDrawables(drawable, null, null, null);
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(v);
                }
                onBackPressedSupport();
            }
        });
    }

    /**
     * 默认的返回
     */
    protected void setBackButton() {
        setBackButton(null, "");
    }

    protected void setBackButton(String str) {
        setBackButton(null, str);
    }

    //控制返回按钮的显示
    protected void setBackButton(String str, boolean vis) {
        setBackButton(null, str, vis);
    }

    /**
     * 设置返回按钮事件
     */
    protected void setBackButton(final View.OnClickListener clickListener, String str, boolean vis) {
        TextView back = (TextView) rootView.findViewById(R.id.back);
        back.setVisibility(View.VISIBLE);
        if (!TextUtils.isEmpty(str)) {
            back.setText(str);
        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null) {
                    clickListener.onClick(v);
                }
                onBackPressedSupport();
            }
        });
        if (vis) {
        } else {
            //不显示箭头
            back.setCompoundDrawables(null, null, null, null);
        }
    }


    protected void setBackButton(View.OnClickListener clickListener) {
        setBackButton(clickListener, "");
    }

    /**
     * 设置返回按钮事件
     */
    protected void setBackButton(final View.OnClickListener clickListener, String str) {
        TextView back = (TextView) rootView.findViewById(R.id.back);
        back.setVisibility(View.VISIBLE);
        if (!TextUtils.isEmpty(str)) {
            back.setText(str);
        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null) {
                    clickListener.onClick(v);
                }
                onBackPressedSupport();
            }
        });
    }


    protected void setBackButton(int image) {
        setBackButton(null, image, null);
    }


    /**
     * 设置返回按钮事件
     */
    protected void setBackButton(View.OnClickListener clickListener, int image) {
        setBackButton(clickListener, image, null);
    }

    /**
     * 设置返回按钮事件
     */
    protected void setBackButton(View.OnClickListener clickListener,
                                 Bitmap imageBitmap) {
        setBackButton(clickListener, null, imageBitmap);
    }

    /**
     * 设置返回按钮事件
     */
    protected void setBackButton(final View.OnClickListener clickListener,
                                 Integer image, Bitmap imageBitmap) {
        try {
            final TextView back = (TextView) rootView.findViewById(R.id.back);
            back.setVisibility(View.VISIBLE);
            if (image != null)
                back.setCompoundDrawables(getResources().getDrawable(image), null, null, null);
            if (imageBitmap != null)
                back.setCompoundDrawables(new BitmapDrawable(imageBitmap), null, null, null);
            if (clickListener == null) {
                back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View paramView) {
                        onBackPressedSupport();
                    }
                });
            } else {
                back.setOnClickListener(clickListener);
            }
        } catch (Exception e) {

        }
    }

    /**
     * 设置title右边的按钮事件
     */
    protected void setMenu(View.OnClickListener clickListener, String text,
                           Integer textColor) {
        try {
            TextView menu = (TextView) rootView.findViewById(R.id.include_title_right_text);
            menu.setVisibility(View.VISIBLE);
            menu.setText(text);
            if (textColor != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    menu.setTextColor(getResources().getColor(textColor, null));
                } else {
                    menu.setTextColor(getResources().getColor(textColor));
                }
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    menu.setTextColor(getResources().getColor(R.color.color_4987f4, null));
                } else {
                    menu.setTextColor(getResources().getColor(R.color.color_4987f4));
                }
            }
//            menu.setCompoundDrawables(null, null, null, null);
            if (clickListener != null)
                menu.setOnClickListener(clickListener);
            rootView.findViewById(R.id.include_title_right_iv).setVisibility(View.GONE);
            // findViewById(R.id.image_menu).setOnClickListener(null);
        } catch (Exception e) {
        }
    }

    /**
     * 设置title右边的按钮事件
     */
    protected void setImageMenu(View.OnClickListener clickListener, int image) {

        try {
            ImageButton menu = (ImageButton) rootView.findViewById(R.id.include_title_right_iv);
            menu.setVisibility(View.VISIBLE);

            if (image == 0) {
                menu.setVisibility(View.GONE);
                return;
            } else if (image != -1) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    menu.setImageDrawable(getContext().getResources().getDrawable(image, null));
                } else {
                    menu.setImageDrawable(getContext().getResources().getDrawable(image));
                }
            }

            if (clickListener != null) {
                menu.setOnClickListener(clickListener);
            }

            rootView.findViewById(R.id.include_title_right_text).setVisibility(View.GONE);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置title右边的按钮事件
     */
    protected void setMenu(View.OnClickListener clickListener, int text,
                           Integer textColor) {
        setMenu(clickListener, getString(text), textColor);
    }

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
     * 处理回退事件
     *
     * @return
     */
    @Override
    public boolean onBackPressedSupport() {
        hideSoftInput();
        PickView.dismiss();
        StyleDialog.fastDismissProgressDialog();
        int count = getFragmentManager().getBackStackEntryCount();
        Log.e("demo", count + "");
        if (count > 2) {
            OkHttpUtils.getInstance().cancelTag(this);
            handler.removeCallbacksAndMessages(null);
            pop();
        } else if (count == 2) {
            OkHttpUtils.getInstance().cancelTag(this);
            handler.removeCallbacksAndMessages(null);
            pop();
            EventBus.getDefault().post(new OperateBarEvent(true));
        } else {
            if (ICoreMapClient.getInstans().getFragment() == null) {
                StyleDialog.showExitDialog(getActivity());
            } else {
                ICoreMapClient.getInstans().setFragment(null);
                EventBus.getDefault().post(new OperateBarEvent(true));
                getActivity().finish();
            }

        }

        return true;
    }


    /**
     * 没有权限
     */
    @Override
    public void noPermission() {
        StyleDialog.showErrorDialog(getActivity(), "", "不具有访问权限,请联系管理员", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pop();
            }
        });
    }

    /**
     * session超时
     */
    @Override
    public void sessionTimeOut() {
//        StyleDialog.showErrorDialog(getActivity(), "", "由于您长时间未使用,请重新登录", new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                OkHttpUtils.getInstance().getCookieStore().removeAll();
//                Intent intent = new Intent(ICoreMapClient.application, SplashActivity.class);
//                PendingIntent restartIntent = PendingIntent.getActivity(
//                        App.application, 0, intent,
//                        PendingIntent.FLAG_ONE_SHOT);
////            退出程序
//                AlarmManager mgr = (AlarmManager) App.application.getSystemService(Context.ALARM_SERVICE);
//                mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 1000,
//                        restartIntent); // 1秒钟后重启应用
//                App.application.stopService(new Intent(App.application, EMChatService.class));
//                App.application.finishActivity();
//                App.application.finishProgram();
//            }
//        });
        ((ICoreBaseActivity) getActivity()).sessionTimeOut();
    }

    public void alert(String message) {
        View dropView = rootView.findViewById(R.id.title_ll);
        ToolTipUtil.showToolTipWindow(getActivity(), message, dropView, ToolTipUtil.TYPE.TOP);
    }

    public void showSweetDialog(String title, View.OnClickListener listener) {
        StyleDialog.showWarnDialog(getActivity(), title, listener);
    }
}
