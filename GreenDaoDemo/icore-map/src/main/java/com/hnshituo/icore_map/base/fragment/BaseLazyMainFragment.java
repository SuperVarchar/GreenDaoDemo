package com.hnshituo.icore_map.base.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * 懒加载
 * Created by YoKeyword on 16/6/5.
 */
public abstract class BaseLazyMainFragment extends BaseFragment {
    private boolean mInited = false;
    private Bundle mSavedInstanceState;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSavedInstanceState = savedInstanceState;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState == null) {
            if (!isHidden()) {
                mInited = true;
                initLazyView(null);
            }
        } else {
            if (!isHidden()) {
                mInited = true;
                initLazyView(savedInstanceState);
            }
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!mInited && !hidden) {
            mInited = true;
            initLazyView(mSavedInstanceState);
        }
    }

    /**
     * 懒加载
     */
    protected abstract void initLazyView(@Nullable Bundle savedInstanceState);

}
