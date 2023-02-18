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

public class Frog1Fragment extends Fragment {

    private TextToSpeech tts;
    private TextView duck;
    Context ct;
    public Frog1Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_frog1, container, false);
        ct = getActivity().getApplication();
        ImageView frog_image = (ImageView) rootView.findViewById(R.id.frogtale_image);

        TextView frog = (TextView) rootView.findViewById(R.id.frog_tale);
        frog.setText(
                "옛날에, 아름다운 딸을 가진 왕이 있었어요. " +
                        "어느 날, 아름다운 공주가 마당에서 황금 공을 가지고 놀다가 실수로 황금 공을 연못에 빠뜨렸어요. " +
                        "공주는 매우 화가 나고 어떻게 해야 할지 몰랐어요. " +
                        "바로 그때, 개구리가 나타나 공주에게 무슨 일이 있는지 물어보았어요. " +
                        "공주는 개구리에게 연못에서 어떻게 황금 공을 잃어버렸는지에 대해 말했답니다." +
                        "" +
                        "그러자 개구리는 말했어요. \"저는 당신이 공을 되찾는 것을 도울 수 있지만, 그 대가로 저에게 무엇을 주실 건가요?" +
                        "" +
                        "공주는 잠시 생각하다가 개구리에게 원하는 것은 무엇이든 주겠다고 약속했어요. " +
                        "그걸 들은 개구리는 연못에 뛰어들어 황금 공을 가지고 돌아왔답니다. " +
                        "공주는 매우 기뻐했고 그 뒤 개구리에 대해 모든 것을 잊고 궁전으로 달려가 버렸답니다."
        );
        // textview 스크롤 가능하게 한다
        frog.setMovementMethod(new ScrollingMovementMethod());

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
        frog_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tts.setSpeechRate(1.0f);
                // TextView에 있는 문장을 읽는다.
                tts.speak(frog.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
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
