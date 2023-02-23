package com.example.test;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
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

public class Page5Fragment extends Fragment {
    private TextView text;
    private SharedViewModel sharedViewModel;
    private DalleInterface dalleapi;

    public Page5Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page5, container, false);

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        text = (TextView) view.findViewById(R.id.duck_tale);
        // textview 스크롤 가능하게 한다
        text.setMovementMethod(new ScrollingMovementMethod());
        sharedViewModel.getText5().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                text.setText(s);
            }
        });
//        sharedViewModel.getArr().observe(getViewLifecycleOwner(), new Observer<ArrayList<String>>() {
//            @Override
//            public void onChanged(ArrayList<String> ar) {
//                sendSummaryToServer(ar.get(4));
//            }
//        });

        // timeout setting 해주기
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(300, TimeUnit.SECONDS)
                .readTimeout(300, TimeUnit.SECONDS)
                .writeTimeout(300, TimeUnit.SECONDS)
                .build();

        // Retrofit으로 통신하기 위한 인스턴스 생성하기
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://20.214.190.71/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        dalleapi = retrofit.create(DalleInterface.class);

        return view;
    }

    // Dall-e 인터페이스
    public interface DalleInterface {
        @Headers({"Content-Type: application/json"})
        @POST("/make_image/")
        Call<ResponseBody> sendText(@Body RequestBody requestBody);
    }

    private void sendSummaryToServer(String summary) {
        try{
            // json 파일 만들기
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("content", summary);
            // JSON 파일을 텍스트로 변환
            String jsonStt = jsonObject.toString();
            // request body를 json 포맷 텍스트로 생성한다
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonStt);

            // 데이터 서버로 보내기
            Call<ResponseBody> call = dalleapi.sendText(requestBody);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    // 성공하면 해야할 반응
                    if(response.isSuccessful()) {
                        try {
                            String result = response.body().string();
                            ObjectMapper object = new ObjectMapper();
                            JsonNode root = object.readTree(result);
                            String url = root.get("url").asText();
                            Log.d("tag", "" + url);
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
