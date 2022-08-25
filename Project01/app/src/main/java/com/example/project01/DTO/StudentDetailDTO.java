package com.example.project01.DTO;

public class StudentDetailDTO {
    String student_name, school_id, parent_phone, student_phone, grade, school_name;

    public StudentDetailDTO(String student_name, String school_id, String parent_phone, String student_phone, String grade, String school_name) {
        this.student_name = student_name;
        this.school_id = school_id;
        this.parent_phone = parent_phone;
        this.student_phone = student_phone;
        this.grade = grade;
        this.school_name = school_name;
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

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getSchool_name() {
        return school_name;
    }

    public void setSchool_name(String school_name) {
        this.school_name = school_name;
    }
}
