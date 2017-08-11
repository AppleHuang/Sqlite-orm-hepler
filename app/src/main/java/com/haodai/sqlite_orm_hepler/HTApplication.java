package com.haodai.sqlite_orm_hepler;

import android.app.Application;

import com.ht.db.core.DBUtils;
import com.ht.utils.LogUtils;

/**
 * Created by huangtao on 16/9/5.
 */
public class HTApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.error(getPackageName());
        DBUtils.init(this, getPackageName()+".db.dao.DBHelper");
    }
}
