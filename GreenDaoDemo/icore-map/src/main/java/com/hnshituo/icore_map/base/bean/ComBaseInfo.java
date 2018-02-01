package com.hnshituo.icore_map.base.bean;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

/**
 * 基础信息表
 */
public class ComBaseInfo implements Parcelable {

    // 基础分类编码
    @Expose
    public String basecode;

    // 基础分类名称
    @Expose
    public String basename;

    // 快速检索码
    @Expose
    public String sortcode;

    // 备注
    @Expose
    public String memo;

    // 备注1
    @Expose
    public String memo1;

    // 可维护标志(1可修改、0不可修改)
    @Expose
    public String isvisible;

    // 维护人
    @Expose
    public String recorder;

    // 维护时间
    @Expose
    public String inputtime;

    // 是否允许增加子类  0:不允许;1:允许
    @Expose
    public String flag;

    // 序号
    @Expose
    public String seq;

    // 拼音助记码
    @Expose
    public String pyzjm;

    // 企业编码
    @Expose
    public String erpcode;

    // 仓库是否属于成品库 1 是成品
    @Expose
    public String cktype;

    // 有效标识
    @Expose
    public String validflag;

    // 删除人
    @Expose
    public String deleteUser;

    // 删除时间
    @Expose
    public String deleteTime;

    // EAS编码
    @Expose
    public String easnum;

    public ComBaseInfo(String basecode,String basename){
        this.basecode = basecode;
        this.basename = basename;
    }

    public String getDeleteUser() {
        return deleteUser;
    }

    public void setDeleteUser(String deleteUser) {
        this.deleteUser = deleteUser;
    }

    public String getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(String deleteTime) {
        this.deleteTime = deleteTime;
    }

    public String getBasecode() {
        return basecode;
    }

    public void setBasecode(String basecode) {
        this.basecode = basecode;
    }

    public String getBasename() {
        return basename;
    }

    public void setBasename(String basename) {
        this.basename = basename;
    }

    public String getSortcode() {
        return sortcode;
    }

    public void setSortcode(String sortcode) {
        this.sortcode = sortcode;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getMemo1() {
        return memo1;
    }

    public void setMemo1(String memo1) {
        this.memo1 = memo1;
    }

    public String getIsvisible() {
        return isvisible;
    }

    public void setIsvisible(String isvisible) {
        this.isvisible = isvisible;
    }

    public String getRecorder() {
        return recorder;
    }

    public void setRecorder(String recorder) {
        this.recorder = recorder;
    }

    public String getInputtime() {
        return inputtime;
    }

    public void setInputtime(String inputtime) {
        this.inputtime = inputtime;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public String getPyzjm() {
        return pyzjm;
    }

    public void setPyzjm(String pyzjm) {
        this.pyzjm = pyzjm;
    }

    public String getErpcode() {
        return erpcode;
    }

    public void setErpcode(String erpcode) {
        this.erpcode = erpcode;
    }

    public String getCktype() {
        return cktype;
    }

    public void setCktype(String cktype) {
        this.cktype = cktype;
    }

    public String getValidflag() {
        return validflag;
    }

    public void setValidflag(String validflag) {
        this.validflag = validflag;
    }

    public String getDelete_user() {
        return deleteUser;
    }

    public void setDelete_user(String delete_user) {
        this.deleteUser = delete_user;
    }

    public String getDelete_time() {
        return deleteTime;
    }

    public void setDelete_time(String delete_time) {
        this.deleteTime = delete_time;
    }

    public String getEasnum() {
        return easnum;
    }

    public void setEasnum(String easnum) {
        this.easnum = easnum;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.basecode);
        dest.writeString(this.basename);
        dest.writeString(this.sortcode);
        dest.writeString(this.memo);
        dest.writeString(this.memo1);
        dest.writeString(this.isvisible);
        dest.writeString(this.recorder);
        dest.writeString(this.inputtime);
        dest.writeString(this.flag);
        dest.writeString(this.seq);
        dest.writeString(this.pyzjm);
        dest.writeString(this.erpcode);
        dest.writeString(this.cktype);
        dest.writeString(this.validflag);
        dest.writeString(this.deleteUser);
        dest.writeString(this.deleteTime);
        dest.writeString(this.easnum);
    }

    public ComBaseInfo() {
    }

    protected ComBaseInfo(Parcel in) {
        this.basecode = in.readString();
        this.basename = in.readString();
        this.sortcode = in.readString();
        this.memo = in.readString();
        this.memo1 = in.readString();
        this.isvisible = in.readString();
        this.recorder = in.readString();
        this.inputtime = in.readString();
        this.flag = in.readString();
        this.seq = in.readString();
        this.pyzjm = in.readString();
        this.erpcode = in.readString();
        this.cktype = in.readString();
        this.validflag = in.readString();
        this.deleteUser = in.readString();
        this.deleteTime = in.readString();
        this.easnum = in.readString();
    }

    public static final Parcelable.Creator<ComBaseInfo> CREATOR = new Parcelable.Creator<ComBaseInfo>() {
        @Override
        public ComBaseInfo createFromParcel(Parcel source) {
            return new ComBaseInfo(source);
        }

        @Override
        public ComBaseInfo[] newArray(int size) {
            return new ComBaseInfo[size];
        }
    };
}

