package com.hnshituo.icore_map.download.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.hnshituo.icore_map.R;
import com.hnshituo.icore_map.base.activity.ICoreBaseActivity;
import com.hnshituo.icore_map.download.adapter.FileListAdapter;
import com.hnshituo.icore_map.download.bean.LocationFile;
import com.hnshituo.icore_map.util.SimpleUtils;
import com.hnshituo.icore_map.view.view.AnimationButton;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;


/**
 * Created by Administrator on 2016/7/15.
 */
public class APKFileTestActivity extends ICoreBaseActivity {

    AnimationButton emptyAdd;
    LinearLayout includeEmpty;
    ListView picFileLv;
    EditText mProductSearch;
    ImageView searchIv;

    private String mDir;
    private List<LocationFile> mData;
    private FileListAdapter mMAdapter;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic_file);
        emptyAdd = (AnimationButton) findViewById(R.id.empty_add);
        includeEmpty = (LinearLayout) findViewById(R.id.include_empty);
        picFileLv = (ListView) findViewById(R.id.pic_file_lv);
        mProductSearch = (EditText) findViewById(R.id.search_et);
        searchIv = (ImageView) findViewById(R.id.search_iv);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);



        setBackButton();
        setTitleText(R.string.download02, null);


        mMAdapter = new FileListAdapter(this);

        Intent intent = this.getIntent();
        Uri uri = intent.getData();
        mDir = uri.getPath();
        mData = getData();
        if (mData.size() == 0) {
            showEmpty();
        } else {
            setAdapter();
        }

        mProductSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                refreshData(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mProductSearch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(mMAdapter.openLayout != null){
                    mMAdapter.openLayout.close();
                }
                return false;
            }
        });

        mProductSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(searchIv != null) {
                    if (hasFocus) {
                        searchIv.setVisibility(View.GONE);
                    } else {
                        searchIv.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

    }

    private void setAdapter() {
        mMAdapter.setData(mData);
        picFileLv.setAdapter(mMAdapter);
        picFileLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                File file = new File(mData.get(position).filePath);
                SimpleUtils.openFile(APKFileTestActivity.this, file);
            }
        });
    }

    private void showEmpty() {
        includeEmpty.setVisibility(TextView.VISIBLE);
        emptyAdd.setVisibility(View.GONE);
        picFileLv.setVisibility(View.GONE);
    }

    public List<LocationFile> getData() {
        List<LocationFile> list = new ArrayList<>();
        LocationFile locationFile;
        File f = new File(mDir);
        File[] files = f.listFiles();
        if (files != null) {
            for (File file : files) {
                if (checkIsAPKFiles(file.getPath()) && file.length() > 0) {
                    locationFile = new LocationFile();
                    locationFile.fileName = file.getName();
                    locationFile.filePath = file.getPath();
                    list.add(locationFile);
                }

            }
        }
        return list;
    }

    public void refreshData(CharSequence s) {
        List<LocationFile> list;
        if (TextUtils.isEmpty(s)) {
            list = mData;
        } else {
            list = new ArrayList<>();
            LocationFile locationFile;
            File f = new File(mDir);
            File[] files = f.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.getName().contains(s) && checkIsAPKFiles(file.getPath()) && file.length() > 0) {
                        locationFile = new LocationFile();
                        locationFile.fileName = file.getName();
                        locationFile.filePath = file.getPath();
                        list.add(locationFile);
                    }
                }
            }
        }

        if (list.size() == 0) {
            showEmpty();
        } else {
            includeEmpty.setVisibility(TextView.GONE);
            picFileLv.setVisibility(View.VISIBLE);
            if (mMAdapter != null) {
                mMAdapter.setData(list);
                mMAdapter.notifyDataSetChanged();
            }
        }
    }

    private boolean checkIsAPKFiles(String fName) {
        boolean isWordFile = false;
        String FileEnd = fName.substring(fName.lastIndexOf(".") + 1,
                fName.length()).toLowerCase();
        if (FileEnd.equals("apk")) {
            isWordFile = true;
        }
        return isWordFile;
    }


    @Override
    public void showSuccess(Call call) {

    }

    @Override
    public void reTry() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
