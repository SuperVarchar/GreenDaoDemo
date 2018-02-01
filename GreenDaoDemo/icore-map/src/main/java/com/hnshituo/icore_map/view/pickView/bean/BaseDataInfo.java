package com.hnshituo.icore_map.view.pickView.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Administrator on 2016/4/8.
 */
@DatabaseTable(tableName = "ST_SaleBaseData")
public class BaseDataInfo implements Parcelable {
    @DatabaseField(id=true)
    @Expose
    public String id;
    @DatabaseField
    @Expose
    public String text;
    @DatabaseField
    @Expose
    public String img;
    @DatabaseField
    @Expose
    public String type;
    @DatabaseField
    @Expose
    public String regionkey;
    @Expose
    private static BaseDataInfo emptyDataInfo;

    public static BaseDataInfo getEmptyDataInfo(){
        if(emptyDataInfo == null){
            emptyDataInfo = new BaseDataInfo();
            emptyDataInfo.text = "--无--";
            emptyDataInfo.id = null;
        }

        return emptyDataInfo;
    }

    public BaseDataInfo(String id, String text, String img, String type) {
        this.id = id;
        this.text = text;
        this.img = img;
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.text);
        dest.writeString(this.img);
        dest.writeString(this.type);
        dest.writeString(this.regionkey);
    }

    public BaseDataInfo(String id, String text) {
        this.id = id;
        this.text = text;
    }

    public BaseDataInfo() {
    }

    protected BaseDataInfo(Parcel in) {
        this.id = in.readString();
        this.text = in.readString();
        this.img = in.readString();
        this.type = in.readString();
        this.regionkey = in.readString();
    }

    public static final Creator<BaseDataInfo> CREATOR = new Creator<BaseDataInfo>() {
        @Override
        public BaseDataInfo createFromParcel(Parcel source) {
            return new BaseDataInfo(source);
        }

        @Override
        public BaseDataInfo[] newArray(int size) {
            return new BaseDataInfo[size];
        }
    };
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BaseDataInfo) {
            if (this.id.equals(((BaseDataInfo) obj).id)) {
                return true;
            }
            else {
                return false;
            }
        }
        return false;
    }
}
