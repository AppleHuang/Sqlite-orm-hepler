package com.ht.utils;

import android.text.TextUtils;

import com.ht.db.bean.ColumnBean;

import java.util.List;

/**
 * Created by huangtao on 16/9/2.
 */
public class SQLFactory {

    public static String createTableConstruteSql(String tableName, List<ColumnBean> columnList) {
        StringBuilder sql = new StringBuilder();
        sql.append("CREATE TABLE IF NOT EXISTS ");
        sql.append(tableName);
        sql.append(" (");
        int size = columnList.size();
        if (columnList != null && size > 0){
            for (int i = 0; i < size; i++) {
                ColumnBean columnBean = columnList.get(i);
                if (columnBean.id){
                    sql.append("`"+columnBean.fileName+"` TEXT NOT NULL,");
                } else {
                    sql.append("`"+columnBean.fileName+"` TEXT,");
                }
            }
            sql.deleteCharAt(sql.length()-1);
        }
        sql.append(");");
        return logSql(sql);
    }

    public static String createInsertSql(String tableName, List<ColumnBean> columnList){
        StringBuilder sql = new StringBuilder("INSERT INTO ");
        sql.append(tableName + " (");
        StringBuilder columns = new StringBuilder();
        StringBuilder values = new StringBuilder();
        int size = columnList.size();
        for (int i = 0; i < size; i++) {
            ColumnBean columnBean = columnList.get(i);
            columns.append("`"+columnBean.fileName+"`,");
            values.append(columnBean.fileValue+",");
        }
        columns.deleteCharAt(columns.lastIndexOf(","));
        values.deleteCharAt(values.lastIndexOf(","));
        sql.append(columns);
        sql.append(") VALUES(");
        sql.append(values);
        sql.append(")");
        return logSql(sql);
    }

    public static String createQueryByConditionSql(String tableName, String condition){
        StringBuilder sql = new StringBuilder("SELECT * FROM ");
        sql.append(tableName);
        if (condition != null){
            sql.append(" WHERE "+condition);
        }
        return logSql(sql);
    }

    public static String createDeleteByConditionSql(String tableName, String condition) {
        StringBuilder sql = new StringBuilder("DELETE FROM ");
        sql.append(tableName);
        sql.append(" WHERE ").append(condition);
        return logSql(sql);
    }

    public static String createUpdateById(String tableName, List<ColumnBean> columnList) {
        StringBuilder sql = new StringBuilder("UPDATE ");
        sql.append(tableName + " SET ");
        int size = columnList.size();
        StringBuilder updateField = new StringBuilder();
        StringBuilder condition = new StringBuilder();
        for (int i = 0; i < size; i++) {
            ColumnBean columnBean = columnList.get(i);
            if (columnBean.id){
                if ("0".equals(columnBean.fileValue) || "".equals(columnBean.fileValue)){
                    throw new RuntimeException("id column don't hava value");
                }
                condition.append("`"+columnBean.fileName+"`  = "+ columnBean.fileValue);
            } else {
                updateField.append("`"+columnBean.fileName+"`  = "+ columnBean.fileValue+" ,");
            }
        }
        updateField.deleteCharAt(updateField.length()-1);
        if (!TextUtils.isEmpty(condition)){
            sql.append(updateField).append(" WHERE ").append(condition);
        } else {
            throw new RuntimeException("don't hava id field");
        }
        return logSql(sql);
    }

    public static String logSql(StringBuilder sqlStr){
        String sql = sqlStr.toString();
        LogUtils.error(sql);
        return sql;
    }
}