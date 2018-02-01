package com.hnshituo.icore_map.base.bean;

import com.google.gson.annotations.Expose;

import java.util.List;

/**
 * Created by Administrator on 2016/6/27.
 *
 */
public class DepartmentTreeInfo {
    /**
     * id : l1-2
     * name : 凌源钢铁
     * pid :
     * type : 1
     * seq : null
     * del : false
     * createMan : null
     * createTime : 2016-04-12 09:36:36
     * updateMan : null
     * updateTime : 2016-04-12 10:31:45
     * memo :
     * children : null
     */
    @Expose
    public String id;
    @Expose
    public String name;
    @Expose
    public String pid;
    @Expose
    public String type;
    @Expose
    public String seq;
    @Expose
    public String del;
    @Expose
    public String createMan;
    @Expose
    public String createTime;
    @Expose
    public String updateMan;
    @Expose
    public String updateTime;
    @Expose
    public String memo;
    @Expose
    public List<DepartmentTreeInfo> children;


}
