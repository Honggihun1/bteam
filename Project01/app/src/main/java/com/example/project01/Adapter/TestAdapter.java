package com.example.project01.Adapter;

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

import com.example.project01.DTO.TestDTO;
import com.example.project01.R;

import java.util.ArrayList;

public class TestAdapter extends
        RecyclerView.Adapter<TestAdapter.ViewHoler> {

    private static final String TAG = "main:TestAdapter";
    // 3. 메인한테 넘겨 받는것
    Context context;
    ArrayList<TestDTO> dtos;

    LayoutInflater inflater;

    // 4. 생성자를 만들어 메인에게서 넘겨받은것을 연결
    public TestAdapter(Context context, ArrayList<TestDTO> dtos) {
        this.context = context;
        this.dtos = dtos;

        inflater = LayoutInflater.from(this.context);
    }

    //// dtos에 dto를 추가하는 매소드(삭제할것)
    public void addDto(TestDTO dto){
        dtos.add(dto);
    }

    // 6. 화면을 인플레이트 시킨다
    @NonNull
    @Override
    public ViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.test_list,
                parent, false);

        return new ViewHoler(itemView, this);
    }
    // 7. 인플레이트 시킨 화면에 데이터를 셋팅시킨다
    @Override
    public void onBindViewHolder(@NonNull ViewHoler holder, @SuppressLint("RecyclerView") int position) {
        Log.d(TAG, "onBindViewHolder: " + position);

        TestDTO dto = dtos.get(position);
        holder.setDto(dto);

    }


    @Override
    public int getItemCount() {
        return dtos.size();
    }

    // 1. xml 파일에서 사용된 모든변수와 사용할 레이아웃을 클래스에서 선언한다
    public class ViewHoler extends RecyclerView.ViewHolder{
        TextView test_name, test_score, test_date;
        LinearLayout test_List;
        // 1-2. test_list.xml에서 정의한 아이디를 찾아 연결시킨다(생성자)
        public ViewHoler(@NonNull View itemView, TestAdapter listener) {
            super(itemView);

            test_List = itemView.findViewById(R.id.test_List);
            test_name = itemView.findViewById(R.id.test_name);
            test_score = itemView.findViewById(R.id.test_score);
            test_date = itemView.findViewById(R.id.test_date);

            test_name.setSingleLine(true);    //한줄로 나오게 하기.
            test_name.setEllipsize(TextUtils.TruncateAt.MARQUEE);//Ellipsize의 MARQUEE 속성 주기
            test_name.setSelected(true);      //해당 텍스트뷰에 포커스가 없어도 문자 흐르게 하기

        }
        // 1-3. 함수를 만들어서 fragment_test 데이터를 연결시킨다
        public void setDto(TestDTO dto){
            test_name.setText(dto.getTest_name());
            test_score.setText(dto.getTest_score());
            test_date.setText(dto.getTest_date());
        }

    }
}
