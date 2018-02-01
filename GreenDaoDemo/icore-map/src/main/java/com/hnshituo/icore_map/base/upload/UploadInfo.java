package com.hnshituo.icore_map.base.upload;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/28.
 */
public class UploadInfo implements Parcelable {
    @Expose
    public List<RowsBean> rows;
    public static class RowsBean implements Parcelable {
        @Expose
        public String name; //文件临时目录路径
        @Expose
        public String filename; //文件名字
        @Expose
        public String filetime;//文件时间
        @Expose
        public String filetype; //文件类型
        @Expose
        public String path; //需要赋值到对象中的值

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.name);
            dest.writeString(this.filename);
            dest.writeString(this.filetime);
            dest.writeString(this.filetype);
        }

        public RowsBean() {
        }

        protected RowsBean(Parcel in) {
            this.name = in.readString();
            this.filename = in.readString();
            this.filetime = in.readString();
            this.filetype = in.readString();
        }

        public static final Creator<RowsBean> CREATOR = new Creator<RowsBean>() {
            @Override
            public RowsBean createFromParcel(Parcel source) {
                return new RowsBean(source);
            }

            @Override
            public RowsBean[] newArray(int size) {
                return new RowsBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.rows);
    }

    public UploadInfo() {
    }

    protected UploadInfo(Parcel in) {
        this.rows = new ArrayList<RowsBean>();
        in.readList(this.rows, RowsBean.class.getClassLoader());
    }

    public static final Creator<UploadInfo> CREATOR = new Creator<UploadInfo>() {
        @Override
        public UploadInfo createFromParcel(Parcel source) {
            return new UploadInfo(source);
        }

        @Override
        public UploadInfo[] newArray(int size) {
            return new UploadInfo[size];
        }
    };
}
