package com.example.project01.ATask;


import static com.example.project01.common.CommonMethod.ipConfig;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class SclassPlus extends AsyncTask<Void, Void, String> {
    private static final String TAG = "main:SClass";

    // 우리는 무조건 생성자를 만들어서 데이터를 넘겨받는다
    String class_id;
    String student_id;
    String state = "";

    public SclassPlus(String student_id, String class_id) {
        this.student_id = student_id;
        this.class_id = class_id;
    }

    // 반드시 선언해야 할것들 : 무조건 해야함 복,붙
    HttpClient httpClient;       // 클라이언트 객체
    HttpPost httpPost;           // 클라이언트에 붙일 본문
    HttpResponse httpResponse;   // 서버에서의 응답을 받는 부분
    HttpEntity httpEntity;       // 응답내용

    // 2. 실질적으로 작업을 하는곳
    @Override                   // 첫번째 파라메터
    protected String doInBackground(Void... voids) {

        try {
            // 무조건 해야함 : 복,붙
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            builder.setCharset(Charset.forName("UTF-8"));

            // 여기가 우리가 수정해야 하는 부분 : 서버로 보내는 데이터
            // builder에 문자열 및 파일 첨부하는곳
            builder.addTextBody("class_id", class_id, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("student_id", student_id, ContentType.create("Multipart/related", "UTF-8"));

            // 전송
            // 전송 Url : 우리가 수정해야 하는 부분
            String postURL = ipConfig + "/app/hongclassplus";

            // 그대로 복,붙
            InputStream inputStream = null;
            httpClient = AndroidHttpClient.newInstance("Android");
            httpPost = new HttpPost(postURL);
            httpPost.setEntity(builder.build());
            httpResponse = httpClient.execute(httpPost); // 보내고 응답받는 부분
            httpEntity = httpResponse.getEntity();  // 응답내용을 저장
            inputStream = httpEntity.getContent();  // 응답내용을 inputStream에 넣음

            // 응답처리 : 문자열(String) 형태
            BufferedReader bufferedReader = new
                    BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            StringBuilder stringBuilder = new StringBuilder();
            String line = null;
            while ((line = bufferedReader.readLine()) != null){
                stringBuilder.append(line + "\n");
            }
            state = stringBuilder.toString();

            inputStream.close();

        }catch (Exception e){
            e.getMessage();
        }finally {
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

    /*// 2-1. 작업중에 데이터를 받는곳
    @Override                       // 두번째 파라메터
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }*/

    // 3. doInBackground 실행(작업)후에 오는부분
    @Override                   // 세번째 파라메터
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        Log.d(TAG, "result: " + result);
    }
}

