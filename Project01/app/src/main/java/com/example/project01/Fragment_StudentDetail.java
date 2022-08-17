package com.example.project01;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.project01.ATask.StudentDetailSelect;
import com.example.project01.common.CommonMethod;

import java.util.concurrent.ExecutionException;

public class Fragment_StudentDetail extends Fragment {

    TextView tvname, tvschool_id, tvgrade, tvstudent_phone, tvparent_phone;

    String TAG = "Fragment_StudentDetail";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_student_detail,
                                container, false);

        String student_id = "";

        Bundle bundle = getArguments();

        if(bundle != null){
            student_id = bundle.getString("student_id");
        }

        Log.d(TAG, ": " + student_id);

        tvname = viewGroup.findViewById(R.id.tvname);
        tvschool_id = viewGroup.findViewById(R.id.tvschool_id);
        tvgrade = viewGroup.findViewById(R.id.tvgrade);
        tvstudent_phone = viewGroup.findViewById(R.id.tvstudent_phone);
        tvparent_phone = viewGroup.findViewById(R.id.tvparent_phone);

        tvschool_id.setSingleLine(true);    //한줄로 나오게 하기.
        tvschool_id.setEllipsize(TextUtils.TruncateAt.MARQUEE);//Ellipsize의 MARQUEE 속성 주기
        tvschool_id.setSelected(true);      //해당 텍스트뷰에 포커스가 없어도 문자 흐르게 하기

        // 서버에 멤버들 ArrayList를 요구해서 가져온다 : AsyncTask 상속 받는 java
        StudentDetailSelect studentDetailSelect = new StudentDetailSelect(student_id);
        try {
            studentDetailSelect.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        tvname.setText(CommonMethod.studentDetailDTO.getStudent_name()+ "님의 상세정보");
        tvschool_id.setText(CommonMethod.studentDetailDTO.getSchool_name());
        tvstudent_phone.setText(CommonMethod.studentDetailDTO.getStudent_phone());
        tvparent_phone.setText(CommonMethod.studentDetailDTO.getParent_phone());
        tvgrade.setText(CommonMethod.studentDetailDTO.getGrade()+ "학년");



        return viewGroup;
    }
}
