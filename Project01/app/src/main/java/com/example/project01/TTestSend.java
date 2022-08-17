package com.example.project01;

import static com.example.project01.common.CommonMethod.teacherDTO;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.project01.ATask.MemberDelete;
import com.google.android.material.navigation.NavigationView;

import java.util.concurrent.ExecutionException;

public class TTestSend extends AppCompatActivity {
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView nav_view;
    ActionBar actionBar;

    //TSend------------
    EditText etPhone, etMessage;
    Button btnTestSend, btnCancel;

    String state="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ttest_send);

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
                this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close );
        drawerLayout.addDrawerListener(toggle); // drawerLayout 에 toggle 을 붙임

        toggle.syncState();

        //TTestSend-------------
        etPhone = findViewById(R.id.etPhone);
        etMessage = findViewById(R.id.etMessage);
        btnTestSend = findViewById(R.id.btnTestSend);
        btnCancel = findViewById(R.id.btnCancel);

        Intent intent = getIntent();
        String test = intent.getStringExtra("test");
        String classname = intent.getStringExtra("classname");
        String avg = intent.getStringExtra("avg");
        String max = intent.getStringExtra("max");
        etMessage.setText("반 : "+classname);
        etMessage.append("\n과제 : "+test);
        etMessage.append("\n평균 : "+avg);
        etMessage.append("\n최고점 : "+max);

        btnTestSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(TTestSend.this, Manifest.permission.SEND_SMS)== PackageManager.PERMISSION_GRANTED){
                    sendMessage();
                }else{
                    ActivityCompat.requestPermissions(TTestSend.this, new String[]{Manifest.permission.SEND_SMS}, 100);
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



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

                    new AlertDialog.Builder(TTestSend.this)
                            .setTitle("로그아웃")
                            .setMessage("로그아웃 하시겠습니까?")
                            .setPositiveButton("로그아웃", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //SharedPreferences에 저장된 값들을 로그아웃 버튼을 누르면 삭제하기 위해
                                    //SharedPreferences를 불러옵니다. 메인에서 만든 이름으로
                                    Intent intent = new Intent(TTestSend.this, TLogin.class);
                                    startActivity(intent);
                                    SharedPreferences auto = getSharedPreferences("setting", Activity.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = auto.edit();
                                    //editor.clear()는 auto에 들어있는 모든 정보를 기기에서 지웁니다.
                                    editor.clear();
                                    editor.commit();
                                    Toast.makeText(TTestSend.this, "로그아웃.", Toast.LENGTH_SHORT).show();
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
                    new AlertDialog.Builder(TTestSend.this)
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
                                        Toast.makeText(TTestSend.this,
                                                "정상적으로 회원탈퇴되었습니다", Toast.LENGTH_SHORT).show();

                                        // 종료하고 학생 메인화면으로
                                        finish();
                                        Intent intent = new Intent(getApplicationContext(), TLogin.class);
                                        startActivity(intent);

                                        finish();

                                    } else {
                                        Toast.makeText(TTestSend.this, "회원 탈퇴에 실패하였습니다", Toast.LENGTH_SHORT).show();
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

    private void sendMessage() {
        //int[] score = {70, 78, 89, 90};
        String sPhone = "5556";
        Intent intent = getIntent();
        String test = intent.getStringExtra("test");
        String classname = intent.getStringExtra("classname");
        String avg = intent.getStringExtra("avg");
        String max = intent.getStringExtra("max");
        String[] sMessage = {classname + "\n" + test + "\n평균 : " + avg + "\n최고점 : " +  max};


        if(!sPhone.equals("") && !sMessage.equals("")){
            SmsManager smsManager = SmsManager.getDefault();
            //for(int i=0 ; i<score.length ; i++) {
                smsManager.sendTextMessage(sPhone, null, sMessage[0], null, null);
            //}
            Toast.makeText(getApplicationContext(), "문자가 발송되었습니다.", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getApplicationContext(), "문자 발송 실패.", Toast.LENGTH_LONG).show();

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == 100 && grantResults.length > 0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            sendMessage();
        }else{
            Toast.makeText(getApplicationContext(), "권한 거절", Toast.LENGTH_SHORT).show();
        }

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


















