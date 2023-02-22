package com.example.test;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Iterator;
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


public class Page1Fragment extends Fragment {

    private TextView duck;
    private SharedViewModel sharedViewModel;
//    private TranslateInterface transapi;


    // stt로 가져온 데이터 서버로 보내기
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        sharedViewModel.getStory().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                duck.setText(s);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page1, container, false);

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        ImageView duck_image = (ImageView) view.findViewById(R.id.ducktale_image);

        duck = (TextView) view.findViewById(R.id.duck_tale);
        // textview 스크롤 가능하게 한다
        duck.setMovementMethod(new ScrollingMovementMethod());

//        // timeout setting 해주기
//        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
//                .connectTimeout(60, TimeUnit.SECONDS)
//                .readTimeout(60, TimeUnit.SECONDS)
//                .writeTimeout(60, TimeUnit.SECONDS)
//                .build();
//
//        // Retrofit으로 통신하기 위한 인스턴스 생성하기
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://20.214.190.71/")
//                .client(okHttpClient)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        // api interface 인스턴스 생성
//        transapi = retrofit.create(TranslateInterface.class);

        return view;
    }


//    public interface TranslateInterface {
//        @Headers({"Content-Type: application/json"})
//        @POST("/translating/?lang=eng")
//        Call<ResponseBody> sendText(@Body RequestBody requestBody);
//    }


//    private void sendDataToServer(String text) {
//        try{
//            // json 파일 만들기
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("content", text);
//            // JSON 파일을 텍스트로 변환
//            String jsonText = jsonObject.toString();
//            // request body를 json 포맷 텍스트로 생성한다
//            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonText);
//
//            // 데이터 서버로 보내기
//            Call<ResponseBody> call = transapi.sendText(requestBody);
//            call.enqueue(new Callback<ResponseBody>() {
//                @Override
//                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                    // 성공하면 해야할 반응
//                    if(response.isSuccessful()) {
//                        try {
//                            String result = response.body().string();
//                            sharedViewModel.setStory(result);
//                            Log.d("tag", "" + result);
//                        }
//                        catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    else {
//                        Toast myToast = Toast.makeText(getActivity(),"에러", Toast.LENGTH_SHORT);
//                        myToast.show();
//                    }
//                }
//                @Override
//                public void onFailure(Call<ResponseBody> call, Throwable t) {
//                    // 실패 시
//                    Toast myToast = Toast.makeText(getActivity(),"실패", Toast.LENGTH_SHORT);
//                    myToast.show();
//                }
//            });
//        }
//        catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
}