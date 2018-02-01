package com.hnshituo.icore_map.download.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.hnshituo.icore_map.R;
import com.hnshituo.icore_map.base.Constant;
import com.hnshituo.icore_map.base.fragment.BaseFragment;
import com.hnshituo.icore_map.download.activity.APKFileTestActivity;
import com.hnshituo.icore_map.download.activity.AllFileTestActivity;
import com.hnshituo.icore_map.download.activity.PicFileTestActivity;
import com.hnshituo.icore_map.download.activity.WordFileActivity;
import com.hnshituo.icore_map.download.activity.ZIPFileTestActivity;
import com.hnshituo.icore_map.download.adapter.FileTypeAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import okhttp3.Call;

/**
 * @author Wzh
 * @date 2016/7/26  17:06
 */
public class FirstFileTypeFragment extends BaseFragment{

    ListView fragmentFileTypeLv;

    @Override
    public View initRootView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_filetype, container, false);
    }

    @Override
    public void initData() {
        fragmentFileTypeLv = (ListView) rootView.findViewById(R.id.fragment_file_type_lv);
        List<String> fileTypeNames = new ArrayList<>();
        fileTypeNames.add("所有");
        fileTypeNames.add("图片");
        fileTypeNames.add("文档");
        fileTypeNames.add("压缩文件");
        fileTypeNames.add("APK");
        List<Integer> fileTypeIcons = new ArrayList<>();
        fileTypeIcons.add(R.drawable.xzgl_all);
        fileTypeIcons.add(R.drawable.xzgl_img);
        fileTypeIcons.add(R.drawable.xzgl_file);
        fileTypeIcons.add(R.drawable.xzgl_yasuo);
        fileTypeIcons.add(R.drawable.xzgl_apk);


        FileTypeAdapter fileTypeAdapter = new FileTypeAdapter(getActivity(), fileTypeNames, fileTypeIcons);
        fragmentFileTypeLv.setAdapter(fileTypeAdapter);
        fragmentFileTypeLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setDataAndType(Uri.fromFile(new File(Constant.DOWNLOAD_PATH)), "**/*//*");
                switch (position) {
                    case 0: //跳到所有文件
                        intent.setClass(getActivity(), AllFileTestActivity.class);
                        break;
                    case 1: //跳到图片
                        intent.setClass(getActivity(), PicFileTestActivity.class);
                        break;
                    case 2: //跳到文档
                        intent.setClass(getActivity(), WordFileActivity.class);
                        break;
                    case 3: //跳到压缩文件
                        intent.setClass(getActivity(), ZIPFileTestActivity.class);
                        break;
                    case 4: //跳到APK
                        intent.setClass(getActivity(), APKFileTestActivity.class);
                        break;
                    default:
                        break;
                }
                getActivity().startActivity(intent);
            }
        });
    }

    public static FirstFileTypeFragment newInstance() {
        FirstFileTypeFragment frg = new FirstFileTypeFragment();
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
}
