package com.justclack.qrgeneratorandbarcodescanner;

public class Const {
    public static final String DATABASE_NAME = "barcodedb.sql";
    public static final String POST_TABLE_NAME = "savedTable";
    public static final String id = "id";
    public static final String title = "title";
    public static final String bar_code = "bar_code";
    public static final String date = "date";
    public static final String post_table_query = "CREATE TABLE IF NOT EXISTS " + POST_TABLE_NAME + "(" + id + " INTEGER PRIMARY KEY AUTOINCREMENT  ," + title + " VARCHAR, " + bar_code + " VARCHAR, " + date + " VARCHAR)";
}