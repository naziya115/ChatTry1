package com.example.chattry;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

        RoomAdapter roomAdapter = new RoomAdapter(getApplicationContext(),R.layout.room_template,roomsList);

        roomsListView.setAdapter(roomAdapter);



    }

    public void initRoomsForTry(){
        for(int i=0;i<=10;i++){
            Room room = new Room();
            room.setRoomName("Room For Try");
            roomsList.add(room);
        }
    }
}