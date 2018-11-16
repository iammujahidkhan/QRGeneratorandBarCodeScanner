package com.justclack.qrgeneratorandbarcodescanner;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

/**
 * Created by Mujahid on 2/23/2018.
 */

public class SQLiteHelper extends SQLiteOpenHelper {
    public SQLiteHelper(Context context) {
        super(context, Const.DATABASE_NAME, null, 1);
    }

    public void createTable(String sql) {
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    public void insertData(String title, String bar_code, String date) {
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO " + Const.POST_TABLE_NAME + " VALUES(NULL, ?,?,?)";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, title);
        statement.bindString(2, bar_code);
        statement.bindString(3, date);
        statement.executeInsert();
    }

    public void updateData(String id, String title, String bar_code, String date) {
        SQLiteDatabase database = getWritableDatabase();

        String sql = "UPDATE " + Const.POST_TABLE_NAME + " SET " + Const.date + "= ? , " + Const.title + " = ? , " + Const.bar_code + " = ? , " + Const.date + " = ? " + "  WHERE " + Const.id + "= ?";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.bindString(1, title);
        statement.bindString(2, bar_code);
        statement.bindString(3, date);
        statement.bindString(4, id);
        statement.execute();
        database.close();
    }


    public void deleteData(String id) {
        SQLiteDatabase database = getWritableDatabase();

        String sql = "DELETE FROM  " + Const.POST_TABLE_NAME + "  WHERE " + Const.id + " = ?";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, id);
        statement.execute();
        database.close();

    }

    public void deleteAllData() {
        SQLiteDatabase database = getWritableDatabase();

        String sql = "DELETE FROM " + Const.POST_TABLE_NAME + "";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.execute();
        database.close();

    }

    public Cursor getData(String sql) {

        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}