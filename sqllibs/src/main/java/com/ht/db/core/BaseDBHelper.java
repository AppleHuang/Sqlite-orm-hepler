package com.ht.db.core;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by huangtao on 16/9/2.
 */
public abstract class BaseDBHelper extends SQLiteOpenHelper {

    public BaseDBHelper(Context context, String dbName, int dbVersion) {
        super(context, dbName, null, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        DBUtils.createTables(db, getCreateTableClasses());
    }

    protected abstract Class[] getCreateTableClasses();

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateDB(oldVersion, newVersion);
    }

    protected abstract void updateDB(int oldVersion, int newVersion);
}
