package com.example.chattry;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.Toast;

public class MyDatabaseMessageHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "MessagesDB.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "messages";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TYPE = "type";
    private static final String COLUMN_ROOMNAME = "roomName";
    private static final String COLUMN_SENDERNAME = "senderName";
    private static final String COLUMN_CONTENT = "content";
    private static final String COLUMN_TIME = "timeStr";

    MyDatabaseMessageHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TYPE + " INTEGER, " +
                COLUMN_ROOMNAME + " TEXT, " +
                COLUMN_SENDERNAME + " TEXT, " +
                COLUMN_CONTENT + " TEXT, " +
                COLUMN_TIME + " TEXT);";
        db.execSQL(query);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    void AddMessage(int type, String roomName, String senderName, String content, String timeStr){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TYPE, type);
        cv.put(COLUMN_ROOMNAME, roomName);
        cv.put(COLUMN_SENDERNAME, senderName);
        cv.put(COLUMN_CONTENT, content);
        cv.put(COLUMN_TIME, timeStr);

        long result = db.insert(TABLE_NAME,null, cv);
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
        }
    }

    Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
}