package com.hnshituo.icore_map.base.model;

import com.hnshituo.icore_map.base.bean.DepartmentTreeInfo;
import com.hnshituo.icore_map.base.bean.LoginUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/11.
 */
public class ChoosePersonModel {
    /**
     * 选择人员的gridList的集合
     */
    private List<LoginUser> gridList;
    /**
     * 回传的人员信息
     */
    private ArrayList<LoginUser> mBackPersonInfos;
    /**
     * 获取的人员的信息的列表
     */
    private List<LoginUser> mPersonInfos;
    /**
     * 部门的树形结构信息
     */
    private List<DepartmentTreeInfo> mDepartmentTreeInfos;

    /**
     * 当前层级的部门名字
     */
    private List<String> mDepartmentNames;

    public List<String> getDepartmentNames() {
        return mDepartmentNames;
    }

    public void addDepartmentNames(String departmentName) {
        mDepartmentNames.add(departmentName);
    }


    public void clearDepartmentNames() {
        mDepartmentNames.clear();
    }




    public List<DepartmentTreeInfo> getDepartmentTreeInfos() {
        return mDepartmentTreeInfos;
    }

    public void setDepartmentTreeInfos(List<DepartmentTreeInfo> departmentTreeInfos) {
        mDepartmentTreeInfos = departmentTreeInfos;
    }
    public void clearDepartmentTreeInfos() {
        mDepartmentTreeInfos.clear();
    }

    public ChoosePersonModel() {
        mDepartmentNames = new ArrayList<>();
        gridList = new ArrayList<>();
        mBackPersonInfos = new ArrayList<>();
        mPersonInfos = new ArrayList<>();
    }

    //girdlist
    public List<LoginUser> getGridList() {
        return gridList;
    }

    public void addGridList(List<LoginUser> gridLists) {
        gridList.addAll(gridLists);
    }

    public void addGirdSingle(LoginUser personInfo) {
        gridList.add(personInfo);
    }

    public void removeGridListWithPosition(int position) {
        gridList.remove(position);
    }

    public void removeGridListWithName(LoginUser personInfo) {
        for (int i = 0 ; i < gridList.size();i++) {
            LoginUser info = gridList.get(i);
            if (info.id.equals(personInfo.id)) {
                gridList.remove(info);
            }
        }

    }

    public void clearGridList() {
        gridList.clear();
    }

    //backPersoninfo
    public ArrayList<LoginUser> getBackPersonInfos() {
        return mBackPersonInfos;
    }

    public void addBackPersonInfos(List<LoginUser> userInfos) {
        mBackPersonInfos.addAll(userInfos);
    }

    public void addBackPersonrInfoSingle(LoginUser userInfo) {
        mBackPersonInfos.add(userInfo);
    }

    public void removeBackPersonInfosWithPosition(int position) {
        mBackPersonInfos.remove(position);
    }

    public void clearBackPersonInfos() {
        mBackPersonInfos.clear();
    }

    //personinfos
    public List<LoginUser> getPersonInfos() {
        return mPersonInfos;
    }

    public void addPersonInfos(List<LoginUser> personInfos) {
        mPersonInfos.addAll(personInfos);
    }

    public void addPersonInfoSingle(LoginUser personInfo) {
        mPersonInfos.add(personInfo);
    }

    public void removePersonInfosWithPosition(int position) {
        mPersonInfos.remove(position);
    }

    public void removePersonInfosWithPersonInfo(LoginUser personInfo) {
        mPersonInfos.remove(personInfo);
    }

    public void clearPersonInfos() {
        mPersonInfos.clear();
    }


}
