package com.hnshituo.icore_map.base.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.hnshituo.icore_map.R;
import com.hnshituo.icore_map.base.Constant;
import com.hnshituo.icore_map.base.activity.ICoreBaseActivity;
import com.hnshituo.icore_map.base.bean.ImageBrowseEvent;
import com.hnshituo.icore_map.base.upload.UploadInfo;
import com.hnshituo.icore_map.okhttp.callback.RequestCallFactory;
import com.hnshituo.icore_map.okhttp.callback.StringCallback;
import com.hnshituo.icore_map.view.photoview.EasePhotoView;

import org.greenrobot.eventbus.EventBus;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by Administrator on 2016/7/18.
 */
public class ImageBrowseAdapter extends PagerAdapter {

    private ICoreBaseActivity mContext;
    private ArrayList<UploadInfo> mUploadInfos;
    private int mtype; //type = 1  浏览  ;  type = 2 浏览并可以删除

    public ImageBrowseAdapter(ICoreBaseActivity context, ArrayList<UploadInfo> uploadInfos, int type) {
        mContext = context;
        mUploadInfos = uploadInfos;
        mtype = type;
    }

    @Override
    public int getCount() {
        return mUploadInfos.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


    public ArrayList<UploadInfo> getUpload() {
        return mUploadInfos;
    }

    public void setUploadInfos(ArrayList<UploadInfo> uploadInfos) {
        mUploadInfos = uploadInfos;
    }

    @Override
    public View instantiateItem(ViewGroup container, int position) {
        final int currentPosition = position;
        final View view = View.inflate(mContext, R.layout.item_image_browse, null);

        final ViewHolder viewHolder = new ViewHolder(view);
        if (mtype == 1) {
            viewHolder.mImageBrowseDelIv.setVisibility(View.GONE);
        } else if (mtype == 2) {
            viewHolder.mImageBrowseDelIv.setVisibility(View.VISIBLE);
        }
        Glide.with(mContext).load(Constant.UPLOAD + mUploadInfos.get(position).rows.get(0).name.replace("\\", "/"))
                .error(R.drawable.bitmap_error)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(new GlideDrawableImageViewTarget(viewHolder.mImageBrowsePv) {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                        super.onResourceReady(resource,animation);
                        viewHolder.mProgressLl.setVisibility(View.GONE);
                    }
                });
        viewHolder.mImageBrowseDelIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    deleteFile(viewHolder, currentPosition);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });
        viewHolder.mImageBrowsePageTv.setText((position + 1) + "/" + getCount());

        viewHolder.mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new ImageBrowseEvent());
            }
        });

        container.addView(view);
        return view;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }


    static class ViewHolder {

        ProgressBar mProgress;
        LinearLayout mProgressLl;
        EasePhotoView mImageBrowsePv;
        TextView mImageBrowsePageTv;
        ImageView mImageBrowseDelIv;
        ImageView mBack;

        ViewHolder(View view) {
            mProgress = (ProgressBar) view.findViewById(R.id.progress);
            mProgressLl = (LinearLayout) view.findViewById(R.id.progress_ll);
            mImageBrowsePv = (EasePhotoView) view.findViewById(R.id.image_browse_pv);
            mImageBrowsePageTv = (TextView) view.findViewById(R.id.image_browse_page_tv);
            mImageBrowseDelIv = (ImageView) view.findViewById(R.id.image_browse_del_iv);
            mBack = (ImageView) view.findViewById(R.id.back);
        }
    }

    /**
     * 删除图片
     *
     * @param viewHolder
     * @param currentPosition
     * @throws UnsupportedEncodingException
     */
    private void deleteFile(ViewHolder viewHolder, final int currentPosition) throws UnsupportedEncodingException {
        Map<String, String> params = new HashMap<>();
        params.put("path", mUploadInfos.get(currentPosition).rows.get(0).name);
        RequestCallFactory.getHttpGet(Constant.DELETE_IMAGE, params, mContext).execute(new StringCallback(mContext, 2) {
            @Override
            public void onResponse(String response) {
                if (mUploadInfos != null) {
                    mUploadInfos.remove(currentPosition);
                    notifyDataSetChanged();
                }
            }
        });

    }


}
