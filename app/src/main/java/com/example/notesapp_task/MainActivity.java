package com.example.notesapp_task;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.notesapp_task.Adapter.RecyclerViewAdapter;
import com.example.notesapp_task.ModelClass.ExpenseModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.search.SearchBar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, RecyclerViewAdapter.OnClickListener {

    ExtendedFloatingActionButton btnAddNotes;
    RecyclerView recyclerView;
    RecyclerViewAdapter adapter;
    SearchBar searchBar;
    int position;
    MaterialToolbar toolbar;
    ArrayList<ExpenseModel> arrayList = new ArrayList<>();
    ExpenseModel expenseModel;
    public static final String NEXT_SCREEN = "details_screen";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initViews();

    }

    private void initViews() {


        btnAddNotes = findViewById(R.id.btnAddNotes);
        recyclerView = findViewById(R.id.rvNotes);
        toolbar = findViewById(R.id.toolBar);



//  ----------------------------toolbar-------------------------------------------------------------

        setSupportActionBar(toolbar);

//-------------------------RecyclerView Adapter-----------------------------------------------------

        adapter = new RecyclerViewAdapter(arrayList, "1", this,MainActivity.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);



//  ----------------------ClickListener-------------------------------------------------------------

        btnAddNotes.setOnClickListener(this);
        toolbar.setOnClickListener(this);


//        addArrayList();
    }

    private void addArrayList() {

//        arrayList.add(new ExpenseModel("sahil", "Hello, I am Sahil Mahyavanshi, pursuing computer engineering(4th year) at aditya silver oak institute of technology from ahmedabad.\n" +
//                "▫️ I am always ready to have new experiences, meet new people and learn new things.\n" +
//                "▫️ I enjoy solving technical problems, researching and developing new technologies. I enjoy to meeting people and working with them in a team environment. I also enjoy interacting with clients and customers.\n" +
//                "▫️ I am a quick learner with a fun, outgoing personality.\n" +
//                "▫️ In addition, I excel in my ability to work under pressure and handle stressful situations very well.These skills that I have will be a benefit for any IT company."));
//        arrayList.add(new ExpenseModel("shubham", "hello my name is suhbham"));
//        arrayList.add(new ExpenseModel("abhishek", "hello my name is abhishek.\n I am a Software Developer."));
//        arrayList.add(new ExpenseModel("devbhai", "hello my name is dev.\n I am a android Developer."));
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
            int color=data.getIntExtra("color",234341);
            Log.d("TAG", "onActivityResult: "+color);

            if (requestCode == RESULT_FIRST_USER)
            {

                if (addDescription.isEmpty())
                {

                }
                else
                {
                    arrayList.add(0,new ExpenseModel(addTitle, addDescription,color));
                    adapter.notifyDataSetChanged();
                }


            } else {
                arrayList.set(positionUpdate, new ExpenseModel(addTitle, addDescription,color));
                adapter.notifyItemChanged(position);

            }
        }
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_option, menu);
        return true;
    }

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
        }

        return (super.onOptionsItemSelected(item));
    }

    @Override
    public void onClick(int position, ExpenseModel model) {
        Intent intent = new Intent(MainActivity.this, Add_NotesActivity.class);
        intent.putExtra(NEXT_SCREEN, model);
        intent.putExtra("position", position);
        startActivityForResult(intent, 2);

        this.position = position;

    }
}