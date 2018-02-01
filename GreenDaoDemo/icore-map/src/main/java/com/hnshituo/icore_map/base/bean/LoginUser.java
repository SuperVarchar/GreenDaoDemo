package com.hnshituo.icore_map.base.bean;

import com.google.gson.annotations.Expose;

/**
 * @author Wzh
 * @date 2016/7/5  8:51
 */
public class LoginUser {
    /**
     * id : 帐号
     * password : 密码
     * wrk_ord : 班次
     * wrk_grp : 班组
     * wrk_date : 当班日期
     */
    @Expose
    public String id;
    @Expose
    public String password;
    @Expose
    public String wrk_ord;
    @Expose
    public String wrk_grp;
    @Expose
    public String wrk_date;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getWrk_ord() {
        return wrk_ord;
    }

    public void setWrk_ord(String wrk_ord) {
        this.wrk_ord = wrk_ord;
    }

    public String getWrk_grp() {
        return wrk_grp;
    }

    public void setWrk_grp(String wrk_grp) {
        this.wrk_grp = wrk_grp;
    }

    public String getWrk_date() {
        return wrk_date;
    }

    public void setWrk_date(String wrk_date) {
        this.wrk_date = wrk_date;
    }
}
