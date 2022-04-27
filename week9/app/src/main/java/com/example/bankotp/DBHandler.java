package com.example.bankotp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHandler extends SQLiteOpenHelper {
    String tableName = "Users";

    public DBHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE Users(name VARCHAR, password VARCHAR, otp CHAR(4), verified INTEGER, PRIMARY KEY (name))";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+tableName);
        onCreate(db);
    }

    public void addNewUser(String name, String password, String otp){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("password", password);
        contentValues.put("otp", otp);
        contentValues.put("verified", 0);
        db.insert(tableName, null, contentValues);
        db.close();
    }

    public boolean checkUserExists(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + tableName + " WHERE name = " + "'" + name + "'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }
        return true;
    }

    public boolean checkUserVerified(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(tableName, new String[]{"verified"}, "name=?", new String[]{name}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();
        boolean res = cursor.getInt(0) == 1;
        cursor.close();
        db.close();
        return res;
    }

    public boolean tryVerifyUser(String name, String otp) {
        SQLiteDatabase read_db = this.getReadableDatabase();

        Cursor cursor = read_db.query(tableName, new String[]{"otp"}, "name=?", new String[]{name}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();
        String actual_otp = cursor.getString(0);

        cursor.close();
        read_db.close();
        boolean res;

        if (actual_otp.equals(otp)) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put("verified", 1);

            db.update(tableName, cv, "name=?", new String[]{name});
            res = true;
            db.close();
        } else {
            res = false;
        }
        return res;
    }
}
