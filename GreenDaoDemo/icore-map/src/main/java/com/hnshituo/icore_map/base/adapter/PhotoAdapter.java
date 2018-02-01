package com.hnshituo.icore_map.base.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hnshituo.icore_map.R;
import com.hnshituo.icore_map.base.Constant;
import com.hnshituo.icore_map.base.upload.UploadInfo;


/**
 * Created by liuba
 * time:2017/2/28 20:26
 * describe --
 */

public class PhotoAdapter extends CustomBaseAdapter<UploadInfo> {

    public PhotoAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.map_item_association_pic, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Glide.with(mContext)
                .load(Constant.UPLOAD + getItemData(position).rows.get(0).name)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.map_default_img)
                .centerCrop()
                .into(holder.photo);
        return convertView;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }
    class ViewHolder {

        ImageView photo;

        ViewHolder(View view) {
            photo = (ImageView) view.findViewById(R.id.association_pic);
        }
    }

}