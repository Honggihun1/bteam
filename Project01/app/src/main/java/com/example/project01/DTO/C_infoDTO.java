package com.example.project01.DTO;

import java.io.Serializable;

public class C_infoDTO implements Serializable {

    private String student_id, student_name, school_name, parent_phone,chk;

    public C_infoDTO(String student_id, String student_name, String school_name, String parent_phone, String chk) {
        this.student_id = student_id;
        this.student_name = student_name;
        this.school_name = school_name;
        this.parent_phone = parent_phone;
        this.chk = chk;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public String getSchool_name() {
        return school_name;
    }

    public void setSchool_name(String school_name) {
        this.school_name = school_name;
    }

    public String getParent_phone() {
        return parent_phone;
    }

    public void setParent_phone(String parent_phone) {
        this.parent_phone = parent_phone;
    }

    public String getChk() {
        return chk;
    }

    public void setChk(String chk) {
        this.chk = chk;
    }
}
