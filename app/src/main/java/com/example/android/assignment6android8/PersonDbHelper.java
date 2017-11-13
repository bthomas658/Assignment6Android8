package com.example.android.assignment6android8;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by THOMAS
 *
 * This is used to implement and use the SQLlite database on Android
 *
 * Contains database definition information
 *
 */
public class PersonDbHelper extends SQLiteOpenHelper{

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "person.db";
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_PERSON = "CREATE TABLE " + PersonContract.PersonEntry.TABLE_NAME + " (" +
            PersonContract.PersonEntry._ID + " INTEGER PRIMARY KEY" + COMMA_SEP +
            PersonContract.PersonEntry.COLUMN_NAME_FIRST + TEXT_TYPE + COMMA_SEP +
            PersonContract.PersonEntry.COLUMN_NAME_LAST + TEXT_TYPE + COMMA_SEP +
            PersonContract.PersonEntry.COLUMN_PHONE + TEXT_TYPE + COMMA_SEP +
            PersonContract.PersonEntry.COLUMN_EMAIL + TEXT_TYPE +  " )";

    private static final String SQL_DELETE =
            "DROP TABLE IF EXISTS" + PersonContract.PersonEntry.TABLE_NAME;

    public PersonDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //need constructor with just context...
    public PersonDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_PERSON);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE);
        onCreate(db);
    }
}
