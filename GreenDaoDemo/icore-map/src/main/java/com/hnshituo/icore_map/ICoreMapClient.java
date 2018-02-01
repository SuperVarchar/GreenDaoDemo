package com.hnshituo.icore_map;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.hnshituo.icore_map.base.bean.LoginUser;
import com.hnshituo.icore_map.fragmention.SupportFragment;
import com.hnshituo.icore_map.okhttp.OkHttpUtils;
import com.hnshituo.icore_map.okhttp.cookie.CookieJarImpl;
import com.hnshituo.icore_map.okhttp.cookie.store.MemoryCookieStore;
import com.hnshituo.icore_map.okhttp.log.LoggerInterceptor;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.mail.Store;

import okhttp3.OkHttpClient;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Administrator on 2017/6/27.
 */

public class ICoreMapClient {
    private volatile static ICoreMapClient instans;
    public static SharedPreferences SP;
    public static Application application;
    public static LoginUser loginUser;

    public  static  String  storageName;
    public  static  String  storageNo;
    /**
     * 邮箱内容
     */
    private Store store;
    private ArrayList<InputStream> attachmentsInputStreams;

    public Store getStore() {
        return store;
    }
    public void setStore(Store store) {
        this.store = store;
    }

    public ArrayList<InputStream> getAttachmentsInputStreams() {
        return attachmentsInputStreams;
    }

    public void setAttachmentsInputStreams(ArrayList<InputStream> attachmentsInputStreams) {
        this.attachmentsInputStreams = attachmentsInputStreams;
    }

    public void init(Application context) {
        SP = context.getSharedPreferences("config", MODE_PRIVATE);
        this.application = context;
        initNetWork();
    }

    public static ICoreMapClient getInstans() {
        if (instans == null) {
            synchronized (ICoreMapClient.class) {
                if (instans == null) {
                    instans = new ICoreMapClient();
                }
            }
        }
        return new ICoreMapClient();
    }

    public static void setLoginUser(LoginUser user){
        loginUser = user;
        Gson gson = new Gson();
        SP.edit().putString("user",gson.toJson(user)).apply();
    }

    public static LoginUser getLoginUser(){
        if(loginUser == null){
            Gson gson = new Gson();
            loginUser = gson.fromJson(SP.getString("user",""),LoginUser.class);
        }
        return loginUser;
    }


    /**
     * 初始化OkHttp
     *
     * @author Wzh
     * @date 2016/7/1 13:02
     */
    private void initNetWork() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60,TimeUnit.SECONDS)
                .addInterceptor(new LoggerInterceptor("http",true))
                .cookieJar(new CookieJarImpl(new MemoryCookieStore()))
                .build();
        OkHttpUtils.initClient(okHttpClient);
    }

    ArrayList<Activity> list = new ArrayList<>();


    /**
     * Activity关闭时，删除Activity列表中的Activity对象
     */
    public void removeActivity(Activity a) {
        list.remove(a);
    }

    /**
     * 向Activity列表中添加Activity对象
     */
    public void addActivity(Activity a) {
        list.add(a);
    }

    /**
     * 关闭Activity列表中的所有Activity
     */
    public void finishActivity() {
        for (Activity activity : list) {
            if (null != activity) {
                activity.finish();
            }
        }
    }

    public void finishProgram() {
        android.os.Process.killProcess(android.os.Process.myPid());
    }


    public static String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager.getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }

    private SupportFragment mFragment;

    public SupportFragment getFragment() {
        return mFragment;
    }

    public void setFragment(SupportFragment fragment) {
        mFragment = fragment;
    }
}
