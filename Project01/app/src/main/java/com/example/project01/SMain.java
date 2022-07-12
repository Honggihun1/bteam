package com.example.project01;

import static com.example.project01.common.CommonMethod.studentDTO;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class SMain extends AppCompatActivity {




    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView nav_view;
    ActionBar actionBar;

    TextView tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smain);

        // 찾기
        tv1 = findViewById(R.id.tv1);

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

        navName.setText("반갑습니다 " + studentDTO.getStudent_name() + "님!!!");
        navId.setText("아이디 : " + studentDTO.getStudent_id());
        navPhone.setText("부모님 전화번호 : " + studentDTO.getParent_phone());

        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.nav_logout) {
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
                // 메뉴 선택 후 드로어가 사라지게 한다
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;

            }
        });



    }




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