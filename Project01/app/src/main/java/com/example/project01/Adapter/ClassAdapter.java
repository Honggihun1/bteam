package com.example.project01.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project01.DTO.ClassDTO;
import com.example.project01.R;
import com.example.project01.TClassInfo;

import java.util.ArrayList;

public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.ViewHolder> {

    private static final String TAG = "확인";




    // 3. 메인한테 넘겨 받는것
    Context context;
    ArrayList<ClassDTO> dtos;
    LayoutInflater inflater;

    EditText editText;

    // 4. 생성자를 만들어 메인에게서 넘겨받은것을 연결
    public ClassAdapter(Context context, ArrayList<ClassDTO> dtos) {
        this.context = context;
        this.dtos = dtos;

        inflater = LayoutInflater.from(this.context);
    }

    // 5. 메소드는 여기에 만든다
    // dtos에 dto를 추가하는 매소드
    public void addDto(ClassDTO dto){
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
        View itemView = inflater.inflate(R.layout.classview,
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
        ClassDTO dto = dtos.get(position);
        holder.setDto(dto);
    }

    // 1. xml 파일에서 사용된 모든변수와 사용할 레이아웃을 클래스에서 선언한다 : 별 5개
    public class ViewHolder extends RecyclerView.ViewHolder{
        // 1-1. singerview.xml 에 사용된 모든 위젯을 정의한다
        TextView name;


        // 1-2. singerview.xml에서 정의한 아이디를 찾아 연결시킨다(생성자)
        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            name = itemView.findViewById(R.id.name);

            // 카드뷰 눌렀을 때 클릭 이벤트( 다른 페이지 연결)
            itemView.setClickable(true);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        Intent intent = new Intent(context, TClassInfo.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

                        intent.putExtra("class_id",dtos.get(pos).getClass_id());

                        context.startActivity(intent);
                    }

                }
            });



            //에딧텍스트 클릭이벤트
           /* editText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        Intent intent = new Intent(context,TClassInfo.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

                        intent.putExtra("TEXT",dtos.get(pos));

                        context.startActivity(intent);
                    }
                }
            });
*/

        }

        // 1-3. 함수를 만들어서 singerview에 데이터를 연결시킨다
        public void setDto(ClassDTO dto){
            name.setText(dto.getClass_name());
            Log.d(TAG, "setDto: " + dto.getClass_name());

        }

    }


}
