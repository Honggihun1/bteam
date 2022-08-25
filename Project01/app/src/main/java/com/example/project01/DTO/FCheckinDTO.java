package com.example.project01.DTO;

public class FCheckinDTO {

    private static String checkin_date, checkin_hour, checkout_hour;

    public FCheckinDTO(String checkin_date, String checkin_hour, String checkout_hour) {
        this.checkin_date = checkin_date;
        this.checkin_hour = checkin_hour;
        this.checkout_hour = checkout_hour;
    }

    public static String getCheckin_date() {
        return checkin_date;
    }

    public static void setCheckin_date(String checkin_date) {
        FCheckinDTO.checkin_date = checkin_date;
    }

    public static String getCheckin_hour() {
        return checkin_hour;
    }

    public static void setCheckin_hour(String checkin_hour) {
        FCheckinDTO.checkin_hour = checkin_hour;
    }

    public static String getCheckout_hour() {
        return checkout_hour;
    }

    public static void setCheckout_hour(String checkout_hour) {
        FCheckinDTO.checkout_hour = checkout_hour;
    }

}
