package com.example.project01.DTO;

import java.io.Serializable;

public class StudentDTO implements Serializable {
    private String student_id, student_pw, student_name, school_id, parent_phone, student_phone, class_id;
    private int grade;

    // 학생 이름만 저장


    public StudentDTO(String student_name) {
        this.student_name = student_name;
    }

    // 모든 정보 저장
    public StudentDTO(String student_id, String student_pw, String student_name, String school_id, String parent_phone, String student_phone, String class_id, int grade) {
        this.student_id = student_id;
        this.student_pw = student_pw;
        this.student_name = student_name;
        this.school_id = school_id;
        this.parent_phone = parent_phone;
        this.student_phone = student_phone;
        this.class_id = class_id;
        this.grade = grade;
    }

    // 비번 제외 저장
    public StudentDTO(String student_id, String student_name, String school_id, String parent_phone, String student_phone, String class_id, int grade) {
        this.student_id = student_id;
        this.student_name = student_name;
        this.school_id = school_id;
        this.parent_phone = parent_phone;
        this.student_phone = student_phone;
        this.class_id = class_id;
        this.grade = grade;
    }


    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getStudent_pw() {
        return student_pw;
    }

    public void setStudent_pw(String student_pw) {
        this.student_pw = student_pw;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public String getSchool_id() {
        return school_id;
    }

    public void setSchool_id(String school_id) {
        this.school_id = school_id;
    }

    public String getParent_phone() {
        return parent_phone;
    }

    public void setParent_phone(String parent_phone) {
        this.parent_phone = parent_phone;
    }

    public String getStudent_phone() {
        return student_phone;
    }

    public void setStudent_phone(String student_phone) {
        this.student_phone = student_phone;
    }

    public String getClass_id() {
        return class_id;
    }

    public void setClass_id(String class_id) {
        this.class_id = class_id;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }
}
