package com.example.test;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Locale;

public class Duck1Fragment extends Fragment {

    private TextToSpeech tts;
    private TextView duck;

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
        // tts를 생성하고 OninitListener로 초기화 한다.
        tts = new TextToSpeech(getActivity(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    // 언어를 선택
                    tts.setLanguage(Locale.KOREAN);
                }
            }
        });
        duck_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tts.setSpeechRate(1.0f);
                // TextView에 있는 문장을 읽는다.
                tts.speak(duck.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
            }
        });

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

    // TTS 객체가 남아있는다면 실행을 중지하고 메모리에서 제거한다.
    @Override
    public void onDestroy() {
        super.onDestroy();
        if(tts != null) {
            tts.stop();
            tts.shutdown();
            tts = null;
        }
    }
}