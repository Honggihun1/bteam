package com.example.project01;

import static com.example.project01.common.CommonMethod.studentDTO;
import static com.example.project01.common.CommonMethod.teacherDTO;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.example.project01.ATask.TCheckOutSelect;
import com.example.project01.ATask.TCheckSelect;
import com.example.project01.Adapter.TCheckAdapter;
import com.example.project01.DTO.StudentDTO;
import com.google.android.material.navigation.NavigationView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class TCheck extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "main:JoinActivity";

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    ActionBar actionBar;
    EditText etNumPad;
    TextView Show_Time;
    String state="";
    String phone="",date="",hour="";
    String student_name = "";
    NavigationView nav_view;


    ArrayList<StudentDTO> dtos = new ArrayList<>();

    RadioButton rbIn,rbOut;
    RadioGroup radioGroup;

    RecyclerView checkRecy;
    TCheckAdapter adapter;


    private TextView Show_Time_TextView;
    ImageButton btn[] = new ImageButton[13];
    EditText userinput;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tcheck);

        etNumPad = findViewById(R.id.etNumPad);
        Show_Time = findViewById(R.id.Show_Time);


        checkRecy = findViewById(R.id.checkRecy);
        // recyclerView에서 반드시 아래와 같이 초기화를 해줘야 함
        LinearLayoutManager layoutManager = new LinearLayoutManager
                (this, RecyclerView.VERTICAL, false);
        checkRecy.setLayoutManager(layoutManager);


        // 어댑터 관련 처리///////////////////////////////////////

        // 어댑터 초기화
       // String student_name= studentDTO.getStudent_name();

        // 어댑터 객체 생성
        adapter = new TCheckAdapter(TCheck.this, dtos);


        // 만든 어댑터를 리싸이클러뷰에 붙인다
        checkRecy.setAdapter(adapter);


        // 어댑터////////////////////////////////////////////////////////

        //라디오 체크 버튼 초기화
        rbIn = findViewById(R.id.rbIn);
        rbOut = findViewById(R.id.rbOut);
        radioGroup = findViewById(R.id.radioGroup);


        // 키패드 버튼 초기화
        btn[0] = (ImageButton)findViewById(R.id.button1);
        btn[1] = (ImageButton)findViewById(R.id.button2);
        btn[2] = (ImageButton)findViewById(R.id.button3);
        btn[3] = (ImageButton)findViewById(R.id.button4);
        btn[4] = (ImageButton)findViewById(R.id.button5);
        btn[5] = (ImageButton)findViewById(R.id.button6);
        btn[6] = (ImageButton)findViewById(R.id.button7);
        btn[7] = (ImageButton)findViewById(R.id.button8);
        btn[8] = (ImageButton)findViewById(R.id.button9);
        btn[9] = (ImageButton)findViewById(R.id.button0);
        btn[10] = (ImageButton)findViewById(R.id.completebutton);
        btn[11] = (ImageButton)findViewById(R.id.deletebutton);
        btn[12] = (ImageButton)findViewById(R.id.clearbutton);

        // 키패드 버튼 클릭이벤트
        for(int i =0;i<13;i++){
            btn[i].setOnClickListener(this);
        }

        // 시간보여주기
        Show_Time_TextView = (TextView) findViewById(R.id.Show_Time);
        ShowTimeMethod();

        // toolbar 적용
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);  // 내가 만든 바를 액션바로 지정
        drawerLayout = findViewById(R.id.drawerLayout);

        actionBar = getSupportActionBar();
        ActionBar actionBar = getSupportActionBar();

        // 원래 있던 제목(Project01) 안보이게 해줌
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

                    new AlertDialog.Builder(TCheck.this)
                            .setTitle("로그아웃")
                            .setMessage("로그아웃 하시겠습니까?")
                            .setPositiveButton("로그아웃", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //SharedPreferences에 저장된 값들을 로그아웃 버튼을 누르면 삭제하기 위해
                                    //SharedPreferences를 불러옵니다. 메인에서 만든 이름으로
                                    Intent intent = new Intent(TCheck.this, TLogin.class);
                                    startActivity(intent);
                                    SharedPreferences auto = getSharedPreferences("setting", Activity.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = auto.edit();
                                    //editor.clear()는 auto에 들어있는 모든 정보를 기기에서 지웁니다.
                                    editor.clear();
                                    editor.commit();
                                    Toast.makeText(TCheck.this, "로그아웃.", Toast.LENGTH_SHORT).show();
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
                    new AlertDialog.Builder(TCheck.this)
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
                                        Toast.makeText(TCheck.this,
                                                "정상적으로 회원탈퇴되었습니다", Toast.LENGTH_SHORT).show();

                                        // 종료하고 학생 메인화면으로
                                        finish();
                                        Intent intent = new Intent(getApplicationContext(), TLogin.class);
                                        startActivity(intent);

                                        finish();

                                    } else {
                                        Toast.makeText(TCheck.this, "회원 탈퇴에 실패하였습니다", Toast.LENGTH_SHORT).show();
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

        }); // 네비







    }//onCreate();

    
    //showTime 메소드
    public void ShowTimeMethod() {
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
               /* Show_Time_TextView.setText(DateFormat.getDateTimeInstance(DateFormat.SHORT, TimeFormat.CLOCK_24H,Locale.KOREA).
                        format(new Date()));*/
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                Show_Time_TextView.setText(dateFormat.format(new Date()));

            }
        };
        Runnable task = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {}
                    handler.sendEmptyMessage(1);
                }
            }
        };
        Thread thread = new Thread(task);
        thread.start();
    }



    //onClick 메소드
    public void onClick(View v){
        switch(v.getId()){
            case R.id.button1:
                addtoarray("1");
                break;
            case R.id.button2:
                addtoarray("2");
                break;
            case R.id.button3:
                addtoarray("3");
                break;
            case R.id.button4:
                addtoarray("4");
                break;
            case R.id.button5:
                addtoarray("5");
                break;
            case R.id.button6:
                addtoarray("6");
                break;
            case R.id.button7:
                addtoarray("7");
                break;
            case R.id.button8:
                addtoarray("8");
                break;
            case R.id.button9:
                addtoarray("9");
                break;
            case R.id.button0:
                addtoarray("0");
                break;
            case R.id.completebutton:

                int slength = 0;

                if(userinput != null){
                    slength = userinput.length();
                }

                if( slength > 7) {
                    if(rbIn.isChecked()){

                        String parent_phone = etNumPad.getText().toString();
                        phone = "010-" + parent_phone.substring(0, 4) + "-" + parent_phone.substring(4, 8);
                        date = Show_Time.getText().toString().substring(0, 10);
                        hour = Show_Time.getText().toString();
                        //String student_name= studentDTO.getStudent_name();

                        studentDTO = null;
                        // 서버로 데이터를 보낸다 : AsyncTask를 상속받는 java파일을 만든다
                        TCheckSelect tCheckSelect = new TCheckSelect(phone, date, hour);
                        try {
                            state = tCheckSelect.execute().get();
                            Log.d(TAG, "state: " + state);

                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }


                        state = state.trim();
                        // 정상적으로 데이터베이스에 삽입이 되면 1을 리턴, 아니면 0이하수를 리턴
                        if (studentDTO != null) {
                            Toast.makeText(TCheck.this,
                                    "출결성공", Toast.LENGTH_SHORT).show();
                            etNumPad.setText("");

                            adapter.addDto(new StudentDTO(studentDTO.getStudent_name()+"님 " + Show_Time.getText().toString()+ " 입실완료"));
                            adapter.notifyDataSetChanged();
                            checkRecy.scrollToPosition(adapter.getItemCount()-1);


                            // finish();
                        } else {
                            Toast.makeText(TCheck.this,
                                    "출결실패", Toast.LENGTH_SHORT).show();
                            etNumPad.setText("");
                        }




                    }else if(rbOut.isChecked()){

                        String parent_phone = etNumPad.getText().toString();
                        phone = "010-" + parent_phone.substring(0, 4) + "-" + parent_phone.substring(4, 8);
                        date = Show_Time.getText().toString().substring(0, 10);
                        hour = Show_Time.getText().toString();


                        studentDTO = null;
                        // 서버로 데이터를 보낸다 : AsyncTask를 상속받는 java파일을 만든다
                        TCheckOutSelect tCheckOutSelect = new TCheckOutSelect(phone);
                        try {
                            state = tCheckOutSelect.execute().get();
                            Log.d(TAG, "state: " + state);
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        state = state.trim();
                        // 정상적으로 데이터베이스에 삽입이 되면 1을 리턴, 아니면 0이하수를 리턴
                        if (studentDTO != null) {
                            Toast.makeText(TCheck.this,
                                    "퇴실성공", Toast.LENGTH_SHORT).show();
                            etNumPad.setText("");

                            adapter.addDto(new StudentDTO(studentDTO.getStudent_name()+"님 " + Show_Time.getText().toString()+ " 퇴실완료"));
                            adapter.notifyDataSetChanged();
                            checkRecy.scrollToPosition(adapter.getItemCount()-1);
                            // finish();
                        } else {
                            Toast.makeText(TCheck.this,
                                    "퇴실실패", Toast.LENGTH_SHORT).show();
                            etNumPad.setText("");

                        }






                    }

                }else{
                    userinput = (EditText)findViewById(R.id.etNumPad);
                    userinput.setText("");
                    Toast.makeText(TCheck.this, "8자리숫자를 입력해주세요", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.deletebutton:
                //get the length of input
                slength = 0;

                if(userinput != null){
                    slength = userinput.length();
                }


                if(slength > 0 ){
                    //get the last character of the input
                    String selection = userinput.getText().toString().substring(0, slength-1);
                    Log.e("Selection",selection);

                    userinput.setText(selection);
                    userinput.setSelection(userinput.getText().length());

                }else{
                    userinput = (EditText)findViewById(R.id.etNumPad);
                    userinput.setText("");
                    Toast.makeText(TCheck.this, "숫자를 입력해주세요", Toast.LENGTH_SHORT).show();

                }

                break;
            case R.id.clearbutton:
                userinput = (EditText)findViewById(R.id.etNumPad);
                userinput.setText("");
                break;
            default:
                
        }
        
    }//onClick();

    //addtoarray 메소드
    public void addtoarray(String numbers){
        //register TextBox
        userinput = (EditText)findViewById(R.id.etNumPad);
        userinput.append(numbers);
        
    }//addtoarray();

}// TCheck