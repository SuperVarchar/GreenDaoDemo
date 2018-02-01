package com.hnshituo.icore_map.download.presenter;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;

import com.hnshituo.icore_map.ICoreMapClient;
import com.hnshituo.icore_map.base.bean.DownloadItem;
import com.hnshituo.icore_map.base.download_service.DownloadService;
import com.hnshituo.icore_map.download.fragment.iview.IDownloadView;
import com.hnshituo.icore_map.download.model.DownloadMode;

import java.util.List;

/**
 *下载界面
 * @author Wzh
 * @date 2016/7/25  17:09
 */
public class DownloadPresenter {

    private IDownloadView iView;
    private DownloadMode mode;


    public DownloadPresenter(IDownloadView iView) {
        this.iView = iView;
        mode = DownloadMode.getDownloadMode();
    }

    public void fillData() {
        List<DownloadItem> items = mode.getList();
        if (items != null && items.size() > 0) {
            iView.showNotEmpty();
            iView.setAdapter(mode.getAdapter());
        } else {
            iView.showEmpty();
        }

    }

    public void removeDownload(FragmentActivity activity, int position) {
        Intent i = new Intent(ICoreMapClient.application,
                DownloadService.class);
        i.putExtra(DownloadService.SERVICE_TYPE_NAME,
                DownloadService.DELETE_DOWNLOAD);
        i.putExtra(DownloadService.DOWNLOAD_TAG_BY_INTENT,position);
        activity.startService(i);
    }

    public void updata() {
        mode.upData();
    }

    public void startDownload(FragmentActivity activity, int index) {
        Intent i = new Intent(ICoreMapClient.application,
                DownloadService.class);
        i.putExtra(DownloadService.SERVICE_TYPE_NAME,
                DownloadService.SUSPEND_TO_STATE);
        i.putExtra(DownloadService.DOWNLOAD_TAG_BY_INTENT,index);
        activity.startService(i);
    }

    public void stopDownload(FragmentActivity activity) {
        Intent i = new Intent(ICoreMapClient.application,
                DownloadService.class);
        i.putExtra(DownloadService.SERVICE_TYPE_NAME,
                DownloadService.DOWNLOAD_STATE_SUSPEND);
        activity.startService(i);
    }

    public void opeateDownload(FragmentActivity activity, int index) {
        DownloadItem downloadItem = mode.getList().get(index);
        if(downloadItem.getDownloadState() == DownloadItem.WAIT){
            startDownload(activity,index);
        }else {
            stopDownload(activity);
        }
    }
}
