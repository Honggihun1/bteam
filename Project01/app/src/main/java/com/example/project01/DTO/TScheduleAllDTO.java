package com.example.project01.DTO;

public class TScheduleAllDTO {

    private String schedule, schedule_date;

    public TScheduleAllDTO(String schedule, String schedule_date) {
        this.schedule = schedule;
        this.schedule_date = schedule_date;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getSchedule_date() {
        return schedule_date;
    }

    public void setSchedule_date(String schedule_date) {
        this.schedule_date = schedule_date;
    }
}
