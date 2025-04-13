package com.pitm.ex;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.io.FileInputStream;

public class MainPage extends AppCompatActivity {
    public Registration.Human human;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainpage);

        human = (Registration.Human) readFile();
        TextView name = (TextView) findViewById(R.id.name);
        name.setText(human.getPerson(readTxtFile()).name);
    }

    public void goProfile(View v) {
        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
    }


    public void startLesson(View v) {
        int s = human.getPerson(readTxtFile()).progress.size();
        Intent intent = new Intent(this, Task1.class);
        if (s == 2) {
            intent = new Intent(this, Task2.class);
        } else if (s == 3) {
            intent = new Intent(this, Task3.class);
        } else if (s == 4) {
            intent = new Intent(this, Task4.class);
        } else if (s == 5) {
            intent = new Intent(this, Task5.class);
        } else if (s == 6) {
            intent = new Intent(this, Task6.class);
        } else if (s == 7) {
            intent = new Intent(this, Task7.class);
        } else if (s == 8) {
            intent = new Intent(this, Task8.class);
        } else if (s == 9) {
            intent = new Intent(this, Task9.class);
        }
        startActivity(intent);
    }
    public void goCataloglesson(View v) {
        Intent intent = new Intent(this, CatalogTasks.class);
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
