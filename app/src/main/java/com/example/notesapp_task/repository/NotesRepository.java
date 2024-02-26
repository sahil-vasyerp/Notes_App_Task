package com.example.notesapp_task.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.notesapp_task.Interface.ExpenseDao;
import com.example.notesapp_task.ModelClass.ExpenseModel;
import com.example.notesapp_task.roomDB.NotesDB;

import java.util.List;

public class NotesRepository {

    public ExpenseDao expenseDao;
    private LiveData<List<ExpenseModel>> allNotes;

    public NotesRepository(Application application) {
        NotesDB db = NotesDB.getDatabase(application);
        expenseDao = db.expenseDao();
        allNotes = expenseDao.getAllData();

    }

    public void insert(ExpenseModel expenseModel) {
        new insertAsyncTask(expenseDao).execute(expenseModel);
    }

    public void update(ExpenseModel expenseModel) {
        new updateAsyncTask(expenseDao).execute(expenseModel);
    }

    public void delete(ExpenseModel expenseModel) {
        new deleteAsyncTask(expenseDao).execute(expenseModel);
    }

    public LiveData<List<ExpenseModel>> getAllNotes() {
        return allNotes;
    }

    private class insertAsyncTask extends AsyncTask<ExpenseModel, Void, Void> {

        public insertAsyncTask(ExpenseDao expenseDao) {

        }

        @Override
        protected Void doInBackground(ExpenseModel... expenseModels) {

            expenseDao.insert(expenseModels[0]);

            return null;
        }
    }


    private class updateAsyncTask extends AsyncTask<ExpenseModel, Void, Void> {

        public updateAsyncTask(ExpenseDao expenseDao) {

        }

        @Override
        protected Void doInBackground(ExpenseModel... expenseModels) {

            expenseDao.update(expenseModels[0]);
            return null;
        }
    }

    private class deleteAsyncTask extends AsyncTask<ExpenseModel, Void, Void> {

        public deleteAsyncTask(ExpenseDao expenseDao) {

        }

        @Override
        protected Void doInBackground(ExpenseModel... expenseModels) {

            expenseDao.delete(expenseModels[0]);
            return null;
        }
    }
}
