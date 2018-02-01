package com.hnshituo.greendaodemo;

import android.app.Application;

import com.hnshituo.greendaodemo.DaoMaster.DevOpenHelper;
import com.hnshituo.icore_map.ICoreMapClient;

import org.greenrobot.greendao.database.Database;

/**
 * Created by Administrator on 2018/2/1.
 */

public class App extends Application {
    public static final boolean ENCRYPTED = true;
    public  static App instances;
    private  DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();

        DevOpenHelper helper = new DevOpenHelper(this, "notes-db");
        Database db =  helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
        instances = this;
        ICoreMapClient.getInstans().init(this);
    }

    public static App getInstances() {
        return instances;
    }
    public  DaoSession getDaoSession() {
        return daoSession;
    }
}
