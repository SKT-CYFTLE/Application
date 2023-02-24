package com.example.test;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
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

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;


import java.io.IOException;
import java.util.List;
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
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Streaming;


public class Page1Fragment extends Fragment {

    private TextView duck;
    private ImageView duck_image;
    private SharedViewModel sharedViewModel;
    private EngToKorInterface engapi;
    private TtsInterface ttsapi;
    private String url;
    private String ttstory;

    // stt로 가져온 데이터 서버로 보내기
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        sharedViewModel.getStory().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                sendEngToServer(s);
            }
        });
        sharedViewModel.getArr().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> urlList) {
                url = urlList.get(0);

                duck_image = view.findViewById(R.id.ducktale_image);
                Picasso.get().load(url).into(duck_image);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page1, container, false);

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        duck = (TextView) view.findViewById(R.id.duck_tale);
        // textview 스크롤 가능하게 한다
        duck.setMovementMethod(new ScrollingMovementMethod());

        ImageView image = (ImageView) view.findViewById(R.id.ducktale_image);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTTSFromServer(ttstory);
            }
        });

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

        engapi = retrofit.create(EngToKorInterface.class);
        ttsapi = retrofit.create(TtsInterface.class);

        return view;
    }


    // 영어 한국어로 변환해주는 인터페이스
    public interface EngToKorInterface {
        @Headers({"Content-Type: application/json"})
        @POST("/translating/?lang=eng")
        Call<ResponseBody> sendText(@Body RequestBody requestBody);
    }


    // tts 인터페이스
    public interface TtsInterface {
        @Headers({"Content-Type: application/json"})
        @POST("/tts_azure/")
        Call<ResponseBody> sendText(@Body RequestBody requestBody);
    }



    private void sendEngToServer(String story) {
        try {
            // json 파일 만들기
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("content", story);
            // JSON 파일을 텍스트로 변환
            String jsonStory = jsonObject.toString();
            // request body를 json 포맷 텍스트로 생성한다
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonStory);

            // 데이터 서버로 보내기
            Call<ResponseBody> call = engapi.sendText(requestBody);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    // 성공하면 해야할 반응
                    if (response.isSuccessful()) {
                        try {
                            String result = response.body().string();
                            String[] story = result.split("\\*\\*");

                            Log.d("tag", "단락1:" + story[0]);
                            Log.d("tag", "단락2:" + story[1]);
                            Log.d("tag", "단락3:" + story[2]);
                            Log.d("tag", "단락4:" + story[3]);
                            Log.d("tag", "단락5:" + story[4]);

                            duck.setText(story[0]);
                            ttstory = story[0];

                            sharedViewModel.setText2(story[1]);
                            sharedViewModel.setText3(story[2]);
                            sharedViewModel.setText4(story[3]);
                            sharedViewModel.setText5(story[4]);


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

    private void getTTSFromServer(String para) {
        try {
            // json 파일 만들기
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("content", para);
            // JSON 파일을 텍스트로 변환
            String jsonStory = jsonObject.toString();
            // request body를 json 포맷 텍스트로 생성한다
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonStory);

            // 데이터 서버로 보내기
            Call<ResponseBody> call = ttsapi.sendText(requestBody);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    // 성공하면 해야할 반응

                    Log.d("tag", "1");
                    if (response.isSuccessful()) {
                        String fileUrl = "http://20.214.190.71/tts_result/azure";
                        MediaPlayer mediaPlayer = new MediaPlayer();

                        try {
                            mediaPlayer.setDataSource(fileUrl);
                            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                            mediaPlayer.prepare();
                            mediaPlayer.start();
                        } catch (IOException e) {
                            Log.d("tag", "Error playing audio from URL: " + e.getMessage());
                        }
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
}

