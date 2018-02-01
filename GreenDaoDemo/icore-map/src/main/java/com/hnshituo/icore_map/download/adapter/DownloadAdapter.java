package com.hnshituo.icore_map.download.adapter;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hnshituo.icore_map.R;
import com.hnshituo.icore_map.base.adapter.CustomBaseAdapter;
import com.hnshituo.icore_map.base.bean.DownloadItem;
import com.hnshituo.icore_map.download.bean.DownloadEvent;

import org.greenrobot.eventbus.EventBus;


/**
 * 下载界面适配器
 *
 * @author Wzh
 * @date 2016/7/25  18:12
 */
public class DownloadAdapter extends CustomBaseAdapter<DownloadItem> {

    private int[] mImageResource = new int[]{
            R.drawable.wjxz_unknown,
            R.drawable.xzgl_file,
            R.drawable.xzgl_img,
            R.drawable.xzgl_yasuo,
            R.drawable.xzgl_apk
    };


    public DownloadAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_download, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        DownloadItem itemData = getItemData(position);
        holder.textName.setText(itemData.getFileName());

       /* Log.e("ProgressCount",((int)itemData.getProgressCount())+"");
        Log.e("CurrentProgress",((int) itemData.getCurrentProgress())+"");*/
        holder.itemProgressbar.setMax((int) itemData.getProgressCount());
        holder.itemProgressbar.setProgress((int) itemData.getCurrentProgress());
        holder.imageView2.setImageResource(mImageResource[chooseImage(itemData.getFileName())]);


        switch (itemData.getDownloadState()) {
            case DownloadItem.DOWNLOADING:
                holder.buttonPause.setImageResource(R.drawable.xzgl_pause);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    holder.itemProgressbar.setProgressDrawable(mContext.getDrawable(R.drawable.progressbar_mini));
                } else {
                    holder.itemProgressbar.setProgressDrawable(mContext.getResources().getDrawable(R.drawable.progressbar_mini));
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    holder.progressTv.setTextColor(mContext.getResources().getColor(R.color.color_00c085, null));
                } else {
                    holder.progressTv.setTextColor(mContext.getResources().getColor(R.color.color_00c085));
                }

                break;
            case DownloadItem.WAIT:
                holder.buttonPause.setImageResource(R.drawable.xzgl_downloads);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    holder.itemProgressbar.setProgressDrawable(mContext.getDrawable(R.drawable.progressbar_mini_black));
                } else {
                    holder.itemProgressbar.setProgressDrawable(mContext.getResources().getDrawable(R.drawable.progressbar_mini_black));
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    holder.progressTv.setTextColor(mContext.getResources().getColor(R.color.color_cccccc, null));
                } else {
                    holder.progressTv.setTextColor(mContext.getResources().getColor(R.color.color_cccccc));
                }

                break;
        }

        int progress = (int) ((((float) itemData.getCurrentProgress()) / ((float) itemData.getProgressCount())) * 100);
        holder.progressTv.setText(progress + "%");

        holder.buttonPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new DownloadEvent(position, false));
            }
        });

        holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new DownloadEvent(position, true));
            }
        });

        return convertView;
    }


    private int chooseImage(String fName) {
        int type = 0;
        String FileEnd = fName.substring(fName.lastIndexOf(".") + 1,
                fName.length()).toLowerCase();
        if (FileEnd.equals("txt") || FileEnd.equals("doc") || FileEnd.equals("hlp") || FileEnd.equals("wps")
                || FileEnd.equals("rtf") || FileEnd.equals("html") || FileEnd.equals("pdf")
                || FileEnd.equals("xls") || FileEnd.equals("htm") || FileEnd.equals("wpd") || FileEnd.equals("docx")
                || FileEnd.equals("dotx") || FileEnd.equals("dot") || FileEnd.equals("xps")
                || FileEnd.equals("xml") || FileEnd.equals("ppt")) {
            type = 1;
        } else if (FileEnd.equals("jpg") || FileEnd.equals("png") || FileEnd.equals("gif")
                || FileEnd.equals("jpeg") || FileEnd.equals("bmp") || FileEnd.equals("gif")
                || FileEnd.equals("tif")) {
            type = 2;
        } else if (FileEnd.equals("rar") || FileEnd.equals("zip") || FileEnd.equals("arj") || FileEnd.equals("gz")
                || FileEnd.equals("z")) {
            type = 3;
        } else if (FileEnd.equals("apk")) {
            type = 4;
        }

        return type;
    }


    static class ViewHolder {
        View view;
        ImageView imageView2;
        TextView textName;
        ProgressBar itemProgressbar;
        TextView progressTv;
        ImageView buttonPause;
        ImageView buttonDelete;
        LinearLayout layout;

        ViewHolder(View view) {
            this.view = view;
            this.imageView2 = (ImageView) view.findViewById(R.id.imageView2);
            this.textName = (TextView) view.findViewById(R.id.text_name);
            this.itemProgressbar = (ProgressBar) view.findViewById(R.id.item_progressbar);
            this.progressTv = (TextView) view.findViewById(R.id.progress_tv);
            this.buttonPause = (ImageView) view.findViewById(R.id.button_pause);
            this.buttonDelete = (ImageView) view.findViewById(R.id.button_delete);
            this.layout = (LinearLayout) view.findViewById(R.id.layout);
        }
    }
}
