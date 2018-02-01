package com.hnshituo.icore_map.download.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.hnshituo.icore_map.R;
import com.hnshituo.icore_map.base.adapter.CustomBaseAdapter;
import com.hnshituo.icore_map.base.bean.DownloadItem;
import com.hnshituo.icore_map.base.download_service.DownloadService;
import com.hnshituo.icore_map.dao.DownloadDao;
import com.hnshituo.icore_map.download.bean.LocationFile;
import com.hnshituo.icore_map.download.model.DownloadMode;
import com.hnshituo.icore_map.util.SimpleUtils;
import com.hnshituo.icore_map.view.dialog.StyleDialog;

import java.io.File;
import java.text.DateFormat;
import java.util.List;
import java.util.Locale;



/**
 * Created by Administrator on 2016/7/15.
 * 图片界面的Adapter
 */
public class PicFileAdapter extends CustomBaseAdapter<LocationFile> {

    DownloadDao dao;
    public SwipeLayout openLayout;

    public PicFileAdapter(Context context) {
        super(context);
        dao = new DownloadDao(context);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        int numItems = 0;

        LocationFile itemData = getItemData(position);

        File file = new File(itemData.filePath);
        DateFormat df = DateFormat.getDateTimeInstance(DateFormat.SHORT,
                DateFormat.SHORT, Locale.getDefault());
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.item_file_info, null);
            holder.img = (ImageView) convertView.findViewById(R.id.fileinfo_row_image);
            holder.title = (TextView) convertView.findViewById(R.id.fileinfo_top_view_txt);
            holder.info = (TextView) convertView.findViewById(R.id.fileinfo_dataview_txt);
            holder.bottomView = (TextView) convertView.findViewById(R.id.fileinfo_bottom_text);
            holder.delete = (TextView) convertView.findViewById(R.id.item_fileinfo_delete);
            holder.swipe = (SwipeLayout) convertView.findViewById(R.id.swipe);
            holder.allBottomLine = convertView.findViewById(R.id.all_bottom_line);
            holder.notAllBottomLine = convertView.findViewById(R.id.not_all_bottom_line);
            holder.swipe.setTag(R.id.swipe_tag,itemData);
            holder.swipe.addSwipeListener(new SimpleSwipeListener(){
                @Override
                public void onOpen(SwipeLayout layout) {
                    if(openLayout != null && openLayout != layout){
                        openLayout.close();
                    }

                    openLayout = layout;
                    ((LocationFile)layout.getTag(R.id.swipe_tag)).isOpen = true;
                }

                @Override
                public void onClose(SwipeLayout layout) {
                    if(openLayout != null && openLayout == layout){
                        openLayout = null;
                    }
                    ((LocationFile)layout.getTag(R.id.swipe_tag)).isOpen = false;
                }
            });

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
            holder.swipe.setTag(R.id.swipe_tag,itemData);
        }
        holder.img.setImageResource(R.drawable.xzgl_img);

        holder.title.setText(itemData.fileName);
        holder.info.setText(df.format(file.lastModified()));
        if (file.isFile()) {
            holder.bottomView.setText(SimpleUtils.formatCalculatedSize(file.length()));
        } else {
            String[] list = file.list();
            if (list != null)
                numItems = list.length;
            holder.bottomView.setText(numItems + mContext.getString(R.string.files));
        }

        if (itemData.isOpen) {
            holder.swipe.open();
        } else {
            holder.swipe.close();
        }

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StyleDialog.showDeleteDialog(mContext, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        deleteFile(position);
                        holder.swipe.close();

                    }
                });
            }
        });

        if(getCount() == 1 || position == getCount() - 1){
            holder.allBottomLine.setVisibility(View.VISIBLE);
            holder.notAllBottomLine.setVisibility(View.GONE);
        }else {
            holder.allBottomLine.setVisibility(View.GONE);
            holder.notAllBottomLine.setVisibility(View.VISIBLE);
        }

        return convertView;
    }

    class ViewHolder {
        public ImageView img;   //图片
        public TextView title;  //名称
        public TextView info;   //操作时间
        public TextView bottomView; //文件大小
        public TextView delete; //删除
        public SwipeLayout swipe;
        public View allBottomLine;
        public View notAllBottomLine;
    }


    private void deleteFile(int position) {
        boolean flag = false;
        int index = 0;
        List<DownloadItem> list = DownloadMode.getDownloadMode().getList();
        for (int i = 0; i < list.size(); i++) {
            DownloadItem o = list.get(i);
            if(o.getFileName().equals(getItemData(position).fileName)){
                flag = true;
                index = i;
                break;
            }
        }

        if(flag){
            Intent i = new Intent(mContext,
                    DownloadService.class);
            i.putExtra(DownloadService.SERVICE_TYPE_NAME,
                    DownloadService.DELETE_DOWNLOAD);
            i.putExtra(DownloadService.DOWNLOAD_TAG_BY_INTENT,index);
            mContext.startService(i);
        }else {
            dao.deleteObjByField("fileName",getItemData(position).fileName);
        }
        SimpleUtils.deleteTarget(getItemData(position).filePath);
        getData().remove(position);
        notifyDataSetChanged();
    }

}
