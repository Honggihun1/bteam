package com.example.project01;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project01.ATask.HomeworkSelect;
import com.example.project01.Adapter.HomeworkAdapter;
import com.example.project01.DTO.HomeworkDTO;

import java.util.ArrayList;

public class Fragment_Homework extends Fragment {
    static String TAG = "확인";
    RecyclerView homewokrRecyclerView;
    ArrayList<HomeworkDTO> dtos;
    HomeworkAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_homework,
                                container, false);

        String student_id = "";

        Bundle bundle = getArguments();

        if(bundle != null){
            student_id = bundle.getString("student_id");
        }


        // 반드시 만들어서 adapter에 넘겨야 한다
        dtos = new ArrayList<>();


        homewokrRecyclerView = viewGroup.findViewById(R.id.homewokrRecyclerView);
        // recyclerView에서 반드시 아래와 같이 초기화를 해줘야 함
        LinearLayoutManager layoutManager = new LinearLayoutManager
                (requireContext(), RecyclerView.VERTICAL, false);
        homewokrRecyclerView.setLayoutManager(layoutManager);

        // 어댑터 객체 생성
        adapter = new HomeworkAdapter(getActivity(), dtos);
        homewokrRecyclerView.setAdapter(adapter);

        // 서버에 멤버들 ArrayList를 요구해서 가져온다 : AsyncTask 상속 받는 java
        HomeworkSelect homeworkSelect = new HomeworkSelect(dtos, adapter, student_id);
        homeworkSelect.execute();


        Log.d(TAG, "0 : " + dtos);



        return viewGroup;
    }


}
