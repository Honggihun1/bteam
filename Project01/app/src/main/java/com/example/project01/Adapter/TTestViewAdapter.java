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

import com.example.project01.DTO.TTestDTO;
import com.example.project01.R;
import com.example.project01.TTestSend;

import java.util.ArrayList;

public class TTestViewAdapter extends RecyclerView.Adapter<TTestViewAdapter.ViewHolder>{
    private static final String TAG = "main:TTextViewAdapter";

    Context context;
    ArrayList<TTestDTO> dtos;
    LayoutInflater inflater;

    public TTestViewAdapter(Context context, ArrayList<TTestDTO> dtos) {
        this.context = context;
        this.dtos = dtos;

        inflater = LayoutInflater.from(this.context);
    }
    //------------------------------------------------------------
    //dtos에 dto를 추가하는 메소드
    public void addDto(TTestDTO dto){
        dtos.add(dto);
    }


    //------------------------------------------------------------

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.listview_test, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TTestDTO dto = dtos.get(position);
        holder.setDto(dto);

        holder.btnTestSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context.getApplicationContext(), TTestSend.class);
                intent.putExtra("classname", dto.getClass_name());
                intent.putExtra("test", dto.getTest_name());
                intent.putExtra("avg", dto.getTest_avg());
                intent.putExtra("max", dto.getTest_max());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dtos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvClassname, tvTestname, tvSubmit, tvAvg, tvMax;
        ImageButton btnTestSend;
        LinearLayout parentTestLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parentTestLayout = itemView.findViewById(R.id.parentTestLayout);
            tvClassname = itemView.findViewById(R.id.tvClassname);
            tvTestname = itemView.findViewById(R.id.tvTestname);
            tvSubmit = itemView.findViewById(R.id.tvSubmit);
            tvAvg = itemView.findViewById(R.id.tvAvg);
            tvMax = itemView.findViewById(R.id.tvMax);
            btnTestSend = itemView.findViewById(R.id.btnTestSend);

            tvClassname.setSingleLine(true);    //한줄로 나오게 하기.
            tvClassname.setEllipsize(TextUtils.TruncateAt.MARQUEE);//Ellipsize의 MARQUEE 속성 주기
            tvClassname.setSelected(true);      //해당 텍스트뷰에 포커스가 없어도 문자 흐르게 하기

            tvTestname.setSingleLine(true);    //한줄로 나오게 하기.
            tvTestname.setEllipsize(TextUtils.TruncateAt.MARQUEE);//Ellipsize의 MARQUEE 속성 주기
            tvTestname.setSelected(true);      //해당 텍스트뷰에 포커스가 없어도 문자 흐르게 하기
        }

        public void setDto(TTestDTO dto){
            tvClassname.setText(dto.getClass_name());
            tvTestname.setText(dto.getTest_name());
            tvSubmit.setText(dto.getApp_num());
            tvSubmit.setText(dto.getApp_num()+"/"+dto.getAll_stu());
            tvAvg.setText("" + dto.getTest_avg());
            tvMax.setText("" + dto.getTest_max());
        }

    }
}
