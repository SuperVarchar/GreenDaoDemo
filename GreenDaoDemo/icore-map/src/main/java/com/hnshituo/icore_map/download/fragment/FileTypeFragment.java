package com.hnshituo.icore_map.download.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hnshituo.icore_map.R;
import com.hnshituo.icore_map.base.fragment.BaseLazyMainFragment;

import okhttp3.Call;

/**
 * 文件分类Fragment
 *
 * @author Wzh
 * @date 2016/7/25  10:15
 */
public class FileTypeFragment extends BaseLazyMainFragment {


    @Override
    public View initRootView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_download_main, container, false);
    }

    @Override
    public void initData() {
    }

    public static FileTypeFragment newInstance() {
        FileTypeFragment frg = new FileTypeFragment();
        Bundle bundle = new Bundle();
        frg.setArguments(bundle);
        return frg;
    }

    @Override
    public void showSuccess(Call call) {

    }

    @Override
    public void reTry() {

    }

    @Override
    protected void initLazyView(@Nullable Bundle savedInstanceState) {
        if(savedInstanceState == null){
            loadRootFragment(R.id.communicate_frame,FirstFileTypeFragment.newInstance());
        }
    }
}
