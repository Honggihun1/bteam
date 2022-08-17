package com.example.project01;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class TSSelect extends AppCompatActivity {

    ImageView back;
    Button btn1, btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tsselect);

        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);

        // 배경지정
        back = findViewById(R.id.back);
        Glide.with(this)
                .load(R.drawable.bg1)
                .centerCrop()
                .into(back); 			// 이미지를 넣을 이미지뷰

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SLogin.class);

                startActivity(intent);
                // finish();

            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(getApplicationContext(), TLogin.class);

                startActivity(intent2);
                // finish();   // 나중에 수정할것

            }
        });




    }
}