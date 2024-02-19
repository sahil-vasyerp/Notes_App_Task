package com.example.notesapp_task;

import static android.content.DialogInterface.BUTTON_NEGATIVE;
import static android.content.DialogInterface.BUTTON_POSITIVE;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.notesapp_task.Adapter.RecyclerViewAdapter;
import com.example.notesapp_task.DataBase.DBHandler;
import com.example.notesapp_task.ModelClass.ExpenseModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, RecyclerViewAdapter.OnClickListener {

    ExtendedFloatingActionButton btnAddNotes;
    RecyclerView recyclerView;
    RecyclerViewAdapter adapter;
    int position;
    int positionUpdate;
    MaterialToolbar toolbar;
    ArrayList<ExpenseModel> arrayList = new ArrayList<>();
    ExpenseModel expenseModel;
    public static final String NEXT_SCREEN = "details_screen";
    private boolean ascendingClickedTitle = false;
    private boolean descendingClickedTitle = false;
    private boolean ascendingClickedDate = false;
    private boolean descendingClickedDate = false;
    DialogInterface.OnClickListener dialogClickListener;
    DrawerLayout drawerLayout;
    DBHandler dbHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);


        initViews();


        readArrayListDB();

    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.START | ItemTouchHelper.END, 0) {

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {

            int fromPosition = viewHolder.getAdapterPosition();
            int toPosition = target.getAdapterPosition();
            Collections.swap(arrayList, fromPosition, toPosition);
            recyclerView.getAdapter().notifyItemMoved(fromPosition, toPosition);
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

        }
    };


    private void initViews() {


        btnAddNotes = findViewById(R.id.btnAddNotes);
        recyclerView = findViewById(R.id.rvNotes);
        toolbar = findViewById(R.id.toolBar);


//  ----------------------------Database-------------------------------------------------------------
        dbHandler = new DBHandler(MainActivity.this);
//  ----------------------------toolbar-------------------------------------------------------------

        setSupportActionBar(toolbar);

//-------------------------RecyclerView Adapter-----------------------------------------------------

        adapter = new RecyclerViewAdapter(arrayList, "1", this, MainActivity.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

//  ----------------------ClickListener-------------------------------------------------------------

        btnAddNotes.setOnClickListener(this);
        toolbar.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnAddNotes: {
                Intent intent = new Intent(MainActivity.this, Add_NotesActivity.class);
                startActivityForResult(intent, 1);
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == RESULT_OK) {

            int positionUpdate = data.getIntExtra("position", -1);
            String addTitle = data.getStringExtra("title");
            String addDescription = data.getStringExtra("description");
            String currentDate = data.getStringExtra("date");
            String imageUri = data.getStringExtra("imageUri");
            int color = data.getIntExtra("color", 0);


            if (requestCode == RESULT_FIRST_USER) {


                readArrayListDB();


//                arrayList.add(new ExpenseModel(addTitle, addDescription, color, currentDate, imageUri));
//                adapter.notifyDataSetChanged();

                if (ascendingClickedTitle) {
                    ascendingOrder();
                } else if (descendingClickedTitle) {

                    descendingOrder();
                } else if (ascendingClickedDate) {
                    ascendingOrderByDate();
                } else if (descendingClickedDate) {
                    descendingOrderByDate();

                }


            } else {
                updateArrayListDB();

//                arrayList.set(positionUpdate, new ExpenseModel( addTitle, addDescription, color, currentDate, imageUri));
//                adapter.notifyItemChanged(position);

            }
        }
    }

    private void updateArrayListDB() {
//        int positionUpdate = getIntent().getIntExtra("position", -1);

        SQLiteDatabase db = dbHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM myNotes", null);
//        Log.d("watch1", "updateArrayListDB: " + positionUpdate);
//        Log.d("watch2", "updateArrayListDB: " + position);

        if (cursor.moveToPosition(position)) {

            expenseModel = new ExpenseModel(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getString(4), cursor.getString(5));
            arrayList.set(position, expenseModel);
            adapter.notifyItemChanged(position);

        }

        cursor.close();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void readArrayListDB() {
        arrayList.clear();
        SQLiteDatabase db = dbHandler.getReadableDatabase();


        Cursor cursor = db.rawQuery("SELECT * FROM myNotes", null);


        while (cursor.moveToNext()) {
            expenseModel = new ExpenseModel(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getString(4), cursor.getString(5));
            arrayList.add(expenseModel);
            adapter.notifyDataSetChanged();

        }
        cursor.close();
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_option, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        switch (id) {

            case R.id.listViewLayout: {
                adapter.changeType("1");
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                recyclerView.setAdapter(adapter);
                return true;

            }
            case R.id.gridViewLayout: {
                adapter.changeType("2");
                recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
                recyclerView.setAdapter(adapter);
                return true;

            }
            case R.id.staggeredLayout: {
                adapter.changeType("3");
                recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                recyclerView.setAdapter(adapter);
                return true;

            }
            case R.id.ascendingList: {

                ascendingClickedTitle = true;
                descendingClickedTitle = false;
                ascendingOrder();

                return true;
            }
            case R.id.descendingList: {

                ascendingClickedTitle = false;
                descendingClickedTitle = true;
                descendingOrder();

                return true;
            }
            case R.id.ascendingDate: {
                ascendingClickedDate = true;
                descendingClickedDate = false;
                ascendingOrderByDate();

                return true;
            }
            case R.id.descendingDate: {
                ascendingClickedDate = false;
                descendingClickedDate = true;
                descendingOrderByDate();
            }
        }

        return (super.onOptionsItemSelected(item));
    }

    @SuppressLint("NotifyDataSetChanged")
    private void descendingOrderByDate() {
        Collections.sort(arrayList, new Comparator<ExpenseModel>() {
            @Override
            public int compare(ExpenseModel o1, ExpenseModel o2) {
                return o2.getCreateDate().compareTo(o1.getCreateDate());
            }
        });
        adapter.notifyDataSetChanged();

    }

    @SuppressLint("NotifyDataSetChanged")
    private void ascendingOrderByDate() {
        Collections.sort(arrayList, new Comparator<ExpenseModel>() {
            @Override
            public int compare(ExpenseModel o1, ExpenseModel o2) {

                return o1.getCreateDate().compareTo(o2.getCreateDate());
            }
        });
        adapter.notifyDataSetChanged();

    }

    private void ascendingOrder() {
        Collections.sort(arrayList, new Comparator<ExpenseModel>() {
            @Override
            public int compare(ExpenseModel o1, ExpenseModel o2) {

                return o1.getTitleNotes().compareTo(o2.getTitleNotes());
            }
        });
        adapter.notifyDataSetChanged();


    }

    @SuppressLint("NotifyDataSetChanged")
    private void descendingOrder() {
        Collections.sort(arrayList, new Comparator<ExpenseModel>() {
            @Override
            public int compare(ExpenseModel o1, ExpenseModel o2) {
                return o2.getTitleNotes().compareTo(o1.getTitleNotes());
            }
        });
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onClick(int position, ExpenseModel model) {
        Intent intent = new Intent(MainActivity.this, Add_NotesActivity.class);
        intent.putExtra(NEXT_SCREEN, model);

        intent.putExtra("position", model.getId());
        startActivityForResult(intent, 2);

        this.position = position;
        this.positionUpdate= model.getId();

    }

    @Override
    public void onDelete(int position) {

//        Log.d("delete", "onDelete: "+position);

        dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                switch (which) {
                    case BUTTON_POSITIVE:
                    {

                        String positionDelete= String.valueOf(arrayList.get(position).getId());
                        dbHandler.deleteNotes(positionDelete);
                        arrayList.remove(position);
                        adapter.notifyItemRemoved(position);
                        break;
                    }

                    case BUTTON_NEGATIVE: {
                        dialog.dismiss();
                        break;
                    }
                    default:
                        throw new IllegalStateException("Unexpected value: " + which);

                }


            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure ?").setPositiveButton("Delete", dialogClickListener).setNegativeButton("Cancel", dialogClickListener).show();


    }
}