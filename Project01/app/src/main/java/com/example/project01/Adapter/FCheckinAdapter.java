package com.example.project01.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project01.DTO.FCheckinDTO;
import com.example.project01.R;

import java.util.ArrayList;

public class FCheckinAdapter extends RecyclerView.Adapter<FCheckinAdapter.ViewHolder> {
    static String TAG = "확인";

    // 3. 메인한테 넘겨 받는것
    Context context;
    ArrayList<FCheckinDTO> dtos;

    LayoutInflater inflater;

    // 4. 생성자를 만들어 메인에게서 넘겨받은것을 연결
    public FCheckinAdapter(Context context, ArrayList<FCheckinDTO> dtos) {
        this.context = context;
        this.dtos = dtos;
        inflater = LayoutInflater.from(this.context);
    }


    // 6. 화면을 인플레이트 시킨다
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.scheduleview, parent, false);

        return new ViewHolder(itemView);
    }

    // 7. 인플레이트 시킨 화면에 데이터를 셋팅시킨다
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // dtos에 있는 데이터를 순서대로 불러온다
        FCheckinDTO dto = dtos.get(position); // 다섯개라고 가정하면 position은 0~4
        // 불러온 데이터를 ViewHolder에 만들어 놓은 setDto를 사용하여 셋팅한다
        holder.setDto(dto);
    }




    @Override
    public int getItemCount() {
        return dtos.size();
    }


    // 1. xml 파일에서 사용된 모든변수와 사용할 레이아웃을 클래스에서 선언한다 : 별 5개
    public class ViewHolder extends RecyclerView.ViewHolder{
        // 1-1. singerview.xml 에 사용된 모든 위젯을 정의한다
        TextView tv1;

        // 1-2. schedleview.xml에서 정의한 아이디를 찾아 연결시킨다(생성자)
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv1 = itemView.findViewById(R.id.tv1);

        }

        // 1-3. 함수를 만들어서 singerview에 데이터를 연결시킨다
        public void setDto(FCheckinDTO dto){
            Log.d(TAG, "chekin_hour: " + dto.getCheckin_hour());
            Log.d(TAG, "chekout_hour: " + dto.getCheckout_hour());

            String checkout="";

            if(dto.getCheckout_hour().length()<10){
                checkout= "미퇴실";
            }else{
                checkout = dto.getCheckout_hour().substring(11);
            }

            String checkin = dto.getCheckin_hour().substring(11);

            tv1.setText(checkin + " ~ " + checkout);

        }
    }
}
