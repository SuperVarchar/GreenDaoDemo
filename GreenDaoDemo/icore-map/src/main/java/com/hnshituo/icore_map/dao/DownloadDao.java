package com.hnshituo.icore_map.dao;

import android.content.Context;

import com.hnshituo.icore_map.base.bean.DownloadItem;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

/**
 * 下载记录数据库
 * @author Wzh
 * @date 2016/7/25  16:20
 */
public class DownloadDao extends BaseDao {

    public DownloadDao(Context context) {
        super(context);
        mClass=DownloadItem.class;
    }

    @Override
    public Dao getDao() {
        Dao dao=null;
        try {
            dao=dbServer.getDao(DownloadItem.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dao;
    }


}
