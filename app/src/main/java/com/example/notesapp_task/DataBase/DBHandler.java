package com.example.notesapp_task.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHandler extends SQLiteOpenHelper {

    private static final String dbName = "notesDB";

    public DBHandler(@Nullable Context context) {
        super(context, dbName, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE myNotes(id INTEGER PRIMARY KEY AUTOINCREMENT,titleNotes TEXT,descriptionNotes TEXT,color INTEGER,imageUri TEXT,createDate TEXT)";
        db.execSQL(query);
    }

    public void addNewNotes(String titleNotes, String descriptionNotes, int color, String imageUri, String createDate)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();

        values.put("titleNotes",titleNotes);
        values.put("descriptionNotes",descriptionNotes);
        values.put("color",color);
        values.put("imageUri",imageUri);
        values.put("createDate",createDate);

        db.insert("myNotes",null,values);

    }
    public void updateNotes(String id,String titleNotes, String descriptionNotes, int color, String imageUri, String createDate)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();

        values.put("titleNotes",titleNotes);
        values.put("descriptionNotes",descriptionNotes);
        values.put("color",color);
        values.put("imageUri",imageUri);
        values.put("createDate",createDate);

        db.update("myNotes",values,"id=?",new String[]{id});

    }
    public void  deleteNotes(String id)
    {
        SQLiteDatabase db=this.getWritableDatabase();

        db.delete("myNotes","id=?",new String[]{id});


    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
