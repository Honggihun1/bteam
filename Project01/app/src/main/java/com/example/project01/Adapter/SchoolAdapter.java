package com.example.project01.Adapter;

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

import com.example.project01.DTO.SchoolDTO;
import com.example.project01.FindSchool;
import com.example.project01.R;

import java.util.ArrayList;

public class SchoolAdapter extends RecyclerView.Adapter<SchoolAdapter.ViewHolder> implements FindSchool.OnSchoolItemClickListener {
    static String TAG ="확인";

    // 3. 리스너 선언 : 메인에서 접근할 수 있는 리스너  : 3번부터는 메인에서 실행할 때 수행되는 것
    FindSchool.OnSchoolItemClickListener listener;



    // 3. 메인한테 넘겨 받는것
    Context context;
    ArrayList<SchoolDTO> dtos;

    LayoutInflater inflater;

    // 4. 생성자를 만들어 메인에게서 넘겨받은것을 연결
    public SchoolAdapter(Context context, ArrayList<SchoolDTO> dtos) {
        this.context = context;
        this.dtos = dtos;
        inflater = LayoutInflater.from(this.context);
    }


    // 4. 메인에서 리스트 dto를 클릭했을 때 위에서 선언한 3번 리스너와 연결해준다
    public void setOnItemClickListener(FindSchool.OnSchoolItemClickListener listener){
        this.listener = listener;

    }

    // 6. 메인에서 클릭한 위치에 있는 DTO 를 가져온다
    public SchoolDTO getItem(int position){
        return dtos.get(position);
    }





    // 6. 화면을 인플레이트 시킨다
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.schoolview, parent, false);

        return new ViewHolder(itemView, this);
    }




    // 7. 인플레이트 시킨 화면에 데이터를 셋팅시킨다
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // dtos에 있는 데이터를 순서대로 불러온다
        SchoolDTO dto = dtos.get(position); // 다섯개라고 가정하면 position은 0~4
        // 불러온 데이터를 ViewHolder에 만들어 놓은 setDto를 사용하여 셋팅한다
        holder.setDto(dto);
    }

    @Override
    public int getItemCount() {
        return  dtos.size();
    }

    @Override
    public void onItemClick(ViewHolder holder, View view, int position) {
        if(listener != null){
            listener.onItemClick(holder, view, position);
        }

    }

    // 1. xml 파일에서 사용된 모든변수와 사용할 레이아웃을 클래스에서 선언한다 : 별 5개
    public class ViewHolder extends RecyclerView.ViewHolder{
        // 1-1. singerview.xml 에 사용된 모든 위젯을 정의한다
        TextView tv1, tv2;
        LinearLayout parentlayout;

        // 1-2. schedleview.xml에서 정의한 아이디를 찾아 연결시킨다(생성자)
        public ViewHolder(@NonNull View itemView, FindSchool.OnSchoolItemClickListener listener) {
            super(itemView);

            tv1 = itemView.findViewById(R.id.tv1);

            // 학교명이 길 경우 흐르게 하기
            tv1.setSingleLine(true);    //한줄로 나오게 하기.
            tv1.setEllipsize(TextUtils.TruncateAt.MARQUEE);//Ellipsize의 MARQUEE 속성 주기
            tv1.setSelected(true);      //해당 텍스트뷰에 포커스가 없어도 문자 흐르게 하기


            tv2 = itemView.findViewById(R.id.tv2);
            parentlayout = itemView.findViewById(R.id.parentlayout);

            // 2-1. 클릭리스너 구현
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(listener != null){
                        listener.onItemClick(ViewHolder.this, view, position);
                    }
                }
            });


        }

        // 1-3. 함수를 만들어서 singerview에 데이터를 연결시킨다
        public void setDto(SchoolDTO dto){
            tv1.setText(dto.getSchool_name());
            tv2.setText(dto.getSchool_location());
            Log.d(TAG, "location: " + dto.getSchool_location());

        }
    }



}
