package com.example.chattry;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import Lists.RoomsLists;
import Models.Room;
import Models.User;

public class RegistrationActivity extends AppCompatActivity {
    private TextView AppTitle;
    private EditText nicknameTxt;
    private Button registerBtn;
    private User user = new User();
    MyDatabaseRoomHelper myDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        AppTitle = findViewById(R.id.AppTitle);
        nicknameTxt = findViewById(R.id.nickname);
        registerBtn = findViewById(R.id.registerBtn);

        myDB = new MyDatabaseRoomHelper(RegistrationActivity.this);

        //Устанавливаем шрифт чтобы было по красоте
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "assets/font7.otf");
        AppTitle.setTypeface(custom_font);

        //устанавливаем ник, введенный юзером к нему в класс, чисто сетим ник
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!isEditTextCorrect(nicknameTxt)){
                    Snackbar.make(v,"Fill the field!", BaseTransientBottomBar.LENGTH_SHORT).show();
                    return;
                }

                user.setNickname(nicknameTxt.getText().toString());

                clearEditText(nicknameTxt);

                Intent intent = new Intent(getApplicationContext(),ChatsListActivity.class);
                startActivity(intent);

                Toast toast = Toast.makeText(getApplicationContext(),
                        "New user has registered", Toast.LENGTH_SHORT);
                toast.show();

                storeDataInArrays();
            }
        });
    }

    public void clearEditText(EditText txt){
        txt.getText().clear();
        txt.clearFocus();
    }

    public boolean isEditTextCorrect(EditText txt){
        if(txt.getText().toString().isEmpty()
                || txt.getText().toString() == null
                || txt.getText().toString().trim() == ""){
            return false;
        }
        return true;
    }

    //для высвечивания rooms, которые есть в бд
    void storeDataInArrays(){
        Cursor cursor = myDB.readAllData();
        if(cursor!=null&&cursor.getCount()!=0) {
            while (cursor.moveToNext()) {
                Room room = new Room(cursor.getString(1));
                RoomsLists.addRoom(room);
            }
        }
    }
}