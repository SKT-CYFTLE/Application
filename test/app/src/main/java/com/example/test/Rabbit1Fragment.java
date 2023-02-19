package com.example.test;

import static android.speech.tts.TextToSpeech.ERROR;

import android.content.Context;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Locale;

public class Rabbit1Fragment extends Fragment {

    private TextToSpeech tts;
    private TextView rabbit;
    Context ct;
    public Rabbit1Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_rabbit1, container, false);
        ct = getActivity().getApplication();
        ImageView rabbit_image = (ImageView) rootView.findViewById(R.id.rabbittale_image);

        // Text file에서 내용 읽어오기
        InputStream is = null;
        try {
            is = getActivity().getAssets().open("rabbit.txt");
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

        TextView rabbit = (TextView) rootView.findViewById(R.id.rabbit_tale);
        rabbit.setText(text);
        // textview 스크롤 가능하게 한다
        rabbit.setMovementMethod(new ScrollingMovementMethod());

        // tts를 생성하고 OninitListener로 초기화 한다.
        tts = new TextToSpeech(ct, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != ERROR) {
                    // 언어를 선택
                    tts.setLanguage(Locale.KOREAN);
                }
            }
        });
        rabbit_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tts.setSpeechRate(1.0f);
                // TextView에 있는 문장을 읽는다.
                tts.speak(rabbit.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
            }
        });
        return rootView;
    }
    // TTS 객체가 남아있다면 실행을 중지하고 메모리에서 제거한다.
//    @Override
//    protected void onDestroy() {
//        super .onDestroy();
//        if(tts != null) {
//            tts.stop();
//            tts.shutdown();
//            tts = null;
//        }
//    }
}
