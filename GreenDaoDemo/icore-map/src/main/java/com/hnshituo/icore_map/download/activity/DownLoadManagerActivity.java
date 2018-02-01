package com.hnshituo.icore_map.download.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;

import com.hnshituo.icore_map.R;
import com.hnshituo.icore_map.base.activity.ICoreBaseActivity;
import com.hnshituo.icore_map.download.fragment.DownloadFragment;
import com.hnshituo.icore_map.download.fragment.FileTypeFragment;

import java.util.ArrayList;

import okhttp3.Call;

public class DownLoadManagerActivity extends ICoreBaseActivity {

    private String[] titles = {"完成下载", "正在下载"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down_load_manager);

//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);


        setBackButton();
        setTitleText(R.string.download01,null);

        //初始化Fragment
        ArrayList<Fragment> fragments = new ArrayList<>();
        for (String title : titles) {
            if (title.equals("完成下载")) {
                fragments.add(FileTypeFragment.newInstance());
            }
            if (title.equals("正在下载")) {
                fragments.add(DownloadFragment.newInstance());
            }
        }

        //初始化ViewPager
        ViewPager viewPager = (ViewPager) findViewById(R.id.vp);
        if (viewPager != null) {
            viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager(), fragments, titles));
        }

        //
        TabLayout tabLayout = (TabLayout) findViewById(R.id.activity_main_tabLayout);
        if (tabLayout != null) {
            tabLayout.setTabTextColors(getResources().getColor(R.color.color_999999), getResources().getColor(R.color.color_4987f4));
            tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.color_4987f4));
            tabLayout.setTabMode(TabLayout.MODE_FIXED);
            tabLayout.setupWithViewPager(viewPager);
        }

    }

    @Override
    public void showSuccess(Call call) {

    }

    @Override
    public void reTry() {

    }


    class PagerAdapter extends FragmentPagerAdapter {

        private ArrayList<Fragment> mFragments = new ArrayList<>();
        private String[] mTitles;

        public PagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments, String[] titles) {
            super(fm);
            mFragments = fragments;
            mTitles = titles;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
