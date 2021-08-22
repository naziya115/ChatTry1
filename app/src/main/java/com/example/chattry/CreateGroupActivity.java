package com.example.chattry;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
                String groupNameStr = groupName.getText().toString();
                if(groupNameStr!=null&&groupNameStr.length()!=0) {

                }else{
                    groupName.setError("Enter group name");
                }
            }
        });
    }
}