package com.haodai.sqlite_orm_hepler.vo;

import com.ht.db.annotation.Column;
import com.ht.db.annotation.Table;

/**
 * Created by huangtao on 16/9/2.
 */
@Table("student")
public class Student {

    @Column(name = "id", id = true)
    public long id;

    @Column(name = "name")
    public String name;

    @Column(name = "truename")
    public String truename;

    @Column
    public boolean isMan;

    @Column
    public char scoreLevel;

    @Column
    public String group;

    public String doNotColumn;

    public Student(long id, String name, String truename, boolean isMan, char scoreLevel, String group) {
        this.id = id;
        this.name = name;
        this.truename = truename;
        this.isMan = isMan;
        this.scoreLevel = scoreLevel;
        this.group = group;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", truename='" + truename + '\'' +
                ", isMan=" + isMan +
                ", scoreLevel=" + scoreLevel +
                ", group='" + group + '\'' +
                '}';
    }

    public Student() {
    }
}
