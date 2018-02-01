package com.hnshituo.icore_map.view.dialog;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.hnshituo.icore_map.R;
import com.hnshituo.icore_map.base.activity.BaseUploadImageActivity;
import com.hnshituo.icore_map.util.SizeUtils;
import com.hnshituo.icore_map.view.forScrollview.GridViewForScrollView;
import com.hnshituo.icore_map.view.pickView.bean.BaseDataInfo;
import com.hnshituo.icore_map.view.progress.MaterialProgressBar;
import com.hnshituo.icore_map.view.view.AnimationButton;
import com.joanzapata.android.BaseAdapterHelper;
import com.joanzapata.android.QuickAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Wzh
 * @date 2016/7/1  14:03
 */
public class StyleDialog {

    //ProgressDialog
    public static Dialog dialog;

    /**
     * 弹出Iphone样式的底部确认对话框
     *
     * @param context
     * @param title
     * @param confirmListener
     * @param cancelListener
     */
    public static void showIphoneConfirgDialog(Context context, String title, String confirmData,
                                               final View.OnClickListener confirmListener,
                                               String cancelData, final View.OnClickListener cancelListener) {
        fastDismissProgressDialog();

        View view = View.inflate(context, R.layout.dialog_confirm_iphone, null);
        dialog = new Dialog(context, R.style.BottomAnimCommDialogStyle);
        dialog.setContentView(view);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setLayout(android.app.ActionBar.LayoutParams.MATCH_PARENT,
                android.app.ActionBar.LayoutParams.WRAP_CONTENT);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        AnimationButton confirmABT = (AnimationButton) view.findViewById(R.id.abt_confirm);
        AnimationButton cancelABT = (AnimationButton) view.findViewById(R.id.abt_cancel);
        tv_title.setText(title);
        confirmABT.setText(confirmData);
        cancelABT.setText(cancelData);
        confirmABT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (confirmListener != null) {
                    confirmListener.onClick(v);
                }
            }
        });
        cancelABT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (cancelListener != null) {
                    cancelListener.onClick(v);
                }
            }
        });
        dialog.show();
    }


    /**
     * 弹出Iphone样式的底部Dialog
     */
    public static void showIphoneDialog(Context context, final List<String> list,
                                        String cancel, final View.OnClickListener cancelListener,
                                        final ListView.OnItemClickListener listener) {
        fastDismissProgressDialog();

        View view = View.inflate(context, R.layout.dialog_iphone, null);
        dialog = new Dialog(context, R.style.CommDialogStyle);
        dialog.setContentView(view);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);

        AnimationButton cancelView = (AnimationButton) view.findViewById(R.id.iphone_cancel);
        if (TextUtils.isEmpty(cancel)) {
            cancelView.setVisibility(View.GONE);
        } else {
            cancelView.setVisibility(View.VISIBLE);
            cancelView.setText(cancel);
            cancelView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    if (cancelListener != null) {
                        cancelListener.onClick(v);
                    }
                }
            });
        }

        ListView listview = (ListView) view.findViewById(R.id.iphone_lv);
        QuickAdapter<String> adapter = new QuickAdapter<String>(context, R.layout.item_dialog_iphone, list) {
            @Override
            protected void convert(BaseAdapterHelper helper, String item) {
                helper.setText(R.id.item_dialog_tv, item);
            }
        };
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dialog.dismiss();
                listener.onItemClick(parent, view, position, id);
            }
        });
        dialog.show();
    }



    /**
     * 弹出Iphone样式的底部  一排四个
     */
    public static void showHorizontalIphoneDialog(Context context, final List<String> list,
                                        String cancel, final View.OnClickListener cancelListener,
                                        final ListView.OnItemClickListener listener) {
        fastDismissProgressDialog();

        View view = View.inflate(context, R.layout.dialog_horizontal, null);
        dialog = new Dialog(context, R.style.CommDialogStyle);
        dialog.setContentView(view);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);

        AnimationButton cancelView = (AnimationButton) view.findViewById(R.id.iphone_cancel);
        if (TextUtils.isEmpty(cancel)) {
            cancelView.setVisibility(View.GONE);
        } else {
            cancelView.setVisibility(View.VISIBLE);
            cancelView.setText(cancel);
            cancelView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    if (cancelListener != null) {
                        cancelListener.onClick(v);
                    }
                }
            });
        }

        GridViewForScrollView listview = (GridViewForScrollView) view.findViewById(R.id.iphone_gv);
        QuickAdapter<String> adapter = new QuickAdapter<String>(context, R.layout.item_dialog_grid_iphone, list) {
            @Override
            protected void convert(BaseAdapterHelper helper, String item) {
                helper.setText(R.id.item_dialog_tv, item);
                helper.setImageResource(R.id.item_dialog_iv,R.drawable.service);
            }
        };
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dialog.dismiss();
                listener.onItemClick(parent, view, position, id);
            }
        });
        dialog.show();
    }

    /**
     * 弹出一个警告信息
     *
     * @param msg
     */
    public static void showWarnDialog(Context context, String msg, View.OnClickListener listener) {
        getDoubleButtonDialog(context, msg, listener, null);
    }

    /**
     * 弹出一个错误Dialog
     *
     * @param context
     * @param msg     错误信息
     */
    public static void showErrorDialog(Context context, String msg) {
        showErrorDialog(context, "", msg, null);
    }

    /**
     * 弹出一个错误Dialog
     *
     * @param context
     * @param msg     错误信息
     */
    public static void showErrorDialog(Context context, String title, String msg, final View.OnClickListener listener) {

        fastDismissProgressDialog();
        if (context != null) {
            View view = LayoutInflater.from(context).inflate(R.layout.dialog_error_single, null);
            TextView content_tv = (TextView) view.findViewById(R.id.content);
            content_tv.setText(msg);
            if (!(TextUtils.isEmpty(title))) {
                TextView title_tv = (TextView) view.findViewById(R.id.dialog_title);
                title_tv.setText(title);
            }

            view.findViewById(R.id.dialog_confirm).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    if (listener != null) {
                        listener.onClick(v);
                    }

                }
            });

            dialog = new Dialog(context, R.style.CommDialogStyle);

            dialog.setContentView(view);
            dialog.getWindow().setGravity(Gravity.CENTER);
            dialog.getWindow().setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            dialog.show();
        }

    }


    /**
     * 弹出一个成功Dialog
     *
     * @param context
     * @param msg
     */
    public static void showSuccessDialog(Context context, String msg, final View.OnClickListener listener) {

        fastDismissProgressDialog();

        View view = LayoutInflater.from(context).inflate(R.layout.dialog_sucess_single, null);
        if (!(TextUtils.isEmpty(msg))) {
            TextView title_tv = (TextView) view.findViewById(R.id.dialog_title);
            title_tv.setText(msg);
        }

        view.findViewById(R.id.dialog_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (listener != null) {
                    listener.onClick(v);
                }

            }
        });

        dialog = new Dialog(context, R.style.CommDialogStyle);

        dialog.setContentView(view);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.getWindow().setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();
    }


    /**
     * 是否需要下载的对话框
     */
    public static void showDownLoadDialog(Context context, View.OnClickListener listener) {
        String content = context.getString(R.string.association11);
        getDoubleButtonDialog(context, content, listener, null);
    }

    /**
     * 显示帐号在别处登录dialog
     */
    public static void showConflictDialog(Context context, View.OnClickListener listener) {
        getSingleButtonDialog(context, context.getString(R.string.Logoff_notification), context.getString(R.string.connect_conflict), listener);
    }

    public static void showAccountRemovedDialog(Context context, View.OnClickListener listener) {
        getSingleButtonDialog(context, context.getString(R.string.Remove_the_notification), context.getString(R.string.em_user_remove), listener);
    }

    /**
     * 退出程序对话框
     */
    public static void showExitDialog(final Activity activity) {
        getDoubleButtonDialog(activity, activity.getString(R.string.exit01), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        }, null);
    }

    /**
     * 弹出删除对话框
     */
    public static void showDeleteDialog(Context context, final View.OnClickListener listener) {
        getDoubleButtonDialog(context, context.getString(R.string.delete02), listener, null);
    }

    /**
     * 弹出为登陆时深色背景所做的加载对话框
     */
    public static void showDarkProgressDialog(Context context, String message) {
        fastDismissProgressDialog();

        View view = View.inflate(context, R.layout.layout_center_progress_dialog_dark, null);
        MaterialProgressBar progressBar = (MaterialProgressBar) view.findViewById(R.id.progress);
        progressBar.setBackgroundColor(0XFF12BBF5);
        TextView textView = (TextView) view.findViewById(R.id.message);
        textView.setText(message);

        dialog = new Dialog(context, R.style.CenterDialog);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);

        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.width = SizeUtils.getScreenWidth(context);
        dialog.getWindow().setAttributes(params);

        dialog.show();
    }


    /**
     * 弹出一个加载进度对话框
     */
    public static void showProgressDialog(Context context) {
        fastDismissProgressDialog();

        if (context == null) {
            return;
        }

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_progress_loading, null);
        view.findViewById(R.id.temp_layout).getBackground().setAlpha(166);
        TextView loadHintText = (TextView) view.findViewById(R.id.temp_tipTextView);
        loadHintText.setText(context.getString(R.string.progress));

        dialog = new Dialog(context, R.style.CommDialogStyle);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();
    }

//    /**
//     * 选择拍照还是照片的对话框
//     *
//     * @param activity    MainActivity
//     * @param captureCode 拍照的RequestCode
//     * @param pickCode    选择照片的RequestCode
//     */
//    public static void showBitmapUploadDialog(final BaseUploadImageActivity activity, final int captureCode, final int pickCode) {
//        List<String> list = new ArrayList<>();
//        list.add("拍照");
//        list.add("图库");
//        showIphoneDialog(activity, list, "取消", null, new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                switch (position) {
//                    case 0:
//                        activity.openCamera(captureCode);
//                        break;
//                    case 1:
//                        activity.openPic(pickCode);
//                        break;
//                }
//            }
//        });
//    }

    /**
     * 有延迟的,隐藏对话框
     */
    public static void dismissProgressDialog() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                    dialog = null;
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }
        }, 500);
    }

    /**
     * 瞬间结束,隐藏对话框
     */
    public static void fastDismissProgressDialog() {
        try {
            if (dialog != null) {
                dialog.dismiss();
            }
            dialog = null;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * 显示dialog
     *
     * @param click 点击监听器
     * @param title title
     */
    public static void showSingleChoiceDialog(Context context, String title, List<String> list, DialogInterface.OnClickListener click) {
        String[] items = list.toArray(new String[list.size()]);
        showSingleChoiceDialog(context, title, items, click);

    }

    /**
     * 显示dialog
     *
     * @param click 点击监听器
     * @param title title
     */
    public static void showSingleChoiceDialog(Context context, String title, String[] items, DialogInterface.OnClickListener click) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setItems(items, click);
        AlertDialog dialog = builder.create();
        dialog.show();
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int screenWidth = dm.widthPixels; // 屏幕宽（像素，如：480px）
        int screenHeight = dm.heightPixels; // 屏幕高（像素，如：800px）
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.height = (int) (screenHeight * 0.7);
        lp.width = (int) (screenWidth * 0.8);
        lp.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(lp);
    }

    /**
     * 隐藏对话框
     */
    public static void dismiss() {
        if (dialog != null) {
            dialog.dismiss();
        }
        dialog = null;
    }



    public static void showCommitConfirmDialog(Context context, String content, View.OnClickListener listener) {
        getSingleButtonDialog(context, "", content, listener);
    }

    /**
     * 更新App的Dialog
     *
     * @param context
     */
    public static void showUpdateDialog(Context context, String versionName, String content, final View.OnClickListener cancel, final View.OnClickListener listener) {
        fastDismissProgressDialog();

        if (context == null) {
            return;
        }


        View view = LayoutInflater.from(context).inflate(R.layout.dialog_update, null);
        TextView content_tv = (TextView) view.findViewById(R.id.update_content);
        content_tv.setText(content);
        TextView name_tv = (TextView) view.findViewById(R.id.version_name);
        AnimationButton cancelIv = (AnimationButton) view.findViewById(R.id.dialog_bt_cancel);
        cancelIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (cancel != null) {
                    cancel.onClick(v);
                }
            }
        });
        name_tv.setText(versionName);
        view.findViewById(R.id.dialog_bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (listener != null) {
                    listener.onClick(v);
                }
            }
        });

        dialog = new Dialog(context, R.style.CommDialogStyle);
        dialog.setContentView(view);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.getWindow().setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();
    }


    /**
     * 基本单按钮对话框基类
     */
    private static void getSingleButtonDialog(Context context, String title, String content, final View.OnClickListener listener) {
        fastDismissProgressDialog();

        View view = LayoutInflater.from(context).inflate(R.layout.dialog_warm_single, null);
        TextView content_tv = (TextView) view.findViewById(R.id.content);
        content_tv.setText(content);
        if (!(TextUtils.isEmpty(title))) {
            TextView title_tv = (TextView) view.findViewById(R.id.dialog_title);
            title_tv.setText(title);
        }

        view.findViewById(R.id.dialog_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (listener != null) {
                    listener.onClick(v);
                }

            }
        });

        dialog = new Dialog(context, R.style.CommDialogStyle);

        dialog.setContentView(view);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.getWindow().setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();
    }

    /**
     * 基本双按钮对话框基类
     */
    public static void getDoubleButtonDialog(Context context, String content, final View.OnClickListener confirmListener, final View.OnClickListener cancelListener) {
        fastDismissProgressDialog();

        View view = LayoutInflater.from(context).inflate(R.layout.dialog_normal_double, null);
        TextView content_tv = (TextView) view.findViewById(R.id.content);
        content_tv.setText(content);
        view.findViewById(R.id.dialog_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (confirmListener != null) {
                    confirmListener.onClick(v);
                }
            }
        });

        view.findViewById(R.id.dialog_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (cancelListener != null) {
                    cancelListener.onClick(v);
                }
            }
        });

        dialog = new Dialog(context, R.style.CommDialogStyle);

        dialog.setContentView(view);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.getWindow().setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();
    }

    /**
     * 选择拍照还是照片的对话框
     *
     * @param activity    MainActivity
     * @param captureCode 拍照的RequestCode
     * @param pickCode    选择照片的RequestCode
     */
    public static void showBitmapUploadDialog(final BaseUploadImageActivity activity, final int captureCode, final int pickCode) {
        List<String> list = new ArrayList<>();
        list.add("拍照");
        list.add("图库");
        showIphoneDialog(activity, list, "取消", null, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        activity.openCamera(captureCode);
                        break;
                    case 1:
                        activity.openPic(pickCode);
                        break;
                }
            }
        });
    }

    /**
     * 编辑页退出的操作
     *
     * @param context
     * @param listener
     */
    public static void showEditDialog(Context context, View.OnClickListener listener) {
        getDoubleButtonDialog(context, "信息未保存，是否确认退出编辑？", listener, null);
    }




    /**
     * 多选框的回调
     */
    public interface MultipleChoiceListener {
        void choiceData(List<BaseDataInfo> selectedData);
    }
    /**
     * 多选框
     *
     * @param context
     * @param title
     * @param list
     * @param selectedList
     * @param listener
     */
    public static void showMultipleChoiceDialog(Context context, String title, List<BaseDataInfo> list, final List<BaseDataInfo> selectedList, final MultipleChoiceListener listener) {
        dismiss();

        if (context == null) {
            return;
        }
        final List<BaseDataInfo> selectList = new ArrayList<>();
        selectList.addAll(selectedList);

        View view = LayoutInflater.from(context).inflate(R.layout.dialog_multiple, null);
        TextView titleView = (TextView) view.findViewById(R.id.dialog_title);
        titleView.setText(title);
        ListView listView = (ListView) view.findViewById(R.id.dialog_lv);
        listView.setAdapter(new QuickAdapter<BaseDataInfo>(context, R.layout.dialog_multiple_item, list) {
            @Override
            protected void convert(BaseAdapterHelper helper, final BaseDataInfo item) {

                final LinearLayout layout = helper.getView(R.id.dialog_item_ll);

                final ImageView imageView = helper.getView(R.id.dialog_item_iv);

                final TextView textView = helper.getView(R.id.dialog_item_tv);
                textView.setText(item.text);
                layout.setSelected(false);
                imageView.setSelected(false);
                textView.setSelected(false);
                for (int i = 0; i < selectList.size(); i++) {
                    BaseDataInfo baseDataInfo = selectList.get(i);
                    if (item.id.equals(baseDataInfo.id)) {
                        layout.setSelected(true);
                        imageView.setSelected(true);
                        textView.setSelected(true);
//                            selectList.add(item);
                    }
                }


                layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        layout.setSelected(!layout.isSelected());
                        imageView.setSelected(layout.isSelected());
                        textView.setSelected(layout.isSelected());

                        if (layout.isSelected()) {
                            selectList.add(item);
                        } else {
                            for (int i = 0; i < selectList.size(); i++) {
                                BaseDataInfo baseDataInfo = selectList.get(i);
                                if (item.id.equals(baseDataInfo.id)) {
                                    selectList.remove(baseDataInfo);
                                }
                            }

                        }
                    }
                });
            }
        });

        AnimationButton confirm = (AnimationButton) view.findViewById(R.id.dialog_confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (listener != null) {
                    listener.choiceData(selectList);
                }
            }
        });

        AnimationButton cancel = (AnimationButton) view.findViewById(R.id.dialog_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        dialog = new Dialog(context, R.style.CommDialogStyle);

        dialog.setContentView(view);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();
    }



}
