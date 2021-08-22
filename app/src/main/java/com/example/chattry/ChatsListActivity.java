package com.example.chattry;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import Adapters.RoomAdapter;
import Models.Room;

public class ChatsListActivity extends AppCompatActivity {

    ListView roomsListView;
    FloatingActionButton addRoomFAB;
    ArrayList<Room> roomsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats_list);

        roomsListView=findViewById(R.id.roomsListView);

        addRoomFAB=findViewById(R.id.addRoomFAB);

        roomsList=new ArrayList<>();

        initRoomsForTry();

        createListView(this.roomsList);

        addRoomFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),CreateGroupActivity.class);
                startActivity(intent);
            }
        });
    }

    //метод нужен для проверки работы самого листвью и чтобы посмотреть, как выглядят мои шаблоны
    public void initRoomsForTry(){
        for(int i=0;i<=10;i++){
            Room room = new Room();
            room.setRoomName("Room For Try");
            roomsList.add(room);
        }
    }

    public void createListView(ArrayList<Room> roomsList){
        RoomAdapter roomAdapter = new RoomAdapter(getApplicationContext(),R.layout.room_template,roomsList);

        roomsListView.setAdapter(roomAdapter);

    }
}