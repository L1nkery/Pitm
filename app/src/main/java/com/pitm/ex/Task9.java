package com.pitm.ex;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class Task9 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_etc);
    }

    public void click_catalog(View v) {
        Intent intent = new Intent(this, CatalogTasks.class);
        startActivity(intent);
    }
}
