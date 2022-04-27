package com.example.restaurant;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {
    String tableName = "Items";
    String col = "name";

    public DBHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE Items(name VARCHAR, PRIMARY KEY (name))";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+tableName);
        onCreate(db);
    }

    void addEntry(String item){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(col, item);
        db.insert(tableName, null, contentValues);
        db.close();
    }

    void removeEntry(String item){
        SQLiteDatabase db = this.getWritableDatabase();
        String removeEntry = "DELETE FROM "+tableName+" WHERE "+col+"="+"'"+item+"'";
        db.execSQL(removeEntry);
        db.close();
    }

    List<String> getItems() {
        List<String> items = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT name from Items", null);

        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            items.add(cursor.getString(0));
            cursor.moveToNext();
        }

        return items;
    }
}
