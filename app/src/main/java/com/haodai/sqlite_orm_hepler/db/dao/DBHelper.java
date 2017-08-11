package com.haodai.sqlite_orm_hepler.db.dao;

import android.content.Context;

import com.haodai.sqlite_orm_hepler.vo.Student;
import com.ht.db.core.BaseDBHelper;

/**
 * Created by huangtao on 16/9/5.
 */
public class DBHelper extends BaseDBHelper {
    private static final String DB_NAME = "ht.db";
    private static final int DB_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DB_NAME, DB_VERSION);
    }

    @Override
    protected Class[] getCreateTableClasses() {
        return new Class[]{Student.class};
    }

    @Override
    protected void updateDB(int oldVersion, int newVersion) {
    }

}
