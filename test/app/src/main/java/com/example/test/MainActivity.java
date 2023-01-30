package com.example.test;

import static androidx.core.content.PackageManagerCompat.LOG_TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // 툴바에 뒤로가기 버튼 활성화
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // 툴바의 홈버튼의 이미지를 변경(기본 이미지는 뒤로가기 화살표)
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.menu_round);
        // 툴바의 타이틀을 삭제 하는 것
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        // 스위치 버튼 커스텀하는 데 필요
        SwitchCompat switchcp = (SwitchCompat) findViewById(R.id.switchOnOff);
        ImageView imgMic = (ImageView) findViewById(R.id.switchMic);
        ImageView imgKey = (ImageView) findViewById(R.id.switchKeyboard);

        // 랜덤 텍스트 구현
        TextView txt = (TextView) findViewById(R.id.txt);
        String[] randomTxt = getResources().getStringArray(R.array.randomTxt);
        Random random = new Random();
        int n = random.nextInt(randomTxt.length - 1);
        txt.setText(randomTxt[n]);

        // 플로팅 버튼
        FloatingActionButton fltButton = (FloatingActionButton) findViewById(R.id.floating);
        fltButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();//인텐트 종료
                overridePendingTransition(0, 0);//인텐트 효과 없애기
                Intent floatIntent = getIntent(); //인텐트
                startActivity(floatIntent); //액티비티 열기
                overridePendingTransition(0, 0);//인텐트 효과 없애기
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.title, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share:
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.addCategory(Intent.CATEGORY_DEFAULT);
                shareIntent.putExtra(Intent.EXTRA_TEXT, "https://github.com/TA-PP");
                shareIntent.setType("text/plain");
                startActivity(Intent.createChooser(shareIntent, "앱을 선택해 주세요"));
                return true;
            case R.id.bell:
                Intent bellIntent = new Intent(MainActivity.this, AlarmActivity.class);
                startActivity(bellIntent);
                finish();
                return true;
        }
        return super .onOptionsItemSelected(item);
    }
}