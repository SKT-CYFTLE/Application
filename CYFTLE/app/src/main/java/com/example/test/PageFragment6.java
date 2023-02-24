package com.example.test;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class PageFragment6 extends Fragment {
    private SuccessInterface sucapi;


    public PageFragment6() {
        // Required empty public constructor
    }

    // stt로 가져온 데이터 서버로 보내기
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test, container, false);


        Button btn = (Button) view.findViewById(R.id.talebtn);
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

        sucapi = retrofit.create(SuccessInterface.class);

        return view;
    }


    public interface SuccessInterface {
        @GET("/tts_result/kakao")
        Call<ResponseBody> getURL();
    }


    private void Test(String para) {
        try {
            // 데이터 서버로 보내기
            Call<ResponseBody> call = sucapi.getURL();
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    // 성공하면 해야할 반응
                    if (response.isSuccessful()) {
                        try {
                            String result = response.body().string();
                            Log.d("tag", "" + result);

                            String address = "http://20.214.190.71/tts_result/kakao";

                            try {
                                Log.d("tag", "Audio Download Started");
                                String fileName = "test.mp3";
                                File file = new File(getActivity().getExternalFilesDir(null), fileName);

                                FileOutputStream fos = new FileOutputStream(file);

                                URL website = new URL(address);
                                ReadableByteChannel rbc = Channels.newChannel((website.openStream()));


                                fos.getChannel().transferFrom(rbc,0,Long.MAX_VALUE);
                                fos.close();

                                Log.d("tag", "Audio Download Complete");

                            } catch (Exception e){
                                e.printStackTrace();
                                Log.d("tag", "Audio Download Failed");

                            }

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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
