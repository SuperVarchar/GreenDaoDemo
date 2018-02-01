package com.hnshituo.icore_map.base.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * @author Wzh
 * @date 2016/7/5  8:39
 */
public abstract class MyBaseAdapter<T> extends BaseAdapter {
    protected List<T> list;
    protected Context context;

    protected OnMyListItemClick onMyListItemClick;

    public MyBaseAdapter(Context context, List<T> list) {
        this.list = list;
        this.context = context;

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public T getItem(int position) {
        return list.get(position);
    }

    public T getItem(Object position) {
        int i;
        try {
            i = Integer.parseInt(String.valueOf(position));
        } catch (Exception e) {
            i = 0;
        }
        return list.get(i);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public abstract View getView(int position, View convertView, ViewGroup parent);

    /**
     * 添加数据
     *
     * @param list
     */
    public void addData(List<T> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    /**
     * 展现对话框
     **/
    public void showPhoneDialog(DialogInterface.OnClickListener click, String title, String[] str) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setItems(str, click);
        builder.create().show();
    }

    /**
     * 刷新列表数据
     *
     * @param list
     */
    public void refreshData(List<T> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    /**
     * 获取列表数据
     */
    public List<T> getData() {
        return this.list;
    }

    /**
     * 清除界面数据
     */
    public void clearData() {
        this.list.clear();
        notifyDataSetChanged();
    }

    public interface OnMyListItemClick {
        public void onClick(View v);
    }

    private AlertDialog dialog;

    public void setOnMyListItemClickListener(OnMyListItemClick onMyListItemClick) {
        this.onMyListItemClick = onMyListItemClick;
    }
}
