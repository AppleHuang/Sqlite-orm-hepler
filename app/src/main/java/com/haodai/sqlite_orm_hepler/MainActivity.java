package com.haodai.sqlite_orm_hepler;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.haodai.sqlite_orm_hepler.db.dao.StudentDao;
import com.haodai.sqlite_orm_hepler.vo.Student;

public class MainActivity extends AppCompatActivity {

    private static final long DEFALUT_ID = 3;
    private StudentDao mStudentDao;
    private boolean isStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mStudentDao = new StudentDao();
    }


    private class InsertThread extends Thread{
        @Override
        public void run() {
            while (!isStop){
                saveStudent(null);
            }
        }
    }


    public void startThread(View view){
        isStop = false;

        for (int i = 0; i < 4; i++) {
            new InsertThread().start();
        }
    }

    public void stopThread(View view){
        isStop = true;
    }

    public void saveStudent(View view){
        Student student = new Student(DEFALUT_ID, "嗯嗯呀", "黄涛", true, 'B', "jinpaiguwen");
        boolean res = mStudentDao.save(student);
        if (res){
            Log.e("============", "插入成功:" + student);
        } else {
            Log.e("============", "插入失败:" + student);
        }
    }

    public void update(View view){
        Student student = new Student(DEFALUT_ID, "嗯嗯呀404", "黄涛", true, 'B', "jinpaiguwen");
        boolean res = mStudentDao.update(student);
        if (res){
            Toast.makeText(this, "更新成功:id="+DEFALUT_ID, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "更新失败:id="+DEFALUT_ID, Toast.LENGTH_SHORT).show();
        }
    }

    public void findById(View view){
        Student student = mStudentDao.findById(DEFALUT_ID);
        if (student == null){
            Toast.makeText(this, "不存在id:"+DEFALUT_ID+" 的用户,请先进行插入操作", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, student.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void findByCondition(View view){
        Object student = mStudentDao.findByCondition("`group`='jinpaiguwen'");
        if (student == null){
            Toast.makeText(this, "不存在group:jinpaiguwen 的用户,请先进行插入操作", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, student.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteById(View view){
        boolean res = mStudentDao.deleteById(DEFALUT_ID);
        if (res){
            Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "删除失败", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteByCondition(View view){
        boolean res = mStudentDao.deleteByCondition("`group`='jinpaiguwen'");
        if (res){
            Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "删除失败", Toast.LENGTH_SHORT).show();
        }
    }

}
