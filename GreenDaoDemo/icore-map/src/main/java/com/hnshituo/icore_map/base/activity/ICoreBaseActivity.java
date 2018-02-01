package com.hnshituo.icore_map.base.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hnshituo.icore_map.ICoreMapClient;
import com.hnshituo.icore_map.R;
import com.hnshituo.icore_map.fragmention.SupportActivity;
import com.hnshituo.icore_map.okhttp.NetworkControl;
import com.hnshituo.icore_map.okhttp.OkHttpUtils;
import com.hnshituo.icore_map.okhttp.callback.BaseCallBack;
import com.hnshituo.icore_map.view.dialog.StyleDialog;
import com.hnshituo.icore_map.view.view.ToolTipUtil;



public abstract class ICoreBaseActivity extends SupportActivity implements NetworkControl {
    protected SharedPreferences SP;
    //SharedPreferences
    protected ImageButton mMore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //界面中如果有EditText，默认隐藏输入法
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        //设置竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ICoreMapClient.getInstans().addActivity(this);
    }

    /**
     * 没有权限
     */
    @Override
    public void noPermission() {
        StyleDialog.showErrorDialog(this, "", "不具有访问权限,请联系管理员", new View.OnClickListener() {
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
        StyleDialog.showErrorDialog(this, "", "由于您长时间未使用,请重新登陆", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionTimeOutDeal();
            }
        });
    }

    protected void sessionTimeOutDeal(){
        OkHttpUtils.getInstance().getCookieStore().removeAll();
        Intent intent = getPackageManager().getLaunchIntentForPackage(getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    /**
     * 显示错误页面
     *
     * @param msg  错误信息
     * @param type
     */
    @Override
    public void showFail(String msg, int type) {
        StyleDialog.fastDismissProgressDialog();
        if (type == BaseCallBack.INNER) {
            findViewById(R.id.include_fail).setVisibility(View.VISIBLE);
            findViewById(R.id.include_fail).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    reTry();
                }
            });
            findViewById(R.id.success).setVisibility(View.GONE);
            findViewById(R.id.include_loadding).setVisibility(View.GONE);
            Glide.clear(findViewById(R.id.loading_image));
        } else {
            StyleDialog.showErrorDialog(this, msg);
        }
    }

    /**
     * 显示进度页面
     *
     * @param type
     */
    @Override
    public void showLoading(int type) {
        if (type == BaseCallBack.INNER) {
            ImageView imageView = (ImageView) findViewById(R.id.loading_image);
            Glide.with(this).load(R.drawable.net_loading).asGif().into(imageView);
            findViewById(R.id.include_loadding).setVisibility(View.VISIBLE);
            findViewById(R.id.success).setVisibility(View.GONE);
            findViewById(R.id.include_fail).setVisibility(View.GONE);
        } else if (type == BaseCallBack.DIALOG) {
            StyleDialog.showProgressDialog(this);
        }
    }

    /**
     * 显示成功的View
     *
     * @param type
     */
    @Override
    public void showSuccessView(int type) {
        if (type == BaseCallBack.INNER) {
            findViewById(R.id.success).setVisibility(View.VISIBLE);
            findViewById(R.id.include_loadding).setVisibility(View.GONE);
            findViewById(R.id.include_fail).setVisibility(View.GONE);
            Glide.clear(findViewById(R.id.loading_image));
        } else if (type == BaseCallBack.DIALOG) {
            StyleDialog.fastDismissProgressDialog();
        }

    }

    /**
     * 设置标题
     */
    protected void setTitleText(int titleId, Integer textColor) {
        setTitleText(getString(titleId), textColor);
    }

    protected void setTitleText(String title, Integer textColor) {
        try {
            ((TextView) findViewById(R.id.include_title_name)).setText(title);
            if (textColor != null) {
                ((TextView) findViewById(R.id.include_title_name)).setTextColor(getResources()
                        .getColor(textColor));
            }
            findViewById(R.id.include_title_name).setVisibility(View.VISIBLE);

        } catch (Exception e) {
        }
    }


    /**
     * 设置默认的回退按钮
     */
    protected void setBackButton() {
        setBackButton(null);
    }

    /**
     * 默认的白色返回
     */
    protected void setWhiteBackButton() {
        TextView back = (TextView) findViewById(R.id.back);
        back.setTextColor(getResources().getColor(R.color.color_00C4F6));
        Drawable drawable = this.getResources().getDrawable(R.drawable.blue_back);
        drawable.setBounds(0, 0, (int) this.getResources().getDimension(R.dimen.base10dp), (int) this.getResources().getDimension(R.dimen.base10dp));
        back.setCompoundDrawables(drawable, null, null, null);
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressedSupport();
            }
        });
    }

    /**
     * 设置返回按钮事件
     */
    protected void setBackButton(final View.OnClickListener clickListener) {
        TextView back = (TextView) findViewById(R.id.back);
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressedSupport();
                if (clickListener != null) {
                    clickListener.onClick(v);
                }
            }
        });
    }

    protected void setBackButton(int image) {
        setBackButton(null, image, null);
    }


    /**
     * 设置返回按钮事件
     */
    protected void setBackButton(View.OnClickListener clickListener, Integer image) {
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
            final TextView back = (TextView) findViewById(R.id.back);
            back.setVisibility(View.VISIBLE);
            if (image != null)
                back.setCompoundDrawables(getResources().getDrawable(image), null, null, null);
            if (imageBitmap != null)
                back.setCompoundDrawables(new BitmapDrawable(imageBitmap), null, null, null);
            if (clickListener == null) {
                int m = (int) getResources().getDimension(R.dimen.base15dp);
                back.setPadding(0, m, m, m);
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
    protected void setMenu(View.OnClickListener clickListener, int text,
                           Integer textColor) {
        setMenu(clickListener, getString(text), textColor);

    }

    /**
     * 设置title右边的按钮事件
     */
    protected void setMenu(View.OnClickListener clickListener, String text,
                           Integer textColor) {
        try {
            TextView menu = (TextView) findViewById(R.id.include_title_right_text);
            menu.setVisibility(View.VISIBLE);
            menu.setText(text);
            if (textColor != null) {
                menu.setTextColor(getResources().getColor(textColor));
            }
            menu.setCompoundDrawables(null, null, null, null);
            if (clickListener != null)
                menu.setOnClickListener(clickListener);
            findViewById(R.id.include_title_right_iv).setVisibility(View.GONE);
            // findViewById(R.id.image_menu).setOnClickListener(null);
        } catch (Exception e) {
        }
    }

    /**
     * 设置title右边的按钮事件
     * 默认为-1
     */
    protected void setImageMenu(View.OnClickListener clickListener, int image) {
        try {
            mMore = (ImageButton) findViewById(R.id.include_title_right_iv);
            mMore.setVisibility(View.VISIBLE);
            if (image != -1) {
                mMore.setImageResource(image);
            }
            if (clickListener != null)
                mMore.setOnClickListener(clickListener);
            findViewById(R.id.include_title_right_text).setVisibility(View.GONE);
            // findViewById(R.id.but_menu).setOnClickListener(null);
        } catch (Exception e) {
        }
    }


    @Override
    protected void onDestroy() {
        OkHttpUtils.getInstance().cancelTag(this);
        ToolTipUtil.dismissTipWindow();
        super.onDestroy();
    }

    public void alert(String message) {
        View dropView = findViewById(R.id.title_ll);
        ToolTipUtil.showToolTipWindow(this, message, dropView, ToolTipUtil.TYPE.TOP);
    }
}
