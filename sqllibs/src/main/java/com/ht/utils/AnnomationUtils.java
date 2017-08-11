package com.ht.utils;


import android.text.TextUtils;

import com.ht.db.annotation.Column;
import com.ht.db.annotation.Table;
import com.ht.db.bean.ColumnBean;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangtao on 16/9/2.
 */
public class AnnomationUtils {

    public static <T>String getTableName(Class<T> clazz){
        String tableName;
        if (clazz.isAnnotationPresent(Table.class)){
            Table table = clazz.getAnnotation(Table.class);
            tableName = table.value();
            if (TextUtils.isEmpty(tableName)){
                tableName = clazz.getSimpleName();
            }
        } else {
            tableName = clazz.getSimpleName();
        }
        return tableName;
    }


    public static List<ColumnBean> getColumnNameAndValueList(Class clazz, Object obj){
        List<ColumnBean> columnList = new ArrayList<>();
        try {
            Field[] fields = clazz.getFields();
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                String filedName;
                String filedValue;
                Object filedValueObj;
                boolean id;
                if (field.isAnnotationPresent(Column.class)){
                    Column column = field.getAnnotation(Column.class);
                    filedName = column.name();
                    filedValueObj = field.get(obj);
                    id = column.id();
                    if (field.getType() == String.class){
                        if (filedValueObj == null){
                            filedValue = "\"\"";
                        } else {
                            filedValue = "\""+filedValueObj.toString()+"\"";
                        }
                    } else if (field.getType() == boolean.class || field.getType() == Boolean.class){
                        filedValue = ((boolean)filedValueObj)?"1":"0";
                    } else if (field.getType() == char.class || field.getType() == Character.class){
                        char c = (Character) filedValueObj;
                        filedValue = (short)c+"";
                    } else {
                        filedValue = filedValueObj.toString();
                    }

                    if (TextUtils.isEmpty(filedName)){
                        filedName = field.getName();
                    }

                    columnList.add(new ColumnBean(id, filedName, filedValue));
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return columnList;
    }


//    public static Map<String, Class<?>> getFieldNameAndType(Class clazz){
//        Field[] fields = clazz.getFields();
//        Map<String, Class<?>> map = new HashMap<>();
//        for (int i = 0; i < fields.length; i++) {
//            Field field = fields[i];
//            String filedName;
//            Class<?>  type;
//            if (field.isAnnotationPresent(Column.class)){
//                filedName = field.getAnnotation(Column.class).name();
//            } else {
//                filedName = field.getName();
//            }
//            type = field.getType();
//            map.put(filedName, type);
//        }
//        return map;
//    }


    public static List<ColumnBean> getColumnNameAndTypeList(Class clazz) {
        Field[] fields = clazz.getFields();
        List<ColumnBean> columnList = new ArrayList<>();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            String fieldName;
            if (field.isAnnotationPresent(Column.class)){
                Column column = field.getAnnotation(Column.class);
                fieldName = column.name();
                if (TextUtils.isEmpty(fieldName)){
                    fieldName = field.getName();
                }
                columnList.add(new ColumnBean(fieldName, field.getType(), column.id()));
            }
        }
        return columnList;
    }

    public static String getIdName(Class clazz) {
        Field[] fields = clazz.getFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            String fieldName;
            if (field.isAnnotationPresent(Column.class)){
                Column column = field.getAnnotation(Column.class);
                fieldName = column.name();
                if (TextUtils.isEmpty(fieldName)){
                    fieldName = field.getName();
                }
                if (column.id()){
                    return fieldName;
                }
            }
        }
        return null;
    }
}
