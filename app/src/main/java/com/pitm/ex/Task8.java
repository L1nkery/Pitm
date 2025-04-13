package com.pitm.ex;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class Task8 extends AppCompatActivity {
    String first, second, third, fourth;
    int result;
    TextView results, r1, r2, r3, r4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task8_task);
    }

    public void click_a(View v) {
        first = "Правильно";
        result += 25;
        setContentView(R.layout.task8_task2);
    } // правильный
    public void click_b(View v) {
        first = "Неправильно";
        setContentView(R.layout.task8_task2);
    }
    public void click_c(View v) {
        first = "Неправильно";
        setContentView(R.layout.task8_task2);
    }
    public void click_d(View v) {
        first = "Неправильно";
        setContentView(R.layout.task8_task2);
    }

    public void click_a2(View v) {
        second = "Неправильно";
        setContentView(R.layout.task8_task3);
    }
    public void click_b2(View v) {
        second = "Неправильно";
        setContentView(R.layout.task8_task3);
    }
    public void click_c2(View v) {
        second = "Правильно";
        result += 25;
        setContentView(R.layout.task8_task3);
    } // правильный
    public void click_d2(View v) {
        second = "Неправильно";
        setContentView(R.layout.task8_task3);
    }

    public void click_a3(View v) {
        third = "Неправильно";
        setContentView(R.layout.task8_task4);
    }
    public void click_b3(View v) {
        third = "Правильно";
        result += 25;
        setContentView(R.layout.task8_task4);
    } // правильно
    public void click_c3(View v) {
        third = "Правильно";
        result += 25;
        setContentView(R.layout.task8_task4);
    } // правильно
    public void click_d3(View v) {
        third = "Неправильно";
        setContentView(R.layout.task8_task4);
    }

    public void click_a4(View v) {
        fourth = "Неправильно";
        fifth_page();
    }
    public void click_b4(View v) {
        fourth = "Неправильно";
        fifth_page();
    }
    public void click_c4(View v) {
        fourth = "Неправильно";
        fifth_page();
    }
    public void click_d4(View v) {
        fourth = "Правильно";
        result += 25;
        fifth_page();
    } // правильно

    void fifth_page() {
        setContentView(R.layout.task8_task5);
        results = (TextView) findViewById(R.id.result);
        r1 = (TextView) findViewById(R.id.first);
        r2 = (TextView) findViewById(R.id.second);
        r3 = (TextView) findViewById(R.id.third);
        r4 = (TextView) findViewById(R.id.fourth);

        results.setText(result + "%");
        r1.setText("Первое задание - " + first);
        r2.setText("Второе задание - " + second);
        r3.setText("Третье задание - " + third);
        r4.setText("Четвёртое задание - " + fourth);
    }

    public void click_next(View v) {
        Intent intent = new Intent(this, Task9.class);
        startActivity(intent);
    }

    public void click_catalog(View v) {
        Intent intent = new Intent(this, CatalogTasks.class);
        startActivity(intent);
    }

    public void editFile(String result) {
        try {
            Registration.Human human = (Registration.Human) readFile();
            human.getPerson(readTxtFile()).progress.put(1, result);

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