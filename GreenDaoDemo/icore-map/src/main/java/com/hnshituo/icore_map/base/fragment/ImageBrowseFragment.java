package com.hnshituo.icore_map.base.fragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hnshituo.icore_map.R;
import com.hnshituo.icore_map.base.Constant;
import com.hnshituo.icore_map.base.activity.ICoreBaseActivity;
import com.hnshituo.icore_map.base.adapter.ImageBrowseAdapter;
import com.hnshituo.icore_map.base.bean.ImageBrowseEvent;
import com.hnshituo.icore_map.base.upload.UploadInfo;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import okhttp3.Call;


/**
 * Created by Administrator on 2016/7/18.
 */
public class ImageBrowseFragment extends BaseFragment {

    ViewPager mImageVp;
    private ImageBrowseAdapter mImageBrowseAdapter;
    
    @Override
    public void showSuccess(Call call) {
    }

    @Override
    public void reTry() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public View initRootView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.activity_imge_browse, container, false);
    }

    @Override
    public void initData() {

        mImageVp = (ViewPager) rootView.findViewById(R.id.image_vp);


        Bundle arguments = getArguments();
        ArrayList<UploadInfo> uploadInfos = arguments.getParcelableArrayList("uploadInfos");
        int postion = arguments.getInt("position");
        int type = arguments.getInt("type");
        mImageBrowseAdapter = new ImageBrowseAdapter((ICoreBaseActivity) getActivity(), uploadInfos, type);
        mImageVp.setAdapter(mImageBrowseAdapter);
        mImageVp.setCurrentItem(postion);
    }

    public static ImageBrowseFragment newInstance(ArrayList<UploadInfo> uploadInfos, int postion, int type) {
        Bundle args = new Bundle();
        args.putParcelableArrayList("uploadInfos", uploadInfos);
        args.putInt("position", postion);
        args.putInt("type", type);
        ImageBrowseFragment fragment = new ImageBrowseFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public boolean onBackPressedSupport() {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("uploadInfos", mImageBrowseAdapter.getUpload());
        setFragmentResult(Constant.ResultCode.IMAGE_BORROW_FINISH, bundle);
        return super.onBackPressedSupport();
    }

    @Subscribe
    public void back(ImageBrowseEvent event){
        onBackPressedSupport();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
