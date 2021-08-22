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

import org.json.JSONException;
import org.json.JSONObject;

import Models.SocketHandler;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class CreateGroupActivity extends AppCompatActivity {
    private TextView Title;
    private EditText groupName;
    private Button AddGroupBtn;
    private Socket mSocket;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);
        Title = findViewById(R.id.Title);
        groupName = findViewById(R.id.groupName);
        AddGroupBtn = findViewById(R.id.AddGroupBtn);



        Typeface custom_font = Typeface.createFromAsset(getAssets(), "assets/font7.otf");
        Title.setTypeface(custom_font);

        SocketHandler.establishConnection();
        mSocket = SocketHandler.getSocket();

        AddGroupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String groupStr = groupName.getText().toString();
                mSocket.emit("groupDetection", groupStr);
                groupName.setText("check!");

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