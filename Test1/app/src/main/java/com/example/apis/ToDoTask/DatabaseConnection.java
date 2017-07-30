package com.example.apis.ToDoTask;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by apis on 7/27/2017.
 */

public class DatabaseConnection extends SQLiteOpenHelper
{

    private static final String database_name = "new.db";
    private static final int database_version = 1;

    public DatabaseConnection(Context context)
    {
        super(context, database_name, null, database_version);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String sql = "create table task(id integer primary key, name text null, task_name text null, task_date text null, task_dateCreated text null, task_dateUpdated text null);";
        Log.d("Data","onCreate: "+sql);
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
