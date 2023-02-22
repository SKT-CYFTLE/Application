package com.example.test;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.gson.Gson;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public class TaleMakeFragment extends Fragment {

    private TranslateInterface transapi;
    private TranslateInterface transapi2;
    private StoryInterface storyapi;
    private String responseString;
    private SharedViewModel sharedViewModel;
    private Button taleBtn;
    private String full;

    // stt로 가져온 데이터 서버로 보내기
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        sharedViewModel.getTranslate().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                taleBtn = (Button) view.findViewById(R.id.talebtn);
                taleBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sendDataToServer(s);
                    }
                });
            }
        });
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_talemake, container, false);

        // timeout setting 해주기
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .build();

        // Retrofit으로 통신하기 위한 인스턴스 생성하기
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://20.214.190.71/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // api interface 인스턴스 생성
        transapi = retrofit.create(TranslateInterface.class);
        storyapi = retrofit.create(StoryInterface.class);

        return view;
    }


    // TranslateApi 인터페이스
    public interface TranslateInterface {
        @Headers({"Content-Type: application/json"})
        @POST("/translating/?lang=kor")
        Call<ResponseBody> sendText(@Body RequestBody requestBody);
    }


    // Make Story 인터페이스
    public interface StoryInterface {
        @Headers({"Content-Type: application/json"})
        @POST("/make_story/")
        Call<ResponseBody> sendText(@Body RequestBody requestBody);
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
            Call<ResponseBody> call = transapi.sendText(requestBody);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    // 성공하면 해야할 반응
                    if(response.isSuccessful()) {
                        try {
                            responseString = response.body().string();
                            sendResultToServer(responseString);
                        }
                        catch (IOException e) {
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
    private void sendResultToServer(String text) {
        try {
            // json 파일 만들기
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("content", text);
            // JSON 파일을 텍스트로 변환
            String jsonText = jsonObject.toString();
            // request body를 json 포맷 텍스트로 생성한다
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonText);

            // 데이터 서버로 보내기
            Call<ResponseBody> call = storyapi.sendText(requestBody);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    // 성공하면 해야할 반응
                    if (response.isSuccessful()) {
                        try {
                            String result = response.body().string();
                            Toast myToast = Toast.makeText(getActivity(), "이야기가 다 만들어 졌어요!", Toast.LENGTH_SHORT);
                            myToast.show();

                            String rest = result.replaceAll("^\"|\"$|\\\\", "");
                            ObjectMapper objectMapper = new ObjectMapper();
                            Map<String, Object> map = objectMapper.readValue(rest, Map.class);
                            Object value = map.get("full");

                            full = value.toString();
                            sendDataToServer(full);
                            Log.d("tag", "" + full);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast myToast = Toast.makeText(getActivity(), "에러", Toast.LENGTH_SHORT);
                        myToast.show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    // 실패 시
                    Toast myToast = Toast.makeText(getActivity(), "실패", Toast.LENGTH_SHORT);
                    myToast.show();
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void sendStoryToServer(String text) {
        try{
            // json 파일 만들기
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("content", text);
            // JSON 파일을 텍스트로 변환
            String jsonText = jsonObject.toString();
            // request body를 json 포맷 텍스트로 생성한다
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonText);

            // 데이터 서버로 보내기
            Call<ResponseBody> call = transapi2.sendText(requestBody);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    // 성공하면 해야할 반응
                    if(response.isSuccessful()) {
                        try {
                            responseString = response.body().string();

                            sharedViewModel.setStory(responseString);
                        }
                        catch (IOException e) {
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