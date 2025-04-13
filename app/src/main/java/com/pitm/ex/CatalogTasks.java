package com.pitm.ex;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.io.FileInputStream;

public class CatalogTasks extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.catalogtasks1);
    }

//    public void click_next(View v) {
//        Intent intent = new Intent(this, .class);
//        startActivity(intent);
//    }


    public void click_0(View v) {
        int i = readTxtFile();
        Integer size_ = readFile().getPerson(i).progress.size();
        Intent intent = new Intent(this, Task1.class);
        if (size_ == 0) {
            intent = new Intent(this, Task1.class);
        }
        if (size_ == 1) {
            intent = new Intent(this, Task2.class);
        }
        if (size_ == 2) {
            intent = new Intent(this, Task3.class);
        }
        if (size_ == 3) {
            intent = new Intent(this, Task4.class);
        }
        if (size_ == 4) {
            intent = new Intent(this, Task5.class);
        }
        if (size_ == 5) {
            intent = new Intent(this, Task6.class);
        }
        if (size_ == 6) {
            intent = new Intent(this, Task7.class);
        }
        if (size_ >= 7) {
            intent = new Intent(this, Task8.class);
        }
        startActivity(intent);
    }
    public void click_1(View v) {
        Intent intent = new Intent(this, Task1.class);
        startActivity(intent);
    }
    public void click_2(View v) {
        Intent intent = new Intent(this, Task2.class);
        startActivity(intent);
    }
    public void click_3(View v) {
        Intent intent = new Intent(this, Task3.class);
        startActivity(intent);
    }
    public void click_4(View v) {
        Intent intent = new Intent(this, Task4.class);
        startActivity(intent);
    }
    public void click_5(View v) {
        Intent intent = new Intent(this, Task5.class);
        startActivity(intent);
    }
    public void click_6(View v) {
        Intent intent = new Intent(this, Task6.class);
        startActivity(intent);
    }
    public void click_7(View v) {
        Intent intent = new Intent(this, Task7.class);
        startActivity(intent);
    }
    public void click_8(View v) {
        Intent intent = new Intent(this, Task8.class);
        startActivity(intent);
    }



    public Registration.Human readFile() {
        try {
            Gson gson = new Gson();
            FileInputStream fin = openFileInput("Accounts.json");
            byte[] bytes = new byte[fin.available()];
            fin.read(bytes);
            String text = new String(bytes);
            return gson.fromJson(text, Registration.Human.class);
        } catch (Exception e) {
            System.out.println("Ошибка в чтении");
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
            System.out.println("Ошибка в чтении txt");
            return 0;
        }
    }
}