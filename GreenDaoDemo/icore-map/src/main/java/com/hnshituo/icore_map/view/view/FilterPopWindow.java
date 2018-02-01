package com.hnshituo.icore_map.view.view;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.hnshituo.icore_map.R;
import com.hnshituo.icore_map.base.adapter.CustomBaseAdapter;
import com.hnshituo.icore_map.base.bean.ComBaseInfo;
import com.hnshituo.icore_map.view.forScrollview.GridViewForScrollView;

import java.util.ArrayList;
import java.util.List;



/**
 * 筛选的PopWindow
 *
 * @author Wzh
 * @date 2016/8/24  20:22
 */
public class FilterPopWindow {
    private PopupWindow FilterPopWindow;
    private View contentView;
    private List<Integer> selected;
    private List<ComBaseInfo> mBaseDataInfos;

    /**
     * -----------------------------------------------------------------------------------------------------------
     *
     * @param context
     * @param title
     * @param items
     * @param listener
     */
    public FilterPopWindow(Context context, List<String> title, final List<ArrayList<String>> items, final OnSelectDataListener listener) {
        FilterPopWindow = new PopupWindow(context);
        selected = new ArrayList<>();
        for (int i = 0; i < title.size(); i++) {
            selected.add(0);
        }
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contentView = inflater.inflate(R.layout.popwindow_filter, null);
        ListView filterlv = (ListView) contentView.findViewById(R.id.filter_lv);
        contentView.findViewById(R.id.filter_ll).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FilterPopWindow.dismiss();
            }
        });
        contentView.findViewById(R.id.filter_finish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FilterPopWindow.dismiss();
                if (listener != null) {
                    listener.onSelected(selected);
                }
            }
        });

        FilterPopWindowListAdapter adapter = new FilterPopWindowListAdapter(context, items);
        adapter.setData(title);
        filterlv.setAdapter(adapter);
    }


    public void showFilterPopWindow(View anchor) {
        if (FilterPopWindow != null && FilterPopWindow.isShowing()) {
            FilterPopWindow.dismiss();
        }

        if (FilterPopWindow != null) {
            FilterPopWindow.setHeight(ActionBar.LayoutParams.MATCH_PARENT);
            FilterPopWindow.setWidth(ActionBar.LayoutParams.MATCH_PARENT);
            FilterPopWindow.setOutsideTouchable(false);
            FilterPopWindow.setTouchable(true);
            FilterPopWindow.setFocusable(true);
            FilterPopWindow.setBackgroundDrawable(new BitmapDrawable());
            FilterPopWindow.setContentView(contentView);

            int screen_pos[] = new int[2];
            //Get Location of anchor view on screen
            anchor.getLocationOnScreen(screen_pos);

            //Call view measure to calculate how big your view should be.
            contentView.measure(ActionBar.LayoutParams.WRAP_CONTENT,
                    ActionBar.LayoutParams.WRAP_CONTENT);

            FilterPopWindow.showAsDropDown(anchor);
        }
    }

    public interface OnSelectDataListener {
        void onSelected(List<Integer> infos);
    }


    /**
     * 字符串类型的适配器
     */
    class FilterPopWindowListAdapter extends CustomBaseAdapter<String> {

        private List<ArrayList<String>> itmes;

        public FilterPopWindowListAdapter(Context context, List<ArrayList<String>> items) {
            super(context);
            this.itmes = items;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.item_popwindow_filter, parent, false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.itemFilterTv.setText(getItemData(position));

            final TabFilterPopWindowAdapter tab = new TabFilterPopWindowAdapter(mContext, position);
            tab.setData(itmes.get(position));
            holder.itemFilterGv.setAdapter(tab);
            holder.itemFilterGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int p, long id) {
                    if (selected.get(position) != p) {
                        selected.remove(position);
                        selected.add(position, p);
                        tab.notifyDataSetChanged();
                    }
                }
            });

            return convertView;
        }
    }

    class TabFilterPopWindowAdapter extends CustomBaseAdapter<String> {

        private int postionY;

        public TabFilterPopWindowAdapter(Context context, int postion) {
            super(context);
            this.postionY = postion;

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TabViewHolder holder;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.item_popwindow_filter_tab, parent, false);
                holder = new TabViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (TabViewHolder) convertView.getTag();
            }

            holder.filterTab.setSelected(false);
            if (position == selected.get(postionY)) {
                holder.filterTab.setSelected(true);
            }

            holder.filterTab.setText(getItemData(position));

            return convertView;
        }
    }

    /**
     * ---------------------------------------------------------------------------------------------------------------------------
     */

    public FilterPopWindow(Context context, List<String> title, final List<List<ComBaseInfo>> items, final onSelectBaseDateListener listener) {
        FilterPopWindow = new PopupWindow(context);

        selected = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            selected.add(0);
        }
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contentView = inflater.inflate(R.layout.popwindow_filter, null);
        ListView filterlv = (ListView) contentView.findViewById(R.id.filter_lv);
        contentView.findViewById(R.id.filter_finish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FilterPopWindow.dismiss();
                if (listener != null) {
                    mBaseDataInfos = new ArrayList<>();
                    for (int i = 0; i < selected.size(); i++) {
                        mBaseDataInfos.add(items.get(i).get(selected.get(i)));
                    }
                    listener.onselected(mBaseDataInfos);
                }
            }
        });

        FilterPopWindowBaseDateListAdapter adapter = new FilterPopWindowBaseDateListAdapter(context, items);
        adapter.setData(title);
        filterlv.setAdapter(adapter);
    }

    public interface onSelectBaseDateListener {
        void onselected(List<ComBaseInfo> infos);
    }

    /**
     * 基础数据类型的数据
     */
    class FilterPopWindowBaseDateListAdapter extends CustomBaseAdapter<String> {

        private List<List<ComBaseInfo>> itmes;

        public FilterPopWindowBaseDateListAdapter(Context context, List<List<ComBaseInfo>> items) {
            super(context);
            this.itmes = items;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.item_popwindow_filter, parent, false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.itemFilterTv.setText(getItemData(position));

            final TabFilterPopWindowBaseDateAdapter tab = new TabFilterPopWindowBaseDateAdapter(mContext, position);
            tab.setData(itmes.get(position));
            holder.itemFilterGv.setAdapter(tab);
            holder.itemFilterGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int p, long id) {
                    if (selected.get(position) != p) {
                        selected.remove(position);
                        selected.add(position, p);
                        tab.notifyDataSetChanged();
                    }
                }
            });

            return convertView;
        }
    }

    class TabFilterPopWindowBaseDateAdapter extends CustomBaseAdapter<ComBaseInfo> {

        private int postionY;

        public TabFilterPopWindowBaseDateAdapter(Context context, int postion) {
            super(context);
            this.postionY = postion;

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TabViewHolder holder;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.item_popwindow_filter_tab, parent, false);
                holder = new TabViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (TabViewHolder) convertView.getTag();
            }

            holder.filterTab.setSelected(false);
            if (position == selected.get(postionY)) {
                holder.filterTab.setSelected(true);
            }

            holder.filterTab.setText(getItemData(position).basename);

            return convertView;
        }
    }

    class ViewHolder {
        TextView itemFilterTv;
        GridViewForScrollView itemFilterGv;

        ViewHolder(View view) {
            itemFilterTv = (TextView) view.findViewById(R.id.item_filter_tv);
            itemFilterGv = (GridViewForScrollView) view.findViewById(R.id.item_filter_gv);
        }
    }


    class TabViewHolder {
        TextView filterTab;

        TabViewHolder(View view) {
            filterTab = (TextView) view.findViewById(R.id.filter_tab);
        }
    }
}
