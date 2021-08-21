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

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import Models.User;

public class RegistrationActivity extends AppCompatActivity {
    private TextView AppTitle;
    private EditText nicknameTxt;
    private Button registerBtn;
    private User user = new User();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        AppTitle = findViewById(R.id.AppTitle);
        nicknameTxt = findViewById(R.id.nickname);
        registerBtn = findViewById(R.id.registerBtn);

        Typeface custom_font = Typeface.createFromAsset(getAssets(), "assets/font7.otf");
        AppTitle.setTypeface(custom_font);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nicknameTxt.getText().toString().isEmpty()
                || nicknameTxt.getText().toString() == null
                || nicknameTxt.getText().toString().trim() == ""){
                    Snackbar.make(v,"Feel the field!", BaseTransientBottomBar.LENGTH_SHORT).show();
                    return;
                }
                user.setNickname(nicknameTxt.getText().toString());

                nicknameTxt.getText().clear();
                nicknameTxt.clearFocus();

                Intent intent = new Intent(getApplicationContext(),ChatsListActivity.class);

                startActivity(intent);

                Toast toast = Toast.makeText(getApplicationContext(),
                        "New user has registered", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }
}