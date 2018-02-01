package com.hnshituo.icore_map.base.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.hnshituo.icore_map.R;
import com.hnshituo.icore_map.base.Constant;
import com.hnshituo.icore_map.view.view.AnimationButton;

import java.util.Map;



/**
 * Created by Administrator on 2016/7/8.
 */
public class ApproverAdapter extends CustomBaseAdapter<Map<String, Object>> {
    private onDeleteAproveListener mListener;

    public void setOnDeleteAproveListener(onDeleteAproveListener listener) {
        mListener = listener;
    }

    public ApproverAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        final int currentPosition = position;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_approver, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (position == getCount() - 1) {
            viewHolder.mApproverBackIv.setVisibility(View.INVISIBLE);
        }else {
            viewHolder.mApproverBackIv.setVisibility(View.VISIBLE);
        }
        //设置头像
        viewHolder.mApproverAvatarIv.setImageResource(R.drawable.head_portrait);
        viewHolder.mApproverNameTv.setVisibility(View.VISIBLE);
        viewHolder.mApproverNameTv.setText((String) getData().get(position).get("name"));
        String path = (String) getData().get(position).get("img");

//
        final ViewHolder finalViewHolder = viewHolder;
        Glide.with(mContext)
                .load(Constant.UPLOAD+path)
                .asBitmap()
                .placeholder(R.drawable.head_portrait)
                .error(R.drawable.head_portrait)
                .into(new BitmapImageViewTarget(finalViewHolder.mApproverAvatarIv) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(mContext.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        finalViewHolder.mApproverAvatarIv.setImageDrawable(circularBitmapDrawable);
                    }
                });
//            viewHolder.mApproverBackIv.setVisibility(View.VISIBLE);

        viewHolder.mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.delete(currentPosition);
                }
            }
        });
        return convertView;
    }


    static class ViewHolder {
        ImageView mApproverAvatarIv;
        TextView mApproverNameTv;
        ImageView mApproverBackIv;
        AnimationButton mDelete;

        ViewHolder(View view) {
            mApproverAvatarIv = (ImageView) view.findViewById(R.id.approver_avatar_iv);
            mApproverNameTv = (TextView) view.findViewById(R.id.approver_name_tv);
            mApproverBackIv = (ImageView) view.findViewById(R.id.approver_back_iv);
            mDelete = (AnimationButton) view.findViewById(R.id.delete);
        }
    }

    public interface onDeleteAproveListener {
        void delete(int position);
    }
}
