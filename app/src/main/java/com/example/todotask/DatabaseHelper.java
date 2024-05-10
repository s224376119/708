
package com.example.todotask;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Curd2";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "taskCurd";
    Context context;
    List<TaskModel> list;

    public DatabaseHelper(@Nullable Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        list = new ArrayList<>();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_QUERY = "CREATE TABLE " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                " TaskName VARCHAR, Dic VARCHAR, TimeStamp long)";
        db.execSQL(CREATE_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }

    public boolean addPost(TaskModel task) {

        ContentValues values = new ContentValues();

            values.put("TaskName", task.getName());
            values.put("Dic", task.getDic());
            values.put("TimeStamp", task.getCurrentTimeStamp());

            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            Long l = sqLiteDatabase.insert(TABLE_NAME, "", values);

            if (l > 0) {
                return true;
            } else {
                return false;
            }


    }

    public List<TaskModel> getAllPosts() {
        List<TaskModel> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String RETRIEVE_RECORD_QUERY = "SELECT * FROM " + TABLE_NAME + " ORDER BY TimeStamp DESC";
        Cursor cursor = sqLiteDatabase.rawQuery(RETRIEVE_RECORD_QUERY, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                TaskModel myPost = new TaskModel();
                myPost.setTaskKey(Integer.parseInt(cursor.getString(0)));
                myPost.setName(cursor.getString(1));
                myPost.setDic(cursor.getString(2));
                myPost.setCurrentTimeStamp(cursor.getLong(3));
                list.add(myPost);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }

        return list;
    }

    public boolean updatePost(TaskModel myPost) {
        ContentValues values = new ContentValues();
        values.put("TaskName", myPost.getName());
        values.put("Dic", myPost.getDic());
        values.put("TimeStamp", myPost.getCurrentTimeStamp());
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        int result = sqLiteDatabase.update(TABLE_NAME, values, "ID=?", new String[]{myPost.getTaskKey() + ""});

        if (result > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean deletePost(String item_ID) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        int result = sqLiteDatabase.delete(TABLE_NAME, "ID=?", new String[]{item_ID});


        if (result > 0) {
            return true;
        } else {
            return false;
        }
    }

}
