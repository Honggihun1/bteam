package com.example.project01;

import static com.example.project01.common.CommonMethod.teacherDTO;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project01.ATask.MemberDelete;
import com.example.project01.ATask.TTestCSelect;
import com.example.project01.Adapter.TTestViewAdapter;
import com.example.project01.DTO.TTestDTO;
import com.example.project01.common.CommonMethod;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class TTest extends AppCompatActivity {

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView nav_view;
    ActionBar actionBar;

    //recyclerView
    Button btnSend;
    RecyclerView recyclerView;

    TTestViewAdapter adapter;

    ArrayList<TTestDTO> dtos;
    BottomNavigationView bottom_navi;
    String state="";
    String teacher_id = CommonMethod.teacherDTO.getTeacher_id();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ttest);

        // toolbar 적용
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);  // 내가 만든 바를 액션바로 지정
        drawerLayout = findViewById(R.id.drawerLayout);

      //  bottom_navi = findViewById(R.id.bottom_navi);

        actionBar = getSupportActionBar();
        ActionBar actionBar = getSupportActionBar();
        // 원래 있던 제목(Project01) 안보이게 해준
        actionBar.setDisplayShowTitleEnabled(false);


        ActionBarDrawerToggle toggle
                = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(toggle); // drawerLayout 에 toggle 을 붙임

        toggle.syncState();




        // 버거메뉴 (공통)  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // 버거메뉴 눌렀을 때 나오는 메뉴 찾아줌
        nav_view = findViewById(R.id.nav_view);


        View headerView = nav_view.getHeaderView(0);

        // textView에 접근
        TextView navName = headerView.findViewById(R.id.proId);
        TextView navId = headerView.findViewById(R.id.proName);
        TextView navPhone = headerView.findViewById(R.id.proPhone);

        navName.setText("반갑습니다 " + teacherDTO.getTeacher_name() + "님!!!");
        navId.setText("아이디 : " + teacherDTO.getTeacher_id());
        navPhone.setText("전화번호 : " + teacherDTO.getTeacher_phone());

        TextView navClass = headerView.findViewById(R.id.proclass);
        navClass.setVisibility(View.INVISIBLE);


        //////////////////////////////////////  네비게이션 관련 (공통)  ////////////////////////////////////////////////////////////
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.nav_logout) {

                    new AlertDialog.Builder(TTest.this)
                            .setTitle("로그아웃")
                            .setMessage("로그아웃 하시겠습니까?")
                            .setPositiveButton("로그아웃", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //SharedPreferences에 저장된 값들을 로그아웃 버튼을 누르면 삭제하기 위해
                                    //SharedPreferences를 불러옵니다. 메인에서 만든 이름으로
                                    Intent intent = new Intent(TTest.this, TLogin.class);
                                    startActivity(intent);
                                    SharedPreferences auto = getSharedPreferences("setting", Activity.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = auto.edit();
                                    //editor.clear()는 auto에 들어있는 모든 정보를 기기에서 지웁니다.
                                    editor.clear();
                                    editor.commit();
                                    Toast.makeText(TTest.this, "로그아웃.", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            })
                            .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).show();

                    // 계정 탈퇴 눌렀을 때
                } else if (id == R.id.nav_withdraw) {
                    new AlertDialog.Builder(TTest.this)
                            .setTitle("계정탈퇴")
                            .setMessage("계정 탈퇴 하시겠습니까?")
                            .setPositiveButton("탈퇴", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    String teacher_id = teacherDTO.getTeacher_id();

                                    // 탈퇴처리
                                    // 서버로 데이터를 보낸다 : AsyncTask를 상속받는 java파일을 만든다
                                    MemberDelete memberDelete = new MemberDelete(teacher_id);
                                    try {
                                        state = memberDelete.execute().get();

                                    } catch (ExecutionException e) {
                                        e.printStackTrace();
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    state = state.trim();
                                    // 정상적으로 데이터베이스에 삽입이 되면 1을 리턴, 아니면 0이하수를 리턴
                                    if (state.equals("1")) {
                                        Toast.makeText(TTest.this,
                                                "정상적으로 회원탈퇴되었습니다", Toast.LENGTH_SHORT).show();

                                        // 종료하고 학생 메인화면으로
                                        finish();
                                        Intent intent = new Intent(getApplicationContext(), TLogin.class);
                                        startActivity(intent);

                                        finish();

                                    } else {
                                        Toast.makeText(TTest.this, "회원 탈퇴에 실패하였습니다", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).show();


                }


                // 메뉴 선택 후 드로어가 사라지게 한다
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;

            }

        });






        //recycler view---------------------------------------------------------------------
        dtos = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new TTestViewAdapter(TTest.this, dtos);

        recyclerView.setAdapter(adapter);


        // 서버에 멤버들 ArrayList를 요구해서 가져온다 : AsyncTask 상속 받는 java
        TTestCSelect tTestCSelect = new TTestCSelect(dtos, adapter, teacher_id);
        tTestCSelect.execute();
    }
    private long pressedTime;  // 뒤로가기 버튼 커스텀시 사용 (레이아웃 별 선택사항) ////////////////////////////////////////////////////////

    // 뒤로가기 버튼 2번 눌러야 종료
    @Override
    public void onBackPressed() {

        // 뒤로가기를 눌렀을 때 만약 드로어 창이 열려있으면 드로어 창을 닫고
        // 아니면 그냥 뒤로가기가 된다.
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();  // 원래 선언한 작업
        }


    } // onBackPressed()
}
























