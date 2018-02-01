package com.hnshituo.icore_map.base.listview;

import com.joanzapata.android.BaseAdapterHelper;
import com.joanzapata.android.QuickAdapter;

import java.util.List;

/**
 * 列表模型基类
 * Coder：wzh
 * Time:  2016/9/10 09:08
 * Email：274590753@qq.com
 */
public abstract class BaseXListMode<T> {

    protected List<T> infos;

    protected QuickAdapter<T> adapter;

    public QuickAdapter<T> getAdapter(){
        if(adapter == null){
            adapter = newAdapter(infos);
        }
        return adapter;
    }

    public abstract QuickAdapter<T> newAdapter(List<T> infos);

    public List<T> getInfos() {
        return infos;
    }

    public void setInfos(List<T> infos) {
        this.infos = infos;
    }

    public void opeaterLine(BaseAdapterHelper helper, T info){
//        if(getInfos().size() == 1 || getInfos().indexOf(info) == getInfos().size() - 1){
//            helper.setVisible(R.id.all_bottom_line, true);
//            helper.setVisible(R.id.not_all_bottom_line, false);
//        }else {
//            helper.setVisible(R.id.all_bottom_line, false);
//            helper.setVisible(R.id.not_all_bottom_line, true);
//
//        }
    }
}
