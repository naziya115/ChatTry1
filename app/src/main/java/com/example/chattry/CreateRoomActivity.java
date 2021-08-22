package com.example.chattry;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import Lists.RoomsLists;
import Models.Room;
import Models.SocketHandler;
import io.socket.client.Socket;

public class CreateRoomActivity extends AppCompatActivity {
    private TextView Title;
    private EditText roomNameTxt;
    private Button addRoomBtn;
    private Socket mSocket;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room);
        Title = findViewById(R.id.Title);
        roomNameTxt = findViewById(R.id.roomNameTxt);
        addRoomBtn = findViewById(R.id.addRoomBtn);



        //Делаем шрифт, чтобы было по красоте
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "assets/font7.otf");
        Title.setTypeface(custom_font);


        mSocket = SocketHandler.getSocket();

        //кнопка нужна для добавления ново созданной комнаты всем, кто в конекте
        addRoomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isEditTextCorrect(roomNameTxt)){
                    Snackbar.make(v,"Fill the field!", BaseTransientBottomBar.LENGTH_SHORT).show();
                    return;
                }

                String roomStr = roomNameTxt.getText().toString();
                mSocket.emit("roomDetection", roomStr);


                clearEditText(roomNameTxt);


                Intent intent = new Intent(getApplicationContext(),ChatsListActivity.class);

                Room room = new Room();
                room.setRoomName(roomStr);
                RoomsLists.addRoom(room);

                startActivity(intent);

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

    public void clearEditText(EditText txt){
        txt.getText().clear();
        txt.clearFocus();
    }
}