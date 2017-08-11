package com.ht.db.core;

import android.app.Application;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ht.db.bean.ColumnBean;
import com.ht.utils.AnnomationUtils;
import com.ht.utils.SQLFactory;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;


/**
 * Created by huangtao on 16/9/2.
 */
public final class DBUtils  {
    private static DBUtils mInstance;
    private SQLiteDatabase mDatabase;
    private BaseDBHelper mDbHelper;

    public static String DB_HELPER_CLASS_NAME;
    public static Application APPLICATION_CONTEXT;

    private DBUtils(){
        if (DB_HELPER_CLASS_NAME == null || APPLICATION_CONTEXT == null){
            throw new RuntimeException("please invoke DBUtils init(application, DBHelperClassName)");
        }

        try {
            Class clazz = Class.forName(DB_HELPER_CLASS_NAME);
            Constructor constructor = clazz.getConstructor(Context.class);
            mDbHelper = (BaseDBHelper) constructor.newInstance(APPLICATION_CONTEXT);
            mDatabase = mDbHelper.getWritableDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void init(Application application, String dbHelperClassName) {
        DB_HELPER_CLASS_NAME = dbHelperClassName;
        APPLICATION_CONTEXT = application;
    }

    public static final DBUtils getInstance(){
        if (mInstance == null){
            synchronized (DBUtils.class){
                if (mInstance == null){
                    mInstance = new DBUtils();
                }
            }
        }
        return mInstance;
    }

    public <T>boolean save(T t){
        try {
            Class<?> clazz = t.getClass();
            String tableName = AnnomationUtils.getTableName(clazz);
            List<ColumnBean> columnList = AnnomationUtils.getColumnNameAndValueList(clazz, t);
            String sql = SQLFactory.createInsertSql(tableName, columnList);
            mDatabase.execSQL(sql);
            return true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public Object findById(Class clazz, Serializable id){
        String idName = AnnomationUtils.getIdName(clazz);
        if (idName == null){
            throw new RuntimeException("the class "+AnnomationUtils.getTableName(clazz)+" doesn't hava id column");
        }
        return  findByCondition(clazz, idName+"="+id);
    }

    public Object findByCondition(Class clazz, String condition){
        Cursor cursor = null;
        try {
            Object obj = clazz.newInstance();

            String tableName = AnnomationUtils.getTableName(clazz);
            String sql = SQLFactory.createQueryByConditionSql(tableName, condition);

            cursor = mDatabase.rawQuery(sql, null);
            if (cursor.getCount() > 0){
                if (cursor.moveToNext()){
                    List<ColumnBean> columnList = AnnomationUtils.getColumnNameAndTypeList(clazz);
                    int size = columnList.size();
                    for (int i = 0; i < size; i++) {
                        ColumnBean columnBean = columnList.get(i);
                        String fieldName = columnBean.fileName;
                        Class<?> type = columnBean.type;
                        Field field = clazz.getField(fieldName);
                        Object value;
                        if (type == String.class){
                            value = cursor.getString(cursor.getColumnIndex(fieldName));
                        }else if (type == int.class || type == Integer.class) {
                            value = cursor.getInt(cursor.getColumnIndex(fieldName));
                        } else if (type == long.class || type == Long.class){
                            value = cursor.getLong(cursor.getColumnIndex(fieldName));
                        } else if (type == boolean.class || type == Boolean.class){
                            value = cursor.getShort(cursor.getColumnIndex(fieldName)) == 1? true : false;
                        } else if (type == float.class || type == Float.class){
                            value = cursor.getFloat(cursor.getColumnIndex(fieldName));
                        } else if (type == double.class || type == Double.class){
                            value = cursor.getDouble(cursor.getColumnIndex(fieldName));
                        } else if (type == short.class || type == Short.class){
                            value = cursor.getShort(cursor.getColumnIndex(fieldName));
                        } else if (type == byte.class || type == Byte.class){
                            value = cursor.getShort(cursor.getColumnIndex(fieldName));
                        } else if (type == char.class || type == Character.class){
                            value = (char)cursor.getShort(cursor.getColumnIndex(fieldName));
                        } else {
                            throw new RuntimeException("can't identification type:"+type);
                        }
                        field.set(obj, value);
                    }
                }

            } else {
                return null;
            }
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null){
                cursor.close();
            }
        }

        return null;
    }
    public boolean deleteById(Class clazz, Serializable id){
        String idName = AnnomationUtils.getIdName(clazz);
        if (idName == null){
            throw new RuntimeException("the class "+AnnomationUtils.getTableName(clazz)+" doesn't hava id column");
        }
        return deleteByCondition(clazz, idName+"="+id);
    }

    public boolean deleteByCondition(Class clazz, String condition){
        try {
            String tableName = AnnomationUtils.getTableName(clazz);
            String sql = SQLFactory.createDeleteByConditionSql(tableName, condition);
            mDatabase.execSQL(sql);
            return true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public <T>boolean update(T t){
        try {
            Class<?> clazz = t.getClass();
            String tableName = AnnomationUtils.getTableName(clazz);
            List<ColumnBean> columnList = AnnomationUtils.getColumnNameAndValueList(clazz, t);
            String sql = SQLFactory.createUpdateById(tableName, columnList);
            mDatabase.execSQL(sql);
            return true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 这个方法在BaseHelper里不能用DBUtils.getInstance()获取,会导致死锁
     * @param db
     * @param clazz
     */
    public static void createTable(SQLiteDatabase db, Class clazz){
        String tableName = AnnomationUtils.getTableName(clazz);
        List<ColumnBean> columnList = AnnomationUtils.getColumnNameAndTypeList(clazz);
        int size = columnList.size();
        if (size == 0){
            throw new RuntimeException("the "+tableName+" must has one column");
        }
        String sql = SQLFactory.createTableConstruteSql(tableName, columnList);
        db.execSQL(sql);
    }

    public static void createTables(SQLiteDatabase db, Class ... clazzs){
        int length = clazzs.length;
        for (int i = 0; i < length; i++) {
            createTable(db, clazzs[i]);
        }
    }
}
