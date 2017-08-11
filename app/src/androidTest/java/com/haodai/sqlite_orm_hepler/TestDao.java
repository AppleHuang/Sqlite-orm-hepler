package com.haodai.sqlite_orm_hepler;

import android.app.Application;
import android.test.ApplicationTestCase;

/**
 * Created by huangtao on 16/9/5.
 */
public class TestDao extends ApplicationTestCase<Application> {

    public TestDao() {
        super(Application.class);
    }

    private static final long DEFALUT_ID = 2;

//
//    public void testCreateTable () throws Exception{
//        DBUtils.getInstance().createTable(Student.class);
//    }
//
//    public void testAdd () throws Exception{
//        StudentDao mStudentDao = new StudentDao();
//        Student student = new Student(DEFALUT_ID, "嗯嗯呀", "黄涛", true, 'B', "jinpaiguwen");
//        boolean res = mStudentDao.save(student);
//        if (res){
//            Toast.makeText(getContext(), "插入成功:"+student, Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(getContext(), "插入失败:"+student, Toast.LENGTH_SHORT).show();
//        }
//    }



}
