package com.example.chattry;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Adapters.MessageAdapter;
import Models.Message;
import Models.SocketHandler;
import Models.User;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class ChatActivity extends AppCompatActivity {

    ImageButton backBtn,sendBtn;
    EditText txtMessage;
    TextView lblOfRoom;


    Socket mSocket;

    RecyclerView recyclerOfMessages;
    ArrayList<Message> messagesList;

    MessageAdapter messageAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        mSocket = SocketHandler.getSocket();

        backBtn = findViewById(R.id.backBtn);
        sendBtn = findViewById(R.id.sendBtn);
        txtMessage =  findViewById(R.id.txt_message);
        lblOfRoom =  findViewById(R.id.lblOfRoom);



        recyclerOfMessages = findViewById(R.id.recycler_of_messages);
        messagesList = new ArrayList<>();




        setLblOfRoom();


        setRecyclerOfMessages();

        //отправляет message в чат о юзере, который дисконнектнулся, далее посылает в ChatsListActivity
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User();
                //создаем сообщение дисконнекта
                String userName = user.getNickname();
                String disconnectionContent = userName + " has disconnected!";
                Message connectionMessage = new Message(2, userName, disconnectionContent);

                messagesList.add(connectionMessage);

                mSocket.emit("leaveRoomDetection", lblOfRoom.getText().toString(), userName);

                //эта штука позволяет появляться сообщениям снизу
                //учитывая, что сам recycler растянут match_parent
                recyclerOfMessages.scrollToPosition(messagesList.size() - 1);

                messageAdapter.notifyDataSetChanged();
                Intent intent = new Intent(getApplicationContext(), ChatsListActivity.class);
                startActivity(intent);
            }
        });
                mSocket.on("newUserInRoom", new Emitter.Listener() {
                    @Override
                    public void call(Object... args) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                JSONObject data = (JSONObject) args[0];
                                try {
                                    //создаем сообщение коннекта
                                    String userName = data.getString("userName");
                                    String connectionContent = userName + " has connected!";
                                    Message connectionMessage = new Message(2, userName, connectionContent);

                                    messagesList.add(connectionMessage);

                                    //эта штука позволяет появляеться сообщениям снизу
                                    //учитывая, что сам recycler растянут match_parent
                                    recyclerOfMessages.scrollToPosition(messagesList.size() - 1);

                                    messageAdapter.notifyDataSetChanged();


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        });
                    }
                });

    }




    public void setLblOfRoom(){
        Intent intent = getIntent();
        String roomName = intent.getStringExtra("roomName");
        lblOfRoom.setText(roomName);
    }




    public void setRecyclerOfMessages(){
        messageAdapter = new MessageAdapter(getApplicationContext(), messagesList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerOfMessages.setLayoutManager(linearLayoutManager);
        recyclerOfMessages.setAdapter(messageAdapter);
    }
}