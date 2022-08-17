package com.example.project01;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project01.DTO.HomeworkDTO;

import java.util.ArrayList;

public class HomeworkAdapter extends
        RecyclerView.Adapter<HomeworkAdapter.ViewHoler> {

    private static final String TAG = "main:HomeworkAdapter";
    // 3. 메인한테 넘겨 받는것
    Context context;
    ArrayList<HomeworkDTO> dtos;

    LayoutInflater inflater;

    // 4. 생성자를 만들어 메인에게서 넘겨받은것을 연결
    public HomeworkAdapter(Context context, ArrayList<HomeworkDTO> dtos) {
        this.context = context;
        this.dtos = dtos;

        inflater = LayoutInflater.from(this.context);
    }

    //// dtos에 dto를 추가하는 매소드(삭제할것)
    public void addDto(HomeworkDTO dto){
        dtos.add(dto);
    }

    // 6. 화면을 인플레이트 시킨다
    @NonNull
    @Override
    public ViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.homework_list,
                parent, false);

        return new ViewHoler(itemView, this);
    }

    // 7. 인플레이트 시킨 화면에 데이터를 셋팅시킨다
    @Override
    public void onBindViewHolder(@NonNull ViewHoler holder, @SuppressLint("RecyclerView") int position) {
        Log.d(TAG, "onBindViewHolder: " + position);

        HomeworkDTO dto = dtos.get(position);
        holder.setDto(dto);
    }

    @Override
    public int getItemCount() {
        return dtos.size();
    }

    // 1. xml 파일에서 사용된 모든변수와 사용할 레이아웃을 클래스에서 선언한다
    public class ViewHoler extends RecyclerView.ViewHolder{
        TextView homework_name, homework_date, homework_done, homework_score;
        LinearLayout homework_list;
        // 1-2. homework_list.xml에서 정의한 아이디를 찾아 연결시킨다(생성자)
        public ViewHoler(@NonNull View itemView, HomeworkAdapter listener) {
            super(itemView);

            homework_list = itemView.findViewById(R.id.homework_list);
            homework_name = itemView.findViewById(R.id.homework_name);
            homework_date = itemView.findViewById(R.id.homework_sub_date);
            homework_done = itemView.findViewById(R.id.homework_done);
            homework_score = itemView.findViewById(R.id.homework_score);

            homework_name.setSingleLine(true);    //한줄로 나오게 하기.
            homework_name.setEllipsize(TextUtils.TruncateAt.MARQUEE);//Ellipsize의 MARQUEE 속성 주기
            homework_name.setSelected(true);      //해당 텍스트뷰에 포커스가 없어도 문자 흐르게 하기

        }
        // 1-3. 함수를 만들어서 homework_list 데이터를 연결시킨다
        public void setDto(HomeworkDTO dto){
            homework_name.setText(dto.getHomework_name());
            Log.d(TAG, "3 : " + dto.getHomework_name());
            homework_score.setText(dto.getHomework_score());
            Log.d(TAG, "3: "+dto.getHomework_score());
            homework_done.setText(dto.getHomework_done());
            homework_date.setText(dto.getHomework_sub_date());
        }

    }


}
