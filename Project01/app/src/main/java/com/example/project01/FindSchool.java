package com.example.project01;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project01.ATask.FindSchoolSelect;
import com.example.project01.Adapter.SchoolAdapter;
import com.example.project01.DTO.SchoolDTO;

import java.util.ArrayList;

public class FindSchool extends AppCompatActivity {
    static String TAG ="확인";

    EditText et1;
    Button btn1;

    ArrayList<SchoolDTO> dtos;
    RecyclerView recyclerView;
    SchoolAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_school);

        et1 = findViewById(R.id.et1);
        btn1 = findViewById(R.id.btn1);
        recyclerView = findViewById(R.id.recyclerView);

        // 반드시 만들어서 adapter에 넘겨야 한다
        dtos = new ArrayList<>();
        // recyclerView에서 반드시 아래와 같이 초기화를 해줘야 함
        LinearLayoutManager layoutManager = new LinearLayoutManager
                (FindSchool.this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        // 어댑터 객체 생성
        adapter = new SchoolAdapter(FindSchool.this, dtos);
        recyclerView.setAdapter(adapter);

        // 찾기 버튼 클릭시
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String school_name = et1.getText().toString();
                if( !school_name.equals("")){

                    // 찾기 누를시 원래 있던 목록 없애줌
                    dtos.clear();

                    // 서버에 멤버들 ArrayList를 요구해서 가져온다 : AsyncTask 상속 받는 java
                    FindSchoolSelect findSchoolSelect = new FindSchoolSelect(dtos, adapter, school_name);
                    findSchoolSelect.execute();

                }
                
            }
        });


        // 리스트 클릭시
        adapter.setOnItemClickListener(new OnSchoolItemClickListener() {
            @Override
            public void onItemClick(SchoolAdapter.ViewHolder holder, View view, int position) {
                SchoolDTO dto = adapter.getItem(position);
                Toast.makeText(FindSchool.this,"학교 선택 : " + dto.getSchool_name(),
                        Toast.LENGTH_LONG).show();
                Intent reIntent = new Intent();
                reIntent.putExtra("school_name", dto.getSchool_name());
                reIntent.putExtra("school_id", dto.getSchool_id());
                setResult(RESULT_OK, reIntent);
                finish();



            }
        });





    } // onCreate



    public interface OnSchoolItemClickListener {
        public void onItemClick(SchoolAdapter.ViewHolder holder, View view, int position);
    }


}