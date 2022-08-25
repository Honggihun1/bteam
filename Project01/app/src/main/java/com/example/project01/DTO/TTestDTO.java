package com.example.project01.DTO;

import java.io.Serializable;

public class TTestDTO implements Serializable {
    private String class_name, test_name, app_num, all_stu, test_avg, test_max;

    public TTestDTO(String class_name, String test_name, String app_num,  String all_stu, String test_avg, String test_max) {
        this.class_name = class_name;
        this.test_name = test_name;
        this.app_num = app_num;
        this.all_stu = all_stu;
        this.test_avg = test_avg;
        this.test_max = test_max;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public String getTest_name() {
        return test_name;
    }

    public void setTest_name(String test_name) {
        this.test_name = test_name;
    }

    public String getApp_num() {
        return app_num;
    }

    public void setApp_num(String app_num) {
        this.app_num = app_num;
    }
    public String getAll_stu() { return all_stu; }

    public void setAll_stu(String all_stu) { this.all_stu = all_stu; }

    public String getTest_avg() {
        return test_avg;
    }

    public void setTest_avg(String test_avg) {
        this.test_avg = test_avg;
    }

    public String getTest_max() {
        return test_max;
    }

    public void setTest_max(String test_max) {
        this.test_max = test_max;
    }
}
