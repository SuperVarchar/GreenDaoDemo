package com.hnshituo.icore_map.base.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * 自定义BaseAdapter
 * @author user
 *
 * @param <T>
 */
public abstract class CustomBaseAdapter<T> extends BaseAdapter {
	private List<T> mDatas;
	public Context mContext;
	public LayoutInflater mInflater;
	public CustomBaseAdapter(Context context) {
		mContext = context;
		mInflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}
	/**
	 * 获取指定item数据
	 * 
	 * @param position
	 * @return
	 */
	public T getItemData(int position) {
		return mDatas == null ? null : mDatas.get(position);
	}

	/**
	 * 获取数据集合
	 * 
	 * @return
	 */
	public List<T> getData() {
		return mDatas;
	}

	/**
	 * 设置数据集合
	 * 
	 * @param datas
	 */
	public void setData(List<T> datas) {
		this.mDatas = datas;
	}

	/**
	 * 移除指定item的数据
	 * @param position
	 */
	public void removeData(int position){
		this.mDatas.remove(position);
	}

	@Override
	public int getCount() {
		return mDatas == null ? 0 : mDatas.size();
	}

	@Override
	public Object getItem(int position) {
		return mDatas == null ? null : mDatas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}


}
