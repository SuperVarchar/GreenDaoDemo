package com.hnshituo.icore_map.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.hnshituo.icore_map.db.DatabaseHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.DatabaseConnection;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public abstract class BaseDao<T> {
    public DatabaseHelper dbServer;
    public Class mClass = null;

    public BaseDao(Context context) {
        if(context == null) {
            throw new NullPointerException("Context can\'t be null!");
        } else {
            context = context.getApplicationContext();
            this.dbServer = DatabaseHelper.getHelper(context);
        }
    }

    public abstract Dao getDao();

    public boolean saveObj(Object object) {
        DatabaseConnection databaseConnection = null;
        try {
            databaseConnection = this.getDao().startThreadConnection();
            this.getDao().setAutoCommit(databaseConnection, false);
            this.getDao().create(object);
            this.getDao().commit(databaseConnection);
            return true;
        } catch (Exception var15) {
            try {
                this.getDao().rollBack(databaseConnection);
            } catch (SQLException var14) {
                var14.printStackTrace();
            }

            var15.printStackTrace();
            return false;
        } finally {
            try {
                this.getDao().endThreadConnection(databaseConnection);
            } catch (SQLException var13) {
                var13.printStackTrace();
            }

        }

    }

    public boolean saveListObj(List<T> lists) {
        DatabaseConnection databaseConnection = null;

        try {
            databaseConnection = this.getDao().startThreadConnection();
            this.getDao().setAutoCommit(databaseConnection, false);
            if(lists != null) {
                Iterator e = lists.iterator();

                while(e.hasNext()) {
                    Object e1 = e.next();
                    this.getDao().create(e1);
                }
            }

            this.getDao().commit(databaseConnection);
            return true;
        } catch (SQLException var15) {
            try {
                this.getDao().rollBack(databaseConnection);
            } catch (SQLException var14) {
                var14.printStackTrace();
            }
            var15.printStackTrace();
            return false;
        } finally {
            try {
                this.getDao().endThreadConnection(databaseConnection);
            } catch (SQLException var13) {
                var13.printStackTrace();
            }

        }

    }
    public void deleteObjByObj(Object object) {
        DatabaseConnection databaseConnection = null;

        try {
            databaseConnection = this.getDao().startThreadConnection();
            this.getDao().setAutoCommit(databaseConnection, false);
           this.getDao().delete(object);
            this.getDao().commit(databaseConnection);
        } catch (SQLException var15) {
            try {
                this.getDao().rollBack(databaseConnection);
            } catch (SQLException var14) {
                var14.printStackTrace();
            }

            var15.printStackTrace();
        } finally {
            try {
                this.getDao().endThreadConnection(databaseConnection);
            } catch (SQLException var13) {
                var13.printStackTrace();
            }
        }
    }
    public Object deleteObjByField(String FieldName, Object value) {
        List list = this.queryObjByField(FieldName, value);
        DatabaseConnection databaseConnection = null;
        try {
            databaseConnection = this.getDao().startThreadConnection();
            this.getDao().setAutoCommit(databaseConnection,false);
            this.getDao().delete(list);
            this.getDao().commit(databaseConnection);
        } catch (SQLException var17) {
            try {
                this.getDao().rollBack(databaseConnection);
            } catch (SQLException var16) {
                var16.printStackTrace();
            }
            var17.printStackTrace();
        } finally {
            try {
                this.getDao().endThreadConnection(databaseConnection);
            } catch (SQLException var15) {
                var15.printStackTrace();
            }

        }

        return null;
    }

    public void deleteObjById(Object id) {
        DatabaseConnection databaseConnection = null;
        try {
            databaseConnection = this.getDao().startThreadConnection();
            this.getDao().setAutoCommit(databaseConnection, false);
            this.getDao().deleteById(id);
            this.getDao().commit(databaseConnection);
        } catch (SQLException var15) {
            try {
                this.getDao().rollBack(databaseConnection);
            } catch (SQLException var14) {
                var14.printStackTrace();
            }
            var15.printStackTrace();
        } finally {
            try {
                this.getDao().endThreadConnection(databaseConnection);
            } catch (SQLException var13) {
                var13.printStackTrace();
            }

        }

    }

    public void deleteObjByList(Collection collection) {
        DatabaseConnection databaseConnection = null;

        try {
            databaseConnection = this.getDao().startThreadConnection();
            this.getDao().setAutoCommit(databaseConnection, false);
            this.getDao().delete(collection);
            this.getDao().commit(databaseConnection);
        } catch (SQLException var15) {
            try {
                this.getDao().rollBack(databaseConnection);
            } catch (SQLException var14) {
                var14.printStackTrace();
            }

            var15.printStackTrace();
        } finally {
            try {
                this.getDao().endThreadConnection(databaseConnection);
            } catch (SQLException var13) {
                var13.printStackTrace();
            }

        }

    }

    public void updateObj(Object object) {
        DatabaseConnection databaseConnection = null;

        try {
            databaseConnection = this.getDao().startThreadConnection();
            this.getDao().setAutoCommit(databaseConnection, false);
            this.getDao().update(object);
            this.getDao().commit(databaseConnection);
        } catch (SQLException var15) {
            try {
                this.getDao().rollBack(databaseConnection);
            } catch (SQLException var14) {
                var14.printStackTrace();
            }

            var15.printStackTrace();
        } finally {
            try {
                this.getDao().endThreadConnection(databaseConnection);
            } catch (SQLException var13) {
                var13.printStackTrace();
            }

        }

    }

    public void updateObjByID(Object object, Object ID) {
        DatabaseConnection databaseConnection = null;

        try {
            databaseConnection = this.getDao().startThreadConnection();
            this.getDao().setAutoCommit(databaseConnection, false);
            this.getDao().updateId(object, ID);
            this.getDao().commit(databaseConnection);
        } catch (SQLException var16) {
            try {
                this.getDao().rollBack(databaseConnection);
            } catch (SQLException var15) {
                var15.printStackTrace();
            }

            var16.printStackTrace();
        } finally {
            try {
                this.getDao().endThreadConnection(databaseConnection);
            } catch (SQLException var14) {
                var14.printStackTrace();
            }

        }

    }

    public T queryObjById(Object id) {
        T t = null;
        DatabaseConnection databaseConnection = null;

        try {
            databaseConnection = this.getDao().startThreadConnection();
            this.getDao().setAutoCommit(databaseConnection, false);
            t = (T) this.getDao().queryForId(id);
            this.getDao().commit(databaseConnection);
        } catch (SQLException var16) {
            try {
                this.getDao().rollBack(databaseConnection);
            } catch (SQLException var15) {
                var15.printStackTrace();
            }

            var16.printStackTrace();
        } finally {
            try {
                this.getDao().endThreadConnection(databaseConnection);
            } catch (SQLException var14) {
                var14.printStackTrace();
            }

        }
        return t;
    }

    public List<T> queryAllObj() {
        Object lists = new ArrayList();
        DatabaseConnection databaseConnection = null;
        try {
            databaseConnection = this.getDao().startThreadConnection();
            this.getDao().setAutoCommit(databaseConnection, false);
            lists = this.getDao().queryForAll();
            this.getDao().commit(databaseConnection);
        } catch (SQLException var15) {
            try {
                this.getDao().rollBack(databaseConnection);
            } catch (SQLException var14) {
                var14.printStackTrace();
            }

            var15.printStackTrace();
        } finally {
            try {
                this.getDao().endThreadConnection(databaseConnection);
            } catch (SQLException var13) {
                var13.printStackTrace();
            }

        }

        return (List)lists;
    }

    public List<T> queryObjByField(String FieldName, Object Value) {
        Object lists = new ArrayList();
        DatabaseConnection databaseConnection = null;
        try {
            databaseConnection = this.getDao().startThreadConnection();
            this.getDao().setAutoCommit(databaseConnection, false);
            lists = this.getDao().queryForEq(FieldName, Value);
            this.getDao().commit(databaseConnection);
        } catch (SQLException var17) {
            try {
                this.getDao().rollBack(databaseConnection);
            } catch (SQLException var16) {
                var16.printStackTrace();
            }

            var17.printStackTrace();
        } finally {
            try {
                this.getDao().endThreadConnection(databaseConnection);
            } catch (SQLException var15) {
                var15.printStackTrace();
            }

        }
        return (List)lists;
    }
    //多字段查询
    public List<T> queryObjByField(String FieldName, Object Value,String FieldName1, Object Value1) {
        Object lists = new ArrayList();
        DatabaseConnection databaseConnection = null;
        try {
            databaseConnection = this.getDao().startThreadConnection();
            this.getDao().setAutoCommit(databaseConnection, false);
            lists = this.getDao().queryBuilder().where().eq(FieldName,Value).and().eq(FieldName1,Value1).query();
            this.getDao().commit(databaseConnection);
        } catch (SQLException var17) {
            try {
                this.getDao().rollBack(databaseConnection);
            } catch (SQLException var16) {
                var16.printStackTrace();
            }
            var17.printStackTrace();
        } finally {
            try {
                this.getDao().endThreadConnection(databaseConnection);
            } catch (SQLException var15) {
                var15.printStackTrace();
            }
        }
        return (List)lists;
    }

    public T queryObjByFieldOnlyOne(String FieldName, Object Value) {
        ArrayList<T> lists = new ArrayList();
        DatabaseConnection databaseConnection = null;

        try {
            databaseConnection = this.getDao().startThreadConnection();
            this.getDao().setAutoCommit(databaseConnection, false);
            lists = (ArrayList<T>) this.getDao().queryForEq(FieldName, Value);
            this.getDao().commit(databaseConnection);
        } catch (SQLException var17) {
            try {
                this.getDao().rollBack(databaseConnection);
            } catch (SQLException var16) {
                var16.printStackTrace();
            }
            var17.printStackTrace();
        } finally {
            try {
                this.getDao().endThreadConnection(databaseConnection);
            } catch (SQLException var15) {
                var15.printStackTrace();
            }
        }

        if(lists.size() > 0){
            return lists.get(0);
        }else {
            return null;
        }

    }

    public boolean tableIsExist() {
        boolean isExist = false;

        try {
            isExist = this.getDao().isTableExists();
        } catch (SQLException var3) {
            var3.printStackTrace();
        }

        return isExist;
    }

    public void deleteTable() {
        String tablename = "";
        if(this.mClass != null) {
            tablename = DatabaseTableConfig.extractTableName(this.mClass);
        }

        SQLiteDatabase db = this.dbServer.getWritableDatabase();
        String sql = "delete from " + tablename;
        db.execSQL(sql);
    }

    public List<T> queryObjByNotField(String FieldName, Object Value) {
        Object lists = new ArrayList();
        DatabaseConnection databaseConnection = null;

        try {
            databaseConnection = this.getDao().startThreadConnection();
            this.getDao().setAutoCommit(databaseConnection, false);
            lists = this.getDao().queryBuilder().where().ne(FieldName, Value).query();
            this.getDao().commit(databaseConnection);
        } catch (SQLException var17) {
            try {
                this.getDao().rollBack(databaseConnection);
            } catch (SQLException var16) {
                var16.printStackTrace();
            }

            var17.printStackTrace();
        } finally {
            try {
                this.getDao().endThreadConnection(databaseConnection);
            } catch (SQLException var15) {
                var15.printStackTrace();
            }

        }

        return (List)lists;
    }

    public List<T> queryObjFieldAllType(String FieldName) {
        Object lists = new ArrayList();
        DatabaseConnection databaseConnection = null;

        try {
            databaseConnection = this.getDao().startThreadConnection();
            this.getDao().setAutoCommit(databaseConnection, false);
            lists = this.getDao().queryBuilder().selectColumns(new String[]{FieldName}).distinct().query();
            this.getDao().commit(databaseConnection);
        } catch (SQLException var16) {
            try {
                this.getDao().rollBack(databaseConnection);
            } catch (SQLException var15) {
                var15.printStackTrace();
            }

            var16.printStackTrace();
        } finally {
            try {
                this.getDao().endThreadConnection(databaseConnection);
            } catch (SQLException var14) {
                var14.printStackTrace();
            }

        }

        return (List)lists;
    }
}
