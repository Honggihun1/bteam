package com.example.project01.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project01.DTO.StudentDTO;
import com.example.project01.R;

import java.util.ArrayList;

public class TCheckAdapter extends RecyclerView.Adapter<TCheckAdapter.ViewHolder> {

    // 3. 메인한테 넘겨 받는것
    private static final String TAG = "확인";
    Context context;
    ArrayList<StudentDTO> student_name;
    String date;
    ArrayList<StudentDTO> dtos = new ArrayList<>();
    LayoutInflater inflater;

    // 4. 생성자를 만들어 메인에게서 넘겨받은것을 연결
    public TCheckAdapter(Context context, ArrayList<StudentDTO> student_name) {
        this.context = context;
        //this.dtos = dtos;
        this.student_name = student_name;


        inflater = LayoutInflater.from(this.context);

    }




    // 5. 메소드는 여기에 만든다
    // dtos에 dto를 추가하는 매소드
    public void addDto(StudentDTO dto){
        dtos.add(dto);
    }



    // 6. 화면을 인플레이트 시킨다
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.checkinview,
                parent, false);

        return new ViewHolder(itemView);
    }



    // 7. 인플레이트 시킨 화면에 데이터를 셋팅시킨다
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        StudentDTO dto = dtos.get(position);
        holder.setDto(dto);
    }

    @Override
    public int getItemCount() {
        return dtos.size();
    }


    // 1. xml 파일에서 사용된 모든변수와 사용할 레이아웃을 클래스에서 선언한다 : 별 5개
    public class ViewHolder extends RecyclerView.ViewHolder{
        // 1-1. checkinview.xml 에 사용된 모든 위젯을 정의한다
        TextView name;
        LinearLayout parentLayout;

        // 1-2. checkinview.xml에서 정의한 아이디를 찾아 연결시킨다(생성자)
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            parentLayout = itemView.findViewById(R.id.parentLayout);
            name = itemView.findViewById(R.id.name);

        }

        // 1-3. 함수를 만들어서 singerview에 데이터를 연결시킨다
        public void setDto(StudentDTO dto){
            name.setText(dto.getStudent_name());
            Log.d(TAG, "student: " + dto.getStudent_name());

        }


    }


}
