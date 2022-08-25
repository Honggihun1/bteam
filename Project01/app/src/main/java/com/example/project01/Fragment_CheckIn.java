package com.example.project01;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project01.ATask.FCheckinAllSelect;
import com.example.project01.ATask.FCheckinSelect;
import com.example.project01.Adapter.FCheckinAdapter;
import com.example.project01.DTO.FCheckinAllDTO;
import com.example.project01.DTO.FCheckinDTO;
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

public class Fragment_CheckIn extends Fragment {

    static String TAG="확인";

    public String readDay = null;
    public String str = null;
    public MaterialCalendarView calendarView;
    public TextView diaryTextView, textView2, textView3;
    public EditText contextEditText;
    RecyclerView fragment_checkin_recyclerview;
    static ArrayList<FCheckinDTO> dtos;
    static FCheckinAdapter adapter;
    static ArrayList<FCheckinAllDTO> dtos2 = new ArrayList<>();
    String student_id = "";

    public static HashMap<String, String> map = new HashMap<String, String>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_check_in,
                container, false);

        Bundle bundle = getArguments();

        if(bundle != null){
            student_id = bundle.getString("student_id");
        }


        // 모든 스케줄을 불러와서 dtos2에 저장
        FCheckinAllSelect fCheckinAllSelect = new FCheckinAllSelect(dtos2, student_id);

        try{

            fCheckinAllSelect.execute().get();
        }catch (InterruptedException | ExecutionException e) {

        }








        calendarView = viewGroup.findViewById(R.id.calendarView);
        diaryTextView = viewGroup.findViewById(R.id.diaryTextView);
        textView3 = viewGroup.findViewById(R.id.textView3);
        fragment_checkin_recyclerview = viewGroup.findViewById(R.id.fragment_checkin_recyclerview);

        // 반드시 만들어서 adapter에 넘겨야 한다
        dtos = new ArrayList<>();

        // recyclerView에서 반드시 아래와 같이 초기화를 해줘야 함
        LinearLayoutManager layoutManager = new LinearLayoutManager
                (getContext(), RecyclerView.VERTICAL, false);
        fragment_checkin_recyclerview.setLayoutManager(layoutManager);

        // 어댑터 객체 생성
        adapter = new FCheckinAdapter(getContext(), dtos);
        fragment_checkin_recyclerview.setAdapter(adapter);

        calendarView = viewGroup.findViewById(R.id.calendarView);

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
        diaryTextView = viewGroup.findViewById(R.id.diaryTextView);
        diaryTextView.setText(""+(CalendarDay.today().getMonth()+1) + "월" + CalendarDay.today().getDay() +"일 출결");
        String schedule_date = String.valueOf(CalendarDay.today().getYear()+"/"+(CalendarDay.today().getMonth()+1)+"/"+CalendarDay.today().getDay());
        Log.d(TAG, "오늘날짜: " + schedule_date);
        FCheckinSelect fCheckinSelect = new FCheckinSelect(dtos, adapter, schedule_date, student_id);
        fCheckinSelect.execute();

        // 토일에 색상넣기함수
        calendarView.addDecorators(
                new SundayDecorator(),
                new SaturdayDecorator()
        );

        // 캘린터뷰 꾸미기
        calendarView.addDecorators(new DayDecorator(getContext()));





        // 눌렀을 때 날짜 보이게 + 일정도 보이게
        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                diaryTextView.setText((date.getMonth()+1) + "월" + date.getDay() +"일 출결");

                String schedule_date= date.getYear() +"/"+ (date.getMonth()+1) +"/"+ date.getDay();
                Log.d(TAG, "student_id : " + student_id);


                // 날짜 선택시 원래 있던 텍스트 지워줌
                dtos.clear();

                // 서버에 멤버들 ArrayList를 요구해서 가져온다 : AsyncTask 상속 받는 java
                FCheckinSelect fCheckinSelect = new FCheckinSelect(dtos, adapter, schedule_date, student_id);
                fCheckinSelect.execute();


            }
        });

        return viewGroup;
    }

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
            view.addSpan(new DotSpan(7, Color.RED));
        }
    }


}
