package com.example.project01.common;


import com.example.project01.DTO.C_infoDTO;
import com.example.project01.DTO.ClassDTO;
import com.example.project01.DTO.FCheckinDTO;
import com.example.project01.DTO.HomeworkDTO;
import com.example.project01.DTO.IdsDTO;
import com.example.project01.DTO.SchoolDTO;
import com.example.project01.DTO.StudentDTO;
import com.example.project01.DTO.StudentDetailDTO;
import com.example.project01.DTO.StudentInfoDTO;
import com.example.project01.DTO.TScheduleAllDTO;
import com.example.project01.DTO.TScheduleDTO;
import com.example.project01.DTO.TeacherDTO;
import com.example.project01.DTO.TestDTO;

import java.util.ArrayList;

public class CommonMethod {
    public static String ipConfig = "http://192.168.0.46:6767";

    public static TeacherDTO teacherDTO = null;
    public static StudentDTO studentDTO = null;
    public static SchoolDTO schoolDTO = null;
    public static IdsDTO idsDTO = null;
    public static TScheduleDTO tScheduleDTO = null;
    public static TScheduleAllDTO tScheduleAllDTO = null;

    public static ArrayList<TScheduleAllDTO> dtos2 = null;

    // 일우
    public static ClassDTO classDTO = null;
    public static C_infoDTO c_infoDTO = null;


    // 창균님
    public static StudentDetailDTO studentDetailDTO = null;
    public static HomeworkDTO homeworkDTO = null;
    public static TestDTO testDTO = null;
    public static FCheckinDTO fCheckinDTO = null;
    public static StudentInfoDTO studentInfoDTO = null;






}
