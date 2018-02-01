package com.hnshituo.icore_map.base.download_service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.hnshituo.icore_map.ICoreMapClient;
import com.hnshituo.icore_map.base.bean.DownloadItem;
import com.hnshituo.icore_map.dao.DownloadDao;
import com.hnshituo.icore_map.download.model.DownloadMode;
import com.hnshituo.icore_map.okhttp.body.ProgressResponseBody;
import com.hnshituo.icore_map.util.MyToast;
import com.hnshituo.icore_map.util.ProgressDownloader;
import com.hnshituo.icore_map.util.SimpleUtils;

import java.io.File;
import java.util.List;


/**
 * 下载服务
 * @author Wzh
 * @date 2016/7/25  18:30
 */
public class DownloadService extends Service {

    public static final String DOWNLOAD_TAG_BY_INTENT = "downloadurl";
    public static final String SERVICE_TYPE_NAME = "servicetype";
    public static final int START_DOWNLOAD_MOVIE_TWO = 999;               //开始下载
    public static final int ERROR_CODE = -1;
    public static final int DOWNLOAD_STATE_SUSPEND = 3;                  // 暂停
    public static final int SUSPEND_TO_STATE = 4;                        //暂停到开始
    public static final int DELETE_DOWNLOAD = 5;                         //删除任务
    public static final int FIND_DB = 6;                                 //从数据库获得缓存数据

    protected ProgressDownloader loader;
    protected DownloadItem downloadItem;                                //正在下载的对象
    protected static DownloadDao dao;
    private boolean isDowndding = false;


    public static void startDownloadService(Context context,DownloadItem item) {

        if(dao == null){
            dao = new DownloadDao(context);
        }

        DownloadItem downloadItem = (DownloadItem) dao.queryObjByFieldOnlyOne("fileName",item.getFileName());
        if(downloadItem == null){
            MyToast.show(ICoreMapClient.application,item.getFileName()+"加入下载队列");
            dao.saveObj(item);
            DownloadMode.getDownloadMode().addDownload(item);

            Intent i = new Intent(context, DownloadService.class);
            i.putExtra(SERVICE_TYPE_NAME, START_DOWNLOAD_MOVIE_TWO);
            context.startService(i);
        }else if(downloadItem.getDownloadState() == DownloadItem.FINISH){

            File file = new File(downloadItem.getTagUrl(), downloadItem.getFileName());
            if(file.exists()){
                SimpleUtils.openFile(context,file);
            }else {
                dao.deleteObjByField("fileName",item.getFileName());
                DownloadItem item2 = (DownloadItem) dao.queryObjByFieldOnlyOne("fileName", item.getFileName());
                if (item2 == null) {
                    MyToast.show(ICoreMapClient.application, item.getFileName() + "加入下载队列");
                    dao.saveObj(item);
                    DownloadMode.getDownloadMode().addDownload(item);

                    Intent i = new Intent(context, DownloadService.class);
                    i.putExtra(SERVICE_TYPE_NAME, START_DOWNLOAD_MOVIE_TWO);
                    context.startService(i);
                }
            }
        }else if(downloadItem.getDownloadState() == DownloadItem.DOWNLOADING){
            MyToast.show(ICoreMapClient.application,"已经正在下载");
        }else if(downloadItem.getDownloadState() == DownloadItem.WAIT){
            MyToast.show(ICoreMapClient.application,"已经正在下载");
        }
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        if (intent == null){
            return;
        }

        int code = intent.getIntExtra(SERVICE_TYPE_NAME,ERROR_CODE);
        File file = new File(com.hnshituo.icore_map.base.Constant.DOWNLOAD_PATH);
        if (!file.exists()) {
            file.mkdirs();
        }

        switch (code) {
            case START_DOWNLOAD_MOVIE_TWO:                          //开始
                if(!isDowndding){
                    startDownload(0);
                }
                break;
            case DOWNLOAD_STATE_SUSPEND:                            //开始到暂停
                suspend();
                break;

            case SUSPEND_TO_STATE:                                  //暂停到开始
                int index = intent.getIntExtra(DOWNLOAD_TAG_BY_INTENT,0);
                suspend();
                startDownload(index);
                break;
            case DELETE_DOWNLOAD:                                   //删除下载
                int i = intent.getIntExtra(DOWNLOAD_TAG_BY_INTENT,0);
                DownloadItem item = DownloadMode.getDownloadMode().getList().get(i);
                if(item.getDownloadState() == DownloadItem.DOWNLOADING){
                    loader.pause();
                    isDowndding = false;
                }

                DownloadMode.getDownloadMode().remove(i);
                dao.deleteObjById(item.getDownloadUrl());
                File file1 = new File(item.getTagUrl(),item.getFileName());
                if(file1.exists()){
                    file1.delete();
                }

                if(!isDowndding){
                    startDownload(i);
                }

                break;

            case FIND_DB :
                if(dao == null){
                    dao = new DownloadDao(this);
                }

                List<DownloadItem> list = dao.queryObjByNotField("downloadState", DownloadItem.FINISH);
                DownloadMode.getDownloadMode().setList(list);
                break;
        }

    }

    /**
     * 暂停
     */
    private void suspend() {
        if(isDowndding){
            isDowndding = false;
            loader.pause();
            downloadItem.setDownloadState(DownloadItem.WAIT);
            dao.updateObj(downloadItem);
        }
    }

    /**
     * 直接开始下载
     */
    private void startDownload(int index) {

        if(DownloadMode.getDownloadMode().getList().size() == 0){
            isDowndding = false;
            return;
        }

        if(index >= DownloadMode.getDownloadMode().getList().size()){
            index = 0;
        }

        downloadItem = DownloadMode.getDownloadMode().getList().get(index);
        MyToast.show(ICoreMapClient.application,downloadItem.getFileName()+"开始下载");

        loader = new ProgressDownloader(downloadItem.getDownloadUrl(), new File(downloadItem.getTagUrl(),downloadItem.getFileName()), new ProgressResponseBody.ProgressListener() {
            @Override
            public void onPreExecute(long contentLength) {
                //设置总长度
                if(downloadItem.getProgressCount() == 0) {
                    downloadItem.setProgressCount(contentLength);
                }
            }

            @Override
            public void update(long totalBytes, boolean done) {
                downloadItem.setCurrentProgress(downloadItem.getCurrentProgress()+totalBytes);
                if(downloadItem.getCurrentProgress() == downloadItem.getProgressCount()){
                    isDowndding = false;
                    downloadItem.setDownloadState(DownloadItem.FINISH);
                    int i = DownloadMode.getDownloadMode().getList().indexOf(downloadItem);
                    DownloadMode.getDownloadMode().removeDownload(downloadItem);
                    dao.updateObj(downloadItem);
                    MyToast.show(ICoreMapClient.application,downloadItem.getFileName()+"下载完成");
                    startDownload(i);
                }else {
                    downloadItem.setDownloadState(DownloadItem.DOWNLOADING);
                }
            }
        });
        loader.download(downloadItem.getCurrentProgress());
        isDowndding = true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        suspend();
    }
}
