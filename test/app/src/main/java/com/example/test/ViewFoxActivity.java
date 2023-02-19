package com.example.test;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

public class ViewFoxActivity extends AppCompatActivity {
    private ViewPager2 pager;
    private FragmentStateAdapter pagerAdapter;
    private ZoomOutPageTransformer pageTransformer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewfox);

        // 툴바 생성
        Toolbar toolbar = findViewById(R.id.alarmToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        // 프래그먼트 리스트 생성
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new Fox1Fragment());
        fragments.add(new Fox2Fragment());
        fragments.add(new SttFragment());
        fragments.add(new Fox3Fragment());
        // 페이저 생성
        pager = findViewById(R.id.pager);
        pagerAdapter = new FoxPagerAdapter(this, fragments);
        pager.setAdapter(pagerAdapter);
        pager.setPageTransformer(pageTransformer);

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
            default:
                return super .onOptionsItemSelected(item);
        }
    }
}