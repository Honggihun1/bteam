package com.example.project01.Adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project01.DTO.THomeworkDTO;
import com.example.project01.R;
import com.example.project01.THomeworkSend;

import java.util.ArrayList;

public class THomeworkViewAdapter extends RecyclerView.Adapter<THomeworkViewAdapter.ViewHolder>{
    private static final String TAG = "main:HomeworkViewAdapter";

    //메인한테 넘겨받는 것
    Context context;
    ArrayList<THomeworkDTO> dtos;
    LayoutInflater inflater;

    //생성자를 만들어 메인에게서 넘겨받은 것을 연결해준다.


    public THomeworkViewAdapter(Context context, ArrayList<THomeworkDTO> dtos) {
        this.context = context;
        this.dtos = dtos;

        inflater = LayoutInflater.from(this.context);
    }

    public THomeworkViewAdapter(ArrayList<THomeworkDTO> dtos) {

    }

    //------------------------------------------------
    //dtos에 dto를 추가하는 메소드
    public void addDto(THomeworkDTO dto){
        dtos.add(dto);
    }

    //dtos의 특정위치에 dto를 삭제하는 메소드
    public void delDto(int position){
        dtos.remove(position);
    }

    //------------------------------------------------

    //화면을 inflate 시킨다.
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.listview_homework, parent, false);
        return new ViewHolder(itemView);
    }

    //inflate 시킨 화면에 데이터를 세팅시킨다.
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        //dtos에 있는 데이터를 순서대로 불러온다.
        THomeworkDTO dto = dtos.get(position);
        //불러온 데이터를 ViewHolder에 만들어 놓은 setDto를 사용하여 세팅한다.
        holder.setDto(dto);

        //문자발송을 문자발송 페이지로 이동...
        holder.btnHomeworkSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context.getApplicationContext(), THomeworkSend.class);
                intent.putExtra("classname", dto.getClass_name());
                intent.putExtra("homework", dto.getHomework_name());
                intent.putExtra("avg", dto.getHomework_avg());
                intent.putExtra("max", dto.getHomework_max());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dtos.size();
    }

    
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvClassname, tvHomeworkname, tvSubmit, tvAll, tvAvg, tvMax;
        ImageButton btnHomeworkSend;
        LinearLayout parentHomeworkLayout;
        public ViewHolder(@NonNull View itemView){
            super(itemView);

            parentHomeworkLayout = itemView.findViewById(R.id.parentHomeworkLayout);
            tvClassname = itemView.findViewById(R.id.tvClassname);
            tvHomeworkname = itemView.findViewById(R.id.tvHomeworkname);
            tvSubmit = itemView.findViewById(R.id.tvSubmit);
            //tvAll = itemView.findViewById(R.id.tvAll);
            tvAvg = itemView.findViewById(R.id.tvAvg);
            tvMax = itemView.findViewById(R.id.tvMax);
            btnHomeworkSend = itemView.findViewById(R.id.btnHomeworkSend);

            tvClassname.setSingleLine(true);    //한줄로 나오게 하기.
            tvClassname.setEllipsize(TextUtils.TruncateAt.MARQUEE);//Ellipsize의 MARQUEE 속성 주기
            tvClassname.setSelected(true);      //해당 텍스트뷰에 포커스가 없어도 문자 흐르게 하기

            tvHomeworkname.setSingleLine(true);    //한줄로 나오게 하기.
            tvHomeworkname.setEllipsize(TextUtils.TruncateAt.MARQUEE);//Ellipsize의 MARQUEE 속성 주기
            tvHomeworkname.setSelected(true);      //해당 텍스트뷰에 포커스가 없어도 문자 흐르게 하기
        }

        public void setDto(THomeworkDTO dto){
            tvClassname.setText(dto.getClass_name());
            tvHomeworkname.setText(dto.getHomework_name());

            tvSubmit.setText("");
            tvSubmit.setText(dto.getSub_num()+"/"+dto.getAll_stu());
            //tvAll.setText(dto.getAll_stu());
            tvAvg.setText("" + dto.getHomework_avg());
            tvMax.setText("" + dto.getHomework_max());
        }

    }


}
