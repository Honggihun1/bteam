package com.example.project01;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class Loading extends Activity {

    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);


        // 배경 넣음
        back = findViewById(R.id.back);

        Glide.with(this)
                .load(R.drawable.splash5)
                .fitCenter()
                .into(back);            // 이미지를 넣을 이미지뷰


        // n초 후 로그인화면으로 넘어감
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), TSSelect.class);  //  초기화면 : TSSelect
                startActivity(intent);
                finish();
            }
        }, 3000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}