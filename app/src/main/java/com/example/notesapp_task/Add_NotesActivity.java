package com.example.notesapp_task;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.notesapp_task.ModelClass.ExpenseModel;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import yuku.ambilwarna.AmbilWarnaDialog;


public class Add_NotesActivity extends AppCompatActivity implements View.OnClickListener {

    TextInputEditText etTitle, etDescription;

    ExtendedFloatingActionButton btnSave;
    ExpenseModel expenseModel;
    View picColorButton;
    Context context;
    int mDefaultColor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notes);


        initViews();
        getValues();
    }

    private void getValues() {

        expenseModel = null;


        if (getIntent().hasExtra(MainActivity.NEXT_SCREEN)) {
            expenseModel = (ExpenseModel) getIntent().getSerializableExtra(MainActivity.NEXT_SCREEN);
        }
        if (expenseModel != null) {
            etTitle.setText(expenseModel.getTitleNotes());
            etDescription.setText(expenseModel.getDescriptionNotes());
        }
    }

    private void initViews() {
        etTitle = findViewById(R.id.etTitle);
        etDescription = findViewById(R.id.etDescription);
        btnSave = findViewById(R.id.btnSaveNotes);
        picColorButton=findViewById(R.id.pick_color_button);
        context=Add_NotesActivity.this;
        mDefaultColor =context.getColor(R.color.list_zero);



        btnSave.setOnClickListener(this);
        picColorButton.setOnClickListener(this);


    }

    private void addArraylist() {

        String addTitle = etTitle.getText().toString().trim();
        String addDescription = etDescription.getText().toString().trim();

        int position = getIntent().getIntExtra("position",-1);

        Intent i = new Intent();
        i.putExtra("position", position);
        i.putExtra("title", addTitle);
        i.putExtra("description", addDescription);
        i.putExtra("color",mDefaultColor);
        setResult(RESULT_OK, i);
        finish();

    }

    @Override
    public void onClick(View v) {


        switch (v.getId())
        {
            case R.id.btnSaveNotes:
            {
                addArraylist();
                break;
            }
            case R.id.pick_color_button:
            {
                openColorPickerDialogue();
            }
        }
    }

    private void openColorPickerDialogue()
    {
        final AmbilWarnaDialog colorPickerDialogue = new AmbilWarnaDialog(this, mDefaultColor,
                new AmbilWarnaDialog.OnAmbilWarnaListener() {
                    @Override
                    public void onCancel(AmbilWarnaDialog dialog) {

                    }

                    @Override
                    public void onOk(AmbilWarnaDialog dialog, int color) {

                        mDefaultColor = color;

                        picColorButton.setBackgroundColor(mDefaultColor);

                    }
                });
        colorPickerDialogue.show();
    }
}