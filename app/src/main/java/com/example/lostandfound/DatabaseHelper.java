package com.example.lostandfound;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {


    public DatabaseHelper(Context context) {
        super(context, "LostFound1.db", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table PostType(KeyId INTEGER primary key,Type text, Name Text,Phone Text,Description text,Date text,Location text,mLat Text,mLng Text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop table if exists PostType");

    }

    public boolean createPost(Post post) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("Type", post.getType());
        contentValues.put("Name", post.getName());
        contentValues.put("Phone", post.getPhone());
        contentValues.put("Description", post.Description);
        contentValues.put("Date", post.getDate());
        contentValues.put("Location", post.getLocation());
        contentValues.put("mLat", post.getmLat());
        contentValues.put("mLng", post.getmLng());


        long Result = db.insert("PostType", null, contentValues);
        if (Result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public List<Post> getAllPosts() {
        List<Post> dataList = new ArrayList<>();

        // Select all Query
        String selectQuery = "SELECT * FROM " + "PostType";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Looping through all rows and adding to list
        if(cursor.moveToFirst()){
            do{
                Post dataModel = new Post();
                dataModel.setKey(Integer.parseInt(cursor.getString(0)));
                dataModel.setType(cursor.getString(1));
                dataModel.setName(cursor.getString(2));
                dataModel.setPhone(cursor.getString(3));
                dataModel.setDescription(cursor.getString(4));
                dataModel.setDate(cursor.getString(5));
                dataModel.setLocation(cursor.getString(6));
                dataModel.setmLat(cursor.getString(7));
                dataModel.setmLng(cursor.getString(8));

                // Adding Reminders to list
                dataList.add(dataModel);
            } while (cursor.moveToNext());
        }
        return dataList;
    }

    public boolean deletePost(int KeyId) {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from PostType where KeyId=?", new String[]{Integer.toString(KeyId)});
        if (cursor.getCount() > 0) {
            long Result = db.delete("PostType", "KeyId=?", new String[]{Integer.toString(KeyId)});
            if (Result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

}

