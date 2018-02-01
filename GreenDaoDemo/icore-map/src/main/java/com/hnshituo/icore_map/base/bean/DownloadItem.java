package com.hnshituo.icore_map.base.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.hnshituo.icore_map.base.Constant;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 下载对象
 * @author Wzh
 * @date 2016/7/25  16:03
 */
@DatabaseTable(tableName ="ST_DownloadData")
public class DownloadItem implements Parcelable {

    public static final int FINISH = 10;
    public static final int WAIT = 20;
    public static final int DOWNLOADING = 30;
    //下载地址
    @DatabaseField(id=true)
    @Expose
    private String downloadUrl;
    //总大小
    @DatabaseField
    @Expose
    private long progressCount;
    //当前大小
    @DatabaseField
    @Expose
    private long currentProgress;
    //下载状态
    @DatabaseField
    @Expose
    private int downloadState = WAIT;
    //储存地址
    @DatabaseField
    @Expose
    private String tagUrl = Constant.DOWNLOAD_PATH;
    //文件名
    @DatabaseField
    @Expose
    private String fileName;                                               //文件名字


    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getTagUrl() {
        return tagUrl;
    }

    public void setTagUrl(String tagUrl) {
        this.tagUrl = tagUrl;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public long getProgressCount() {
        return progressCount;
    }

    public void setProgressCount(long progressCount) {
        this.progressCount = progressCount;
    }

    public long getCurrentProgress() {
        return currentProgress;
    }

    public void setCurrentProgress(long currentProgress) {
        this.currentProgress = currentProgress;
    }

    public int getDownloadState() {
        return downloadState;
    }

    public void setDownloadState(int downloadState) {
        this.downloadState = downloadState;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.downloadUrl);
        dest.writeLong(this.progressCount);
        dest.writeLong(this.currentProgress);
        dest.writeInt(this.downloadState);
        dest.writeString(this.tagUrl);
        dest.writeString(this.fileName);
    }

    public DownloadItem() {
    }

    protected DownloadItem(Parcel in) {
        this.downloadUrl = in.readString();
        this.progressCount = in.readLong();
        this.currentProgress = in.readLong();
        this.downloadState = in.readInt();
        this.tagUrl = in.readString();
        this.fileName = in.readString();
    }

    public static final Creator<DownloadItem> CREATOR = new Creator<DownloadItem>() {
        @Override
        public DownloadItem createFromParcel(Parcel source) {
            return new DownloadItem(source);
        }

        @Override
        public DownloadItem[] newArray(int size) {
            return new DownloadItem[size];
        }
    };
}
