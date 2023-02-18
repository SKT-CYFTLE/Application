package com.example.test;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class CyftleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cyftle);

        // 툴바 생성
        Toolbar toolbar = findViewById(R.id.alarmToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        ImageView duckImage = (ImageView) findViewById(R.id.duck_image);
        TextView duckText = (TextView) findViewById(R.id.duck_text);
        ImageView frogImage = (ImageView) findViewById(R.id.frog_image);
        TextView frogText = (TextView) findViewById(R.id.frog_text);
        duckImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent duckIntent = new Intent(CyftleActivity.this, ViewDuckActivity.class);
                startActivity(duckIntent);
            }
        });
        duckText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent duckIntent = new Intent(CyftleActivity.this, ViewDuckActivity.class);
                startActivity(duckIntent);
            }
        });
        frogImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent frogIntent = new Intent(CyftleActivity.this, ViewFrogActivity.class);
                startActivity(frogIntent);
            }
        });
        frogText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent frogIntent = new Intent(CyftleActivity.this, ViewFrogActivity.class);
                startActivity(frogIntent);
            }
        });
    }
    // 툴바 초기화
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.alarm_title, menu);
        return true;
    }
    // 툴바 버튼 기능 구현
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.close:
                finish();

                return true;
        }
        return super .onOptionsItemSelected(item);
    }
}