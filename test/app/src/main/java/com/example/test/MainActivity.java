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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private String[] phrases = {"겨울엔 역시 붕어빵이지!\n 군고구마도 좋고~\n 고구마 라떼도 좋아~\n 넌 어때?",
            "하루에 하나씩 나의\n 유용한 기능을 알아가볼래?\n 오늘은 재밌는 tv 보여줄게~",
            "클래식을 들으면\n 마음이 차분해져서 좋아~\n 지금 클래식 감상 어때?",
            "겨울바람에 꽁꽁 얼어 있어?\n 그래도 움츠려있기 보단 힘차게\n 운동해보는 거 어때?",
            "밥친구 영상 고르기 힘들지?\n 재밌는 걸로 골라서 보여줄게~",
            "지금 좀 심심하지 않아?\n 뭘하면 좋을까?",
            "이제 뭐 할 거야?",
            "멍하니 흘려버리는\n시간이 아깝다면\n 팟캐스트 듣는 거 어때?"
    };
    private List<Integer> usedIndices = new ArrayList<>();

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

        // 랜덤 텍스트 구현
        TextView txt = (TextView) findViewById(R.id.txt);

        // 플로팅 버튼
        FloatingActionButton fltButton = (FloatingActionButton) findViewById(R.id.floating);
        fltButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = getRandomIndex();
                // Retrieve the phrase associated with the index
                String phrase = phrases[index];

                txt.setText(phrase);
            }
        });
    }
    // 커스텀 툴바 xml에서 버튼을 불러오는 메소드
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.title, menu);
        return true;
    }
    // 커스텀 툴바 버튼이 눌렸을 때 구현 시킬 기능들
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
                // 액티비티 왼쪽에서 오른쪽으로 들어오는 애니메이션
                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_none);

                return true;
            case android.R.id.home:
                Intent drawerIntent = new Intent(MainActivity.this, DrawerActivity.class);
                startActivity(drawerIntent);
                // 액티비티 오른쪽에서 왼쪽으로 들어오는 애니메이션
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_none);

                return true;
        }
        return super .onOptionsItemSelected(item);
    }
    // 랜덤 텍스트 구현
    private int getRandomIndex() {
        // Generate a random index that has not been used before
        int index = (int) (Math.random() * phrases.length);
        while (usedIndices.contains(index)) {
            index = (int) (Math.random() * phrases.length);
        }

        // Add the index to the list of used indices
        usedIndices.add(index);

        // If all indices have been used, clear the list and start over
        if (usedIndices.size() == phrases.length) {
            usedIndices.clear();
        }

        return index;
    }
}