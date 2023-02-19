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

public class Fox1Fragment extends Fragment {

    private TextToSpeech tts;
    private TextView frog;
    Context ct;
    public Fox1Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_fox1, container, false);
        ct = getActivity().getApplication();
        ImageView fox_image = (ImageView) rootView.findViewById(R.id.foxtale_image);

        // Text file에서 내용 읽어오기
        InputStream is = null;
        try {
            is = getActivity().getAssets().open("fox.txt");
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

        TextView fox = (TextView) rootView.findViewById(R.id.fox_tale);
        fox.setText(text);
        // textview 스크롤 가능하게 한다
        fox.setMovementMethod(new ScrollingMovementMethod());

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
        fox_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tts.setSpeechRate(1.0f);
                // TextView에 있는 문장을 읽는다.
                tts.speak(fox.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
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
