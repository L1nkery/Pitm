package com.pitm.ex;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class Task7 extends AppCompatActivity {
    Button but_a;
    Button but_b;
    Button but_c;
    TextView obvios;
    TextView trueorfalse;
    Button but_next;
    Button but_cat;
    boolean btnclicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task6_writingnoteonstan);
    }

    public void click_next2(View v) {
        setContentView(R.layout.task6_writingnoteonstantask);
        but_a = (Button) findViewById(R.id.but_a);
        but_b = (Button) findViewById(R.id.but_b);
        but_c = (Button) findViewById(R.id.but_c);
        obvios = (TextView) findViewById(R.id.obvios);
        trueorfalse = (TextView) findViewById(R.id.trueorfalse);
        but_next = (Button) findViewById(R.id.button_next);
        but_cat = (Button) findViewById(R.id.button_catalog);
    }

    public void click_next(View v) {
        setContentView(R.layout.task6_writingnoteonstan2);
    }

    public void click_next3(View v) {
        Intent intent = new Intent(this, Task8.class);
        startActivity(intent);
    }

    public void click_catalog(View v) {
        Intent intent = new Intent(this, CatalogTasks.class);
        startActivity(intent);
    }

    public void click_a(View v) {
        if (btnclicked == false) {
            trueorfalse.setText("Неправильно");
            trueorfalse.setVisibility(1);
            trueorfalse.setTextColor(getColor(R.color.red));
            obvios.setVisibility(1);
            btnclicked = true;
            but_next.setVisibility(1);
            but_cat.setVisibility(1);
        }
        but_a.setBackgroundColor(getColor(R.color.red));
    }
    public void click_b(View v) {
        if (btnclicked == false) {
            trueorfalse.setText("Неправильно");
            trueorfalse.setVisibility(1);
            trueorfalse.setTextColor(getColor(R.color.red));
            obvios.setVisibility(1);
            btnclicked = true;
            but_next.setVisibility(1);
            but_cat.setVisibility(1);
        }
        but_b.setBackgroundColor(getColor(R.color.red));
    }
    public void click_c(View v) {
        if (btnclicked == false) {
            trueorfalse.setText("Правильно");
            trueorfalse.setVisibility(1);
            trueorfalse.setTextColor(getColor(R.color.green));
            obvios.setVisibility(1);
            btnclicked = true;
            but_next.setVisibility(1);
            but_cat.setVisibility(1);
        }
        but_c.setBackgroundColor(getColor(R.color.green));
    }



    public void editFile(String result) {
        try {
            Registration.Human human = (Registration.Human) readFile();
            human.getPerson(readTxtFile()).progress.put(7, result);

            Gson gson = new Gson();
            String json = gson.toJson(human);
            FileOutputStream fileOutputStream = openFileOutput(Registration.fileAccounts, MODE_PRIVATE);
            fileOutputStream.write(json.getBytes());
            fileOutputStream.close();
        } catch (Exception e) {
            Toast.makeText(this, "Ошибка в редактировании txt", Toast.LENGTH_SHORT).show();
        }
    }


    public Registration.Human readFile() {
        try {
            Gson gson = new Gson();
            FileInputStream fin = openFileInput(Registration.fileAccounts);
            byte[] bytes = new byte[fin.available()];
            fin.read(bytes);
            String text = new String(bytes);
            return gson.fromJson(text, Registration.Human.class);
        } catch (Exception e) {
            Toast.makeText(this, "Ошибка в чтении", Toast.LENGTH_SHORT).show();
            return new Registration.Human();
        }
    }

    public int readTxtFile() {
        try {
            FileInputStream fin = openFileInput(Registration.fileAcc);
            byte[] bytes = new byte[fin.available()];
            fin.read(bytes);
            String text = new String(bytes);
            return Integer.parseInt(text);
        } catch (Exception e) {
            Toast.makeText(this, "Ошибка в чтении txt", Toast.LENGTH_SHORT).show();
            return 0;
        }
    }
}