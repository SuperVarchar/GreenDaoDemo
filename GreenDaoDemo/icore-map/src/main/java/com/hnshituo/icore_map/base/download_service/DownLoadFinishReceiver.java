package com.hnshituo.icore_map.base.download_service;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import com.hnshituo.icore_map.ICoreMapClient;
import com.hnshituo.icore_map.base.Constant;

import java.io.File;

public class DownLoadFinishReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        long  completeDownloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID,-1);
        long id = ICoreMapClient.SP.getLong("downloadManagerID", -1);
        if(id == completeDownloadId){
            String name = ICoreMapClient.SP.getString("downloadManagerName","");
            String appId = ICoreMapClient.SP.getString("appId","");
            File file = new File(Constant.DOWNLOAD_PATH,name);
            //调用系统的安装方法
            Uri contentUri;
            Intent intent2 = new Intent(Intent.ACTION_VIEW);
            //兼容安卓7.0
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                intent2.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                contentUri = FileProvider.getUriForFile(context, appId+".fileProvider",file);
            }else {
                contentUri = Uri.fromFile(file);
                intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            intent2.setDataAndType(contentUri, "application/vnd.android.package-archive");
            context.startActivity(intent2);
        }
    }
}
