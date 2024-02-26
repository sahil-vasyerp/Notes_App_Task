package com.example.notesapp_task.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.notesapp_task.ModelClass.ExpenseModel;
import com.example.notesapp_task.repository.NotesRepository;

import java.util.List;

public class NotesViewModel  extends AndroidViewModel {


    private NotesRepository notesRepository;
    private LiveData<List<ExpenseModel>> allNotes;
    public NotesViewModel(@NonNull Application application) {
        super(application);

        notesRepository=new NotesRepository(application);
        allNotes=notesRepository.getAllNotes();
    }

    public LiveData<List<ExpenseModel>> getAllNotes() {
        return allNotes;
    }

    public void insert(ExpenseModel expenseModel)
    {
        notesRepository.insert(expenseModel);
    }

    public void update(ExpenseModel expenseModel)
    {
        notesRepository.update(expenseModel);
    }
    public void delete(ExpenseModel expenseModel)
    {
        notesRepository.delete(expenseModel);
    }
}
