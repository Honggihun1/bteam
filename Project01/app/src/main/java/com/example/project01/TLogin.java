package com.example.project01;

import static com.example.project01.common.CommonMethod.teacherDTO;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
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
import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.common.KakaoSdk;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.Account;
import com.navercorp.nid.NaverIdLoginSDK;
import com.navercorp.nid.oauth.NidOAuthLogin;
import com.navercorp.nid.oauth.OAuthLoginCallback;
import com.navercorp.nid.oauth.view.NidOAuthLoginButton;
import com.navercorp.nid.profile.NidProfileCallback;
import com.navercorp.nid.profile.data.NidProfileResponse;
import com.nhn.android.naverlogin.OAuthLogin;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutionException;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

public class TLogin extends AppCompatActivity {
    private static final String TAG = "TLogin";

    ImageView back;

    EditText etId, etPw;
    CheckBox auto;
    Button btnLogin;
    String state = "";

    SharedPreferences setting;

    SharedPreferences.Editor editor;



    // 네이버 로그인
    //client 정보
    private static String OAUTH_CLIENT_ID = "nSU3FcDBSR0dEiJT2SZA";
    private static String OAUTH_CLIENT_SECRET = "rS6EMiQkej";
    private static String OAUTH_CLIENT_NAME = "project";
    private static OAuthLogin mOAuthLoginInstance;
    private static Context mContext;



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

        // 카카오 로그인
        getHashKey(); // 해쉬키 받아오기
        KakaoSdk.init(this,"eeaf3d6c1d83726a88804885c211cfa5");

        Function2<OAuthToken, Throwable, Unit> callback =
                new Function2<OAuthToken, Throwable, Unit>() {
                    @Override
                    public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {
                        if(oAuthToken != null){
                            Log.d(TAG, " 카카오 토큰이 있음 . 로그인 정보를 빼오면 됨");
                            getKakaoProfile();
                        }else{
                            Log.d(TAG, " 카카오 토큰이 없음 . " + throwable.toString());
                        }
                        return null;
                    }
                };

        findViewById(R.id.kakaoLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(UserApiClient.getInstance().isKakaoTalkLoginAvailable(TLogin.this)){
                    Log.d(TAG, " 카톡 설치 되있음");
                    UserApiClient.getInstance().loginWithKakaoTalk(TLogin.this,callback);
                }else{
                    Log.d(TAG, " 카톡 설치 X");
                    UserApiClient.getInstance().loginWithKakaoAccount(TLogin.this,callback);
                }
            }
        });



        //네이버 로그인 초기화
        NaverIdLoginSDK.INSTANCE.initialize(
                this,OAUTH_CLIENT_ID,
                OAUTH_CLIENT_SECRET,
                OAUTH_CLIENT_NAME);

        NidOAuthLoginButton btn_naver = findViewById(R.id.btn_naver);
        btn_naver.setOAuthLoginCallback(new OAuthLoginCallback() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "onSuccess: " + NaverIdLoginSDK.INSTANCE.getAccessToken());
                Log.d(TAG, "onSuccess: " + NaverIdLoginSDK.INSTANCE.getRefreshToken());
                getNaverProfile();

            }

            @Override
            public void onFailure(int i, String s) {
                Log.d(TAG, "onFailure: " + s);
            }

            @Override
            public void onError(int i, String s) {
                Log.d(TAG, "onError: " + s);
            }
        }); // 네이버 로그인




        // 로그인 화면 띄울시 자동으로 dto 를 null로 만듬
        StudentDTO studentDTO = null;

        // 배경지정
        back = findViewById(R.id.back);
        Glide.with(this)
                .load(R.drawable.bg1)
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



    // 네이버 로그인
    public void getNaverProfile(){ //<- 억세스 토큰 O일때만 성공함.
        NidOAuthLogin authLogin = new NidOAuthLogin();
        authLogin.callProfileApi(new NidProfileCallback<NidProfileResponse>() {
            @Override
            public void onSuccess(NidProfileResponse nidProfileResponse) {

                Log.d(TAG, "onSuccess: " + nidProfileResponse.getProfile().getId());
                Log.d(TAG, "onSuccess: " + nidProfileResponse.getProfile().getEmail());
                Log.d(TAG, "onSuccess: " + nidProfileResponse.getProfile().getMobile());
                Log.d(TAG, "onSuccess: " + nidProfileResponse.getProfile().getName());


                String id = nidProfileResponse.getProfile().getId();

                // 서버로 데이터를 보낸다 :  AsyncTask를 상속받는 java파일을 만든다
                TLoginSelect loginSelect = new TLoginSelect(id, "social");
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


            }

            @Override
            public void onFailure(int i, String s) {
                Log.d(TAG, "onFailure: " + s);
            }

            @Override
            public void onError(int i, String s) {
                Log.d(TAG, "onFailure: " + s);
            }
        });

    }


    // 카카오 로그인 : 해쉬키 받아오기
    // 만약 다른 컴퓨터에서 작업을 할려면 키를 받아서 카카오프로젝트에 추가해주어야 함
    private void getHashKey(){
        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageInfo == null)
            Log.e("KeyHash", "KeyHash:null");

        for (Signature signature : packageInfo.signatures) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            } catch (NoSuchAlgorithmException e) {
                Log.e("KeyHash", "Unable to get MessageDigest. signature=" + signature, e);
            }
        }
    }


    // 카카오톡 로그인
    public void getKakaoProfile(){
        // 사용자 정보 요청 (기본)
        UserApiClient.getInstance().me(  (user, throwable) -> {
            if(throwable != null){
                Log.d(TAG, " 사용자 정보 요청 실패"+ throwable.toString());
                //오류가 났을때는 어떤 오류인지를 Kakao에서 제공해줌 . KOE + 숫자
            }else{
                // [ { } ] json 구조처럼 바로 데이터가 있는게 아니라 Accuount라는 키로 한칸을 들어감(오브젝트)
                //그안에서 profile을 얻어 올수가있음.
                Account account = user.getKakaoAccount();
                if(account != null){

                    Log.d(TAG, " 사용자 정보 요청 성공 : "+account.getProfile().getNickname());
                    Log.d(TAG, " 사용자 정보 요청 성공 : "+account.getEmail());
                }

            }

            return null;
        });
    }





}//class



