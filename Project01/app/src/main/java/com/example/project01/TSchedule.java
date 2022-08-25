package com.example.project01;

import static com.example.project01.common.CommonMethod.teacherDTO;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.style.ForegroundColorSpan;
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
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project01.ATask.MemberDelete;
import com.example.project01.ATask.TScheduleAllSelect;
import com.example.project01.ATask.TScheduleSelect;
import com.example.project01.Adapter.TScheduleAdapter;
import com.example.project01.DTO.TScheduleAllDTO;
import com.example.project01.DTO.TScheduleDTO;
import com.google.android.material.navigation.NavigationView;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.format.ArrayWeekDayFormatter;
import com.prolificinteractive.materialcalendarview.format.MonthArrayTitleFormatter;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class TSchedule extends AppCompatActivity {
    static String TAG="확인";

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView nav_view;
    ActionBar actionBar;

    MaterialCalendarView calendarView;
    TextView tv1;

    static ArrayList<TScheduleDTO> dtos;
    static ArrayList<TScheduleAllDTO> dtos2 = new ArrayList<>();
    RecyclerView recyclerView;
    static TScheduleAdapter adapter;

    String state="";


    public static HashMap<String, String> map = new HashMap<String, String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tschedule);

        // 모든 스케줄을 불러와서 dtos2에 저장
        TScheduleAllSelect tScheduleAllSelect = new TScheduleAllSelect(dtos2, teacherDTO.getTeacher_id());

        try{

            tScheduleAllSelect.execute().get();
        }catch (InterruptedException | ExecutionException e) {

        }

//        Log.d(TAG, "길이: " + dtos2.size());
      //  Log.d(TAG, "길이: " + dtos2.get(1).getSchedule_date());
        /*if (dtos2 != null) {
            for (int i = 0; i < dtos2.size(); i++) {
                map.put(dtos2.get(i).getSchedule_date(), "1");
                Log.d(TAG, "진짜 확인: " + dtos2.get(i).getSchedule_date());
            }
        }*/




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


        // 리사이클러뷰, 어댑터 //////////////////////////////////////////////////////////////

        recyclerView = findViewById(R.id.recyclerView);

        // 반드시 만들어서 adapter에 넘겨야 한다
        dtos = new ArrayList<>();
        // recyclerView에서 반드시 아래와 같이 초기화를 해줘야 함
        LinearLayoutManager layoutManager = new LinearLayoutManager
                (TSchedule.this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        // 어댑터 객체 생성
        adapter = new TScheduleAdapter(TSchedule.this, dtos);
        recyclerView.setAdapter(adapter);

        // 서버에 멤버들 ArrayList를 요구해서 가져온다 : AsyncTask 상속 받는 java
        //TScheduleSelect tScheduleSelect = new TScheduleSelect(dtos, adapter );
        //tScheduleSelect.execute();


        // calendarView  ////////////////////////////////////////////////////////////////////////////

        calendarView = findViewById(R.id.calendarview);

        calendarView.state().edit()
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setMinimumDate(CalendarDay.from(2017, 0, 1))
                .setMaximumDate(CalendarDay.from(2030, 11, 31))
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();


        // 월, 요일을 한글로 보이게 설정 (MonthArrayTitleFormatter의 작동을 확인하려면 밑의 setTitleFormatter()를 지운다)
        calendarView.setTitleFormatter(new MonthArrayTitleFormatter(getResources().getTextArray(R.array.custom_months)));
        calendarView.setWeekDayFormatter(new ArrayWeekDayFormatter(getResources().getTextArray(R.array.custom_weekdays)));
        // 오늘 날짜 선택하기
        calendarView.setSelectedDate(CalendarDay.today());
        tv1 = findViewById(R.id.tv1);
        tv1.setText(""+(CalendarDay.today().getMonth()+1) + "월" + CalendarDay.today().getDay() +"일 일정");
        String hmonth = ""+(CalendarDay.today().getMonth()+1);
        if(CalendarDay.today().getMonth()+1 < 10){
            hmonth = "0" + hmonth;
        }
        String hday = ""+CalendarDay.today().getDay();
        if(CalendarDay.today().getDay() < 10){
            hday = "0" + hday;
        }
        String schedule_date = String.valueOf(CalendarDay.today().getYear()+"/" + hmonth+"/"+ hday);
        Log.d(TAG, "오늘날짜: " + schedule_date);
        TScheduleSelect tScheduleSelect = new TScheduleSelect(dtos, adapter, schedule_date, teacherDTO.getTeacher_id());
        tScheduleSelect.execute();




        // 토일에 색상넣기함수
        calendarView.addDecorators(
                new SundayDecorator(),
                new SaturdayDecorator()
        );

        // 캘린터뷰 꾸미기
        calendarView.addDecorators(new DayDecorator(this));






        // 눌렀을 때 날짜 보이게 + 일정도 보이게
        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                tv1.setText((date.getMonth()+1) + "월" + date.getDay() +"일 일정");

                String month = "", day="";
                if(date.getMonth()+1<10) {
                    month = "0" + (date.getMonth() + 1);
                }else{
                    month = "" + (date.getMonth() + 1);
                }
                if(date.getDay()<10) {
                    day = "0" + date.getDay();
                }else{
                    day = "" + date.getDay();
                }

                String schedule_date= date.getYear() +"/"+ month +"/"+ day;
                Log.d(TAG, "schedule_date : " + schedule_date);

                // 날짜 선택시 원래 있던 텍스트 지워줌
               dtos.clear();

                // 서버에 멤버들 ArrayList를 요구해서 가져온다 : AsyncTask 상속 받는 java
               TScheduleSelect tScheduleSelect = new TScheduleSelect(dtos, adapter, schedule_date, teacherDTO.getTeacher_id());
                tScheduleSelect.execute();


            }
        });

        // 버거메뉴 /////////////////////////////////////////////////////////////////////
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

                    new AlertDialog.Builder(TSchedule.this)
                            .setTitle("로그아웃")
                            .setMessage("로그아웃 하시겠습니까?")
                            .setPositiveButton("로그아웃", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //SharedPreferences에 저장된 값들을 로그아웃 버튼을 누르면 삭제하기 위해
                                    //SharedPreferences를 불러옵니다. 메인에서 만든 이름으로
                                    Intent intent = new Intent(TSchedule.this, TLogin.class);
                                    startActivity(intent);
                                    SharedPreferences auto = getSharedPreferences("setting", Activity.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = auto.edit();
                                    //editor.clear()는 auto에 들어있는 모든 정보를 기기에서 지웁니다.
                                    editor.clear();
                                    editor.commit();
                                    Toast.makeText(TSchedule.this, "로그아웃.", Toast.LENGTH_SHORT).show();
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
                    new AlertDialog.Builder(TSchedule.this)
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
                                        Toast.makeText(TSchedule.this,
                                                "정상적으로 회원탈퇴되었습니다", Toast.LENGTH_SHORT).show();

                                        // 종료하고 학생 메인화면으로
                                        finish();
                                        Intent intent = new Intent(getApplicationContext(), TLogin.class);
                                        startActivity(intent);

                                        finish();

                                    } else {
                                        Toast.makeText(TSchedule.this, "회원 탈퇴에 실패하였습니다", Toast.LENGTH_SHORT).show();
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




    // 토요일에 색생넣기
    class SaturdayDecorator implements DayViewDecorator {

        private final Calendar calendar = Calendar.getInstance();

        public SaturdayDecorator() {
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            day.copyTo(calendar);
            int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
            return weekDay == Calendar.SATURDAY;
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.addSpan(new ForegroundColorSpan(Color.BLUE));
        }
    }

    //일요일에 색상넣기
    class SundayDecorator implements DayViewDecorator {

        private final Calendar calendar = Calendar.getInstance();

        public SundayDecorator() {
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            day.copyTo(calendar);
            int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
            return weekDay == Calendar.SUNDAY;
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.addSpan(new ForegroundColorSpan(Color.RED));
        }



    }




    /* 스케줄 있는 요일의 background를 설정하는 Decorator 클래스  ( 우선 보류 )*/
    private static class DayDecorator implements DayViewDecorator {

        private final Calendar calendar = Calendar.getInstance();
        private final Drawable drawable;

        public DayDecorator(Context context) {
            drawable = ContextCompat.getDrawable(context, R.drawable.calendar_selector);
        }

        // true를 리턴 시 모든 요일에 내가 설정한 드로어블이 적용된다
        @Override
        public boolean shouldDecorate(CalendarDay day) {
            day.copyTo(calendar);
            String date = ""+calendar.get(Calendar.DATE);

            int month = calendar.get(Calendar.MONTH);
            int year = calendar.get(Calendar.YEAR);
           String yymmdd = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
          //  Log.d(TAG, "진짜: " + yymmdd);
           // Log.d(TAG, "참: " +month+"-" + date +"-"+ TScheduleAllSelect.map.get("2022-0"+month + "-"+ date));
            String chk = "0";
            if(map.get( yymmdd )== null){
                chk = "0";
            }else{
                chk = "1";
            }

            return chk.equals("1");
        }

        // 일자 선택 시 내가 정의한 드로어블이 적용되도록 한다
        @Override
        public void decorate(DayViewFacade view) {

           // view.addSpan(new StyleSpan(Typeface.BOLD));   // 달력 안의 모든 숫자들이 볼드 처리됨
           //view.addSpan(new ForegroundColorSpan(Color.GREEN));
            //view.setBackgroundDrawable(drawable);
            view.addSpan(new DotSpan(10, Color.RED));
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




}// class


