package com.hnshituo.greendaodemo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Administrator on 2018/2/1.
 */

public class MyOpenHelper extends DaoMaster.OpenHelper {

    public MyOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.e("版本变更","oldVersion"+oldVersion+"newVersion"+newVersion);
        MigrationHelper.migrate(db,UserDao.class);//数据版本变更才会执行
    }
}
