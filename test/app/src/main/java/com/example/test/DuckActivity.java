package com.example.test;

import static android.speech.tts.TextToSpeech.ERROR;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;

public class DuckActivity extends AppCompatActivity {

    private TextToSpeech tts;
    private TextView duck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duck);

        // 툴바
        Toolbar toolbar = (Toolbar) findViewById(R.id.alarmToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        ImageView duck_image = (ImageView) findViewById(R.id.ducktale_image);

        TextView duck = (TextView) findViewById(R.id.duck_tale);
        duck.setText(
                "따스한 햇살이 내려앉은 개울가 덤불 숲에서,\n" +
                "어미오리가 알을 품고 있었어요.\n" +
                "톡! 톡! 알에 금이 가더니,\n" +
                "빠직 빠지직! 아기오리들이 하나둘 고개를 내밀었어요.\n" +
                "“꽥꽥! 꽥꽥!”\n" +
                "\n" +
                "마지막으로 가장 커다란 알 하나만 남았어요.\n" +
                "어미 오리는 다시 알을 품고 기다렸지요.\n" +
                "\n" +
                "어느 따스한 날\n" +
                "알을 톡!톡!\n" +
                "알이 빠직 빠지직! 깨지면서\n" +
                "귀여운 아기 오리들이\n" +
                "하나 둘 태어납니다.\n" +
                "\n" +
                "그런데 유독 커다란 알이 하나 남아 있네요.\n" +
                "엄마 오리는 다시 알을 품습니다."
        );
        // textview 스크롤 가능하게 한다
        duck.setMovementMethod(new ScrollingMovementMethod());
        // tts를 생성하고 OninitListener로 초기화 한다.
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
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
     }
    // 커스텀 툴바 xml에서 버튼을 불러오는 메소드
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.alarm_title, menu);
        return true;
    }
    // 커스텀 툴바 버튼이 눌렸을 때 구현 시킬 기능들
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.close:
                finish();

                return true;
        }
        return super .onOptionsItemSelected(item);
    }
    // TTS 객체가 남아있다면 실행을 중지하고 메모리에서 제거한다.
    @Override
    protected void onDestroy() {
        super .onDestroy();
        if(tts != null) {
            tts.stop();
            tts.shutdown();
            tts = null;
        }
    }
}