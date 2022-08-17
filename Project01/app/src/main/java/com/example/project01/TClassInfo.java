package com.example.project01;

import static com.example.project01.common.CommonMethod.teacherDTO;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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

import com.example.project01.ATask.ClassInfoSearch;
import com.example.project01.ATask.ClassInfoSelect;
import com.example.project01.ATask.MemberDelete;
import com.example.project01.Adapter.C_infoAdapter;
import com.example.project01.DTO.C_infoDTO;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class TClassInfo extends AppCompatActivity {

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView nav_view;
    ActionBar actionBar;
    Button btn1, btn2;

    RecyclerView recyclerView;
    C_infoAdapter c_infoAdapter;

    ArrayList<C_infoDTO> dtos;

    private List<String> list;          // 데이터를 넣은 리스트변수
    private ListView listView;          // 검색을 보여줄 리스트변수
    private EditText editText;        // 검색어를 입력할 Input 창
    private C_infoAdapter adapter;      // 리스트뷰에 연결할 아답터
    private ArrayList<String> arraylist;

    String state="";
    String TAG = "TClassInfo";
    String class_id="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tclass_info);

        dtos = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerView);

        editText = (EditText) findViewById(R.id.editText);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);



        // recyclerView에서 반드시 아래와 같이 초기화를 해줘야 함
        LinearLayoutManager layoutManager = new LinearLayoutManager
                (this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);


        // 어댑터 객체 생성
        c_infoAdapter = new C_infoAdapter(TClassInfo.this,dtos);


        // 만든 어댑터를 리싸이클러뷰에 붙인다
        recyclerView.setAdapter(c_infoAdapter);

        /////////////////////////////////////////////
        /* 반 목록 눌렀을 때 class_id 가져오는 처리 */

        Intent intent = getIntent();


        if(intent != null){
            class_id = intent.getStringExtra("class_id");

        }

        Log.d(TAG, " class_id: " + class_id);

        // 전체목록 가져와서 출력함
        ClassInfoSelect classInfoSelect = new ClassInfoSelect(dtos, c_infoAdapter, class_id);
        classInfoSelect.execute();



        // 이름순 버튼 클릭시
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editText.getText().toString();
                String order = "name";
                dtos.clear();

               ClassInfoSearch classInfoSearch = new ClassInfoSearch(dtos, c_infoAdapter, class_id, name, order);
               classInfoSearch.execute();

            }
        });
    /*
        // 또는 엔터 입력시
        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    //Enter키눌렀을떄 처리
                    String name = editText.getText().toString();
                    dtos.clear();
                    ClassInfoSearch classInfoSearch = new ClassInfoSearch(dtos, c_infoAdapter, class_id, name);
                    classInfoSearch.execute();

                    editText.setText("");

                    return true;
                }
                return false;
            }

        });
*/

        // 학교순 버튼 클릭시
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editText.getText().toString();
                String order = "school";
                dtos.clear();
                ClassInfoSearch classInfoSearch = new ClassInfoSearch(dtos, c_infoAdapter, class_id, name, order);
                classInfoSearch.execute();

            }
        });




        // 검색창에 입력할 때마다 목록뜨게 함 ///////////////////////////////////////////////////////////
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String name = editText.getText().toString();
                String order = "name";
                dtos.clear();
                ClassInfoSearch classInfoSearch = new ClassInfoSearch(dtos, c_infoAdapter, class_id, name, order);
                classInfoSearch.execute();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        }); // 검색창 입력할 때마다 실행





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


        // 버거메뉴 ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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

        //////////////////////////////////////  네비게이션 관련 ////////////////////////////////////////////////////////////
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.nav_logout) {

                    new AlertDialog.Builder(TClassInfo.this)
                            .setTitle("로그아웃")
                            .setMessage("로그아웃 하시겠습니까?")
                            .setPositiveButton("로그아웃", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //SharedPreferences에 저장된 값들을 로그아웃 버튼을 누르면 삭제하기 위해
                                    //SharedPreferences를 불러옵니다. 메인에서 만든 이름으로
                                    Intent intent = new Intent(TClassInfo.this, TLogin.class);
                                    startActivity(intent);
                                    SharedPreferences auto = getSharedPreferences("setting", Activity.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = auto.edit();
                                    //editor.clear()는 auto에 들어있는 모든 정보를 기기에서 지웁니다.
                                    editor.clear();
                                    editor.commit();
                                    Toast.makeText(TClassInfo.this, "로그아웃.", Toast.LENGTH_SHORT).show();
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
                    new AlertDialog.Builder(TClassInfo.this)
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
                                        Toast.makeText(TClassInfo.this,
                                                "정상적으로 회원탈퇴되었습니다", Toast.LENGTH_SHORT).show();

                                        // 종료하고 학생 메인화면으로
                                        finish();
                                        Intent intent = new Intent(getApplicationContext(), TLogin.class);
                                        startActivity(intent);

                                        finish();

                                    } else {
                                        Toast.makeText(TClassInfo.this, "회원 탈퇴에 실패하였습니다", Toast.LENGTH_SHORT).show();
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






    } //onCreate

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);


    }

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