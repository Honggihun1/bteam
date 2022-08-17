package com.example.project01;

import static com.example.project01.common.CommonMethod.studentDTO;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
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
import androidx.fragment.app.Fragment;

import com.example.project01.ATask.SMemberDelete;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.concurrent.ExecutionException;

public class SStudentInfo extends AppCompatActivity {
    private static final String TAG = "main:StudentInfo";

    Toolbar toolbar;
    TabLayout tabs;
    NavigationView nav_view;
    DrawerLayout drawerLayout;
    ActionBar actionBar;
    Fragment fragment_check_in, fragment_StudentDetail, fragment_Homework, fragment_test;
    Fragment selFragment = null;
    TextView studentname_detail;

    String state="", student_id="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_info);


        // toolbar 적용
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);  // 내가 만든 바를 액션바로 지정
        drawerLayout = findViewById(R.id.drawerLayout);


        actionBar = getSupportActionBar();
        ActionBar actionBar = getSupportActionBar();
        // 원래 있던 제목(Project01) 안보이게 해준
        actionBar.setDisplayShowTitleEnabled(false);

        fragment_check_in = new Fragment_CheckIn();
        fragment_Homework = new Fragment_Homework();
        fragment_StudentDetail = new Fragment_StudentDetail();
        fragment_test = new Fragment_Test();
        // 먼저 화면에 학생정보가 보이게 초기화
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contain, fragment_StudentDetail).commit();

        Intent intent = getIntent();
        int num = intent.getIntExtra("num", 0);



        // 로그인시 저장했던 dto 에서 student_id 가져오기
       student_id = studentDTO.getStudent_id();

        studentname_detail = findViewById(R.id.studentname_detail);

        studentname_detail.setText(studentDTO.getStudent_name()+ "님의 상세정보");



        Bundle bundle = new Bundle(1); // 파라미터의 숫자는 전달하려는 값의 갯수
        bundle.putString("student_id", student_id);
        fragment_Homework.setArguments(bundle);
        fragment_StudentDetail.setArguments(bundle);
        fragment_test.setArguments(bundle);
        fragment_check_in.setArguments(bundle);


        // 탭을 동적으로 생성한다
        tabs = findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("학생정보"));
        tabs.addTab(tabs.newTab().setText("출결상황"));
        tabs.addTab(tabs.newTab().setText("과제결과"));
        tabs.addTab(tabs.newTab().setText("테스트결과"));


        if(num == 2){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.contain, fragment_check_in).commit();
            TabLayout.Tab tab = tabs.getTabAt(1);
            tabs.selectTab(tab);

        }else if(num == 3){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.contain, fragment_Homework).commit();
            TabLayout.Tab tab = tabs.getTabAt(2);
            tabs.selectTab(tab);
        }else if(num == 4){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.contain, fragment_test).commit();
            TabLayout.Tab tab = tabs.getTabAt(3);
            tabs.selectTab(tab);
        }



        // 탭 레이아웃에 리스너를 달아준다
        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            // 탭이 선택되었을때
            @Override    //               선택된 탭
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();  // 0, 1, 2, ...
                Log.d(TAG, "onTabSelected: " + position);

                if(position == 0){
                    selFragment = fragment_StudentDetail;
                }else if(position == 1){
                    selFragment = fragment_check_in;
                }else if(position == 2){
                    selFragment = fragment_Homework;
                }else if(position == 3){
                    selFragment = fragment_test;
                }
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.contain, selFragment).commit();
            }
            // 탭이 선택되지 않았을 때
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            // 탭이 재선택 되었을때
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        ActionBarDrawerToggle toggle
                = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close );
        drawerLayout.addDrawerListener(toggle); // drawerLayout 에 toggle 을 붙임

        toggle.syncState();



        // 버거메뉴 ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // 버거메뉴 눌렀을 때 나오는 메뉴 찾아줌
        nav_view = findViewById(R.id.nav_view);


        View headerView = nav_view.getHeaderView(0);

        // textView에 접근
        TextView navName = headerView.findViewById(R.id.proId);
        TextView navId = headerView.findViewById(R.id.proName);
        TextView navPhone = headerView.findViewById(R.id.proPhone);
        TextView navClass = headerView.findViewById(R.id.proclass);

        navName.setText("반갑습니다 " + studentDTO.getStudent_name() + "님!!!");
        navId.setText("아이디 : " + studentDTO.getStudent_id());
        navPhone.setText("전화번호 : " + studentDTO.getParent_phone());
        navClass.setText("클래스 ID : " + studentDTO.getClass_id());

        //////////////////////////////////////  네비게이션 관련 (공통)  ////////////////////////////////////////////////////////////
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.nav_logout) {

                    new AlertDialog.Builder(SStudentInfo.this)
                            .setTitle("로그아웃")
                            .setMessage("로그아웃 하시겠습니까?")
                            .setPositiveButton("로그아웃", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //SharedPreferences에 저장된 값들을 로그아웃 버튼을 누르면 삭제하기 위해
                                    //SharedPreferences를 불러옵니다. 메인에서 만든 이름으로
                                    Intent intent = new Intent(SStudentInfo.this, SLogin.class);
                                    startActivity(intent);
                                    SharedPreferences auto = getSharedPreferences("setting2", Activity.MODE_PRIVATE);
                                    SharedPreferences.Editor editor2 = auto.edit();
                                    //editor.clear()는 auto에 들어있는 모든 정보를 기기에서 지웁니다.
                                    editor2.clear();
                                    editor2.commit();
                                    Toast.makeText(SStudentInfo.this, "로그아웃.", Toast.LENGTH_SHORT).show();
                                    finishAffinity();
                                }
                            })
                            .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).show();

                    // 계정 탈퇴 눌렀을 때
                } else if (id == R.id.nav_withdraw) {
                    new AlertDialog.Builder(SStudentInfo.this)
                            .setTitle("계정탈퇴")
                            .setMessage("계정 탈퇴 하시겠습니까?")
                            .setPositiveButton("탈퇴", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    String student_id = studentDTO.getStudent_id();

                                    // 탈퇴처리
                                    // 서버로 데이터를 보낸다 : AsyncTask를 상속받는 java파일을 만든다
                                    SMemberDelete smemberDelete = new SMemberDelete(student_id);
                                    try {
                                        state = smemberDelete.execute().get();  // 수정해야함

                                    } catch (ExecutionException e) {
                                        e.printStackTrace();
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    state = state.trim();
                                    // 정상적으로 데이터베이스에 삽입이 되면 1을 리턴, 아니면 0이하수를 리턴
                                    if (state.equals("1")) {
                                        Toast.makeText(SStudentInfo.this,
                                                "정상적으로 회원탈퇴되었습니다", Toast.LENGTH_SHORT).show();

                                        // 종료하고 학생 메인화면으로
                                        finish();
                                        Intent intent = new Intent(getApplicationContext(), SLogin.class);
                                        startActivity(intent);

                                        finishAffinity();

                                    } else {
                                        Toast.makeText(SStudentInfo.this, "회원 탈퇴에 실패하였습니다", Toast.LENGTH_SHORT).show();
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




    } // onCreate

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
    }
}// class