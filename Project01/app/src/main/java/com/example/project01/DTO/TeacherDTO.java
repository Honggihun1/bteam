package com.example.project01.DTO;

import java.io.Serializable;

public class TeacherDTO implements Serializable {
    private String teacher_id, teacher_pw, teacher_name, teacher_phone, sms_send ;

    // 모든 정보 저장
    public TeacherDTO(String teacher_id, String teacher_pw, String teacher_name, String teacher_phone, String sms_send) {
        this.teacher_id = teacher_id;
        this.teacher_pw = teacher_pw;
        this.teacher_name = teacher_name;
        this.teacher_phone = teacher_phone;
        this.sms_send = sms_send;
    }

    // 비번 제외 저장
    public TeacherDTO(String teacher_id, String teacher_name, String teacher_phone, String sms_send) {
        this.teacher_id = teacher_id;
        this.teacher_name = teacher_name;
        this.teacher_phone = teacher_phone;
        this.sms_send = sms_send;
    }

    public String getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(String teacher_id) {
        this.teacher_id = teacher_id;
    }

    public String getTeacher_pw() {
        return teacher_pw;
    }

    public void setTeacher_pw(String teacher_pw) {
        this.teacher_pw = teacher_pw;
    }

    public String getTeacher_name() {
        return teacher_name;
    }

    public void setTeacher_name(String teacher_name) {
        this.teacher_name = teacher_name;
    }

    public String getTeacher_phone() {
        return teacher_phone;
    }

    public void setTeacher_phone(String teacher_phone) {
        this.teacher_phone = teacher_phone;
    }

    public String getSms_send() {
        return sms_send;
    }

    public void setSms_send(String sms_send) {
        this.sms_send = sms_send;
    }
}
