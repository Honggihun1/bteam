package com.example.project01;

import static com.example.project01.common.CommonMethod.studentDTO;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import com.example.project01.ATask.SclassPlus;
import com.google.android.material.navigation.NavigationView;

import java.util.concurrent.ExecutionException;

public class SMain extends AppCompatActivity {


    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView nav_view;
    ActionBar actionBar;
   // BottomNavigationView bottom_navi;
    String state = "";
    TextView tv1;
    LinearLayout sinfoLayout, scheckinLayout, shomeworkLayout, stestLayout, classplusLayout;
    Fragment fragment_check_in, fragment_StudentDetail, fragment_Homework, fragment_test;   // 이동시 사용할려고 미리 선언
    String TAG = "확인";

    EditText etclass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smain);

        // 찾기
        tv1 = findViewById(R.id.tv1);
        sinfoLayout = findViewById(R.id.sinfoLayout);
        scheckinLayout = findViewById(R.id.scheckinLayout);
        shomeworkLayout = findViewById(R.id.shomeworkLayout);
        stestLayout = findViewById(R.id.stestLayout);
        classplusLayout = findViewById(R.id.classplusLayout);
        etclass = findViewById(R.id.etclass);

     //   bottom_navi = findViewById(R.id.bottom_navi);

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


        // dto에서 데이터 가져오기 ( 이름 뜨게 함)
        tv1.setText(studentDTO.getStudent_name()+"님 어서오세요");


        // 버거메뉴 ///////////////////////////////////////////////////////////////////////////////////////////////////
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
        navPhone.setText("부모님 전번 : " + studentDTO.getParent_phone());
        navClass.setText("클래스 ID : " + studentDTO.getClass_id());



        //////////////////////////////////////  네비게이션 관련 (공통)  ////////////////////////////////////////////////////////////
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.nav_logout) {

                    new AlertDialog.Builder(SMain.this)
                            .setTitle("로그아웃")
                            .setMessage("로그아웃 하시겠습니까?")
                            .setPositiveButton("로그아웃", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //SharedPreferences에 저장된 값들을 로그아웃 버튼을 누르면 삭제하기 위해
                                    //SharedPreferences를 불러옵니다. 메인에서 만든 이름으로
                                    Intent intent = new Intent(SMain.this, SLogin.class);
                                    startActivity(intent);
                                    SharedPreferences auto = getSharedPreferences("setting2", Activity.MODE_PRIVATE);
                                    SharedPreferences.Editor editor2 = auto.edit();
                                    //editor.clear()는 auto에 들어있는 모든 정보를 기기에서 지웁니다.
                                    editor2.clear();
                                    editor2.commit();
                                    Toast.makeText(SMain.this, "로그아웃.", Toast.LENGTH_SHORT).show();
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
                    new AlertDialog.Builder(SMain.this)
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
                                        state = smemberDelete.execute().get();

                                    } catch (ExecutionException e) {
                                        e.printStackTrace();
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    state = state.trim();
                                    // 정상적으로 데이터베이스에 삽입이 되면 1을 리턴, 아니면 0이하수를 리턴
                                    if (state.equals("1")) {
                                        Toast.makeText(SMain.this,
                                                "정상적으로 회원탈퇴되었습니다", Toast.LENGTH_SHORT).show();

                                        // 종료하고 학생 메인화면으로
                                        finish();
                                        Intent intent = new Intent(SMain.this, SLogin.class);
                                        startActivity(intent);
                                        SharedPreferences auto = getSharedPreferences("setting2", Activity.MODE_PRIVATE);
                                        SharedPreferences.Editor editor2 = auto.edit();
                                        //editor.clear()는 auto에 들어있는 모든 정보를 기기에서 지웁니다.
                                        editor2.clear();
                                        editor2.commit();

                                        finish();

                                    } else {
                                        Toast.makeText(SMain.this, "회원 탈퇴에 실패하였습니다", Toast.LENGTH_SHORT).show();
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

        }); // 네비게이션



        // 메인메뉴의 LinearLayout 클릭했을 때 ///////////////////////////////////////
        sinfoLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SMain.this, SStudentInfo.class);
                startActivity(intent);
            }
        });

        scheckinLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SMain.this, SStudentInfo.class);
                intent.putExtra("num", 2);
                startActivity(intent);

            }
        });

        shomeworkLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SMain.this, SStudentInfo.class);
                intent.putExtra("num", 3);
                startActivity(intent);
            }
        });

        stestLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SMain.this, SStudentInfo.class);
                intent.putExtra("num", 4);
                startActivity(intent);

            }
        });



        // 클래스 아이디 없을 때 클래스 추가 버튼이 나타나게 한다

        if(studentDTO.getClass_id().equals("")){
            classplusLayout.setVisibility(View.VISIBLE);
        }else {
            classplusLayout.setVisibility(View.INVISIBLE);
        }

        // 클래스 ID 추가하기 ///////////////////////////////////////////////////////////////////////
        findViewById(R.id.btnClassPlus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String class_id = etclass.getText().toString();

                if(class_id.equals("")){
                    Toast.makeText(SMain.this,"클래스ID를 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                SclassPlus sclassPlus = new SclassPlus(studentDTO.getStudent_id(), class_id);
                try{
                    state = sclassPlus.execute().get();


                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                state = state.trim();
                // 정상적으로 데이터베이스에 삽입이 되면 1을 리턴, 아니면 0이하수를 리턴
                if (state.equals("1")) {
                    Toast.makeText(SMain.this,
                            "정상적으로 클래스 추가가 되었습니다", Toast.LENGTH_SHORT).show();

                    etclass.setText("");
                    studentDTO.setClass_id(class_id);
                    navClass.setText("클래스 ID : " + studentDTO.getClass_id());
                    classplusLayout.setVisibility(View.INVISIBLE);



                } else {
                    Toast.makeText(SMain.this, "클래스 추가에 실패하였습니다\n다시 확인 후 클래스 추가 해주세요", Toast.LENGTH_SHORT).show();
                }



            }
        });




    } // onCreate






    private long pressedTime;  // 뒤로가기 버튼 커스텀시 사용
    // 뒤로가기 버튼 2번 눌러야 종료 (메인에서)
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        if ( pressedTime == 0 ) {
            Toast.makeText(SMain.this, "한번 더 누르면 종료됩니다", Toast.LENGTH_LONG).show();
            pressedTime = System.currentTimeMillis();
        }
        else {
            int seconds = (int) (System.currentTimeMillis() - pressedTime);

            if ( seconds > 2000 ) {
                pressedTime = 0;
            }
            else {
                finishAffinity();
            }
        }
    } // onBackPressed()

}