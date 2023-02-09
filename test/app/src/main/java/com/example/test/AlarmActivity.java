package com.example.test;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class AlarmActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.alarm_title, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.close:
                Intent backIntent = new Intent(AlarmActivity.this, MainActivity.class);
                backIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(backIntent);
                finish();
                return true;
        }
        return super .onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent backIntent = new Intent(AlarmActivity.this, MainActivity.class);
        backIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(backIntent);
        finish();
    }
}
