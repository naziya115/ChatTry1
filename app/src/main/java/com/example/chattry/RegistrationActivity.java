package com.example.chattry;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import Models.User;

public class RegistrationActivity extends AppCompatActivity {
    private TextView AppTitle;
    private EditText nickname;
    private Button registerBtn;
    private User user = new User();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        AppTitle = findViewById(R.id.AppTitle);
        nickname = findViewById(R.id.nickname);
        registerBtn = findViewById(R.id.registerBtn);

        Typeface custom_font = Typeface.createFromAsset(getAssets(), "assets/font7.otf");
        AppTitle.setTypeface(custom_font);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setNickname(nickname.getText().toString());
                Toast toast = Toast.makeText(getApplicationContext(),
                        "New user has registered", Toast.LENGTH_SHORT);
                toast.show();

                //comment
            }
        });
    }
}