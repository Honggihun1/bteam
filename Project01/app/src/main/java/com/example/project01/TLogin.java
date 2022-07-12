package com.example.project01;

import static com.example.project01.common.CommonMethod.teacherDTO;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.project01.ATask.TLoginSelect;
import com.example.project01.DTO.StudentDTO;

import java.util.concurrent.ExecutionException;

public class TLogin extends AppCompatActivity {

    ImageView back;

    EditText etId, etPw;
    CheckBox auto;
    Button btnLogin;
    String state = "";

    SharedPreferences setting;

    SharedPreferences.Editor editor;



    // 로그인 화면 띄울시 dto를 null로 만들어서 문제 해결
    @Override
    protected void onResume() {
        super.onResume();

        if( teacherDTO != null){
            teacherDTO = null;
        }

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tlogin);

        // 로그인 화면 띄울시 자동으로 dto 를 null로 만듬
        StudentDTO studentDTO = null;

        // 배경지정
        back = findViewById(R.id.back);
        Glide.with(this)
                .load(R.drawable.back4)
                .centerCrop()
                .into(back); 			// 이미지를 넣을 이미지뷰

        // 찾기
        etId = findViewById(R.id.etId);
        etPw = findViewById(R.id.etPw);
        btnLogin = findViewById(R.id.btnLogin);




        /////////////////자동로그인//////////////////////////////////////////////////
        // 자동로그인 체크박스 찾기
        auto = findViewById(R.id.auto);
        setting = getSharedPreferences("setting", 0);
        editor= setting.edit();

        // checkBox 자동로그인 체크시
        auto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){

                    String ID = etId.getText().toString();

                    String PW = etPw.getText().toString();

                    editor.putString("ID", ID);

                    editor.putString("PW", PW);

                    editor.putBoolean("Auto_Login_enabled", true);

                    editor.commit();

                }else{

                    editor.clear();

                    editor.commit();

                }
            }
        });

        if(setting.getBoolean("Auto_Login_enabled", false)){

            etId.setText(setting.getString("ID", ""));

            etPw.setText(setting.getString("PW", ""));

            auto.setChecked(true);

        }


        //처음에는 SharedPreferences에 아무런 정보도 없으므로 값을 저장할 키들을 생성한다.
        // getString의 첫 번째 인자는 저장될 키, 두 번쨰 인자는 값입니다.
        // 첨엔 값이 없으므로 키값은 원하는 것으로 하시고 값을 null을 줍니다.
        String loginId = setting.getString("ID",null);
        String loginPwd = setting.getString("PW",null);


        if (loginId != null && loginPwd != null) {
            Toast.makeText(TLogin.this, loginId + "님 자동로그인 입니다.", Toast.LENGTH_SHORT).show();


            // editText 에 적은 아이이디와 패스워드를 가져온다
            if(etId.getText().toString().length()>0 && etPw.getText().toString().length() > 0){
                String teacher_id = etId.getText().toString();
                String teacher_pw = etPw.getText().toString();

                // 서버로 데이터를 보낸다 :  AsyncTask를 상속받는 java파일을 만든다
                TLoginSelect loginSelect = new TLoginSelect(teacher_id, teacher_pw);
                try {
                    state = loginSelect.execute().get();

                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // loginSelect에서 loginDTO를 채웠으면 로그인이 된거고
                // loginDTO가 null이면 id나 pw 가 틀린것이다
                if(teacherDTO != null){
                    Toast.makeText(TLogin.this, "로그인이 잘 되었네", Toast.LENGTH_SHORT).show();

                    // 로그인이 되었으므로 로그인화면 없애고 메인화면을 부른다
                    Intent intent = new Intent(TLogin.this, TMain.class);
                    startActivity(intent);
                    finishAffinity();

                }else{  //loginDTO가 null 이면
                    Toast.makeText(TLogin.this, "아이디나 비밀번호가 맞지 않습니다", Toast.LENGTH_SHORT).show();
                    etId.setText("");
                    etPw.setText("");
                    etId.requestFocus();

                }



            }else{  // 에디트텍스트에 내용이 없을 때
                Toast.makeText(TLogin.this, "아이디나 비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                etId.setText("");
                etPw.setText("");
                etId.requestFocus();
                return;
            }






        }



        // 로그인 버튼 클릭시 ( 값 던짐 )
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // editText 에 적은 아이이디와 패스워드를 가져온다
                if(etId.getText().toString().length()>0 && etPw.getText().toString().length() > 0){
                    String teacher_id = etId.getText().toString();
                    String teacher_pw = etPw.getText().toString();

                    // 서버로 데이터를 보낸다 :  AsyncTask를 상속받는 java파일을 만든다
                    TLoginSelect loginSelect = new TLoginSelect(teacher_id, teacher_pw);
                    try {
                        state = loginSelect.execute().get();

                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    // loginSelect에서 loginDTO를 채웠으면 로그인이 된거고
                    // loginDTO가 null이면 id나 pw 가 틀린것이다
                    if(teacherDTO != null){
                        Toast.makeText(TLogin.this, "로그인이 잘 되었네", Toast.LENGTH_SHORT).show();

                        // 로그인이 되었으므로 로그인화면 없애고 메인화면을 부른다
                        Intent intent = new Intent(TLogin.this, TMain.class);
                        startActivity(intent);
                        finishAffinity();

                    }else{  //loginDTO가 null 이면
                        Toast.makeText(TLogin.this, "아이디나 비밀번호가 맞지 않습니다", Toast.LENGTH_SHORT).show();
                        etId.setText("");
                        etPw.setText("");
                        etId.requestFocus();

                    }



                }else{  // 에디트텍스트에 내용이 없을 때
                    Toast.makeText(TLogin.this, "아이디나 비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                    etId.setText("");
                    etPw.setText("");
                    etId.requestFocus();
                    return;
                }

            } // onClick
        });  // setOnListener


    } // onCreate


}//class