package com.hnshituo.icore_map.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.hnshituo.icore_map.R;
import com.hnshituo.icore_map.base.bean.ComBaseInfo;
import com.hnshituo.icore_map.view.view.AnimationButton;
import com.joanzapata.android.BaseAdapterHelper;
import com.joanzapata.android.QuickAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/9.
 */
public class PickDialogView {

    public static Dialog optionsPickerView;

    public interface OnSelectDataListener {
        void onSelected(ComBaseInfo info);
    }

    public static void showNotCancelPickView(ComBaseInfo currentinfo, Context context, String title, final List<ComBaseInfo> items, final OnSelectDataListener listener) {
        optionsPickerView = getPickView(currentinfo, context, title, "暂无数据", false, items, listener);
        optionsPickerView.show();
    }

    public static void showPickView(ComBaseInfo currentinfo, Context context, String title, final List<ComBaseInfo> items, final OnSelectDataListener listener) {
        optionsPickerView = getPickView(currentinfo, context, title, "暂无数据", true, items, listener);
        optionsPickerView.show();
    }

    public static void showPickView() {
        if (optionsPickerView != null && !optionsPickerView.isShowing()) {
            optionsPickerView.show();
        }
    }

    public static Dialog getPickView(ComBaseInfo currentinfo,
                                     Context context, String title, String emptyString, boolean cancelVisibility, final List<ComBaseInfo> items,
                                     final OnSelectDataListener listener) {

        dismiss();

        final View view = LayoutInflater.from(context).inflate(R.layout.dialog_single, null);
        TextView titleView = (TextView) view.findViewById(R.id.dialog_title);
        titleView.setText(title);


        final List<ComBaseInfo> selectList = new ArrayList<>();
        if(currentinfo != null){
            selectList.add(currentinfo);
        }

        ListView listView = (ListView) view.findViewById(R.id.dialog_lv);
        TextView empty = (TextView) view.findViewById(R.id.empty);

        if (items.size() == 0) {
            listView.setVisibility(View.GONE);
            empty.setVisibility(View.VISIBLE);
            empty.setText(emptyString);
        } else {
            listView.setVisibility(View.VISIBLE);
            empty.setVisibility(View.GONE);
            listView.setAdapter(new QuickAdapter<ComBaseInfo>(context, R.layout.dialog_single_item, items) {
                @Override
                protected void convert(BaseAdapterHelper helper, final ComBaseInfo item) {

                    final LinearLayout layout = helper.getView(R.id.dialog_item_ll);

                    final ImageView imageView = helper.getView(R.id.dialog_item_iv);

                    final TextView textView = helper.getView(R.id.dialog_item_tv);
                    textView.setText(item.basename);
                    layout.setSelected(false);
                    imageView.setSelected(false);
                    textView.setSelected(false);
                    for (int i = 0; i < selectList.size(); i++) {
                        ComBaseInfo baseDataInfo = selectList.get(i);
                        if (item.basecode.equals(baseDataInfo.basecode)) {
                            layout.setSelected(true);
                            imageView.setSelected(true);
                            textView.setSelected(true);
                        }
                    }
                    layout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            selectList.clear();
                            if (layout.isSelected()) {
                                layout.setSelected(false);
                            }else {
                                selectList.add(item);
                            }
                            notifyDataSetChanged();
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
                        if (selectList.size() > 0) {
                            listener.onSelected(selectList.get(0));
                        } else {
                            listener.onSelected(new ComBaseInfo());
                        }
                    }
                }
            });
        }

        AnimationButton cancel = (AnimationButton) view.findViewById(R.id.dialog_cancel);
        cancel.setVisibility(cancelVisibility ? View.VISIBLE : View.GONE);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        optionsPickerView = new Dialog(context, R.style.CommDialogStyle);

        optionsPickerView.setContentView(view);
        optionsPickerView.getWindow().setGravity(Gravity.CENTER);
        optionsPickerView.setCanceledOnTouchOutside(false);
        optionsPickerView.setCancelable(false);

        return optionsPickerView;
    }

    public static void dismiss() {
        if (optionsPickerView != null) {
            optionsPickerView.dismiss();
        }
    }
}
