package com.project.intership.cico.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by giang on 1/6/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "xxxxxx";

    private static final String TABLE_USER = "user";
    private static final String CREATE_TABLE_USER = "CREATE TABLE "+TABLE_USER+"("
            + ")";
/*
    =================>
    */
    public DatabaseHelper (Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Xóa table cũ
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        //Khởi tạo lại database
        onCreate(db);
    }
}
