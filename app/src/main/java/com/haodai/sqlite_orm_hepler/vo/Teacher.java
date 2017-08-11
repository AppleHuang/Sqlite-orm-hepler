package com.haodai.sqlite_orm_hepler.vo;

import com.ht.db.annotation.Column;

import java.util.List;

/**
 * Created by huangtao on 16/9/2.
 */
public class Teacher {

    @Column (id = true)
    public long id;

    @Column
    public String name;

    public List<Student> students;

}
