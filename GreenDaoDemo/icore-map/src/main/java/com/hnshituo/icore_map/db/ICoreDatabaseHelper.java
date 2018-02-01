package com.hnshituo.icore_map.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


public class ICoreDatabaseHelper extends OrmLiteSqliteOpenHelper {
    private static String DB_NAME = "oa.db";
    private static int DB_VERSION = 2;

    protected ICoreDatabaseHelper(Context context, String name, int version) {
        super(context, name, null, version);
        DB_NAME = name;
        DB_VERSION = version;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {

            createDBTable(connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void createDBTable(ConnectionSource connectionSource) throws SQLException {

    }

    //可能存在数据丢失的问题
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {
        try {


            //TableUtils.dropTable(connectionSource, ScheduleInfo.class,true);
            updateDBTable(connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        onCreate(sqLiteDatabase, connectionSource);
    }

    protected void updateDBTable(ConnectionSource connectionSource) throws SQLException {

    }



    private Map<String, Dao> daos = new HashMap<>();

    /**
     * 获取dao
     *
     * @param clazz
     * @return
     * @throws SQLException
     */
    public synchronized Dao getDao(Class clazz) throws SQLException {
        Dao dao;
        String className = clazz.getSimpleName();
        if (daos.containsKey(className)) {
            dao = daos.get(className);
        } else {
            dao = super.getDao(clazz);
            daos.put(className, dao);
        }
        return dao;
    }

    //释放资源
    @Override
    public void close() {
        super.close();
        daos.clear();
    }

}
