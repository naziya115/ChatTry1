package com.example.chattry;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Adapters.RoomAdapter;
import Lists.RoomsLists;
import Models.Room;
import Models.SocketHandler;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class ChatsListActivity extends AppCompatActivity {

    ListView roomsListView;
    RoomAdapter roomAdapter;

    FloatingActionButton addRoomFAB;

    TextView lblNothingHere;

    private Socket mSocket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats_list);

        roomsListView=findViewById(R.id.roomsListView);

        addRoomFAB=findViewById(R.id.addRoomFAB);



        lblNothingHere = findViewById(R.id.lblNothingHere);

        //подключаемся к сокету и получаем его
        SocketHandler.establishConnection();
        mSocket = SocketHandler.getSocket();



        createListView(RoomsLists.getRooms());

        checkRoomsAndSetTextVisibility();

        //послыает на активити добавления комнаты
        addRoomFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreateRoomActivity.class);
                startActivity(intent);
            }
        });


        //вызываю потому что все двоится у того кому приходит эмит
        mSocket.off();

        //листенер для сокета, добавляющий комнаты
        mSocket.on("newRoom", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject data = (JSONObject) args[0];
                        try {
                            String groupName = data.getString("roomName");
                            Room room = new Room();
                            room.setRoomName(groupName);

                            RoomsLists.addRoom(room);
                            roomAdapter.notifyDataSetChanged();


                            checkRoomsAndSetTextVisibility();



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        });
    }



    public void createListView(ArrayList<Room> roomsList){
        roomAdapter = new RoomAdapter(getApplicationContext(),R.layout.room_template,roomsList);

        roomsListView.setAdapter(roomAdapter);

    }

    public void checkRoomsAndSetTextVisibility(){
        if(RoomsLists.getRooms().isEmpty()){
            Typeface custom_font = Typeface.createFromAsset(getAssets(), "assets/font7.otf");
            lblNothingHere.setTypeface(custom_font);
            lblNothingHere.setVisibility(View.VISIBLE);
            return;
        }
        lblNothingHere.setVisibility(View.GONE);
    }



}