package com.example.project01;

import static com.example.project01.common.CommonMethod.idsDTO;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project01.ATask.IdCheck;
import com.example.project01.ATask.JoinInsert;
import com.example.project01.DTO.IdsDTO;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

public class Join extends AppCompatActivity {
    private static final String TAG = "확인용";
    EditText etId, etPw, etPwch, etName, etSchool, etPhoneParent, etPhoneStudent, etGrade;
    TextView warn1, warn2;
    Button btn1, btn2;
    String state = "";
    int state2;
    Button btnId, btnSchool;
    String school;

    ArrayList<IdsDTO> dtos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        //  찾아줌
        etId = findViewById(R.id.etId);
        etPw = findViewById(R.id.etPw);
        etPwch = findViewById(R.id.etPwch);
        etName = findViewById(R.id.etName);
        etSchool = findViewById(R.id.etSchool);
        etGrade = findViewById(R.id.etGrade);
        etPhoneParent = findViewById(R.id.etPhoneParent);
        etPhoneStudent = findViewById(R.id.etPhoneStudent);
        btnId = findViewById(R.id.btnId);
        btnSchool = findViewById(R.id.btnSchool);
        warn1 = findViewById(R.id.warn1);
        warn2 = findViewById(R.id.warn2);


        // 입력할 때마다 아이디 중복확인 ( DB 에 있는 ID가 많아질수록 가져오기 힘드므로 ID를 보내서 중복체크하는 방향으로 구현 )
 /*       etId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String id = etId.getText().toString();
                dtos = new ArrayList<>();
                IdsSelect idsSelect = new IdsSelect(dtos, Join.this);
                try{
                    idsSelect.execute().get();  // get() 해야 값을 가져옴 / trt catch 해줘야 함
                }catch(Exception e){
                }
                for (int i=0; i < dtos.size(); i++) {
                    if (id.equals( dtos.get(i).getId())) {
                        warn1.setText("중복된 아이디입니다");
                        break;
                    }else{
                        warn1.setText("");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
 */

        // 아이디 중복확인 버튼 누를시
        btnId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = etId.getText().toString();

                IdCheck idCheck = new IdCheck(id);

                if(id.equals("")){
                    Toast.makeText(getApplicationContext(), "아이디를 입력해주세요", Toast.LENGTH_SHORT).show();
                }else {
                    try {
                        idCheck.execute().get();
                        Log.d(TAG, "id: " + idsDTO.getId());

                        if (idsDTO.getId().equals("0")) {
                            warn1.setText("");
                            warn1.setText("중복되지 않는 아이디입니다");
                        } else {
                            warn1.setText("");
                            warn1.setText("중복된 아이디입니다");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        // ID 입력시 중복체크하라는 문구 뜨게
        etId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                warn1. setText("아이디 중복체크를 해주세요");

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        
        
        


        // 학교명 직접 입력 못하게 설절
        etSchool.setClickable(false);
        etSchool.setFocusable(false);

        // 학교명 찾기 누를 시
        btnSchool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FindSchool.class);
                startActivityForResult(intent, 1);
            }
        });


        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);

        int requestCode = 0;


        etPwch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String pw = etPw.getText().toString();
                String pwCh = etPwch.getText().toString();

                if(! pw.equals(pwCh)){
                    etPwch.setTextColor(Color.RED);
                    warn2.setText("비밀번화와 비밀번호 재입력이 일치하지 않습니다");
                }else{
                    etPwch.setTextColor(Color.parseColor("#5d20aa"));
                    warn2.setText("");
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });  // 비밀번호 재입력 확인

        // 가입버튼 누를시
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = etId.getText().toString();
                String pw = etPw.getText().toString();
                String pwCh = etPwch.getText().toString();
                String name = etName.getText().toString();
                String school_name = etSchool.getText().toString();
                String grade = etGrade.getText().toString();
                String phone_parent = etPhoneParent.getText().toString();
                String phone_student = etPhoneStudent.getText().toString();

                String[] datas = {id, pw, pwCh, name, school_name, grade, phone_parent};
                String[] text = {"아이디", "비밀번호", "비밀번호 재확인", "이름", "학교명", "학년", "부모님전화번호"};
                EditText[] edit = {etId, etPw, etPwch, etName, etSchool, etGrade, etPhoneParent};

                // 아이디 중복확인 안눌렀을 때
                if( warn1.getText().toString().equals("중복되지 않는 아이디입니다")){

                }else if(warn1.getText().toString().equals("중복된 아이디입니다")){
                    Toast.makeText(Join.this, "중복된 아이디입니다", Toast.LENGTH_SHORT).show();
                    return;
                }else if(warn1.getText().toString().equals("아이디 중복체크를 해주세요")){
                    Toast.makeText(Join.this, "아이디 중복확인을 해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }else{

                }
                
                

                //비밀번호 유효성 ( 숫자, 문자, 특수문자 포험 8~15자)
                if (!Pattern.matches("^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&]).{8,15}.$", pw)) {
                    Toast.makeText(Join.this, "비밀번호는 숫자, 문자, 특수문자를 모두 포함하여 8~15자여야 합니다", Toast.LENGTH_SHORT).show();
                    return;
                }

                //핸드폰번호 유효성
                if (!Pattern.matches("^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$", phone_parent)) {
                    Toast.makeText(Join.this, "올바른 핸드폰 번호가 아닙니다.", Toast.LENGTH_SHORT).show();
                    return;
                }


                // 공백확인
                for (int i = 0; i < datas.length; i++) {
                    if (datas[i].equals("")) {
                        Toast.makeText(getApplicationContext(), text[i] + "를 입력해주세요", Toast.LENGTH_SHORT).show();
                        edit[i].requestFocus();
                        break;
                    }
                    if (i == 6) {
                        if (pw.equals(pwCh)) {
                            if (grade.equals("") || !(grade.equals("1") || grade.equals("2") || grade.equals("3"))) {
                                Toast.makeText(getApplicationContext(), "학년을 다시 입력해주세요", Toast.LENGTH_SHORT).show();
                                etGrade.requestFocus();
                            } else {
                                // 서버로 데이터를 보낸다 : AsyncTask를 상속받는 java파일을 만든다
                                JoinInsert joinInsert = new JoinInsert(id, pw, name, school, grade, phone_parent, phone_student);

                                try {
                                    state = joinInsert.execute().get();


                                } catch (ExecutionException e) {
                                    e.printStackTrace();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                state = state.trim();
                                // 정상적으로 데이터베이스에 삽입이 되면 1을 리턴, 아니면 0이하수를 리턴
                                if (state.equals("1")) {
                                    Toast.makeText(Join.this,
                                            "정상적으로 회원가입이 되었습니다", Toast.LENGTH_SHORT).show();

                                    // 종료하고 학생 메인화면으로
                                    finish();
                                    Intent intent = new Intent(getApplicationContext(), SLogin.class);
                                    startActivity(intent);


                                } else {
                                    Toast.makeText(Join.this, "회원가입에 실패하였습니다\n다시 확인 후 회원가입을 해주세요", Toast.LENGTH_SHORT).show();
                                }
                            }

                        } else {
                            Toast.makeText(getApplicationContext(), "비밀번호와 재입력이 일치하지 않습니다. 다시 입력해주세요", Toast.LENGTH_SHORT).show();
                            etPwch.requestFocus();
                        }
                    }
                }

            } // onclick
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    } // onreate


    // 4. 서브에서 보낸 데이터 받는 곳 : 정해져 있음, 반드시 오버라이드 시켜야 한다.
    // 창이 닫히면 requestCode가 부여된 것은 무조건 여기를 수행하게 된다.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // 창을 생성할 때 코드부여 :           1000,            -1,  서브에서 보낸 Intent : reIntent
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){ // 1000
            if(data != null){
                String school_name = data.getStringExtra("school_name");
                etSchool.setText(school_name);

                // 학교찾기로 찾은 학교명 받아오기

                school = data.getStringExtra("school_id");


            }
        }else if(requestCode == 1001){

        }else{
        }

    }



}