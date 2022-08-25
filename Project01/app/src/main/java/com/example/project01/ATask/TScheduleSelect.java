package com.example.project01.ATask;

import static com.example.project01.common.CommonMethod.ipConfig;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import com.example.project01.Adapter.TScheduleAdapter;
import com.example.project01.DTO.TScheduleDTO;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;

public class TScheduleSelect extends AsyncTask<Void, Void, Void> {
    static String TAG = "확인";
    ArrayList<TScheduleDTO> dtos;
    TScheduleAdapter adapter;
    String schedule_date;
    String teacher_id;

    // 우리는 무조건 생성자를 만들어서 데이터를 넘겨받는다
    public TScheduleSelect(ArrayList<TScheduleDTO> dtos, TScheduleAdapter adapter, String schedule_date, String teacher_id) {
        this.dtos = dtos;
        this.adapter = adapter;
        this.schedule_date = schedule_date;
        this.teacher_id = teacher_id;
    }





    // 반드시 선언해야 할것들 : 무조건 해야함 복,붙
    HttpClient httpClient;       // 클라이언트 객체
    HttpPost httpPost;           // 클라이언트에 붙일 본문
    HttpResponse httpResponse;   // 서버에서의 응답을 받는 부분
    HttpEntity httpEntity;       // 응답내용

    // 2. 실질적으로 작업을 하는곳
    @Override                   // 첫번째 파라메터
    protected Void doInBackground(Void... voids) {

        try {
            // 무조건 해야함 : 복,붙
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            builder.setCharset(Charset.forName("UTF-8"));

            // 여기가 우리가 수정해야 하는 부분 : 서버로 보내는 데이터
            // builder에 문자열 및 파일 첨부하는곳
            builder.addTextBody("schedule_date", schedule_date, ContentType.create("Multipart/related", "utf-8"));
            builder.addTextBody("teacher_id", teacher_id, ContentType.create("Multipart/related", "utf-8"));


            // 전송
            // 전송 Url : 우리가 수정해야 하는 부분
            String postURL = ipConfig + "/app/hongTScheduleSelect";  // 서블릿에 연결해주는 키워드

            // 그대로 복,붙
            InputStream inputStream = null;
            httpClient = AndroidHttpClient.newInstance("Android");
            httpPost = new HttpPost(postURL);
            httpPost.setEntity(builder.build());
            httpResponse = httpClient.execute(httpPost); // 보내고 응답받는 부분
            httpEntity = httpResponse.getEntity();  // 응답내용을 저장
            inputStream = httpEntity.getContent();  // 응답내용을 inputStream에 넣음
            Log.d(TAG, "확인: "+ teacher_id);
            // 응답처리 : 데이터가 ArrayList<DTO> 형태 :
            readJsonStream(inputStream);

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

        return null;
    }

    // 3. doInBackground 실행(작업)후에 오는부분
    @Override                   // 세번째 파라메터
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);

        // 화면갱신
        adapter.notifyDataSetChanged();
    }

    // ArrayList<DTO>로 넘어왔을때
    private void readJsonStream(InputStream inputStream) throws IOException {
        Log.d(TAG, "111: " );

        JsonReader reader = new JsonReader (new InputStreamReader(inputStream, "utf-8"));
        Log.d(TAG, "222: " );
        try {
            reader.beginArray();
           Log.d(TAG, "333: " );

            while (reader.hasNext()){
                dtos.add(readMessage(reader));
            }
            reader.endArray();

        }catch (Exception e){
            e.getMessage();
        }finally {
            reader.close();
        }

    }
    public static HashMap<String, String> map = new HashMap<String, String>();

    //public Sting[] schedule_list
    // 하나의 DTO형태로 데이터를 받을때 파싱하는 부분
    private TScheduleDTO readMessage(JsonReader reader) throws IOException {
        String schedule="", schedule_date="";

        reader.beginObject();
        Log.d(TAG, "readMessage확인: "+ reader);
        while (reader.hasNext()){
            String readStr = reader.nextName();
           Log.d(TAG, "readStr : "+ readStr);
            if(readStr.equals("schedule")){
                schedule = reader.nextString();
            }else if(readStr.equals("schedule_date")){
                schedule_date = reader.nextString();
          //      map.put(schedule_date.substring(0,10), "1");
         //       Log.d(TAG, "데이트: " + schedule_date.substring(0,10));
          //      Log.d(TAG, "맵: " + map);
          //     Log.d(TAG, "맵: " + map.get("2022-07-11"));
            }else {
                reader.skipValue();
            }
        } // while
        reader.endObject();

        Log.d(TAG, "schedule: "+ schedule);
        return new TScheduleDTO(schedule, schedule_date);
    }




}
