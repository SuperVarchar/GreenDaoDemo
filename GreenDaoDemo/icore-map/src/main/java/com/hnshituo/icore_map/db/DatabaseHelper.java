package com.hnshituo.icore_map.db;

import android.content.Context;

import com.hnshituo.icore_map.base.bean.ComBaseInfo;
import com.hnshituo.icore_map.base.bean.DownloadItem;
import com.hnshituo.icore_map.base.bean.LoginUser;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;


/**
 * Description: 数据库工具类
 * Email: sleepend@foxmail.com
 * Created by Halcyon on 2016/7/20 0020.
 * YongXion Propprietary and Confidential.
 */
public class DatabaseHelper extends ICoreDatabaseHelper {
    private static final String DB_NAME = "mydb.db";
    private static final int DB_VERSION = 2;

    private DatabaseHelper(Context context) {
        super(context, DB_NAME, DB_VERSION);
    }


    @Override
    protected void createDBTable(ConnectionSource connectionSource) throws SQLException {
        super.createDBTable(connectionSource);
       /* //下载数据流量的表
        TableUtils.createTable(connectionSource, DownloadItem.class);
        TableUtils.createTable(connectionSource, LoginUser.class);
        //销售信息基础的表
        TableUtils.createTable(connectionSource, ComBaseInfo.class);*/
    }

    @Override
    protected void updateDBTable(ConnectionSource connectionSource) throws SQLException {
        super.updateDBTable(connectionSource);
        /*TableUtils.dropTable(connectionSource, DownloadItem.class, true);
        TableUtils.dropTable(connectionSource, ComBaseInfo.class, true);
        TableUtils.dropTable(connectionSource, LoginUser.class, true);*/
    }

    private static DatabaseHelper instance;

    /**
     * 获取数据库实例
     *
     * @param context
     * @return
     */
    public static synchronized DatabaseHelper getHelper(Context context) {
        context = context.getApplicationContext();
        if (instance == null) {
            synchronized (ICoreDatabaseHelper.class) {
                if (instance == null) {
                    instance = new DatabaseHelper(context);
                }
            }
        }
        return instance;
    }
}
