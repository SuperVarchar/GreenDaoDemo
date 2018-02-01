/***文档注释***********************************************
 * 作者               :                   邓佳威
 * 创建日期      :                   2017.07.20
 * 描述               :                   web端查询请求及返回pojo
 * 注意事项      :                   无
 * 遗留BUG :                   无
 * 修改日期      :                  
 * 修改人员      :                   
 * 修改内容      :                   
 ***********************************************************/

package com.hnshituo.icore_map.base.bean;

import com.google.gson.annotations.Expose;

import java.util.List;


public class CommonPage<T> {
	//接收的对象
	@Expose
	public T object;
	
	//接收对象的集合
	@Expose
	public List<T> objectList;
	
	//页码(查询条件)
	@Expose
	public String pageIndex;
	
	//条数(查询条件)
	@Expose
	public String pageSize;
	
	//开始时间(查询条件)
	public String startTime;
	
	//结束时间(查询条件)
	@Expose
	public String endTime;
	
	//条件1(查询条件)
	@Expose
	public String memo1;
	
	//条件2(查询条件)
	@Expose
	public String memo2;
	
	//条件3(查询条件)
	@Expose
	public String memo3;
	
	//条件4(查询条件)
	@Expose
	public String memo4;
	
	//对象参数
	@Expose
	public Object objParam;

	//排序字段
	@Expose
	public String sortField;
	
	//排序类型 0正序 1倒叙
	@Expose
	public String sortType;
	
	//排序条件
	@Expose
	public String sortSqlCondition;
	
	public String getSortSqlCondition() {
		if(sortSqlCondition==null||"desc".equals(sortSqlCondition.trim()))
			return "";
		return sortSqlCondition;
	}

	/**
	 * @param ide 标识，有时候是多表，所以需要用类似 t1,t2之类的标识来表示是哪个表字段排序
	 * */
	public void setSortSqlCondition(String ide) {
		if(sortSqlCondition!=null&&!"".equals(sortSqlCondition.trim())&&!"desc".equals(sortSqlCondition.trim())){
			String sqlCondition="";
			for(String s:this.sortSqlCondition.split(",")){
				sqlCondition+=ide+"."+s+",";
			}
			this.sortSqlCondition = sqlCondition.substring(0,sqlCondition.length()-1);
		}else
			this.sortSqlCondition="";
	}

	public String getPageIndex() {
		return pageIndex;
	}

	public String getSortField() {
		return sortField;
	}

	public void setSortField(String sortField) {
		this.sortField = sortField;
		sortSqlCondition=(sortField==null?"":sortField.trim())+" "+(sortSqlCondition==null?"":sortSqlCondition);
	}

	public String getSortType() {
		return sortType;
	}

	public void setSortType(String sortType) {
		this.sortType = sortType;
		sortSqlCondition=(sortSqlCondition==null?"":sortSqlCondition)+" "+(sortType==null?"":("1".equals(sortType.trim())?"desc":""));
	}

	public void setPageIndex(String pageIndex) {
		this.pageIndex = pageIndex;
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public String getMemo1() {
		return memo1;
	}

	public void setMemo1(String memo1) {
		this.memo1 = memo1;
	}

	public String getMemo2() {
		return memo2;
	}

	public void setMemo2(String memo2) {
		this.memo2 = memo2;
	}

	public String getMemo3() {
		return memo3;
	}

	public void setMemo3(String memo3) {
		this.memo3 = memo3;
	}

	public void setObject(T object) {
		this.object = object;
	}

	public T getObject() {
		return object;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public void setObjParam(Object objParam) {
		this.objParam = objParam;
	}

	public Object getObjParam() {
		return objParam;
	}

	public String getMemo4() {
		return memo4;
	}

	public void setMemo4(String memo4) {
		this.memo4 = memo4;
	}

	public List<T> getObjectList() {
		return objectList;
	}

	public void setObjectList(List<T> objectList) {
		this.objectList = objectList;
	}


}
