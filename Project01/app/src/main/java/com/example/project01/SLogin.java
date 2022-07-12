package com.example.project01;

import static com.example.project01.common.CommonMethod.studentDTO;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.example.project01.ATask.SLoginSelect;

import java.util.concurrent.ExecutionException;

public class SLogin extends AppCompatActivity {

    ImageView back;
    CheckBox auto;
    Button btnJoin, btnLogin;
    EditText etId, etPw;
    String state = "";

    SharedPreferences setting2;

    SharedPreferences.Editor editor2;


    // 로그인 화면 띄울시 dto를 null로 만들어서 문제 해결
    @Override
    protected void onResume() {
        super.onResume();

        if (studentDTO != null) {
            studentDTO = null;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slogin);

        // 위험권한 물어보기 ( 발표시 주석 풀기 )
        // checkDangerousPermissions();

        // 찾기
        etId = findViewById(R.id.etId);
        etPw = findViewById(R.id.etPw);
        btnLogin = findViewById(R.id.btnLogin);
        btnJoin = findViewById(R.id.btnJoin);


        /////////////////자동로그인//////////////////////////////////////////////////
        // 자동로그인 체크박스 찾기
        auto = findViewById(R.id.auto);
        setting2 = getSharedPreferences("setting2", 0);
        editor2 = setting2.edit();


        // checkBox 자동로그인 체크시
        auto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    String ID = etId.getText().toString();

                    String PW = etPw.getText().toString();

                    editor2.putString("ID", ID);

                    editor2.putString("PW", PW);

                    editor2.putBoolean("Auto_Login_enabled", true);

                    editor2.commit();

                } else {

                    editor2.clear();

                    editor2.commit();

                }
            }
        });

        if (setting2.getBoolean("Auto_Login_enabled", false)) {

            etId.setText(setting2.getString("ID", ""));

            etPw.setText(setting2.getString("PW", ""));

            auto.setChecked(true);

        }


        //처음에는 SharedPreferences에 아무런 정보도 없으므로 값을 저장할 키들을 생성한다.
        // getString의 첫 번째 인자는 저장될 키, 두 번쨰 인자는 값입니다.
        // 첨엔 값이 없으므로 키값은 원하는 것으로 하시고 값을 null을 줍니다.
        String loginId = setting2.getString("ID", null);
        String loginPwd = setting2.getString("PW", null);


        if (loginId != null && loginPwd != null) {
            Toast.makeText(SLogin.this, loginId + "님 자동로그인 입니다.", Toast.LENGTH_SHORT).show();

            // editText 에 적은 아이이디와 패스워드를 가져온다
            if (etId.getText().toString().length() > 0 && etPw.getText().toString().length() > 0) {
                String student_id = etId.getText().toString();
                String student_pw = etPw.getText().toString();

                // 서버로 데이터를 보낸다 :  AsyncTask를 상속받는 java파일을 만든다
                SLoginSelect loginSelect = new SLoginSelect(student_id, student_pw);
                try {
                    state = loginSelect.execute().get();

                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // loginSelect에서 loginDTO를 채웠으면 로그인이 된거고
                // loginDTO가 null이면 id나 pw 가 틀린것이다
                if (studentDTO != null) {
                    Toast.makeText(SLogin.this, "로그인이 잘 되었네", Toast.LENGTH_SHORT).show();

                    // 로그인이 되었으므로 로그인화면 없애고 메인화면을 부른다
                    Intent intent = new Intent(SLogin.this, SMain.class);
                    startActivity(intent);
                    finishAffinity();

                } else {  //loginDTO가 null 이면
                    Toast.makeText(SLogin.this, "아이디나 비밀번호가 맞지 않습니다", Toast.LENGTH_SHORT).show();
                    etId.setText("");
                    etPw.setText("");
                    etId.requestFocus();

                }


            } else {  // 에디트텍스트에 내용이 없을 때
                Toast.makeText(SLogin.this, "아이디나 비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                etId.setText("");
                etPw.setText("");
                etId.requestFocus();
                return;
            }

        } // 자동로그인 관련


        // 배경지정
        back = findViewById(R.id.back);
        Glide.with(this)
                .load(R.drawable.back4)
                .centerCrop()
                .into(back);            // 이미지를 넣을 이미지뷰

        // 회원가입 버튼 클릭시 화면 연결
        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Join.class);
                startActivity(intent);
            }
        });

        // 로그인 버튼 클릭시
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // editText 에 적은 아이이디와 패스워드를 가져온다
                if (etId.getText().toString().length() > 0 && etPw.getText().toString().length() > 0) {
                    String student_id = etId.getText().toString();
                    String student_pw = etPw.getText().toString();

                    // 서버로 데이터를 보낸다 :  AsyncTask를 상속받는 java파일을 만든다
                    SLoginSelect loginSelect = new SLoginSelect(student_id, student_pw);
                    try {
                        state = loginSelect.execute().get();

                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    // loginSelect에서 loginDTO를 채웠으면 로그인이 된거고
                    // loginDTO가 null이면 id나 pw 가 틀린것이다
                    if (studentDTO != null) {
                        Toast.makeText(SLogin.this, "로그인이 잘 되었네", Toast.LENGTH_SHORT).show();

                        // 로그인이 되었으므로 로그인화면 없애고 메인화면을 부른다
                        Intent intent = new Intent(SLogin.this, SMain.class);
                        startActivity(intent);
                        finishAffinity();

                    } else {  //loginDTO가 null 이면
                        Toast.makeText(SLogin.this, "아이디나 비밀번호가 맞지 않습니다", Toast.LENGTH_SHORT).show();
                        etId.setText("");
                        etPw.setText("");
                        etId.requestFocus();

                    }


                } else {  // 에디트텍스트에 내용이 없을 때
                    Toast.makeText(SLogin.this, "아이디나 비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                    etId.setText("");
                    etPw.setText("");
                    etId.requestFocus();
                    return;
                }

            } // onclick
        });  // setOnClick


    }


    // 위험권한 : 실행시 허용여부를 다시 물어봄   ///////////////////////////////////////////////////////
    private void checkDangerousPermissions() {
        String[] permissions = {
                // 위험권한 내용 : 메니페스트에 권한을 여기에 적음

                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_MEDIA_LOCATION,
                Manifest.permission.CAMERA
        };

        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for (int i = 0; i < permissions.length; i++) {
            permissionCheck = ContextCompat.checkSelfPermission(this, permissions[i]);
            if (permissionCheck == PackageManager.PERMISSION_DENIED) {
                break;
            }
        }

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "권한 있음", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "권한 없음", Toast.LENGTH_LONG).show();

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
                Toast.makeText(this, "권한 설명 필요함.", Toast.LENGTH_LONG).show();
            } else {
                ActivityCompat.requestPermissions(this, permissions, 1);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, permissions[i] + " 권한이 승인됨.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, permissions[i] + " 권한이 승인되지 않음.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }


}