package com.example.project01.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project01.DTO.C_infoDTO;
import com.example.project01.R;
import com.example.project01.TStudentInfo;

import java.util.ArrayList;

public class C_infoAdapter extends RecyclerView.Adapter<C_infoAdapter.ViewHolder> {

    // 3. 메인한테 넘겨 받는것
    Context context;
    ArrayList<C_infoDTO> dtos;
    LayoutInflater inflater;

    // 4. 생성자를 만들어 메인에게서 넘겨받은것을 연결
    public C_infoAdapter(Context context, ArrayList<C_infoDTO> dtos) {
        this.context = context;
        this.dtos=dtos;

        inflater = LayoutInflater.from(this.context);
    }




    // 5. 메소드는 여기에 만든다
    // dtos에 dto를 추가하는 매소드
    public void addDto(C_infoDTO dto){
        dtos.add(dto);
    }

    // dtos의 특정위치에 dto를 삭제하는 매소드
    public void delDto(int position){
        dtos.remove(position);
    }

    // 6. 화면을 인플레이트 시킨다
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.c_infoview,
                parent, false);

        return new ViewHolder(itemView);


    }

    @Override
    public int getItemCount() {
        return dtos.size();
    }


    // 7. 인플레이트 시킨 화면에 데이터를 셋팅시킨다
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        C_infoDTO dto = dtos.get(position);
        holder.setDto(dto);
    }

    // 1. xml 파일에서 사용된 모든변수와 사용할 레이아웃을 클래스에서 선언한다 : 별 5개
    public class ViewHolder extends RecyclerView.ViewHolder{
        // 1-1. singerview.xml 에 사용된 모든 위젯을 정의한다
        TextView name, school, parent,chk;



        // 1-2. singerview.xml에서 정의한 아이디를 찾아 연결시킨다(생성자)
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            school = itemView.findViewById(R.id.school);
            parent = itemView.findViewById(R.id.parent);
            chk = itemView.findViewById(R.id.chk);

            school.setSingleLine(true);    //한줄로 나오게 하기.
            school.setEllipsize(TextUtils.TruncateAt.MARQUEE);//Ellipsize의 MARQUEE 속성 주기
            school.setSelected(true);      //해당 텍스트뷰에 포커스가 없어도 문자 흐르게 하기




            // 카드뷰 눌렀을 때 클릭 이벤트( 다른 페이지 연결)
            itemView.setClickable(true);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        Intent intent = new Intent(context, TStudentInfo.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

                        intent.putExtra("student_id",dtos.get(pos).getStudent_id());

                        context.startActivity(intent);
                    }

                }
            });



        }

        // 1-3. 함수를 만들어서 singerview에 데이터를 연결시킨다
        public void setDto(C_infoDTO dto){
            name.setText(dto.getStudent_name());
            school.setText(dto.getSchool_name());
            parent.setText(dto.getParent_phone());
            chk.setText(dto.getChk());
        }

    }


    public void setItems(ArrayList<C_infoDTO> list){
        dtos = list;
        notifyDataSetChanged();
    }

}
