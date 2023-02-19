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
        ImageView rabbitImage = (ImageView) findViewById(R.id.rabbit_image);
        TextView rabbitText = (TextView) findViewById(R.id.rabbit_text);
        ImageView pigImage = (ImageView) findViewById(R.id.pig_image);
        TextView pigText = (TextView) findViewById(R.id.pig_text);
        ImageView goldImage = (ImageView) findViewById(R.id.gold_image);
        TextView goldText = (TextView) findViewById(R.id.gold_text);
        ImageView foxImage = (ImageView) findViewById(R.id.fox_image);
        TextView foxText = (TextView) findViewById(R.id.fox_text);
        ImageView tigerImage = (ImageView) findViewById(R.id.tiger_image);
        TextView tigerText = (TextView) findViewById(R.id.tiger_text);
        ImageView backfrogImage = (ImageView) findViewById(R.id.backfrog_image);
        TextView backfrogText = (TextView) findViewById(R.id.backfrog_text);
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
        rabbitImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent rabbitIntent = new Intent(CyftleActivity.this, ViewRabbitActivity.class);
                startActivity(rabbitIntent);
            }
        });
        rabbitText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent rabbitIntent = new Intent(CyftleActivity.this, ViewRabbitActivity.class);
                startActivity(rabbitIntent);
            }
        });
        pigImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pigIntent = new Intent(CyftleActivity.this, ViewPigActivity.class);
                startActivity(pigIntent);
            }
        });
        pigText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pigIntent = new Intent(CyftleActivity.this, ViewPigActivity.class);
                startActivity(pigIntent);
            }
        });
        goldImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goldIntent = new Intent(CyftleActivity.this, ViewGoldActivity.class);
                startActivity(goldIntent);
            }
        });
        goldText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goldIntent = new Intent(CyftleActivity.this, ViewGoldActivity.class);
                startActivity(goldIntent);
            }
        });
        foxImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent foxIntent = new Intent(CyftleActivity.this, ViewFoxActivity.class);
                startActivity(foxIntent);
            }
        });
        foxText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent foxIntent = new Intent(CyftleActivity.this, ViewFoxActivity.class);
                startActivity(foxIntent);
            }
        });
        tigerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tigerIntent = new Intent(CyftleActivity.this, ViewTigerActivity.class);
                startActivity(tigerIntent);
            }
        });
        tigerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tigerIntent = new Intent(CyftleActivity.this, ViewTigerActivity.class);
                startActivity(tigerIntent);
            }
        });
        backfrogImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backfrogIntent = new Intent(CyftleActivity.this, ViewBackFrogActivity.class);
                startActivity(backfrogIntent);
            }
        });
        backfrogText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backfrogIntent = new Intent(CyftleActivity.this, ViewBackFrogActivity.class);
                startActivity(backfrogIntent);
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