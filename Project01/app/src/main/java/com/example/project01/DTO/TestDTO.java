package com.example.project01.DTO;

public class TestDTO {

    String test_date, test_name, test_score;
    /*int test_score;*/

    public TestDTO(String test_date, String test_name, String test_score) {
        this.test_date = test_date;
        this.test_name = test_name;
        this.test_score = test_score;
    }

    public String getTest_date() {
        return test_date;
    }

    public void setTest_date(String test_date) {
        this.test_date = test_date;
    }

    public String getTest_name() {
        return test_name;
    }

    public void setTest_name(String test_name) {
        this.test_name = test_name;
    }

    public String getTest_score() {
        return test_score;
    }

    public void setTest_score(String test_score) {
        this.test_score = test_score;
    }
}
