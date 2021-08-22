package com.example.chattry;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class CreateGroupActivity extends AppCompatActivity {
    private TextView Title;
    private EditText groupName;
    private Button AddGroupBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);
        Title = findViewById(R.id.Title);
        groupName = findViewById(R.id.groupName);
        AddGroupBtn = findViewById(R.id.AddGroupBtn);

        Typeface custom_font = Typeface.createFromAsset(getAssets(), "assets/font7.otf");
        Title.setTypeface(custom_font);


        AddGroupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isEditTextCorrect(groupName)){
                    Snackbar.make(v,"Fill the field!", BaseTransientBottomBar.LENGTH_SHORT).show();
                    return;
                }
            }
        });
    }

    public boolean isEditTextCorrect(EditText txt){
        if(txt.getText().toString().isEmpty()
                || txt.getText().toString() == null
                || txt.getText().toString().trim() == ""){
            return false;
        }
        return true;
    }
}