package com.example.thomas.ydusjx;

/**
 * Created by Thomas on 03/11/2018.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import static java.sql.DriverManager.println;

/*
Works as a database helper where the tables are created and methods such as insertModule, insertNote, deleteModule, deleteNote,
updateModule, updateNote, getAllRows and getModuleNotes are created
 */
public class MyDbHelper extends SQLiteOpenHelper {
    SQLiteDatabase db;

    public static final String DB_NAME = "GaelicDB1";
    public static final int DB_VERSION = 3;

    // Table name
    public static final String TABLE_PlAYER = "player_table";

    //Contents of Player table
    public static final String PID = "pid";
    public static final String PNAME = "pname";


    public MyDbHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
        SQLiteDatabase db = this.getWritableDatabase();
    }



    //Used to create the database
    public void onCreate(SQLiteDatabase db){
        String TaskTable = "CREATE TABLE IF NOT EXISTS " + TABLE_PlAYER + " (" + PID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + PNAME + " TEXT NOT NULL" + ");";
        db.execSQL(TaskTable);
        System.out.println("Table is created");

    }

    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer){
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_PlAYER);
        onCreate(db);
    }

    //Used to insert player into the database
    public void insertPlayer(String pname){
        System.out.println("Entered insertPlayer");
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + PNAME + " FROM " + TABLE_PlAYER + " WHERE " + PNAME + "= '" + pname + "'" ;
        Cursor cursor = db.rawQuery(query, null);
        cursor.getCount();//Used to count the results from the query
        System.out.println(cursor.getCount());

        //Used to check if the module has been already exists
        if (cursor.getCount() > 0)
        {
            System.out.println("Player already exists");
        }
        else {
            ContentValues initialValues = new ContentValues();
            initialValues.put(PNAME, pname);
            db.insert(TABLE_PlAYER, null, initialValues);
        }
    }

    //Used to update a player
    public void updatePlayer(String newPlayer, String original){
        System.out.println("Entered pdatePlayer");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues newVal = new ContentValues();
        db.execSQL("UPDATE " + TABLE_PlAYER + " SET " + PNAME + "= '" + newPlayer + "' " + " WHERE " + PNAME + "= '" + original + "'");

    }

    //Used to delete a player
    public void deletePlayer(String pname){
        System.out.println("Entered deletePlayer");
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_PlAYER + " WHERE " + PNAME + "= '" + pname + "'");
    }

    //Reference: The following code an Android example https://stackoverflow.com/questions/45870812/showing-data-in-listview-from-database-in-android-studio
    public Cursor getAllRows(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_PlAYER;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null)
        {
            cursor.moveToFirst();
        }
        return cursor;
        //Reference complete
    }
}
