package com.hnshituo.icore_map.base.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hnshituo.icore_map.ICoreMapClient;
import com.hnshituo.icore_map.R;
import com.hnshituo.icore_map.base.Constant;
import com.hnshituo.icore_map.base.upload.UploadInfo;

import java.util.ArrayList;


/**
 * 添加图片适配器
 * @author Wzh
 * @date 2017/5/18  14:15
 */
public class AddPhotoAdapter extends BaseAdapter {

    private ArrayList<UploadInfo> imageList;

    public AddPhotoAdapter() {
        this.imageList = new ArrayList<>();
    }

    public ArrayList<UploadInfo> getImageList() {
        return imageList;
    }

    public void setImageList(ArrayList<UploadInfo> imageList) {
        this.imageList = imageList;
        notifyDataSetChanged();
    }

    public void deleteImage(UploadInfo info) {
        imageList.remove(info);
        notifyDataSetChanged();
    }
    public void deleteImage(int position) {
        imageList.remove(position);
        notifyDataSetChanged();
    }

    public void setData(UploadInfo uploadInfo){
        this.imageList.add(uploadInfo);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return imageList.size() + 1;
    }

    @Override
    public Object getItem(int position) {

        if (position < imageList.size()) {
            return imageList.get(position);
        } else {
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(ICoreMapClient.application).inflate(R.layout.map_item_association_pic, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if(position < imageList.size()){
            Glide.with(ICoreMapClient.application)
                    .load(Constant.UPLOAD + imageList.get(position).rows.get(0).name)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(R.drawable.map_message_pic)
                    .placeholder(R.drawable.map_message_pic)
                    .into(viewHolder.mAssociationPic);
        }else {
            Glide.with(ICoreMapClient.application)
                    .load(R.drawable.map_message_pic_add)
                    .into(viewHolder.mAssociationPic);
        }
        return convertView;
    }
    class ViewHolder {

        ImageView mAssociationPic;

        ViewHolder(View view) {
            mAssociationPic = (ImageView) view.findViewById(R.id.association_pic);
        }
    }
}
