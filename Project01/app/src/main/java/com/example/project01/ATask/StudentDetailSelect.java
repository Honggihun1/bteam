package com.example.project01.ATask;

import static com.example.project01.common.CommonMethod.ipConfig;
import static com.example.project01.common.CommonMethod.studentDetailDTO;


import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import com.example.project01.DTO.StudentDetailDTO;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class StudentDetailSelect extends AsyncTask<Void, Void, String> {



    private static final String TAG = "확인용";
    String student_id;
    String state = "";

    public StudentDetailSelect(String student_id) {
        this.student_id = student_id;
    }

    // 1. doInBackground 실행 전에 설정부분 : 초기화 설정
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    // 반드시 선언해야 할것들 : 무조건 해야함 복,붙
    HttpClient httpClient;      // 클라이언트 객체
    HttpPost httpPost;          // 클라이언트에 붙일 본문
    HttpResponse httpResponse;  // 서버에서의 응답을 받는 부분
    HttpEntity httpEntity;      // 응답내용

    // 2. 실질적으로 작업을 하는 곳
    @Override
    protected String doInBackground(Void... voids) {

        try {
            // 무조건 해야함 : 복,붙
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            builder.setCharset(Charset.forName("utf-8"));

            // 여기가 우리가 수정해야 하는 부분 :서버로 보내는 데이터
            // builder에 문자열 및 파일 첨부하는 곳
            builder.addTextBody("student_id", student_id, ContentType.create("Multipart/related", "utf-8"));

            // 전송
            // 전송 Url : 우리가 수정해야 하는 부분
            String postURL = ipConfig + "/app/StudentInfo";

            // 그대로 복,툽
            InputStream inputStream=null;
            httpClient = AndroidHttpClient.newInstance("Android");
            httpPost = new HttpPost(postURL);
            httpPost.setEntity(builder.build());
            httpResponse = httpClient.execute(httpPost);    // 보내고 응답받는 부분
            httpEntity = httpResponse.getEntity();  // 응답 내용을 저장
            inputStream = httpEntity.getContent();  // 응답내용을 inputStream에 넣음

            // 응답처리 : DTO 형태 :
            studentDetailDTO = readMessage(inputStream);
            Log.d(TAG, "StudentDetailDTO : " + studentDetailDTO);


            // 응답처리 : 문자열(String) 형태
            BufferedReader bufferedReader =new BufferedReader(new InputStreamReader(inputStream, "utf-8"));

            StringBuilder stringBuilder = new StringBuilder();
            String line = null;
            while((line = bufferedReader.readLine()) != null){
                stringBuilder.append(line+"\n");
            }
            state = stringBuilder.toString();

            inputStream.close();

        }catch (Exception e){
            e.getMessage();
        }finally{
            if(httpEntity != null){
                httpEntity = null;
            }
            if(httpResponse != null){
                httpResponse = null;
            }
            if(httpPost != null){
                httpPost = null;
            }
            if(httpClient != null){
                httpClient = null;
            }

        }

        return state;
    }


    // 2-1. 작업 중에 데이터를 받는 곳
    @Override               // 두번째 파라미터
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    // 3. doInBackground 실행 후에 오는 부분
    @Override               // 세번째 파라미터
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

    }

    // 하나의 DTO형태로 데이터를 받을 때 파싱하는 부분
    private StudentDetailDTO readMessage(InputStream inputStream) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "utf-8"));
        String student_name = "", school_id = "", parent_phone="", student_phone ="", grade ="", school_name="";

        reader.beginObject();
        while (reader.hasNext()){
            String readerStr = reader.nextName();
            if(readerStr.equals("student_name")) {
                student_name = reader.nextString();
            }else if(readerStr.equals("school_id")){
                school_id = reader.nextString();
            }else if(readerStr.equals("parent_phone")){
                parent_phone = reader.nextString();
            }else if(readerStr.equals("student_phone")){
                student_phone = reader.nextString();
            }else if(readerStr.equals("grade")){
                grade = reader.nextString();
            }else if(readerStr.equals("school_name")){
                school_name = reader.nextString();
            }else{
                reader.skipValue();
            }
        }
        reader.endObject();
        Log.d(TAG, "애"+school_id);

        return new StudentDetailDTO(student_name, school_id, parent_phone, student_phone, grade, school_name);

    }



}
