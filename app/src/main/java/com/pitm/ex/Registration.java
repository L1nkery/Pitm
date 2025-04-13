package com.pitm.ex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Environment;
import android.view.View;
import android.os.Bundle;
import android.widget.Toast;
import android.widget.EditText;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class Registration extends AppCompatActivity {
    public static String fileAccounts = "Accounts.json";
    public static String fileAcc = "NowAcc.txt";


    public static class Human{
        public Map<Integer, Person> map_people = new HashMap<>();

        public Person getPerson(int i) {
            return (Person) map_people.get(i);
        }
    }

    public static class Person{
        public String password;
        public String name;
        public Map<Integer, String> progress = new HashMap<>();

        public void setNameP(String nameP) {
            name = nameP;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);



        File dir = new File("/data/data/com.pitm.ex/files");
        String[] names = dir.list(
            new FilenameFilter()
            {
                public boolean accept(File dir, String name)
                {
                    System.out.println(name);
                    return name.endsWith(".json");
                }
            });
        if (new File("/data/data/com.pitm.ex/files/" + fileAcc).exists()) {
            Intent intent = new Intent(this, MainPage.class);
            startActivity(intent);
        }
        if (!new File("/data/data/com.pitm.ex/files/" + fileAccounts).exists()) {
            createFile();
        }

    }


    public void haveLogin(View v) {
        setContentView(R.layout.login);
    }

    public void notHaveLogin(View v) {
        setContentView(R.layout.registration);
    }

    public void comeIn(View v) {
        EditText editl = findViewById(R.id.editNameL);
        String namel = editl.getText().toString();

        EditText passl = findViewById(R.id.editPasswordL);
        String text_passl = passl.getText().toString();

        Human h = readFile();

        boolean proverka = false;
        for (int i = 1; i <= h.map_people.size(); i++) {
            Person p = (Person) h.getPerson(i);
            if (p.name.equals(namel) && p.password.equals(text_passl)) // проверка на правильность логина и пароля
                proverka = true;
        }
        if (proverka) {
            Integer s = readFile().map_people.size();
            createTxtFile(s.toString());

            Intent intent = new Intent(this, MainPage.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Неправильный логин или пароль", Toast.LENGTH_SHORT).show();
        }
    }


    public void createAccount(View v) {
        EditText edit = findViewById(R.id.editName);
        String name = edit.getText().toString();

        EditText pass = findViewById(R.id.editPassword);
        String text_pass = pass.getText().toString();
        EditText pass2 = findViewById(R.id.editPassword2);
        String text_pass2 = pass2.getText().toString();
        if (text_pass.equals(text_pass2)) {
            Human h = readFile();
            if (!h.map_people.isEmpty()) {

                boolean proverka = true; // проверка на имя
                for (int i = 1; i <= h.map_people.size(); i++) {
                    Person p = (Person) h.getPerson(i);
                    if (p.name.equals(name))
                        proverka = false;

                } if (proverka) {
                    Person pers = new Person();
                    pers.password = text_pass;
                    pers.name = name;
                    editFile(pers);

                    Integer s = readFile().map_people.size();
                    createTxtFile(s.toString());
                    Intent intent = new Intent(this, MainPage.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(this, "Такое имя существует", Toast.LENGTH_SHORT).show();
                }
            } else {
                createFile();
            }
        } else {
            Toast.makeText(this, "Пароли не совпадают", Toast.LENGTH_SHORT).show();
        }
    }


    public void createFile() {
        try {
            Human human = new Human();
            Person person = new Person();

            person.password = "example";
            person.name = "example";
            human.map_people.put(1, person);

            Gson gson = new Gson();
            String json = gson.toJson(human);
            FileOutputStream fileOutputStream = openFileOutput(fileAccounts, MODE_PRIVATE);
            fileOutputStream.write(json.getBytes());
            fileOutputStream.close();
        } catch (Exception e) {
            Toast.makeText(this, "Ошибка в создании JSON", Toast.LENGTH_SHORT).show();
        }
    }


    public void editFile(Person person) { // добавление нового аккаунта
        try {
            Human human = (Human) readFile();


            human.map_people.put(human.map_people.size() + 1, person);

            Gson gson = new Gson();
            String json = gson.toJson(human);
            FileOutputStream fileOutputStream = openFileOutput(fileAccounts, MODE_PRIVATE);
            fileOutputStream.write(json.getBytes());
            fileOutputStream.close();
        } catch (Exception e) {
            Toast.makeText(this, "Ошибка в редактировании JSON", Toast.LENGTH_SHORT).show();
        }
    }


    public Human readFile() {
        try {
            Gson gson = new Gson();
            FileInputStream fin = openFileInput(fileAccounts);
            byte[] bytes = new byte[fin.available()];
            fin.read(bytes);
            String text = new String(bytes);
            return gson.fromJson(text, Human.class);
        } catch (Exception e) {
            Toast.makeText(this, "Ошибка в чтении", Toast.LENGTH_SHORT).show();
            return new Human();
        }
    }

    public void printFile() {
        Human h = (Human) readFile();
        for (int i = 1; i <= h.map_people.size(); i++) {
            System.out.println(i);
            System.out.println(h.getPerson(i).name + " " + h.getPerson(i).password);
            System.out.println(h.getPerson(i).progress.toString());
        }
    }

    public void createTxtFile(String text) {
        try {
            FileOutputStream fileOutputStream = openFileOutput(fileAcc, MODE_PRIVATE);
            fileOutputStream.write(text.getBytes());
            fileOutputStream.close();
        } catch (Exception e) {
            Toast.makeText(this, "Ошибка в распозновании", Toast.LENGTH_SHORT).show();
        }
    }

    public int readTxtFile() {
        try {
            FileInputStream fin = openFileInput(fileAcc);
            byte[] bytes = new byte[fin.available()];
            fin.read(bytes);
            String text = new String(bytes);
            return Integer.parseInt(text);
        } catch (Exception e) {
            Toast.makeText(this, "Ошибка в чтении распознавания", Toast.LENGTH_SHORT).show();
            return 0;
        }
    }
}