package com.hnshituo.icore_map.download.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.hnshituo.icore_map.R;
import com.hnshituo.icore_map.base.fragment.BaseFragment;
import com.hnshituo.icore_map.download.bean.DownloadEvent;
import com.hnshituo.icore_map.download.fragment.iview.IDownloadView;
import com.hnshituo.icore_map.download.presenter.DownloadPresenter;
import com.hnshituo.icore_map.view.dialog.StyleDialog;
import com.hnshituo.icore_map.view.view.AnimationButton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import okhttp3.Call;

/**
 * 正在下载界面
 * @author Wzh
 * @date 2016/7/26  17:03
 */
public class FirstDownloadFragment extends BaseFragment implements IDownloadView {

    ListView downloadFileLv;
    LinearLayout includeEmpty;
    AnimationButton emptyAdd;

    private DownloadPresenter presenter;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            presenter.updata();
            handler.sendEmptyMessageDelayed(0,300);
        }
    };


    public static FirstDownloadFragment newInstance() {
        FirstDownloadFragment frg = new FirstDownloadFragment();
        Bundle bundle = new Bundle();
        frg.setArguments(bundle);
        return frg;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);

    }

    @Override
    public View initRootView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_download, container, false);
    }

    @Override
    public void initData() {
        downloadFileLv = (ListView) rootView.findViewById(R.id.download_file_lv);
        includeEmpty = (LinearLayout) rootView.findViewById(R.id.include_empty);
        emptyAdd = (AnimationButton) rootView.findViewById(R.id.empty_add);
        presenter = new DownloadPresenter(this);
        presenter.fillData();
    }

    @Override
    public void showSuccess(Call call) {

    }

    @Override
    public void reTry() {

    }


    @Override
    public void showNotEmpty() {
        includeEmpty.setVisibility(View.GONE);
        downloadFileLv.setVisibility(View.VISIBLE);
    }

    @Override
    public void showEmpty() {
        includeEmpty.setVisibility(View.VISIBLE);
        emptyAdd.setVisibility(View.GONE);
        downloadFileLv.setVisibility(View.GONE);
    }

    @Override
    public void setAdapter(BaseAdapter adapter) {
        downloadFileLv.setAdapter(adapter);
        handler.sendEmptyMessageDelayed(0,300);
    }


    @Subscribe
    public void opeateDownload(final DownloadEvent event) {
        if(event.isDelete){
            StyleDialog.showWarnDialog(getContext(), "是否删除下载文件", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    presenter.removeDownload(getActivity(), event.index);
                }
            });
        }else {
            presenter.opeateDownload(getActivity(),event.index);
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
        EventBus.getDefault().unregister(this);
    }
}
