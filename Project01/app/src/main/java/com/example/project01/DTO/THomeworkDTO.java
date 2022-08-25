package com.example.project01.DTO;

import java.io.Serializable;

public class THomeworkDTO implements Serializable {
    private String class_name, homework_name, sub_num, all_stu, homework_avg, homework_max;

    public THomeworkDTO(String class_name, String homework_name, String sub_num, String all_stu, String homework_avg, String homework_max) {

        this.class_name = class_name;
        this.homework_name = homework_name;
        this.sub_num = sub_num;
        this.all_stu = all_stu;
        this.homework_avg = homework_avg;
        this.homework_max = homework_max;
    }



    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public String getHomework_name() {
        return homework_name;
    }

    public void setHomework_name(String homework_name) {
        this.homework_name = homework_name;
    }

    public String getSub_num() {
        return sub_num;
    }

    public void setSub_num(String sub_num) {
        this.sub_num = sub_num;
    }

    public String getAll_stu() { return all_stu; }

    public void setAll_stu(String all_stu) { this.all_stu = all_stu; }

    public String getHomework_avg() {
        return homework_avg;
    }

    public void setHomework_avg(String homework_avg) {
        this.homework_avg = homework_avg;
    }

    public String getHomework_max() {
        return homework_max;
    }

    public void setHomework_max(String homework_max) {
        this.homework_max = homework_max;
    }
}

