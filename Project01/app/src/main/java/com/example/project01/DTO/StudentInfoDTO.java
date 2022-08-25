package com.example.project01.DTO;

public class StudentInfoDTO {
    private static String student_name;

    public StudentInfoDTO(String student_name) {
        this.student_name = student_name;
    }

    public static String getStudent_name() {
        return student_name;
    }

    public static void setStudent_name(String student_name) {
        StudentInfoDTO.student_name = student_name;
    }
}
