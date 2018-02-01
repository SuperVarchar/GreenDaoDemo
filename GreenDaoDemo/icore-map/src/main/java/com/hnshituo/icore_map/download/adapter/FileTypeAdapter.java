package com.hnshituo.icore_map.download.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.hnshituo.icore_map.R;
import com.hnshituo.icore_map.view.view.AutoAlignTextView;

import java.util.List;



/**
 * 主界面 文件类型的适配器
 */
public class FileTypeAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> mName;
    private List<Integer> mIcon;


    public FileTypeAdapter(Context context, List<String> name, List<Integer> icon) {
        mContext = context;
        mName = name;
        mIcon = icon;
    }

    @Override
    public int getCount() {
        return mName.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_file_type, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (getCount() == 1 || position == getCount() - 1) {
            viewHolder.allBottomLine.setVisibility(View.VISIBLE);
            viewHolder.notAllBottomLine.setVisibility(View.GONE);
        } else {
            viewHolder.allBottomLine.setVisibility(View.GONE);
            viewHolder.notAllBottomLine.setVisibility(View.VISIBLE);
        }

        viewHolder.itemFiletypeImage.setImageResource(mIcon.get(position));
        viewHolder.itemFiletypeViewTxt.setText(mName.get(position));
        return convertView;
    }

    class ViewHolder {
        View view;
        ImageView itemFiletypeImage;
        AutoAlignTextView itemFiletypeViewTxt;
        View allBottomLine;
        View notAllBottomLine;

        ViewHolder(View view) {
            this.view = view;
            this.itemFiletypeImage = (ImageView) view.findViewById(R.id.item_filetype_image);
            this.itemFiletypeViewTxt = (AutoAlignTextView) view.findViewById(R.id.item_filetype_view_txt);
            this.allBottomLine = (View) view.findViewById(R.id.all_bottom_line);
            this.notAllBottomLine = (View) view.findViewById(R.id.not_all_bottom_line);
        }
    }
}
