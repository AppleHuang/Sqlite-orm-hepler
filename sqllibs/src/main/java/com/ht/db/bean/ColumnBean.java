package com.ht.db.bean;


/**
 * Created by huangtao on 16/9/5.
 */
public class ColumnBean {

    public boolean id;

    public String fileName;

    public String fileValue;

    public Class<?> type;

    public ColumnBean(boolean id, String fileName, String fileValue) {
        this.id = id;
        this.fileName = fileName;
        this.fileValue = fileValue;
    }

    public ColumnBean(String fileName, Class<?> type, boolean id) {
        this.fileName = fileName;
        this.type = type;
        this.id = id;
    }
}
