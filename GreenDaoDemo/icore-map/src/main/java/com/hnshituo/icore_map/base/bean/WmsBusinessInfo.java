package com.hnshituo.icore_map.base.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

/**
 * Created by Administrator on 2016/4/24.
 */
public class WmsBusinessInfo implements Parcelable {

    /**
     * business_no : 201606301703298667238514130503
     * option_no : 20160630170329839262
     * business_type_no : 2
     * perform_type : 0
     * person_no : shituo307
     * content :
     * valid_flag : 0       0未处理、1已拒绝、2已完成
     * create_time : 2016-06-30 17:03:30
     * create_man : shituo306
     * person_name : 陈崇辉
     * foption_no : 0
     */
    @Expose
    public String business_no;
    @Expose
    public String option_no;
    @Expose
    public String business_type_no;
    @Expose
    public String perform_type;
    @Expose
    public String person_no;
    @Expose
    public String content;
    @Expose
    public String valid_flag;
    @Expose
    public String create_time;
    @Expose
    public String create_man;
    @Expose
    public String person_name;
    @Expose
    public String foption_no;


    public String getBusiness_no() {
        return business_no;
    }

    public void setBusiness_no(String business_no) {
        this.business_no = business_no;
    }

    public String getOption_no() {
        return option_no;
    }

    public void setOption_no(String option_no) {
        this.option_no = option_no;
    }

    public String getBusiness_type_no() {
        return business_type_no;
    }

    public void setBusiness_type_no(String business_type_no) {
        this.business_type_no = business_type_no;
    }

    public String getPerform_type() {
        return perform_type;
    }

    public void setPerform_type(String perform_type) {
        this.perform_type = perform_type;
    }

    public String getPerson_no() {
        return person_no;
    }

    public void setPerson_no(String person_no) {
        this.person_no = person_no;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getValid_flag() {
        return valid_flag;
    }

    public void setValid_flag(String valid_flag) {
        this.valid_flag = valid_flag;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getCreate_man() {
        return create_man;
    }

    public void setCreate_man(String create_man) {
        this.create_man = create_man;
    }

    public String getPerson_name() {
        return person_name;
    }

    public void setPerson_name(String person_name) {
        this.person_name = person_name;
    }

    public String getFoption_no() {
        return foption_no;
    }

    public void setFoption_no(String foption_no) {
        this.foption_no = foption_no;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.business_no);
        dest.writeString(this.option_no);
        dest.writeString(this.business_type_no);
        dest.writeString(this.perform_type);
        dest.writeString(this.person_no);
        dest.writeString(this.content);
        dest.writeString(this.valid_flag);
        dest.writeString(this.create_time);
        dest.writeString(this.create_man);
        dest.writeString(this.person_name);
        dest.writeString(this.foption_no);
    }

    public WmsBusinessInfo() {
    }

    protected WmsBusinessInfo(Parcel in) {
        this.business_no = in.readString();
        this.option_no = in.readString();
        this.business_type_no = in.readString();
        this.perform_type = in.readString();
        this.person_no = in.readString();
        this.content = in.readString();
        this.valid_flag = in.readString();
        this.create_time = in.readString();
        this.create_man = in.readString();
        this.person_name = in.readString();
        this.foption_no = in.readString();
    }

    public static final Creator<WmsBusinessInfo> CREATOR = new Creator<WmsBusinessInfo>() {
        @Override
        public WmsBusinessInfo createFromParcel(Parcel source) {
            return new WmsBusinessInfo(source);
        }

        @Override
        public WmsBusinessInfo[] newArray(int size) {
            return new WmsBusinessInfo[size];
        }
    };
}
