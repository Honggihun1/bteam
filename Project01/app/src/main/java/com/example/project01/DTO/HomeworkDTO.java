package com.example.project01.DTO;

public class HomeworkDTO {

    String homework_name, homework_done, homework_sub_date, homework_score;
    /*int homework_score;*/

    public HomeworkDTO(String homework_name, String homework_done, String homework_sub_date, String homework_score) {
        this.homework_name = homework_name;
        this.homework_done = homework_done;
        this.homework_sub_date = homework_sub_date;
        this.homework_score = homework_score;
    }

    public String getHomework_name() {
        return homework_name;
    }

    public void setHomework_name(String homework_name) {
        this.homework_name = homework_name;
    }

    public String getHomework_done() {
        return homework_done;
    }

    public void setHomework_done(String homework_done) {
        this.homework_done = homework_done;
    }

    public String getHomework_sub_date() {
        return homework_sub_date;
    }

    public void setHomework_sub_date(String homework_sub_date) {
        this.homework_sub_date = homework_sub_date;
    }

    public String getHomework_score() {
        return homework_score;
    }

    public void setHomework_score(String homework_score) {
        this.homework_score = homework_score;
    }
}