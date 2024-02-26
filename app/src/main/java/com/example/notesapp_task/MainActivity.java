package com.example.notesapp_task;

import static android.content.DialogInterface.BUTTON_NEGATIVE;
import static android.content.DialogInterface.BUTTON_POSITIVE;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.notesapp_task.Adapter.RecyclerViewAdapter;
import com.example.notesapp_task.ModelClass.ExpenseModel;
import com.example.notesapp_task.viewmodel.NotesViewModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, RecyclerViewAdapter.OnClickListener {

    ExtendedFloatingActionButton btnAddNotes;
    RecyclerView recyclerView;
    RecyclerViewAdapter adapter;
    int position;
    int positionUpdate;
    MaterialToolbar toolbar;

    List<ExpenseModel> arrayList = new ArrayList<>();
    public static final String NEXT_SCREEN = "details_screen";
    private boolean ascendingClickedTitle = false;
    private boolean descendingClickedTitle = false;
    private boolean ascendingClickedDate = false;
    private boolean descendingClickedDate = false;
    DialogInterface.OnClickListener dialogClickListener;
    private NotesViewModel notesViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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
        notesViewModel = ViewModelProviders.of(this).get(NotesViewModel.class);


//  ----------------------------toolbar-------------------------------------------------------------

        setSupportActionBar(toolbar);

//-------------------------RecyclerView Adapter-----------------------------------------------------

        adapter = new RecyclerViewAdapter(arrayList, "1", this, MainActivity.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


//-------------------------Drag & Drop--------------------------------------------------------------
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

//  ----------------------ClickListener-------------------------------------------------------------

        btnAddNotes.setOnClickListener(this);


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

            if (requestCode == RESULT_FIRST_USER) {

                readArrayListDB();


            } else {

                readArrayListDB();

            }
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    private void readArrayListDB() {

        notesViewModel.getAllNotes().observe(this, new Observer<List<ExpenseModel>>() {
            @Override
            public void onChanged(List<ExpenseModel> expenseModels) {
                adapter.setNotes(expenseModels);
                arrayList = expenseModels;

                if (ascendingClickedTitle) {
                    ascendingOrder();
                } else if (descendingClickedTitle) {
                    descendingOrder();
                } else if (ascendingClickedDate) {
                    ascendingOrderByDate();
                } else if (descendingClickedDate) {
                    descendingOrderByDate();
                }

            }
        });
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
                ascendingClickedDate = false;
                descendingClickedDate = false;
                ascendingOrder();

                return true;
            }
            case R.id.descendingList: {

                ascendingClickedTitle = false;
                descendingClickedTitle = true;
                ascendingClickedDate = false;
                descendingClickedDate = false;
                descendingOrder();

                return true;
            }
            case R.id.ascendingDate: {
                ascendingClickedTitle = false;
                descendingClickedTitle = false;
                ascendingClickedDate = true;
                descendingClickedDate = false;
                ascendingOrderByDate();

                return true;
            }
            case R.id.descendingDate: {
                ascendingClickedTitle = false;
                descendingClickedTitle = false;
                ascendingClickedDate = false;
                descendingClickedDate = true;
                descendingOrderByDate();
                return true;
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


    }

    @Override
    public void onDelete(int position) {


        dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                switch (which) {
                    case BUTTON_POSITIVE: {


//  ---------------------------room db delete values------------------------------------------------
                        notesViewModel.delete(arrayList.get(position));
//  ---------------------------room db delete values------------------------------------------------

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