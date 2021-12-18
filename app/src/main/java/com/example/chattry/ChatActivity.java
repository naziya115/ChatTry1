package com.example.chattry;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import Adapters.MessageAdapter;
import Lists.RoomsLists;
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

    MyDatabaseMessageHelper myDB;
    String roomName;


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

        //после этого надо только заполнить лист, больше ничего не надо
        messagesList = new ArrayList<>();
        myDB = new MyDatabaseMessageHelper(ChatActivity.this);
        // очищаем лист, чтобы не дублировалось
        messagesList.clear();
        //заполняем лист
        storeDataInArrays();

        setLblOfRoom();

        setRecyclerOfMessages();

        //отправляет message в чат о юзере, который дисконнектнулся, далее посылает в ChatsListActivity
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = User.getNickname();
                mSocket.emit("leaveRoomDetection", lblOfRoom.getText().toString(), userName);

                Intent intent = new Intent(getApplicationContext(), ChatsListActivity.class);
                startActivity(intent);
            }
        });

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                String messageStr = txtMessage.getText().toString();
                //валидация строки сообщения
                if(messageStr.isEmpty() ||
                        messageStr.replaceAll(" ","") == "" || messageStr.equals(null)){
                    return;
                }

                //создали свое сообщение и добавили его в лист для себя
                String userName = User.getNickname();

                LocalTime time = LocalTime.now();
                String timeStr = time.format(DateTimeFormatter.ofPattern("HH:mm"));

                setLblOfRoom();
                Message myMessage = new Message(3,roomName,userName,messageStr,timeStr);
                //adding message to database
                MyDatabaseMessageHelper myDB = new MyDatabaseMessageHelper(ChatActivity.this);
                myDB.AddMessage(myMessage.getType(),lblOfRoom.getText().toString(),myMessage.getSenderName(), myMessage.getContent(), myMessage.getTimeStr());


                messagesList.add(myMessage);

                //эта штука позволяет появляеться сообщениям снизу
                //учитывая, что сам recycler растянут match_parent
                recyclerOfMessages.scrollToPosition(messagesList.size() - 1);

                messageAdapter.notifyDataSetChanged();

                //очищаем поле ввода
                txtMessage.setText("");

                //отправляем сообщение на сокет

                String roomName = lblOfRoom.getText().toString();
                mSocket.emit("messageDetection",roomName , userName,messageStr,timeStr);



            }
        });
        mSocket.on("outerMessageReceived", new Emitter.Listener() {
            @Override
            public void call(Object... args) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject data = (JSONObject) args[0];
                        try {
                            //создаем сообщение дисконнекта
                            String senderName = data.getString("senderName");
                            String messageStr = data.getString("messageStr");
                            String time = data.getString("time");


                            setLblOfRoom();
                            Message outerMessage = new Message(4,roomName,senderName,messageStr,time);


                            //adding message to database
                            MyDatabaseMessageHelper myDB = new MyDatabaseMessageHelper(ChatActivity.this);
                            myDB.AddMessage(outerMessage.getType(),roomName, outerMessage.getSenderName(), outerMessage.getContent(), outerMessage.getTimeStr());


                            messagesList.add(outerMessage);

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
        mSocket.on("newUserOutOfRoom", new Emitter.Listener() {
            @Override
            public void call(Object... args) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject data = (JSONObject) args[0];
                        try {
                            //создаем сообщение дисконнекта
                            String userName = data.getString("userName");
                            String disconnectionContent = userName + " has disconnected!";
                            setLblOfRoom();
                            Message disconnectionMessage = new Message(1, roomName,userName, disconnectionContent);


                            //adding message to database
                            MyDatabaseMessageHelper myDB = new MyDatabaseMessageHelper(ChatActivity.this);
                            myDB.AddMessage(disconnectionMessage.getType(),roomName,disconnectionMessage.getSenderName(), disconnectionMessage.getContent(), disconnectionMessage.getTimeStr());


                            messagesList.add(disconnectionMessage);

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
                            setLblOfRoom();
                            Message connectionMessage = new Message(2, roomName, userName, connectionContent);


                            //adding message to database
                            setLblOfRoom();
                            MyDatabaseMessageHelper myDB = new MyDatabaseMessageHelper(ChatActivity.this);
                            myDB.AddMessage(connectionMessage.getType(),roomName,connectionMessage.getSenderName(), connectionMessage.getContent(), connectionMessage.getTimeStr());


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
        roomName = intent.getStringExtra("roomName");
        lblOfRoom.setText(roomName);
    }




    public void setRecyclerOfMessages(){
        messageAdapter = new MessageAdapter(getApplicationContext(), messagesList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerOfMessages.setLayoutManager(linearLayoutManager);
        recyclerOfMessages.setAdapter(messageAdapter);
    }



    void storeDataInArrays(){
        Cursor cursor = myDB.readAllData();
        if(cursor.getCount() == 0){
        }else{
            while (cursor.moveToNext()){
                setLblOfRoom();
                if(cursor.getString(2).equals(roomName)) {
                    Message message = new Message(Integer.parseInt(cursor.getString(1)),
                            cursor.getString(2), cursor.getString(3),
                            cursor.getString(4),cursor.getString(5));
                    messagesList.add(message);
               }
            }
        }
    }
}