package com.example.project01.DTO;

public class SchoolDTO {
    private String school_id, school_name, location_id, type_id, school_location;

    public SchoolDTO(String school_id, String school_name, String location_id, String type_id, String school_location) {
        this.school_id = school_id;
        this.school_name = school_name;
        this.location_id = location_id;
        this.type_id = type_id;
        this.school_location = school_location;
    }

    public String getSchool_location() {
        return school_location;
    }

    public void setSchool_location(String school_location) {
        this.school_location = school_location;
    }

    public String getSchool_id() {
        return school_id;
    }

    public void setSchool_id(String school_id) {
        this.school_id = school_id;
    }

    public String getSchool_name() {
        return school_name;
    }

    public void setSchool_name(String school_name) {
        this.school_name = school_name;
    }

    public String getLocation_id() {
        return location_id;
    }

    public void setLocation_id(String location_id) {
        this.location_id = location_id;
    }

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }
}
