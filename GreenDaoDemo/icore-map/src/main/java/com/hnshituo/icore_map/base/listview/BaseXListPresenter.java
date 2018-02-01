package com.hnshituo.icore_map.base.listview;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

import com.hnshituo.icore_map.ICoreMapClient;
import com.hnshituo.icore_map.R;
import com.hnshituo.icore_map.base.listview.search.DataProcessUtil;
import com.hnshituo.icore_map.view.listview.XListView;
import com.joanzapata.android.QuickAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * 带上拉刷新下拉加载的列表基类
 * Coder：wzh
 * Time:  2016/9/10 10:15
 * Email：274590753@qq.com
 */
public abstract class BaseXListPresenter<T> {

    public BaseXListIView<T> iView;
    public BaseXListMode<T> mode;
    private XListView mXListView;
    private boolean isSetAdapter = false;
    public int pageSize = 15;
    private boolean isAddHead = false;
    private int headNum = 0;
    private String filterStr;

    private String[] searchCondition;

    /**
     * 带搜索的列表
     *
     * @param listView
     * @param search
     * @param iView
     * @param mode
     */
    public BaseXListPresenter(XListView listView, EditText search, String[] searchCondition, BaseXListIView<T> iView, BaseXListMode<T> mode) {
        this.iView = iView;
        this.mode = mode;
        this.mXListView = listView;
        this.searchCondition = searchCondition;
//        if (searchCondition != null&&searchCondition.length >0) {
//            System.arraycopy(searchCondition,0,this.searchCondition,0,searchCondition.length);
//        }
        if (search != null) {
            search.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    filterData(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }
    }

    /**
     * 不带搜素功能
     *
     * @param listView
     * @param iView
     * @param mode
     */
    public BaseXListPresenter(XListView listView, BaseXListIView<T> iView, BaseXListMode<T> mode) {
        this.iView = iView;
        this.mode = mode;
        this.mXListView = listView;
    }


    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    private void setAdapter(QuickAdapter<T> adapter) {

        if (mXListView != null) {
            mXListView.setPullRefreshEnable(true);
            mXListView.setAdapter(adapter);
            mXListView.setXListViewListener(new XListView.IXListViewListener() {
                @Override
                public void onRefresh() {
                    refreshData(mode.getInfos().size());
                    mXListView.onLoad();
                }

                @Override
                public void onLoadMore() {
                    loadMoreData();
                    mXListView.onLoad();
                }
            });

            mXListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    int p = -1;
//                    if (isAddHead) {
//                        if (position > 1) {
//                            p = position - 2;
//                        }
//                    } else {
//                        if (position > 0) {
//                            p = position - 1;
//                        }
//                    }
                    if (position > 0) {
                        p = position - headNum - 1;
                    }


                    startFragment(p);
                }
            });
        }
    }

    /**
     * 给ListView 添加头布局
     *
     * @param string
     */
    public void setHeadView(String string) {
        if (mXListView != null) {
            if (TextUtils.isEmpty(string)) {
                View view = View.inflate(ICoreMapClient.application, R.layout.item_head_not_text, null);
                mXListView.addHeaderView(view);
            } else {
                View view = View.inflate(ICoreMapClient.application, R.layout.item_head_text, null);
                ((TextView) view.findViewById(R.id.product_black)).setText(string);
                mXListView.addHeaderView(view);
            }
            headNum++;
        }


    }


    /**
     * 给ListView 添加头布局
     */
    public void addHeadView(View view) {
        if (mXListView != null) {
            mXListView.addHeaderView(view);
            headNum++;
        }
    }


    /**
     * 下拉加载更多
     */
    public abstract void loadMoreData();

    /**
     * 下拉刷新
     */
    public abstract void refreshData(int size);

    /**
     * 填充数据
     */
    public abstract void fillData();

    /**
     * 筛选数据
     */
    public List<T> filter(String filterStr, String[] filterCondition) {
        DataProcessUtil<T> dataProcessUtil = new DataProcessUtil<>();
        return dataProcessUtil.filterData(getInfos(), filterCondition, filterStr);
    }

    /**
     * 替换所有的数据
     */
    public void replaceAll(List<T> filterDateList) {
        if (mode.getAdapter() != null) {
            mode.getAdapter().replaceAll(filterDateList);
        }

        if (filterDateList.size() == 0) {
            iView.showEmpty();
        } else {
            iView.showNotEmpty();
            setListViewLoadMore(filterDateList.size());
        }

    }

    public void fillData(List<T> response) {
        mode.setInfos(response);
        if (response.size() == 0) {
            iView.showEmpty();
        } else {
            iView.showNotEmpty();
            mode.setInfos(response);
            if (isSetAdapter) {
                mode.getAdapter().replaceAll(response);
            } else {
                setAdapter(mode.getAdapter());
                isSetAdapter = true;
            }
            setListViewLoadMore(response.size());
        }
        filterData(filterStr);


    }

    private void setListViewLoadMore(int size) {
        if (mXListView != null) {
            if (size < pageSize) {
                mXListView.setPullLoadEnable(false);
            } else {
                mXListView.setPullLoadEnable(true);
            }
        }
    }

    public void refresh(List<T> response) {
        mode.setInfos(response);
        if (response.size() == 0) {
            iView.showEmpty();
        } else {
            iView.showNotEmpty();
            if (isSetAdapter) {
                mode.getAdapter().replaceAll(response);
            } else {
                setAdapter(mode.getAdapter());
                isSetAdapter = true;
            }
            setListViewLoadMore(response.size());
        }
        filterData(filterStr);
    }

    public void loadMore(List<T> response) {
        mode.getInfos().addAll(response);
        filterData(filterStr);
    }

    public List<T> getInfos() {
        return mode.getInfos();
    }

    public void startFragment(int p) {
        iView.startFragment(mode.getAdapter().getItem(p));
    }

    public BaseXListIView<T> getiView() {
        return iView;
    }

    /**
     * 筛选数据
     */
    public void filterData(String filterStr) {
        this.filterStr = filterStr;
        if (getInfos() == null) {
            return;
        }
        List<T> filterDateList = new ArrayList<>();

        if (TextUtils.isEmpty(filterStr)) {
            filterDateList = getInfos();
        } else {
            if (searchCondition != null) {
                filterDateList = filter(filterStr, searchCondition);
            }
        }
        replaceAll(filterDateList);
    }

    /**
     * 排序的方法
     *
     * @param sortFildName
     * @param orderBy
     */
    public void sort(String sortFildName, int orderBy) {
        DataProcessUtil<T> dataProcessUtil = new DataProcessUtil<T>();
        mode.getAdapter().replaceAll(dataProcessUtil.dataSort(mode.getInfos(), sortFildName, orderBy));
        mode.getAdapter().notifyDataSetChanged();
    }


}

