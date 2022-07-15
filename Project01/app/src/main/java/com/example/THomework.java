package com.example.project01;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;

import com.example.project01.ATask.THomeworkCSelect;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class THomework extends AppCompatActivity {

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView nav_view;
    ActionBar actionBar;

    //recyclerView
    Button btnSend;
    RecyclerView recyclerView;

    THomeworkViewAdapter adapter;

    ArrayList<THomeworkDTO> dtos;

    //String teacher_id="teacher"; // 나중에 수정
    String class_name="class_name"; // 나중에 수정
    String hw_name="hw_name"; // 나중에 수정
    String hw_id = "hw_id"; // 나중에 수정
    String class_id = "1"; // 나중에 수정
    String teacher_id = "teacher"; // 나중에 수정



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thomework);

        // toolbar 적용
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);  // 내가 만든 바를 액션바로 지정
        drawerLayout = findViewById(R.id.drawerLayout);

        actionBar = getSupportActionBar();
        ActionBar actionBar = getSupportActionBar();
        // 원래 있던 제목(Project01) 안보이게 해준
        actionBar.setDisplayShowTitleEnabled(false);


        ActionBarDrawerToggle toggle
                = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(toggle); // drawerLayout 에 toggle 을 붙임

        toggle.syncState();

        //recycler view---------------------------------------------------------------------

        //반드시 생성해서 adapter에 넘겨야 한다.
        dtos = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        //recyclerView 에서 반드시 아래와 같이 초기화를 해줘야 한다.
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        //adapter 객체를 생성한다.
        adapter = new THomeworkViewAdapter(THomework.this, dtos);

        //adapter 에 있는 ArrayList(dtos) 에 dto 추가한다.
        /*adapter.addDto(new THomeworkDTO("고2S", "1강 과제", "23/34", 45, 56));
        adapter.addDto(new THomeworkDTO("고1S", "2강 과제", "33/34", 50, 60));
        adapter.addDto(new THomeworkDTO("고2A", "3강 과제", "28/34", 52, 55));
        adapter.addDto(new THomeworkDTO("고1B", "4강 과제", "29/34", 45, 56));
        adapter.addDto(new THomeworkDTO("고2B", "5강 과제", "30/34", 45, 56));*/

        //만든 adapter 를 recyclerView에 붙인다.
        recyclerView.setAdapter(adapter);


        // 서버에 멤버들 ArrayList를 요구해서 가져온다 : AsyncTask 상속 받는 java
        THomeworkCSelect tHomeworkCSelect = new THomeworkCSelect(dtos, adapter, teacher_id );
        tHomeworkCSelect.execute();





    } // onCreate

} // class

