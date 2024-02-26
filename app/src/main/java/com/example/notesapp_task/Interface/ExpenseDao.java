package com.example.notesapp_task.Interface;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.notesapp_task.ModelClass.ExpenseModel;

import java.util.List;

@Dao
public interface ExpenseDao {


    @Insert
    void insert(ExpenseModel expenseModel);

    @Update
    void update(ExpenseModel expenseModel);

    @Delete
    void delete(ExpenseModel expenseModel);

    @Query("SELECT * FROM myNotes")
    LiveData<List<ExpenseModel>> getAllData();
}
