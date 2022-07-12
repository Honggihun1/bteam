package com.example.project01;

import static com.example.project01.common.CommonMethod.teacherDTO;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project01.ATask.TScheduleSelect;
import com.example.project01.Adapter.TScheduleAdapter;
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

import java.util.ArrayList;
import java.util.Calendar;

public class TSchedule extends AppCompatActivity {
    static String TAG="확인";

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView nav_view;
    ActionBar actionBar;

    MaterialCalendarView calendarView;
    TextView tv1;

    ArrayList<TScheduleDTO> dtos;
    RecyclerView recyclerView;
    TScheduleAdapter adapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tschedule);


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
        String schedule_date = String.valueOf(CalendarDay.today().getYear()+"/"+(CalendarDay.today().getMonth()+1)+"/"+CalendarDay.today().getDay());
        Log.d(TAG, "오늘날짜: " + schedule_date);
        TScheduleSelect tScheduleSelect = new TScheduleSelect(dtos, adapter, schedule_date, teacherDTO.getTeacher_id());
        tScheduleSelect.execute();




        // 토일에 색상넣기함수
        calendarView.addDecorators(
                new SundayDecorator(),
                new SaturdayDecorator()
        );


        // 눌렀을 때 날짜 보이게 + 일정도 보이게
        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                tv1.setText((date.getMonth()+1) + "월" + date.getDay() +"일 일정");

                String schedule_date= date.getYear() +"/"+ (date.getMonth()+1) +"/"+ date.getDay();
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

        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.nav_logout) {
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




}// class


