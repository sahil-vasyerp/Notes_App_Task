package com.example.notesapp_task.roomDB;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.notesapp_task.Interface.ExpenseDao;
import com.example.notesapp_task.ModelClass.ExpenseModel;

@Database(entities = {ExpenseModel.class}, version = 1)
public abstract class NotesDB extends RoomDatabase {
    private static NotesDB INSTANCE;

    public static NotesDB getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (NotesDB.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context, NotesDB.class, "notes_db")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }

        }
        return INSTANCE;
    }

private static  RoomDatabase.Callback sRoomDatabaseCallback=new RoomDatabase.Callback()
{
    @Override
    public void onOpen(@NonNull SupportSQLiteDatabase db) {
        super.onOpen(db);
    }
};

    public abstract ExpenseDao expenseDao();
}
