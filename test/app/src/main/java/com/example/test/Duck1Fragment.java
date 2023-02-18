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

import java.util.Locale;

public class Duck1Fragment extends Fragment {

    private TextToSpeech tts;
    private TextView duck;
    Context ct;
    public Duck1Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_duck1, container, false);
        ct = getActivity().getApplication();
        ImageView duck_image = (ImageView) rootView.findViewById(R.id.ducktale_image);

        TextView duck = (TextView) rootView.findViewById(R.id.duck_tale);
        duck.setText(
                "옛날 옛적에 아름다운 농장에서 \n어미 오리 한 마리가 여러 개의 \n알을 부화하였어요." +
                        " 마지막 알이 \n부화했을 때, 어미 오리는 지저분한 깃털을 가진 회색의 아기 오리를 \n보고 놀랐답니다." +
                        " 그 작은 아기 \n오리는 그녀의 다른 아기 오리들과 너무 달랐기 때문이죠." +
                        " 엄마 오리는 자신의 어린 아기 오리가 다른 아기 오리들처럼 예쁘지 않다고 \n생각했어요." +
                        " 심지어 농장의 다른 \n동물들도 그 미운 아기 오리를 \n놀렸답니다." +
                "\n\n" +
                        "못생긴 아기 오리는 그의 외모 \n때문에 모든 사람들에게 \n외면당하고 조롱을 받았어요." +
                        " 다른 아기 오리들은 그에게 꽥꽥거리며 그의 지저분한 깃털을 놀렸고, \n암탉들은 그를 쪼아댔고 수탉은 \n못생긴 아기 오리를 발로 찼기까지 했답니다." +
                        " 못생긴 아기 오리는 너무 슬프고 외로웠고, 그는 왜 그가 다른 사람들과 그렇게 다른지 \n궁금했어요."
        );
        // textview 스크롤 가능하게 한다
        duck.setMovementMethod(new ScrollingMovementMethod());

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
        duck_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tts.setSpeechRate(1.0f);
                // TextView에 있는 문장을 읽는다.
                tts.speak(duck.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
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
