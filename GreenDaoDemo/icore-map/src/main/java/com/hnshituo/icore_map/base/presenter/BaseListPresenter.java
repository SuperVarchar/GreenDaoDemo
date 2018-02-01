package com.hnshituo.icore_map.base.presenter;


import com.hnshituo.icore_map.base.iview.BaseListIView;
import com.hnshituo.icore_map.base.model.BaseListMode;

import java.util.List;

/**
 * 列表基类
 * Coder：wzh
 * Time:  2016/9/10 10:15
 * Email：274590753@qq.com
 */
public class BaseListPresenter<T> {

    protected BaseListIView<T> iView;
    protected BaseListMode<T> mode;
    private boolean isSetAdapter;

    public BaseListPresenter(BaseListIView<T> iView, BaseListMode<T> mode) {
        this.iView = iView;
        this.mode = mode;
    }

    public void fillData(List<T> response){
        if(response.size() == 0){
            iView.showEmpty();
        }else {
            iView.showNotEmpty();
            mode.setInfos(response);
            if(isSetAdapter){
                mode.getAdapter().replaceAll(response);
            }else {
                iView.setAdapter(mode.getAdapter());
                isSetAdapter = true;
            }
            iView.setListViewLoadMore(response.size());
        }
    }

    public void refresh(List<T> response) {
        mode.setInfos(response);
        if(response.size() == 0){
            iView.showEmpty();
        }else {
            iView.showNotEmpty();
            if(isSetAdapter){
                mode.getAdapter().replaceAll(response);
            }else {
                iView.setAdapter(mode.getAdapter());
                isSetAdapter = true;
            }
            iView.setListViewLoadMore(response.size());
        }
    }

    public void loadMore(List<T> response) {
        mode.getInfos().addAll(response);
        mode.getAdapter().addAll(response);
        iView.setListViewLoadMore(response.size());
    }

    public List<T> getInfos(){
        return mode.getInfos();
    }

    public void replaceAll(List<T> filterDateList){
        if (mode.getAdapter() != null) {
            mode.getAdapter().replaceAll(filterDateList);
        }

        if(filterDateList.size() == 0){
            iView.showEmpty();
        }else {
            iView.showNotEmpty();
        }
    }

    public void startFragment(int p){
        iView.startFragment(mode.getAdapter().getItem(p));
    }

    public void startFragment(T info){
        iView.startFragment(info);
    }

    public BaseListIView<T> getiView() {
        return iView;
    }

    public void remove(T t) {
        getInfos().remove(t);
        mode.getAdapter().notifyDataSetChanged();
    }
}
