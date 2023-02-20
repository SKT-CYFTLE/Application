package com.example.test;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public class Duck1Fragment extends Fragment {

    private FastInterface api;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_duck1, container, false);

        ImageView duck_image = (ImageView) view.findViewById(R.id.ducktale_image);

        // Text file에서 내용 읽어오기
        InputStream is = null;
        try {
            is = getActivity().getAssets().open("duck.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line;
        while (true) {
            try {
                if (!((line = reader.readLine()) != null)) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            sb.append(line).append("\n");
        }
        String text = sb.toString();

        TextView duck = (TextView) view.findViewById(R.id.duck_tale);
        duck.setText(text);

        // textview 스크롤 가능하게 한다
        duck.setMovementMethod(new ScrollingMovementMethod());
        // Duck 이미지를 눌렀을 때 나오는 이벤트
        duck_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendDataToServer("아기 오리가 집 나가는 동화 만들어 줘");
            }
        });
        // Retrofit으로 통신하기 위한 인스턴스 생성하기
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://20.214.190.71/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // api interface 인스턴스 생성
        api = retrofit.create(FastInterface.class);

        return view;
    }

    // 커스텀 툴바 xml에서 버튼을 불러오는 메소드
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.alarm_title, menu);
    }
    // 커스텀 툴바 버튼이 눌렸을 때 구현 시킬 기능들
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.close:
                getActivity().finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    // Api 인터페이스
    public interface FastInterface {
        @Headers({"Content-Type: application/json"})
        @POST("/translating/?lang=kor")
        Call<ResponseBody> sendText(@Body RequestBody requestBody);
    }
    public class Message {
        private String message;

        public Message(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
    private void sendDataToServer(String text) {
        try{
            // json 파일 만들기
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("content", text);
            // JSON 파일을 텍스트로 변환
            String jsonText = jsonObject.toString();
            // request body를 json 포맷 텍스트로 생성한다
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonText);

            // 데이터 서버로 보내기
            Call<ResponseBody> call = api.sendText(requestBody);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    // 성공하면 해야할 반응
                    if(response.isSuccessful()) {
                        try {
                            String responseString = response.body().string();
                            Toast myToast = Toast.makeText(getActivity(),"Response from server: " + responseString, Toast.LENGTH_SHORT);
                            myToast.show();
                            Log.d("TAG", "Response from server: " + responseString);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    else {
                        Toast myToast = Toast.makeText(getActivity(),"에러", Toast.LENGTH_SHORT);
                        myToast.show();
                    }
                }
                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    // 실패 시
                    Toast myToast = Toast.makeText(getActivity(),"실패", Toast.LENGTH_SHORT);
                    myToast.show();
                }
            });
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }
}