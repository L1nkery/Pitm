package com.pitm.ex;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class Profile extends AppCompatActivity { // ДОДЕЛАТЬ
    public ImageView avatar;
    public String filePhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        filePhoto = readTxtFile() + ".jpg";
        set_name_and_photo();
    }

    public void set_name_and_photo() {
        try {
            String name = readFile().getPerson(readTxtFile()).name;
            TextView namePerson = findViewById(R.id.namePerson);
            namePerson.setText(name);


            avatar = findViewById(R.id.avatar);
            File imgFile = new File("/data/data/com.pitm.ex/files/" + filePhoto);

            Bitmap imgBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            avatar.setImageBitmap(imgBitmap);

        }
        catch (Exception ignored) {
            System.out.println("Ошибка в фото или имени");
        }
    }

    public void edit_account(View v) {
        setContentView(R.layout.edit_profile);
        set_name_and_photo();
    }

    public void exit_account(View v) {
        Intent intent = new Intent(this, Registration.class);
        startActivity(intent);
        (new File("/data/data/com.pitm.ex/files/" + Registration.fileAcc)).delete();
    }
    public void main_page(View v) {
        Intent intent = new Intent(this, MainPage.class);
        startActivity(intent);
    }



    public void edit_photo(View v) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT).setType("image/*");
        startActivityForResult(intent, 1);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            try {
                Uri filePath = data.getData();
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), filePath);
                FileOutputStream fos = openFileOutput(filePhoto, MODE_PRIVATE);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 85, fos);
                fos.close();
            } catch (Exception ignored) {}
        }
        set_name_and_photo();
    }

    public void edit_name(View v) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_edit_name, null);
        mBuilder.setView(view).setTitle("Изменение никнейма").
                setPositiveButton("Сохранить", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        EditText edname = findViewById(R.id.edit_name_dialog);
                        String text = (String) edname.getText().toString();
                        if (valid_name(text)) {
                            editName(text);
                        }
                        dialog.cancel();
                    }}).
                setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }});
        AlertDialog dialog = mBuilder.create();
        dialog.show();
    }

    public void edit_password(View v) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_edit_password, null);
        mBuilder.setView(view).
                setTitle("Изменение никнейма").
                setPositiveButton("Сохранить", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        EditText edpassword = findViewById(R.id.password);
                        String text = edpassword.getText().toString();
                        EditText ednew_password = findViewById(R.id.new_password);
                        String new_password = ednew_password.getText().toString();
                        EditText ednew_password2 = findViewById(R.id.new_password2);
                        String new_password2 = ednew_password2.getText().toString();
                        if (valid_password(text, new_password, new_password2)) {
                            editPassword(new_password);
                            dialog.cancel();
                        } else {
                            Toast.makeText(Profile.this, "Ошибка в паролях", Toast.LENGTH_SHORT).show();
                        }
                    }}).
                setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }});
        AlertDialog dialog = mBuilder.create();
        dialog.show();
    }

    public boolean valid_name(String name) {
        Registration.Human h = readFile();
        boolean proverka = true; // проверка на имя
        for (int i = 1; i <= h.map_people.size(); i++) {
            Registration.Person p = h.getPerson(i);
            if (p.name.equals(name))
                proverka = false;
        }
        return proverka;
    }

    public boolean valid_password(String password, String new_password, String new_password2) {
        Registration.Human h = readFile();
        String p = h.getPerson(readTxtFile()).password;
        return password.equals(p) && new_password.equals(new_password2);
    }

    public void save(View v) {
        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
    }



    public void editPassword(String password) {
//        try {
//            FileOutputStream fos = openFileOutput(Registration.fileAccounts, MODE_PRIVATE);
//            fos.write(password.getBytes());
//            fos.close();
//        } catch (Exception ignored) {}
    }

    public void editName (String name) {
        try {
            Registration.Human human = (Registration.Human) readFile();
            int i = readTxtFile();
            human.getPerson(i).setNameP(name);
            Gson gson = new Gson();
            String json = gson.toJson(human);
            FileOutputStream fileOutputStream = openFileOutput(Registration.fileAccounts, MODE_PRIVATE);
            fileOutputStream.write(json.getBytes());
            fileOutputStream.close();
        } catch (Exception e) {
            Toast.makeText(this, "Ошибка в редактировании JSON", Toast.LENGTH_SHORT).show();
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